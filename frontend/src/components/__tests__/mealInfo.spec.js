import { shallowMount } from '@vue/test-utils';
import { describe, test, expect, beforeEach } from "vitest";
import MealInfo from "@/components/mealDescription/mealInfo.vue";

describe('MealInfo.vue', () => {
    let wrapper;

    beforeEach(() => {
        wrapper = shallowMount(MealInfo, {
            props: {
                meal: {
                    servingSize: '2',
                    cookingTime: 1,
                    difficulty: 3,
                    author: 'Tomas Beranek',
                    allergens: [
                        { displayName: 'Gluten' },
                        { displayName: 'Dairy' },
                    ],
                },
            },
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        });
    });

    test('displays serving size', () => {
        expect(wrapper.find('.serving-size').text()).toContain('2');
    });

    test('displays cooking time in minutes', () => {
        expect(wrapper.find('.cooking-time').text()).toContain('Under 20 min');
    });

    test('displays difficulty level as emojis', () => {
        expect(wrapper.find('.difficulty-stars').text()).toBe('😐');
    });

    test('displays author name', () => {
        expect(wrapper.find('#meal-auther').text()).toContain('Tomas Beranek');
    });

    test('displays list of allergens', () => {
        expect(wrapper.findAll('li').length).toBe(2);
        expect(wrapper.findAll('li').at(0).text()).toBe('Gluten');
        expect(wrapper.findAll('li').at(1).text()).toBe('Dairy');
    });
});