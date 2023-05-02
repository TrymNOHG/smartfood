import { shallowMount } from '@vue/test-utils';
import { describe, test, expect } from "vitest";
import mealHeader from "@/components/mealDescription/mealHeader.vue";

describe('MealHeader.vue', () => {
    const meal = {
        recipeName: 'Spaghetti Bolognese',
        thumbnail: 'https://example.com/images/spaghetti.jpg',
        description: 'A classic Italian dish with tomato sauce and minced meat.',
    };

    test('renders the meal name', () => {
        const wrapper = shallowMount(mealHeader, {
            propsData: { meal },
        });
        expect(wrapper.find('.meal-name').text()).toBe(meal.recipeName);
    });

    test('renders the meal description', () => {
        const wrapper = shallowMount(mealHeader, {
            propsData: { meal },
        });
        expect(wrapper.find('#description').text()).toBe(meal.description);
    });

    test('displays the meal thumbnail', () => {
        const wrapper = shallowMount(mealHeader, {
            propsData: { meal },
        });
        expect(wrapper.find('img').attributes('src')).toBe(meal.thumbnail);
        expect(wrapper.find('img').attributes('alt')).toBe('meal picture');
    });
});