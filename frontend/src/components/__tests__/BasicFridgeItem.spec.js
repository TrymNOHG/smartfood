import { mount } from "@vue/test-utils";
import { describe, test, expect } from "vitest";
import BasicFridgeItem from "@/components/SpecificFridge/BasicFridgeItem.vue";
import {createTestingPinia} from "@pinia/testing";
import sinon from "sinon";

describe('BasicFridgeItem', () => {
    test('renders the component', () => {
        const spy = sinon.spy()
        const wrapper = mount(BasicFridgeItem, {
            global: {
                plugins: [createTestingPinia({ createSpy: spy })],
                mocks: {
                    $t: (msg) => msg, // Mock the translation function
                },
            },
            props: {
                item: {
                    description: 'test',
                    expirationDate: '2023-05-01',
                    image: 'test',
                    name: 'test',
                    price: '10',
                    purchaseDate: '2023-04-27',
                    quantity: 2,
                    store: 'test'
                }
            }
        });


        expect(wrapper.exists()).toBe(true);
    });

    test('name should render', () => {
        const spy = sinon.spy()

        const item = {
            description: 'test',
            expirationDate: '2023-05-01',
            image: 'test',
            name: 'test',
            price: '10',
            purchaseDate: '2023-04-27',
            quantity: 2,
            store: 'test'
        };
        const wrapper = mount(BasicFridgeItem, {
            global: {
                plugins: [createTestingPinia({createSpy: spy})],
                mocks: {
                    $t: (msg) => msg, // Mock the translation function
                },
            },
            props: {
                item: item
            }
        });

        const name = wrapper.find('#item-name-h2')
        expect(name.text()).toContain(item.name);
    });

    test('purchase date should render', () => {
        const spy = sinon.spy()

        const item = {
            description: 'test',
            expirationDate: '2023-05-01',
            image: 'test',
            name: 'test',
            price: '10',
            purchaseDate: '2023-04-27',
            quantity: 2,
            store: 'test'
        };
        const wrapper = mount(BasicFridgeItem, {
            global: {
                plugins: [createTestingPinia({createSpy: spy})],
                mocks: {
                    $t: (msg) => msg, // Mock the translation function
                },
            },
            props: {
                item: item
            }
        });

        const name = wrapper.find('#item-expiration-date')
        expect(name.text()).include("1. mai 2023");
    });

    test('expiration date should render', () => {
        const spy = sinon.spy()

        const item = {
            description: 'test',
            expirationDate: '2023-05-01',
            image: 'test',
            name: 'test',
            price: '10',
            purchaseDate: '2023-04-27',
            quantity: 2,
            store: 'test'
        };
        const wrapper = mount(BasicFridgeItem, {
            global: {
                plugins: [createTestingPinia({createSpy: spy})],
                mocks: {
                    $t: (msg) => msg, // Mock the translation function
                },
            },
            props: {
                item: item
            }
        });

        const name = wrapper.find('#item-purchase-date')
        expect(name.text()).include("27. april 2023");
    });

    test('price date should render', () => {
        const spy = sinon.spy()

        const item = {
            description: 'test',
            expirationDate: '2023-05-01',
            image: 'test',
            name: 'test',
            price: '10',
            purchaseDate: '2023-04-27',
            quantity: 2,
            store: 'test'
        };
        const wrapper = mount(BasicFridgeItem, {
            global: {
                plugins: [createTestingPinia({createSpy: spy})],
                mocks: {
                    $t: (msg) => msg, // Mock the translation function
                },
            },
            props: {
                item: item
            }
        });

        const name = wrapper.find('#item-price')
        expect(name.text()).include("10");
    });

    test('quantity date should render', () => {
        const spy = sinon.spy()

        const item = {
            description: 'test',
            expirationDate: '2023-05-01',
            image: 'test',
            name: 'test',
            price: '10',
            purchaseDate: '2023-04-27',
            quantity: 2,
            store: 'test'
        };
        const wrapper = mount(BasicFridgeItem, {
            global: {
                plugins: [createTestingPinia({createSpy: spy})],
                mocks: {
                    $t: (msg) => msg, // Mock the translation function
                },
            },
            props: {
                item: item
            }
        });

        const name = wrapper.find('#item-quantity')
        expect(name.text()).include("2");
    });
});