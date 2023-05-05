import { describe, test, expect, beforeEach, afterEach, beforeAll, afterAll } from 'vitest';
import { setActivePinia, createPinia } from 'pinia';
import { rest } from 'msw';
import { setupServer } from 'msw/node';
import { useItemStore } from '@/store/store';
import { getItemsFromFridge } from '@/services/ItemService'; // Import the service function used in the fetchItemsFromFridgeById action
import { setMockToken } from '@/features/SessionToken';

const server = setupServer(
    rest.get('http://localhost:8089/api/item/fridge/get?fridgeId=test_fridge_id', (req, res, ctx) => {
        const fridgeId = req.url.searchParams.get('fridgeId');
        if (fridgeId === 'test_fridge_id') {
            return res(ctx.json([
                { id: 1, name: 'Apple', amount: 5 },
                { id: 2, name: 'Orange', amount: 3 },
            ]));
        }
    }),
);

describe('Test item store', () => {
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

    test('Initial store has empty allItems array and null currentItem', () => {
        const store = useItemStore();
        expect(store.allItems).toEqual([]);
        expect(store.currentItem).toBeNull();
    });

    test('getCurrentItem getter works correctly', () => {
        const store = useItemStore();
        const testItem = { id: 1, name: 'Apple', amount: 5 };
        store.setCurrentItem(testItem);
        expect(store.getCurrentItem).toEqual(testItem);
    });

    test('fetchItemsFromFridgeById action works correctly', async () => {
        const store = useItemStore();
        const fridgeId = 'test_fridge_id';
        await store.fetchItemsFromFridgeById(fridgeId);

        const expected = [
            { id: 1, name: 'Apple', amount: 5 },
            { id: 2, name: 'Orange', amount: 3 },
        ];

        expect(store.allItems).toEqual(expected);
    });

    test('setCurrentItem action works correctly', () => {
        const store = useItemStore();
        const testItem = { id: 1, name: 'Apple', amount: 5 };
        store.setCurrentItem(testItem);
        expect(store.currentItem).toEqual(testItem);
    });

    // Other tests involving API calls can't be performed without mocking.
});