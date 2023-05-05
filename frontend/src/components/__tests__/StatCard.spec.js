import { mount } from '@vue/test-utils'
import StatCard from "@/components/statistic/StatCard.vue";
import { describe, test, expect } from "vitest";
import {createI18n} from "vue-i18n";

describe('StatCard', () => {
    const i18n = createI18n({
        legacy: false,
        locale: 'EN', // Set the initial locale to 'en'
        messages: {
            EN: {
                daily_stat: "Today's statistics",
                card: {
                    name: "Food Waste",
                    dailyWaste: "According to Matvett, an average of {amount} of food per person is wasted daily, and an average cost of {cost} per day.",
                    todayWaste: "Today's waste was {amount} of food, and {cost}."
                },
            },
        },
    })

    test('renders the name prop', () => {

        const name = 'Total Sales'
        const wrapper = mount(StatCard, {
            props: {
                name: name,
                iconName: 'fa-chart-bar'
            },
            global: {
                plugins: [i18n],
            },
        })
        expect(wrapper.text()).toContain("Today's statistics")
    })

    test('renders the value prop', () => {
        const wrapper = mount(StatCard, {
            props: {
                name: 'Total Sales',
                iconName: 'fa-chart-bar',
                food: "123g",
                money: "kr"
            },
            global: {
                plugins: [i18n],
            },
        })
        expect(wrapper.text()).toContain("123g")
    })
})
