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

    test('emits an "update-item" event when the button is clicked', () => {
        const item = {
            expirationDate: '2023-05-01',
            image: 'test',
            name: 'test',
            price: '10',
            purchaseDate: '2023-04-27',
            quantity: 2,
            store: 'test',
            amount: 10,
            unit: '%'
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

        const updateButton = wrapper.findAll('.basic-button').filter((button) => {
            return button.text() === wrapper.vm.$t('update_item');
        })[0];

        updateButton.trigger('click');

        expect(wrapper.emitted('update-item')).toBeTruthy();
        expect(wrapper.emitted('update-item')[0][0]).toEqual(item);
        expect(wrapper.emitted('update-item')[0][1]).toBe(wrapper.vm.sliderValue);
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

        const deleteButton = wrapper.findAll('.basic-button').filter((button) => {
            return button.text() === wrapper.vm.$t('delete_item');
        })[0];

        deleteButton.trigger('click');

        expect(wrapper.emitted('delete-item')).toBeTruthy();
        expect(wrapper.emitted('delete-item')[0][0]).toEqual(item);
    });


    test('updates the slider value when the user interacts with it', async () => {
        const wrapper = mount(ItemDelete, {
            props: {
                item: {
                    expirationDate: '2023-05-01',
                    image: 'test',
                    name: 'test',
                    price: '10',
                    purchaseDate: '2023-04-27',
                    quantity: 2,
                    store: 'test',
                    amount: 10,
                    unit: '%'
                }
            },
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        });

        expect(wrapper.vm.sliderValue).toBe(5);
        expect(wrapper.find('#rangeValue').text()).toBe('5 %');

        const input = wrapper.find('input[type="range"]');
        input.setValue(7);
        await wrapper.vm.$nextTick();

        expect(wrapper.vm.sliderValue).toBe('7');
        expect(wrapper.find('#rangeValue').text()).toBe('7 %');
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

    test('updates the slider value when the user interacts with it', async () => {
        const wrapper = mount(ItemDelete, {
            props: {
                item: {
                    expirationDate: '2023-05-01',
                    image: 'test',
                    name: 'test',
                    price: '10',
                    purchaseDate: '2023-04-27',
                    quantity: 2,
                    store: 'test',
                    amount: 10,
                    unit: '%'
                }
            },
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        });

        expect(wrapper.vm.sliderValue).toBe(5);
        expect(wrapper.find('#rangeValue').text()).toBe('5 %');

        const input = wrapper.find('input[type="range"]');
        input.setValue(7);
        await wrapper.vm.$nextTick();

        expect(wrapper.vm.sliderValue).toBe('7');
        expect(wrapper.find('#rangeValue').text()).toBe('7 %');
    });
});