import { mount } from '@vue/test-utils'
import LanguageComponent from "@/components/basic-components/language-component.vue";
import { describe, test, expect } from "vitest";
import { createI18n } from 'vue-i18n'

describe('LanguageSelector', () => {
    test('changes the language when the user selects a new language', async () => {
        const i18n = createI18n({
            legacy: false,
            locale: 'EN', // Set the initial locale to 'en'
            messages: {
                EN: {
                    chosen_language: 'EN',
                },
                NO: {
                    chosen_language: 'NO',
                },
            },
        })

        const wrapper = mount(LanguageComponent, {
            global: {
                plugins: [i18n],
            },
        })

        // Select the Norwegian language
        const select = wrapper.find('select')
        await select.setValue('NO')

        // Check that the language has been updated
        expect(i18n.global.locale.value).toBe('NO')

        // Check that the UI has been updated
        const languageLabel = wrapper.find('p')
        expect(languageLabel.text()).include('NO')
    })
});
