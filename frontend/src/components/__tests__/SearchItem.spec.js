import { mount } from '@vue/test-utils';
import { describe, test, expect, } from "vitest";
import SearchItem from "@/components/searchFromApi/SearchItem.vue";

describe('SearchItem', () => {
    const image = 'image_url';
    const text = 'Item name';
    const price = 99.99;
    const store = 'Store name';

    test('renders the component with correct props', () => {
        const wrapper = mount(SearchItem, {
            props: {
                image,
                text,
                price,
                store,
            },
        });

        expect(wrapper.find('#search-image').attributes('src')).toBe(image);
        expect(wrapper.find('#text').text()).toBe(text);
        expect(wrapper.find('#store').text()).toBe(store);
        expect(wrapper.find('#price').text()).toBe(`${price} KR`);
        expect(wrapper.find('#cart-icon').exists()).toBe(true);
    });
});






