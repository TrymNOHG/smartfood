<template>
    <div class="line-chart">
      <Line :data="chartData" :options="options" />
    </div>
</template>
  
<script>
  import { Line } from 'vue-chartjs';
  
  export default {
    props: ['chartData', 'options'],
  };
</script>
  
<style scoped>
  .line-chart {
    position: relative;
    width: 100%;
    height: 100%;
  }
</style>