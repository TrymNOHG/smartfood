import { describe, test, expect, beforeEach, afterEach, beforeAll, afterAll } from 'vitest';
import { setActivePinia, createPinia } from 'pinia';
import { rest } from 'msw';
import { setupServer } from 'msw/node';
import { useItemStore } from '@/store/store';
import { getItemsFromFridge } from '@/services/ItemService'; // Import the service function used in the fetchItemsFromFridgeById action
import { setMockToken } from '@/features/SessionToken';
import {addItemToFridge} from "../../services/ItemService";

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
    rest.get('http://localhost:8089/api/item/fridge/get', (req, res, ctx) => {
        const fridgeId = req.url.searchParams.get('fridgeId');
        if (fridgeId === 'test_fridge_id') {
            return res(ctx.json([
                { id: 1, name: 'Apple', amount: 5 },
                { id: 2, name: 'Orange', amount: 3 },
            ]));
        } else {
            return res(ctx.status(400));
        }
    }),
    rest.delete('http://localhost:8089/api/item/fridge/delete', (req, res, ctx) => {
        const itemRemoveDTO = req.body;
        if (itemRemoveDTO.itemId === 1 && itemRemoveDTO.fridgeId === 'test_fridge_id') {
            return res(ctx.json({ success: true }));
        } else {
            return res(ctx.status(400));
        }
    }),
    rest.post('http://localhost:8089/api/item/fridge/add', (req, res, ctx) => {
        const fridgeId = req.url.searchParams.get('fridgeId');
        if (fridgeId === 'test_fridge_id') {
            return res(ctx.json({ success: true }));
        } else {
            return res(ctx.status(400), ctx.json({ error: 'Invalid fridge ID' }));
        }
    })

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

    test('addItemToFridge with valid parameters', async () => {
        const itemDTO = { name: 'Test Item', amount: 1 };
        const fridgeId = 'test_fridge_id';
        const result = await addItemToFridge(itemDTO, fridgeId);
        expect(result.status).toEqual(200);
        expect(result.data.success).toBe(true);
    });


    test('getItemsFromFridge with valid parameters', async () => {
        const fridgeId = 'test_fridge_id';
        const result = await getItemsFromFridge(fridgeId);
        expect(result.status).toEqual(200);
        expect(result.data).toEqual([
            { id: 1, name: 'Apple', amount: 5 },
            { id: 2, name: 'Orange', amount: 3 },
        ]);
    });

    // Other tests involving API calls can't be performed without mocking.
});