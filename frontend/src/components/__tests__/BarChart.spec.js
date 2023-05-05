import { mount } from "@vue/test-utils";
import BarChart from "@/components/statistic/BarChart.vue";
import { describe, test, expect } from "vitest";

describe("BarChart", () => {
    test("renders the component", () => {
        const wrapper = mount(BarChart);

        expect(wrapper.exists()).toBe(true);
    });

    test("renders the chart with correct data", () => {
        const wrapper = mount(BarChart);
        const chartData = wrapper.vm.chartData;

        expect(chartData.labels).toEqual(["January", "February", "March"]);
        expect(chartData.datasets[0].label).toBe("Dataset 1");
        expect(chartData.datasets[0].data).toEqual([40, 20, 100]);
        expect(chartData.datasets[0].backgroundColor).toBe("#31c48d");
    });

    test("renders the chart with correct options", () => {
        const wrapper = mount(BarChart);
        const chartOptions = wrapper.vm.chartOptions;

        expect(chartOptions.responsive).toBe(true);
        expect(chartOptions.plugins.legend.display).toBe(false);
        expect(chartOptions.scales.y.ticks.beginAtZero).toBe(true);
        expect(chartOptions.scales.y.grid.color).toBe("rgba(0, 0, 0, 0.05)");
        expect(chartOptions.scales.y.grid.drawBorder).toBe(false);
        expect(chartOptions.scales.x.grid.color).toBe("rgba(0, 0, 0, 0.05)");
        expect(chartOptions.scales.x.grid.drawBorder).toBe(false);
    });

    test("renders the chart legend correctly", () => {
        const wrapper = mount(BarChart);
        const legend = wrapper.find(".chart-legend");

        expect(legend.exists()).toBe(true);
        expect(legend.find("li").text()).toContain("Dataset 1");
    });
});