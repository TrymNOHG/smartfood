import { mount } from '@vue/test-utils';
import CartControl from '@/components/shoppingcart/CartControl.vue';
import { describe, test, expect } from "vitest";


const factory = (props = {}) => {
    return mount(CartControl, {
        propsData: {
            ...props,
        },
        global: {
            mocks: {
                $t: (msg) => msg, // Mock the $t function to return the message key
            },
        },
    });
};

describe('CartControl.vue', () => {
    test('renders all control buttons', () => {
        const wrapper = factory();

        expect(wrapper.find('.control-buttons').exists()).toBe(true);
        expect(wrapper.findAll('.control-buttons').length).toBe(3);
    });

    test('emits the check-all event when the mark all button is clicked', async () => {
        const wrapper = factory();

        await wrapper.find('.control-buttons:nth-child(1)').trigger('click');
        expect(wrapper.emitted('check-all')).toBeTruthy();
    });

    test('emits the buy event when the buy button is clicked', async () => {
        const wrapper = factory();

        await wrapper.find('.control-buttons:nth-child(2)').trigger('click');
        expect(wrapper.emitted('buy')).toBeTruthy();
    });

    test('emits the delete event when the delete button is clicked', async () => {
        const wrapper = factory();

        await wrapper.find('.control-buttons:nth-child(3)').trigger('click');
        expect(wrapper.emitted('delete')).toBeTruthy();
    });
});