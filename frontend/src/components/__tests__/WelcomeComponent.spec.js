import { mount } from "@vue/test-utils";
import { describe, test, expect } from "vitest";
import WelcomeComponent from "@/components/WelcomeComponent.vue";

describe('WelcomeView', () => {
    test('displays the register button', () => {
        const wrapper = mount(WelcomeComponent, {
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        })

        const registerButton = wrapper.find('.register-btn')

        expect(registerButton.exists()).toBe(true)
        expect(registerButton.text()).toBe('register')
    })

    test('displays the login button', () => {
        const wrapper = mount(WelcomeComponent, {
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        })

        const loginButton = wrapper.find('.login-btn')

        expect(loginButton.exists()).toBe(true)
        expect(loginButton.text()).toBe('login')
    })
})