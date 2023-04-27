import { mount } from "@vue/test-utils";
import { describe, test, expect } from "vitest";
import WelcomeComponent from "@/components/WelcomeComponent.vue";
import { router } from "@/router/router"
import {createRouter, createWebHistory} from "vue-router";
import { RouterLinkStub } from "@vue/test-utils";


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

    test('test router', async () => {
        const wrapper = mount(WelcomeComponent, {
            global: {
                stubs: {
                    RouterLink: RouterLinkStub,
                },
                mocks: {
                    $t: (msg) => msg,
                },
            },
        });

        const registerButton = wrapper.getComponent(RouterLinkStub, { selector: '.register-btn' });
        const loginButton = wrapper.getComponent(RouterLinkStub, { selector: '.login-btn' });

        expect(registerButton.props().to).toBe('/register');
        expect(loginButton.props().to).toBe('/register');
    });
})