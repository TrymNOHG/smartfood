<template>
  <BarChart />
  <LineChart />
</template>

<script>
import BarChart from '@/components/statistic/BarChart.vue'
import LineChart from '../components/statistic/LineChart.vue';

export default {
  name: 'StatisticsView',
  components: { BarChart, LineChart }
}
</script>