import { shallowMount } from '@vue/test-utils';
import { describe, test, expect } from "vitest";
import NotificationList from "@/components/basic-components/NotificationList.vue";

describe('NotificationList', () => {
    test('renders notification name', () => {
        const notification = {
            name: 'Test Notification',
            expirationDate: '2023-05-31',
            border: false
        }
        const wrapper = shallowMount(NotificationList, {
            propsData: {notification},
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        })
        expect(wrapper.text()).toContain(notification.name)
    })


    test('renders notification expiration date', () => {
        const notification = {
            name: 'Test Notification',
            expirationDate: '2023-05-31',
            border: false
        }
        const wrapper = shallowMount(NotificationList, {
            propsData: { notification },
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        })
        expect(wrapper.text()).toContain('31. mai 2023')
    })


    test('emits delete-notification event when delete icon is clicked', () => {
        const notification = {
            name: 'Test Notification',
            expirationDate: '2023-05-31',
            border: false
        }
        const userStatus = true

        const wrapper = shallowMount(NotificationList, {
            propsData: { notification, userStatus },
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        })



        wrapper.find('.delete-icon').trigger('click')
        expect(wrapper.emitted('delete-notification')).toBeTruthy()
    })

    test('emits remove-border event when check icon is clicked', () => {
        const notification = {
            name: 'Test Notification',
            expirationDate: '2023-05-31',
            border: false
        }
        const userStatus = true

        const wrapper = shallowMount(NotificationList, {
            propsData: { notification, userStatus },
            global: {
                mocks: {
                    $t: (msg) => msg
                }
            }
        })
        wrapper.find('.check-icon').trigger('click')
        expect(wrapper.emitted('remove-border')).toBeTruthy()
    })
})
