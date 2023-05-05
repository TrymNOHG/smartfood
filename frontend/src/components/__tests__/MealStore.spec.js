import {beforeEach, describe, expect, test} from "vitest";
import {createPinia, setActivePinia} from "pinia";
import {useMealStore} from "@/store/store";

describe('Test meal store', () => {
    beforeEach(() => {
        setActivePinia(createPinia());
    });

    test('Initial store has an empty list', () => {
        const store = useMealStore();
        expect(store.all).toEqual([]);
    });


    test("setCurrentMeal action sets currentMeal state", () => {
        const meal = { id: 1, name: "Pasta", calories: 500 };
        const mealStore = useMealStore();

        mealStore.setCurrentMeal(meal);

        expect(mealStore.currentMeal).toEqual(meal);
    });

    test("getCurrentMeal getter returns currentMeal state", () => {
        const meal = { id: 1, name: "Pasta", calories: 500 };
        const mealStore = useMealStore();

        mealStore.setCurrentMeal(meal);

        expect(mealStore.getCurrentMeal).toEqual(meal);
    });
});