import { mount } from '@vue/test-utils';
import {describe, expect, test} from "vitest";
import Instructions from '@/components/mealDescription/Instructions.vue';

describe('Instructions.vue', () => {
    const instructions = [
        {
            instruction: 'Add **flour** and **water** to the bowl.',
            imageLink: null,
        },
        {
            instruction: 'Mix the ingredients until the dough is formed.',
            imageLink: 'https://example.com/dough.jpg',
        },
        {
            instruction: 'Cover the bowl with a damp cloth and let it rest for **30 minutes**.',
            imageLink: null,
        },
    ]

    test('renders a list of instructions', () => {
        const wrapper = mount(Instructions, {
            props: {
                instructions,
            },
            global: {
                mocks: {
                    $t: (msg) => msg,
                },
            },
        })
        const listItems = wrapper.findAll('div > div > h4')
        expect(listItems.length).toBe(instructions.length)
    })

    test('renders bold text in instructions', () => {
        const wrapper = mount(Instructions, {
            props: {
                instructions,
            },
            global: {
                mocks: {
                    $t: (msg) => msg,
                },
            },
        })

        const boldText = wrapper.findAll('.bold-text')
        expect(boldText.length).toBe(3)
        expect(boldText[0].text()).toContain('flour')
        expect(boldText[1].text()).toContain('water')
    })

    test('renders images with appropriate alt text', () => {
        const wrapper = mount(Instructions, {
            props: {
                instructions,
            },
            global: {
                mocks: {
                    $t: (msg) => msg,
                },
            },
        })
        const images = wrapper.findAll('div > div > img')
        expect(images.length).toBe(1)
        expect(images[0].attributes('alt')).toBe('step 2')
    })
})