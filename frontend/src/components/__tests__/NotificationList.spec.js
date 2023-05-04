import { shallowMount } from '@vue/test-utils';
import { describe, test, expect } from "vitest";
import NotificationList from "@/components/basic-components/NotificationList.vue";

describe('NotificationList', () => {
    test('renders notification information correctly', () => {
        const notification = {
            notificationId: 1,
            itemName: 'Milk',
            expirationDate: '2023-05-08T16:53:20.264134',
            isRead: false,
            fridgeId: 1
        }
        const wrapper = shallowMount(NotificationList, {
            props: {
                notification,
                userStatus: true
            },
            global: {
                mocks: {
                    $t: (msg) => msg
                },
            }
        })
        expect(wrapper.find('.notification-title').text()).toBe('Milk soon_expire')
        expect(wrapper.find('.notification-date').text()).toBe('expire_date: 8. mai 2023')
    })
})
