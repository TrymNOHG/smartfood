import {mount} from '@vue/test-utils'
import {describe, expect, test} from "vitest";
import ItemDelete from "@/components/itemDescription/itemDelete.vue";
describe('ItemDelete', () => {
    test('renders correctly', () => {
        const wrapper = mount(ItemDelete, {
            props: {
                item: {
                    expirationDate: '2023-05-01',
                    image: 'test',
                    name: 'test',
                    price: '10',
                    purchaseDate: '2023-04-27',
                    quantity: 2,
                    store: 'test'
                }
            },
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        });

        expect(wrapper.find('.info-window').exists()).toBe(true);
        expect(wrapper.find('.slider').exists()).toBe(true);
        expect(wrapper.find('.basic-button').exists()).toBe(true);
    });

    test('emits a "delete-item" event when the button is clicked', () => {
        const item = {
            expirationDate: '2023-05-01',
            image: 'test',
            name: 'test',
            price: '10',
            purchaseDate: '2023-04-27',
            quantity: 2,
            store: 'test'
        };
        const wrapper = mount(ItemDelete, {
            props: {
                item: item
            },
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        });

        wrapper.find('.basic-button').trigger('click');

        expect(wrapper.emitted('delete-item')).toBeTruthy();
        expect(wrapper.emitted('delete-item')[0][0]).toEqual(item);
    });

    test('updates the slider value when the user interacts with it', () => {
        const wrapper = mount(ItemDelete, {
            props: {
                item: {
                    expirationDate: '2023-05-01',
                    image: 'test',
                    name: 'test',
                    price: '10',
                    purchaseDate: '2023-04-27',
                    quantity: 2,
                    store: 'test'
                }
            },
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        });

        expect(wrapper.vm.sliderValue).toBe(50);
        expect(wrapper.find('#rangeValue').text()).toBe('50%');
    });

    test('on render should ask how muc is left', () => {
        const wrapper = mount(ItemDelete, {
            props: {
                item: {
                    expirationDate: '2023-05-01',
                    image: 'test',
                    name: 'test',
                    price: '10',
                    purchaseDate: '2023-04-27',
                    quantity: 2,
                    store: 'test'
                }
            },
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        });

        const text = wrapper.find('#how-much-left')

        expect(text.exists()).toBe(true)
        expect(text.text()).toBe('how_much_left')

    });
});