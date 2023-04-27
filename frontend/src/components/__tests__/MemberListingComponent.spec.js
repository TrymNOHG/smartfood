import { mount } from '@vue/test-utils';
import swal from 'sweetalert2';
import { describe, test, expect } from "vitest";

import MemberList from '@/components/FridgeList/MemberListingComponent.vue';
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

const factory = (props = {}) => {
    return mount(MemberList, {
        props,
        global: {
            components: { FontAwesomeIcon },
            mocks: {
                $t: (msg) => msg, // Mock the translation function
            },
        },
    });
};

describe('MemberList.vue', () => {
    test('renders a list of members', () => {
        const membersList = [
            { username: 'User1', isSuperUser: false, userId: 1 },
            { username: 'User2', isSuperUser: true, userId: 2 },
            { username: 'User3', isSuperUser: false, userId: 3 },
        ];

        const wrapper = factory({ membersList });

        const memberElements = wrapper.findAll('.member');
        expect(memberElements).toHaveLength(membersList.length);

        memberElements.forEach((element, index) => {
            expect(element.text()).toContain(membersList[index].username);
        });
    });

    // Add more tests for different scenarios
});