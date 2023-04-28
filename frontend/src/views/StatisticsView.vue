<template>
  <div class="grey-bar">
    <h2 id="grey-header" >{{ $t('statistics') }}</h2>
    <div class="information-button">
      <img src="@/assets/images/info.svg" id="info-picture" @click="showInformation" :alt=" $t('alt_info_button') ">
    </div>
      <div>
          <h2>Dummy test Stats</h2>
          <BarChart />
          <div>
              <h2>User Stats</h2>
          </div>
      </div>
  </div>

</template>

<script>
//<line-chart :chart-data="userStatsChartData" :options="chartOptions"></line-chart>
import BarChart from '@/components/statistic/BarChart.vue';
import LineChart from '@/components/statistic/LineChart.vue';
import { getUserStats, getFridgeStats } from '@/services/StatsService.js';
import {useFridgeStore} from "../store/store";

export default {
  name: "StatisticsView",
    components: { BarChart, LineChart },
    data() {
        return {
            userStats: [],
            fridgeStats: [],
            chartOptions: {
                responsive: true,
                maintainAspectRatio: false,
            },
            fridgeId: useFridgeStore().currentFridge.fridgeId
        };
    },
    computed: {

    },
    methods: {
    },
    mounted() {
        //this.fetchStats();
    },
};
</script>

<style scoped>
  .grey-bar {
    background-color: #6C6C6C;
    max-height : 35px;
    text-align: center;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
  }

  #grey-header{
    grid-column: 2;
    color: white;
  }

  .information-button{
    grid-column: 3;
    text-align: right;
    padding: 2px 5px;
  }

  #info-picture{
    height: 30px;
    width: 30px;
    cursor: pointer;
  }
</style>