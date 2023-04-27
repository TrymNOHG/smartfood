import { shallowMount } from '@vue/test-utils';
import {describe, expect, test} from "vitest";
import ItemHeader from "@/components/itemDescription/itemHeader.vue";

describe('ItemHeader', () => {
    test('renders item name', () => {
        const item = {
            name: 'Test Item',
            image: 'test-image.jpg',
        };
        const wrapper = shallowMount(ItemHeader, {
            propsData: { item },
        });
        expect(wrapper.find('.item-name').text()).toMatch(item.name);
    });

    test('renders item image', () => {
        const item = {
            name: 'Test Item',
            image: 'test-image.jpg',
        };
        const wrapper = shallowMount(ItemHeader, {
            propsData: { item },
        });
        expect(wrapper.find('.item-image img').attributes('src')).toMatch(item.image);
    });
});