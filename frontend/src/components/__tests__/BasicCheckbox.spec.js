import { mount } from '@vue/test-utils'
import { describe, test, expect } from "vitest";
import Checkbox from "@/components/basic-components/BasicCheckbox.vue";

describe('Checkbox', () => {
    test('emits update:modelValue when checkbox is clicked', async () => {
        const wrapper = mount(Checkbox, {
            props: {
                label: 'My checkbox',
                modelValue: false,
                error: '',
                item: null,
            },
        })

        // Check the checkbox
        const checkbox = wrapper.find('input')
        await checkbox.setChecked()

        // Check that the update:modelValue event was emitted with the correct value
        expect(wrapper.emitted('update:modelValue')).toHaveLength(1)
        expect(wrapper.emitted('update:modelValue')[0][0].isChecked).toBe(true)
    })

    test('displays error message when error prop is set', async () => {
        const wrapper = mount(Checkbox, {
            props: {
                label: 'My checkbox',
                modelValue: false,
                error: 'This field is required',
                item: null,
            },
        })

        // Check that the error message is displayed
        const errorMessage = wrapper.find('.errorMessage')
        expect(errorMessage.exists()).toBe(true)
        expect(errorMessage.text()).toBe('This field is required')
    })

    test('does not display error message when error prop is not set', async () => {
        const wrapper = mount(Checkbox, {
            props: {
                label: 'My checkbox',
                modelValue: false,
                error: '',
                item: null,
            },
        })

        // Check that the error message is not displayed
        const errorMessage = wrapper.find('.errorMessage')
        expect(errorMessage.exists()).toBe(false)
    })
})
