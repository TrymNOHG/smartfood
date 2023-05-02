import { mount } from '@vue/test-utils'
import { describe, test, expect, } from "vitest";
import SearchInput from "@/components/searchFromApi/SearchInput.vue";

describe('BasicInput', () => {
    test('renders correctly', () => {
        const wrapper = mount(SearchInput, {
            props: {
                label: 'Name',
                modelValue: '',
                error: '',
            },
        })

        expect(wrapper.exists()).toBe(true)
        expect(wrapper.find('#receipt-btn').exists()).toBe(true)
        expect(wrapper.find('.errorMessage').exists()).toBe(false)
        expect(wrapper.find('#shadow').exists()).toBe(true)
    })

    test('emits receipt-upload event when the button is clicked', async () => {
        const wrapper = mount(SearchInput, {
            props: {
                label: 'Name',
                modelValue: '',
                error: '',
            },
        })

        await wrapper.find('#receipt-btn').trigger('click')

        expect(wrapper.emitted('receipt-upload')).toBeTruthy()
    })

    test('displays error message when error prop is passed', async () => {
        const wrapper = mount(SearchInput, {
            props: {
                label: 'Name',
                modelValue: '',
                error: 'Name is required',
            },
        })

        expect(wrapper.find('.errorMessage').text()).toBe('Name is required')
    })
})