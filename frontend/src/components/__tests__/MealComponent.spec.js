import { mount } from "@vue/test-utils";
import { describe, test, expect } from "vitest";
import Meal from "@/components/dinner/MealComponent.vue";
import { createTestingPinia } from "@pinia/testing";
import sinon from "sinon";

describe("Meal", () => {
    test("renders the component", () => {
        const spy = sinon.spy();
        const wrapper = mount(Meal, {
            global: {
                plugins: [createTestingPinia({ createSpy: spy })],
                mocks: {
                    $t: (msg) => msg, // Mock the translation function
                },
            },
            props: {
                meal: {
                    recipeName: "Test Meal",
                    description: "A test meal description.",
                    thumbnail: "test-image.jpg",
                    recipeParts: [],
                },
            },
        });

        expect(wrapper.exists()).toBe(true);
    });

    test("meal name should render", () => {
        const spy = sinon.spy();

        const meal = {
            recipeName: "Test Meal",
            description: "A test meal description.",
            thumbnail: "test-image.jpg",
            recipeParts: [],
        };
        const wrapper = mount(Meal, {
            global: {
                plugins: [createTestingPinia({ createSpy: spy })],
                mocks: {
                    $t: (msg) => msg, // Mock the translation function
                },
            },
            props: {
                meal: meal
            }
        });

        const name = wrapper.find("#item-name-h2");
        expect(name.text()).toContain(meal.recipeName);
    });

    test("meal description should render", async () => {
        const spy = sinon.spy();

        const meal = {
            recipeName: "Test Meal",
            description: "A very long test meal description. ".repeat(20),
            thumbnail: "test-image.jpg",
            recipeParts: [],
        };
        const wrapper = mount(Meal, {
            global: {
                plugins: [createTestingPinia({ createSpy: spy })],
                mocks: {
                    $t: (msg) => msg, // Mock the translation function
                },
            },
            props: {
                meal: meal,
            },
        });

        await wrapper.vm.$nextTick();

        const description = wrapper.vm.$refs.descriptionRef;
        expect(description.textContent).toContain("A very long test meal description.");
    });

    test("delete button should be displayed for super users", () => {
        const spy = sinon.spy();

        const meal = {
            recipeName: "Test Meal",
            description: "A test meal description.",
            thumbnail: "test-image.jpg",
            recipeParts: [],
        };
        const wrapper = mount(Meal, {
            global: {
                plugins: [createTestingPinia({ createSpy: spy })],
                mocks: {
                    $t: (msg) => msg, // Mock the translation function
                },
            },
            props: {
                meal: meal,
                isSuperUser: true,
            },
        });

        const deleteButton = wrapper.find(".delete-btn");
        expect(deleteButton.exists()).toBe(true);
    });

    test("accept button should be displayed for super users", () => {
        const spy = sinon.spy();

        const meal = {
            recipeName: "Test Meal",
            description: "A test meal description.",
            thumbnail: "test-image.jpg",
            recipeParts: [],
        };
        const wrapper = mount(Meal, {
            global: {
                plugins: [createTestingPinia({ createSpy: spy })],
                mocks: {
                    $t: (msg) => msg, // Mock the translation function
                },
            },
            props: {
                meal: meal,
                isSuperUser: true,
            },
        });

        const acceptButton = wrapper.find(".accept-btn");
        expect(acceptButton.exists()).toBe(true);
    });

    test("meal thumbnail image should render", () => {
        const spy = sinon.spy();

        const meal = {
            recipeName: "Test Meal",
            description: "A test meal description.",
            thumbnail: "test-image.jpg",
            recipeParts: [],
        };
        const wrapper = mount(Meal, {
            global: {
                plugins: [createTestingPinia({ createSpy: spy })],
                mocks: {
                    $t: (msg) => msg, // Mock the translation function
                },
            },
            props: {
                meal: meal,
            },
        });

        const image = wrapper.find("img");
        expect(image.attributes("src")).toBe(meal.thumbnail);
    });

    test("delete button should not be displayed for non-super users", () => {
        const spy = sinon.spy();

        const meal = {
            recipeName: "Test Meal",
            description: "A test meal description.",
            thumbnail: "test-image.jpg",
            recipeParts: [],
        };
        const wrapper = mount(Meal, {
            global: {
                plugins: [createTestingPinia({ createSpy: spy })],
                mocks: {
                    $t: (msg) => msg, // Mock the translation function
                },
            },
            props: {
                meal: meal,
                isSuperUser: false,
            },
        });

        const deleteButton = wrapper.find(".delete-btn");
        expect(deleteButton.exists()).toBe(false);
    });

    test("accept button should not be displayed for non-super users", () => {
        const spy = sinon.spy();

        const meal = {
            recipeName: "Test Meal",
            description: "A test meal description.",
            thumbnail: "test-image.jpg",
            recipeParts: [],
        };
        const wrapper = mount(Meal, {
            global: {
                plugins: [createTestingPinia({ createSpy: spy })],
                mocks: {
                    $t: (msg) => msg, // Mock the translation function
                },
            },
            props: {
                meal: meal,
                isSuperUser: false,
            },
        });

        const acceptButton = wrapper.find(".accept-btn");
        expect(acceptButton.exists()).toBe(false);
    });
});