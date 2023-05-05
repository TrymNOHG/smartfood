import { mount } from '@vue/test-utils';
import Item from '@/components/shoppingcart/CartItem.vue';
import { describe, test, expect } from "vitest";

const factory = (propsData) => {
    return mount(Item, {
        propsData: {
            image: 'test-image.jpg',
            item: {
                id: 1,
                isChecked: false,
            },
            name: 'Test Item',
            quantity: 1,
            ...propsData, // Ensure you are passing the additional propsData
        },
        global: {
            mocks: {
                $t: (msg) => msg
            }
        }
    });
};

describe('Item.vue', () => {
    test('renders the item with the correct image and name', () => {
        const image = 'https://example.com/image.jpg';
        const name = 'Item Name';
        const quantity = 5;
        const isSuperUser = true;
        const item = {
            id: 1,
            isChecked: false,
        };

        const wrapper = factory({ image, name, quantity, isSuperUser, item });

        expect(wrapper.find('.img-container img').attributes('src')).toBe(image);
        expect(wrapper.find('.description span').text()).toBe(name);
    });

    test('renders quantity input with correct value', () => {
        const quantity = 5;
        const wrapper = factory({ quantity });

        expect(wrapper.find('.quantity .number-input').element.value).toBe(String(quantity));
    });

    test('renders the correct buttons based on isSuperUser true prop', () => {
        const isSuperUser = true;
        const item = { isChecked: true };
        const wrapper = factory({ isSuperUser, item });

        expect(wrapper.find('.minus-btn').exists()).toBe(true);
        expect(wrapper.find('.plus-btn').exists()).toBe(true);
        expect(wrapper.find('.btn-cart').exists()).toBe(true);
        expect(wrapper.find('.btn-checkbox').exists()).toBe(true);
    });

    test('renders the correct buttons based on isSuperUser false prop', () => {
        const isSuperUser = false;
        const item = { isChecked: false };
        const wrapper = factory({ isSuperUser, item });

        expect(wrapper.find('.minus-btn').exists()).toBe(false);
        expect(wrapper.find('.plus-btn').exists()).toBe(false);
        expect(wrapper.find('.btn-cart').exists()).toBe(false);
        expect(wrapper.find('.btn-checkbox').exists()).toBe(false);
    });


    test('emits the add event when plus button is clicked', async () => {
        const wrapper = factory({ isSuperUser: true });

        await wrapper.find('.plus-btn').trigger('click');

        expect(wrapper.emitted().add).toBeTruthy();
    });

    test('emits the subtract event when minus button is clicked', async () => {
        const wrapper = factory({ isSuperUser: true } );

        await wrapper.find('.minus-btn').trigger('click');

        expect(wrapper.emitted().subtract).toBeTruthy();
    });

    test('emits the delete-item event when trash button is clicked', async () => {
        const wrapper = factory({ isSuperUser: true });

        await wrapper.find('.btn-trash').trigger('click');

        expect(wrapper.emitted()['delete-item']).toBeTruthy();
    });

    test('emits the handle-checked event when checkbox state changes', async () => {
        const item = { id: 1, isChecked: false };
        const wrapper = factory({ item, isSuperUser: true }  );

        await wrapper.find('.btn-checkbox').setChecked(true);


        expect(wrapper.emitted()['handle-checked']).toBeTruthy();
        expect(wrapper.emitted()['handle-checked'][0][1]).toBe(true);
    });

    test('emits the handle-buy event when cart button is clicked', async () => {
        const wrapper = factory({ isSuperUser: true }); // Pass isSuperUser as true

        await wrapper.find('.btn-cart').trigger('click');

        expect(wrapper.emitted()['handle-buy']).toBeTruthy();
    });

    // Add more tests for different scenarios
});