import { mount } from '@vue/test-utils';
import DinnerView from '@/views/DinnerView.vue';
import DinnerSuggestion from '@/components/dinner/DinnerSuggestionComponent.vue';
import WeekMenu from '@/components/dinner/WeekMenuComponent.vue';
import { describe, test, expect, beforeEach } from "vitest";
import sinon from "sinon";
import {createTestingPinia} from "@pinia/testing";


describe('DinnerView', () => {
    const spy = sinon.spy()

    test('renders the dinner suggestion component when "suggestion" tab is selected', async () => {
        const wrapper = mount(DinnerView, {
            global: {
                mocks: {
                    $t: (msg) => msg
                },
                plugins: [createTestingPinia({ createSpy: spy })]
            }
        });

        wrapper.vm.selectedTab = 'suggestion';
        await wrapper.vm.$nextTick();

        expect(wrapper.findComponent(DinnerSuggestion).exists()).toBe(true);
    });

    test('renders the week menu component when "weekMenu" tab is selected', async () => {
        const wrapper = mount(DinnerView, {
            global: {
                mocks: {
                    $t: (msg) => msg
                },
                plugins: [createTestingPinia({ createSpy: spy })]
            }
        });

        wrapper.vm.selectedTab = 'weekMenu';
        await wrapper.vm.$nextTick();

        expect(wrapper.findComponent(WeekMenu).exists()).toBe(true);
    });

    test('shows information on click of information button', async () => {
        const wrapper = mount(DinnerView, {
            global: {
                mocks: {
                    $t: (msg) => msg
                },
                plugins: [createTestingPinia({ createSpy: spy })]
            }
        });

        const informationButton = wrapper.find('#info-picture');
        informationButton.trigger('click');

        // TODO: Add assertions for expected behavior when information button is clicked
    });
});
