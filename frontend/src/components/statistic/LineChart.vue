<template>
    <div class="line-chart">
      <Line :data="chartData" :options="options" />
    </div>
</template>
  
<script>
import {Bar, Line} from 'vue-chartjs'
  import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
  } from 'chart.js'

  ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
  )
  
  export default {
    props: {
        chartData: {
        type: Object,
        required: true,
        },
        options: {
        type: Object,
        required: true,
        },
    },
      components: { Line },
      mounted() {
        console.log(this.chartData ," Options2: ",  this.options)
      }
  };
</script>
  
<style scoped>
  .line-chart {
    position: relative;
    width: 100%;
    height: 100%;
  }
</style>