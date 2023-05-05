import { mount } from "@vue/test-utils";
import RecipeParts from "@/components/mealDescription/RecipeParts.vue";
import { describe, test, expect } from "vitest";

const mocks = {
    $t: (key) => key,
};

describe("RecipeParts", () => {
    test("renders the component", () => {
        const wrapper = mount(RecipeParts, {
            propsData: {
                recipeParts: [],
            },
            global: {
                mocks,
            },
        });

        expect(wrapper.exists()).toBe(true);
    });

    test("renders the recipe parts and ingredients", () => {
        const wrapper = mount(RecipeParts, {
            propsData: {
                recipeParts: [
                    {
                        partName: "Part 1",
                        ingredients: [
                            {
                                itemId: 1,
                                name: "Ingredient 1",
                                quantity: 100,
                                unitOfMeasurement: "ml",
                                fridgeAmount: 120,
                                itemOriginalUnit: "ml",
                            },
                        ],
                    },
                ],
            },
            global: {
                mocks,
            },
        });

        const part = wrapper.find(".recipe-parts");
        expect(part.exists()).toBe(true);
        expect(part.find("h3").text()).toBe("Part 1");
        expect(part.find(".item-info").exists()).toBe(true);
        expect(part.find(".item-info h4").text()).toBe("Ingredient 1");
    });

    test("getItemQuantity works correctly", () => {
        const wrapper = mount(RecipeParts, {
            propsData: {
                recipeParts: [],
            },
            global: {
                mocks,
            },
        });

        const ingredient1 = {
            fridgeAmount: 120,
            itemOriginalUnit: "ml",
            unitOfMeasurement: "ml",
        };

        const ingredient2 = {
            fridgeAmount: 2,
            itemOriginalUnit: "pieces",
            unitOfMeasurement: "ml",
        };

        expect(wrapper.vm.getItemQuantity(ingredient1)).toBe(120);
        expect(wrapper.vm.getItemQuantity(ingredient2)).toBe(500);
    });

    test("renders item status correctly", () => {
        const wrapper = mount(RecipeParts, {
            propsData: {
                recipeParts: [
                    {
                        partName: "Part 1",
                        ingredients: [
                            {
                                itemId: 1,
                                name: "Ingredient 1",
                                quantity: 100,
                                unitOfMeasurement: "ml",
                                fridgeAmount: 120,
                                itemOriginalUnit: "ml",
                            },
                            {
                                itemId: 2,
                                name: "Ingredient 2",
                                quantity: 500,
                                unitOfMeasurement: "ml",
                                fridgeAmount: 200,
                                itemOriginalUnit: "ml",
                            },
                        ],
                    },
                ],
            },
            global: {
                mocks,
            },
        });

        const items = wrapper.findAll(".item-info");
        expect(items.length).toBe(2);
        expect(items[0].classes()).toContain("has-item");
        expect(items[1].classes()).toContain("no-item");
    });
});