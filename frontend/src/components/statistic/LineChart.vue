<template>
  <div class="line-chart-container">
    <Line
        :id="chartId"
        :data="chartData"
        :options="chartOptions"
    />
    <div class="chart-legend">
      <ul>
        <li>
          <span :style="{ backgroundColor: chartData.datasets[0].backgroundColor }"></span>
          <span>{{ chartData.datasets[0].label }}</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import {Line} from 'vue-chartjs'
import {Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend} from 'chart.js'
import ChartAnnotation from 'chartjs-plugin-annotation'
ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, ChartAnnotation)

export default {
  props: {
    data: {
      type: Object,
      required: true,
    },
    ymin: {
      type: Number,
      required: true,
    },
    ymax: {
      type: Number,
      required: true,
    },
    chartId: {
      type: String,
      default: 'my-chart-id',
    },
    chartLabel: {
      type: String,
      default: 'Dataset 1',
    },
    backgroundColor: {
      type: String,
      default: '#31c48d',
    },
    borderColor: {
      type: String,
      default: '#31c48d',
    },
    pointBackgroundColor: {
      type: String,
      default: '#31c48d',
    },
    chartTitle: {
      type: String,
      default: '',
    },
    legendPosition: {
      type: String,
      default: 'top',
    },
    showLabel: {
      type: Boolean,
      default: true,
    },
    labelContent: {
      type: String,
      default: '55',
    },
  },
  computed: {
    chartData() {
      return {
        labels: this.data.labels,
        datasets: [
          {
            label: this.chartLabel,
            backgroundColor: this.backgroundColor,
            borderColor: this.borderColor,
            pointBackgroundColor: this.pointBackgroundColor,
            data: this.data.values,
          },
        ],
      };
    },
    chartOptions() {
      return {
        title: {
          display: !!this.chartTitle,
          text: this.chartTitle,
        },
        legend: {
          display: this.legendPosition !== 'none',
          position: this.legendPosition,
        },
        plugins: {
          annotation: {
            annotations: {
              line1: {
                type: 'line',
                yMin: this.ymin,
                yMax: this.ymax,
                borderColor: '#444444',
                borderWidth: 2,
                label: {
                  content: this.showLabel ? this.labelContent : '',
                  enabled: this.showLabel,
                  position: 'center',
                },
              },
            },
          },
        },
      };
    },
  },
  components: {Line},
};
</script>

<style scoped>
.line-chart-container {
  background-color: white;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.chart-wrapper {
  position: relative;
  height: 300px;
  width: 100%;
}

.chart-legend {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.chart-legend li {
  display: flex;
  align-items: center;
  font-size: 14px;
  line-height: 1.2;
  margin-right: 20px;
}

.chart-legend-color {
  display: inline-block;
  width: 10px;
  height: 10px;
  margin-right: 5px;
}

.chart-legend-text {
  white-space: nowrap;
}

.chart-legend li:last-child {
  margin-right: 0;
}

@media (max-width: 768px) {
  .chart-legend {
    flex-wrap: wrap;
  }

  .chart-legend li {
    margin-right: 0;
    margin-bottom: 5px;
  }
}
</style>
