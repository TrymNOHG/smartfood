import { shallowMount } from '@vue/test-utils';
import {describe, expect, test} from "vitest";
import ItemInfo from "@/components/itemDescription/itemInfo.vue";

describe('ItemInfo', () => {
    const item = {
        purchaseDate: '2022-04-26',
        expirationDate: '2022-05-26',
        price: '50',
        amount: 1
    };

    test('renders purchase date', () => {
        const wrapper = shallowMount(ItemInfo, {
            propsData: { item },
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        });
        expect(wrapper.find('.text h3:first-of-type').text()).toMatch('buy_date: 26. april 2022');
    });

    test('renders expiration date', () => {
        const wrapper = shallowMount(ItemInfo, {
            propsData: { item },
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        });
        expect(wrapper.find('.text h3:nth-of-type(2)').text()).toMatch('expire_date: 26. mai 2022');
    });

    test('renders price', () => {
        const wrapper = shallowMount(ItemInfo, {
            propsData: { item },
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        });
        expect(wrapper.find('.text h3:nth-of-type(3)').text()).toMatch('price: 50kr');
    });

    test('renders amount', () => {
        const wrapper = shallowMount(ItemInfo, {
            propsData: { item },
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        });
        expect(wrapper.find('.text h3:last-of-type').text()).toMatch('Amount: 1.0');
    });
});