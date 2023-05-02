import { mount } from '@vue/test-utils';
import InputField from '@/components/basic-components/BasicInput.vue';
import { describe, test, expect, } from "vitest";


describe('InputField', () => {
    test('emits an input event when the value changes', async () => {
        const wrapper = mount(InputField)
        const input = wrapper.find('input')

        await input.setValue('new value')

        expect(wrapper.emitted('update:modelValue')).toBeTruthy()
        expect(wrapper.emitted('update:modelValue')[0]).toEqual(['new value'])
    })

    test('sets the input value to the modelValue prop', async () => {
        const wrapper = mount(InputField, {
            props: {
                modelValue: 'initial value'
            }
        })
        const input = wrapper.find('input')

        expect(input.element.value).toBe('initial value')

        await wrapper.setProps({
            modelValue: 'new value'
        })

        expect(input.element.value).toBe('new value')
    })

    test('sets the input placeholder to the label prop', () => {
        const wrapper = mount(InputField, {
            props: {
                label: 'Enter your name'
            }
        })
        const input = wrapper.find('input')

        expect(input.attributes('placeholder')).toBe('Enter your name')
    })

    test('displays the error message', async () => {
        const wrapper = mount(InputField, {
            props: {
                error: 'Invalid input'
            }
        })

        const errorMessage = wrapper.find('.errorMessage')

        expect(errorMessage.exists()).toBe(true)
        expect(errorMessage.text()).toBe('Invalid input')
    })

    test('error message not displayed', async () => {
        const wrapper = mount(InputField, {
            props: {
                error: null
            }
        })

        const errorMessage = wrapper.find('.errorMessage')

        expect(errorMessage.exists()).toBe(false)
    })
})
