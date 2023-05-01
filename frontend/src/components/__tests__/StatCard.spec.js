import { shallowMount } from '@vue/test-utils';
import {describe, expect, test} from "vitest";
import StatCard from "@/components/statistic/StatCard.vue";

describe('StatCard.vue', () => {
    test('renders the wrapper', () => {
        const wrapper = shallowMount(StatCard, {
            props: {
                value: 100,
                name: 'Total Users',
                iconName: 'fa-users',
            },
        });

        expect(wrapper.exists()).toBe(true);
    });


    test('renders the correct name', () => {
        const wrapper = shallowMount(StatCard, {
            props: {
                value: 100,
                name: 'Total Users',
                iconName: 'fa-users',
            },
        });

        expect(wrapper.find('.description').text()).toBe('Total Users');
    });

    test('renders the correct icon', () => {
        const wrapper = shallowMount(StatCard, {
            props: {
                value: 100,
                name: 'Total Users',
                iconName: 'fa-users',
            },
        });

        expect(wrapper.props().iconName).toBe('fa-users');
    });

    test('renders the correct value', () => {
        const wrapper = shallowMount(StatCard, {
            props: {
                value: 100,
                name: 'Total Users',
                iconName: 'fa-users',
            },
        });

        expect(wrapper.find('.stats').text()).toBe('100');
    });
});
