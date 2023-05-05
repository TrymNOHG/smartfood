import { describe, test, expect, beforeEach, afterEach, beforeAll, afterAll } from 'vitest';import { setActivePinia, createPinia } from 'pinia';
import { rest } from 'msw';
import { setupServer } from 'msw/node';
import {useLoggedInStore, useStatStore} from "@/store/store";
import { setMockToken } from '@/features/SessionToken';




const server = setupServer(
    rest.get('http://localhost:8089/api/stat/get/user-stats/avg-thrown-per-day', (req, res, ctx) => {
        return res(ctx.json({ a: 10, b: 20 }));
    }),
    rest.get('http://localhost:8089/api/stat/get/user-stats/money-wasted-per-day', (req, res, ctx) => {
        return res(ctx.json({ c: 30, d: 40 }));
    }),
    rest.get('http://localhost:8089/api/stat/get/fridge-stats/avg-thrown-per-day/:fridgeId', (req, res, ctx) => {
        return res(ctx.json({ e: 50, f: 60 }));
    }),
    rest.get('http://localhost:8089/api/stat/get/fridge-stats/money-wasted-per-day/:fridgeId', (req, res, ctx) => {
        return res(ctx.json({ g: 70, h: 80 }));
    }),
);

describe('Test stat store', () => {
    beforeAll(() => {
        server.listen();
        setMockToken('fake_token');
    });

    beforeEach(() => {
        setActivePinia(createPinia());
    });

    afterEach(() => {
        server.resetHandlers();
    });

    afterAll(() => {
        server.close();
    });

    test('Initial store has empty percentageChart and moneyChart arrays', () => {
        const store = useStatStore();
        expect(store.percentageChart).toEqual([]);
        expect(store.moneyChart).toEqual([]);
    });

    test('getPercentageChart and getMoneyChart getters work correctly', () => {
        const store = useStatStore();
        store.percentageChart = { a: 10, b: 20 };
        store.moneyChart = { c: 30, d: 40 };

        const percentageChart = store.getPercentageChart;
        const moneyChart = store.getMoneyChart;

        expect(percentageChart.labels).toEqual(['a', 'b']);
        expect(percentageChart.values).toEqual([10, 20]);
        expect(moneyChart.labels).toEqual(['c', 'd']);
        expect(moneyChart.values).toEqual([30, 40]);
    });


    test('fetchUserStatsPercentage action works correctly', async () => {
        const store = useStatStore();
        await store.fetchUserStatsPercentage();
        const expected = [];
        expected['a'] = 10;
        expected['b'] = 20;
        expect(store.percentageChart).toEqual(expected);
    });

    test('fetchUserStatsMoney action works correctly', async () => {
        const store = useStatStore();
        await store.fetchUserStatsMoney();
        const expected = [];
        expected['c'] = 30;
        expected['d'] = 40;
        expect(store.moneyChart).toEqual(expected);
    });

    test('fetchFridgePercentage action works correctly', async () => {
        const store = useStatStore();
        const fridge = { fridgeId: 'test_fridge_id' };
        await store.fetchFridgePercentage(fridge);
        const expected = [];
        expected['e'] = 50;
        expected['f'] = 60;
        expect(store.percentageChart).toEqual(expected);
    });

    test('fetchFridgeMoney action works correctly', async () => {
        const store = useStatStore();
        const fridge = { fridgeId: 'test_fridge_id' };
        await store.fetchFridgeMoney(fridge);
        const expected = [];
        expected['g'] = 70;
        expected['h'] = 80;
        expect(store.moneyChart).toEqual(expected);
    });
    // Other tests involving API calls can't be performed without mocking.
});