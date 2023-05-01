<template>
  <div class="bar-chart-container">
    <Bar
        id="my-chart-id"
        :options="chartOptions"
        :data="chartData"
    />
    <div class="chart-legend">
      <ul>
        <li>
          <span style="background-color: #31c48d;"></span>
          <span>Dataset 1</span>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
import {Bar} from 'vue-chartjs'
import {Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale} from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
  name: 'BarChart',
  components: {Bar},
  data() {
    return {
      chartData: {
        labels: ['January', 'February', 'March'],
        datasets: [
          {
            label: 'Dataset 1',
            data: [40, 20, 100],
            backgroundColor: '#31c48d',
            borderWidth: 1,
            borderColor: '#31c48d',
            hoverBackgroundColor: '#39e8a8',
            hoverBorderColor: '#39e8a8'
          }
        ]
      },
      chartOptions: {
        responsive: true,
        plugins: {
          legend: {
            display: false
          },
        },
        scales: {
          y: {
            ticks: {
              beginAtZero: true
            },
            grid: {
              color: 'rgba(0, 0, 0, 0.05)',
              drawBorder: false
            }
          },
          x: {
            grid: {
              color: 'rgba(0, 0, 0, 0.05)',
              drawBorder: false
            }
          }
        }
      }
    }
  }
}
</script>
<style scoped>
.bar-chart-container {
  background-color: white;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.chart-legend {
  display: flex;
  justify-content: center;
}

.chart-legend li {
  display: flex;
  align-items: center;
  font-size: 14px;
  line-height: 1.2;
  margin-right: 20px;
}

.chart-legend li span:first-child {
  display: inline-block;
  width: 10px;
  height: 10px;
  margin-right: 5px;
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