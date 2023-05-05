import { shallowMount } from '@vue/test-utils';
import BasicSelect from "@/components/basic-components/BasicSelect.vue";
import { describe, test, expect, beforeEach } from "vitest";

describe('MySelect', () => {
    const options = ['option1', 'option2', 'option3'];
    const label = 'Select an option';
    let wrapper;

    beforeEach(() => {
        wrapper = shallowMount(BasicSelect, {
            propsData: {
                options,
                label,
            },
            global: {
                mocks: {
                    $t: (msg) => msg, // Add this line to mock the $t function
                },
            },
        });
    });

    test('renders a label if a label prop is provided', () => {
        expect(wrapper.find('label').exists()).toBe(true);
        expect(wrapper.find('label').text()).toBe(label);
    });

    test('renders a select element with options', () => {
        const select = wrapper.find('select');

        expect(select.exists()).toBe(true);
        expect(select.findAll('option').length).toBe(options.length);
    });

    test('emits an event when an option is selected', async () => {
        const select = wrapper.find('select');
        const selectedOption = options[1];

        await select.setValue(selectedOption);

        expect(wrapper.emitted('update:modelValue')).toBeTruthy();
        expect(wrapper.emitted('update:modelValue')[0]).toEqual([selectedOption]);
    });
});
