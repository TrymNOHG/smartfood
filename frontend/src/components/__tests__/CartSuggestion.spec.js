import { mount } from '@vue/test-utils';
import { describe, test, expect } from "vitest";
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

import SuggestionItem from '@/components/shoppingcart/CartSuggestion.vue';

const factory = (propsData) => {
    return mount(SuggestionItem, {
        propsData: {
            image: 'test-image.jpg',
            item: {
                id: 1,
            },
            name: 'Test Item',
            quantity: 1,
            ...propsData,
        },
    });
};

describe('SuggestionItem.vue', () => {
    test('renders the item with the correct image and name', () => {
        const image = 'https://example.com/image.jpg';
        const name = 'Item Name';
        const quantity = 5;
        const isSuperUser = true;

        const wrapper = factory({ image, name, quantity, isSuperUser });

        expect(wrapper.find('.img-container img').attributes('src')).toBe(image);
        expect(wrapper.find('.description span').text()).toBe(name);
    });

    test('renders quantity input with correct value and disabled attribute', () => {
        const quantity = 5;
        const wrapper = factory({ quantity });

        expect(wrapper.find('.quantity input').element.value).toBe(String(quantity));
        expect(wrapper.find('.quantity input').element.disabled).toBe(true);});

    test('renders the correct icons based on isSuperUser true prop', () => {
        const isSuperUser = true;
        const wrapper = factory({ isSuperUser });

        expect(wrapper.find('.modify-check-mark.fa-solid.fa-check').exists()).toBe(true);
        expect(wrapper.find('.modify-check-mark.fa-solid.fa-trash').exists()).toBe(true);
    });

    test('renders the correct icons based on isSuperUser false prop', () => {
        const isSuperUser = false;
        const wrapper = factory({ isSuperUser });

        expect(wrapper.find('.modify-check-mark.fa-solid.fa-check').exists()).toBe(false);
        expect(wrapper.find('.modify-check-mark.fa-solid.fa-trash').exists()).toBe(true);
    });


    test('emits the accept-suggestion event when accept button is clicked', async () => {
        const wrapper = factory({ isSuperUser: true });

        await wrapper.find('.modify-check-mark.fa-check').trigger('click');

        expect(wrapper.emitted()['accept-suggestion']).toBeTruthy();
    });

    test('emits the delete-suggestion event when delete button is clicked', async () => {
        const wrapper = factory();

        await wrapper.find('.modify-check-mark.fa-trash').trigger('click');

        expect(wrapper.emitted()['delete-suggestion']).toBeTruthy();
    });

    // Add more tests for different scenarios
});