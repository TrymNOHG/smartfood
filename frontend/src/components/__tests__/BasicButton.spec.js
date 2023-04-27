import { shallowMount } from "@vue/test-utils";
import { describe, test, expect } from "vitest";
import BasicButton from "@/components/basic-components/BasicButton.vue";

describe("BasicButton.vue", () => {
    test("renders the button text", () => {
        const wrapper = shallowMount(BasicButton, {
            propsData: {
                buttonText: "Test Button",
            },
        });
        expect(wrapper.text()).toContain("Test Button");
    });

    test("uses the default button text when none is provided", () => {
        const wrapper = shallowMount(BasicButton);
        expect(wrapper.text()).toContain("Button");
    });

    test("emits a click event when the button is clicked", () => {
        const wrapper = shallowMount(BasicButton);
        wrapper.find("button").trigger("click");
        expect(wrapper.emitted().click).toBeTruthy();
    });
});
