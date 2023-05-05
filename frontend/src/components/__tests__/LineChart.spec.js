import { mount } from "@vue/test-utils";
import LineChart from "@/components/statistic/LineChart.vue";
import { describe, test, expect } from "vitest";

describe("LineChart", () => {
    test("renders the component", () => {
        const wrapper = mount(LineChart, {
            propsData: {
                data: {
                    labels: ["January", "February", "March"],
                    values: [40, 20, 100],
                },
            },
        });

        expect(wrapper.exists()).toBe(true);
    });

    test("renders the chart with correct data", () => {
        const wrapper = mount(LineChart, {
            propsData: {
                data: {
                    labels: ["January", "February", "March"],
                    values: [40, 20, 100],
                },
            },
        });
        const chartData = wrapper.vm.chartData;

        expect(chartData.labels).toEqual(["January", "February", "March"]);
        expect(chartData.datasets[0].label).toBe("Dataset 1");
        expect(chartData.datasets[0].data).toEqual([40, 20, 100]);
        expect(chartData.datasets[0].backgroundColor).toBe("#31c48d");
    });

    test("renders the chart legend correctly", () => {
        const wrapper = mount(LineChart, {
            propsData: {
                data: {
                    labels: ["January", "February", "March"],
                    values: [40, 20, 100],
                },
                ymin: 0,
                ymax: 100,
            },
        });
        const legend = wrapper.find(".chart-legend");

        expect(legend.exists()).toBe(true);
        expect(legend.find("li").text()).toContain("Dataset 1");
    });
});