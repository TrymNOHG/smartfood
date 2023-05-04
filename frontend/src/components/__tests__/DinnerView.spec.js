import { mount } from '@vue/test-utils';
import { describe, test, expect } from "vitest";
import DinnerView from '@/views/DinnerView.vue';
import DinnerSuggestion from '@/components/dinner/DinnerSuggestionComponent.vue';
import WeekMenu from '@/components/dinner/WeekMenuComponent.vue';
import InfoAndBell from "@/components/basic-components/InfoAndBell.vue";
import { createI18n } from "vue-i18n";
import { createTestingPinia } from "@pinia/testing";
import sinon from 'sinon';

describe("DinnerView", () => {
    const spy = sinon.spy();

    const i18n = createI18n({
        legacy: false,
        locale: "en",
        messages: {
            en: {},
        },
    });

    const mountWithPinia = (options = {}) => {
        const mockUseFridgeStore = () => ({
            getCurrentFridge: {
                fridgeId: "2",
            },
        });

        return mount(DinnerView, {
            global: {
                plugins: [createTestingPinia({ createSpy: () => spy }), i18n],
                mocks: {
                    useFridgeStore: mockUseFridgeStore,
                },
                ...options,
            },
        });
    };


    test('renders the DinnerSuggestion component when "tips" tab is selected', async () => {
        const wrapper = mountWithPinia();

        await wrapper.vm.$nextTick(() => {
            wrapper.vm.selectedTab = 'tips';
        });

        await wrapper.vm.$nextTick();

        expect(wrapper.findComponent(DinnerSuggestion).isVisible()).toBe(true);
    });

    test('renders the WeekMenu component when "weekMenu" tab is selected', async () => {
        const wrapper = mountWithPinia();

        await wrapper.vm.$nextTick(() => {
            wrapper.vm.selectedTab = 'weekMenu';
        });

        await wrapper.vm.$nextTick();

        expect(wrapper.findComponent(WeekMenu).isVisible()).toBe(true);
    });

});