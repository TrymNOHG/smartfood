import { mount } from "@vue/test-utils";
import {describe, expect, test} from "vitest";
import ListingComponent from "@/components/FridgeList/listingComponent.vue";
import sinon from "sinon";
import {createTestingPinia} from "@pinia/testing";

describe("List", () => {
    const spy = sinon.spy()

    test("renders a list of fridges with their names", () => {
        const fridgeList = [
            { fridgeId: 1, fridgeName: "Fridge A" },
            { fridgeId: 2, fridgeName: "Fridge B" },
        ];
        const wrapper = mount(ListingComponent, {
            propsData: { fridgeList },
            global: {
                plugins: [createTestingPinia({ createSpy: spy })]
            },
        });
        const itemTexts = wrapper.findAll(".item-text");
        expect(itemTexts).toHaveLength(2);
        expect(itemTexts.at(0).text()).toBe("Fridge A");
        expect(itemTexts.at(1).text()).toBe("Fridge B");
    });
});