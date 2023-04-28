<template>
  <div class="grey-bar">
    <h2 id="grey-header" >{{ $t('statistics') }}</h2>
    <div class="information-button">
      <img src="@/assets/images/info.svg" id="info-picture" @click="showInformation" :alt=" $t('alt_info_button') ">
    </div>
      <div>
          <h2>Test Stats</h2>
          <BarChart />
          <div>
              <h2>User Stats</h2>
              <h3>Percentage Thrown</h3>
              <line-chart :chart-data="userStatsChartData(1)" :options="chartOptions"></line-chart>
              <h3>Money Saved</h3>
              <line-chart :chart-data="userStatsChartData(2)" :options="chartOptions"></line-chart>
              <h3>Bought Price</h3>
              <line-chart :chart-data="userStatsChartData(3)" :options="chartOptions"></line-chart>

              <h2>Fridge Stats</h2>
              <h3>Percentage Thrown</h3>
              <line-chart :chart-data="fridgeStatsChartData(1)" :options="chartOptions"></line-chart>
              <h3>Money Saved</h3>
              <line-chart :chart-data="fridgeStatsChartData(2)" :options="chartOptions"></line-chart>
              <h3>Bought Price</h3>
              <line-chart :chart-data="fridgeStatsChartData(3)" :options="chartOptions"></line-chart>
          </div>
      </div>
  </div>

</template>

<script>
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
        userStatsChartData(statTypeId) {
            return this.createChartData(this.userStats, statTypeId);
        },
        fridgeStatsChartData(statTypeId) {
            return this.createChartData(this.userStats, statTypeId);
        },
        async fetchStats() {
            try {
                const userStatsResponse = await getUserStats();
                this.userStats = userStatsResponse.data;
                const fridgeStatsResponse = await getFridgeStats(this.fridgeId);
                this.fridgeStats = fridgeStatsResponse.data;
            } catch (error) {
                console.error('Error fetching statistics:', error);
            }
        },
        createChartData(stats, statTypeId) {
            console.log("Inserted stats from createChartData: ", stats);
            const groupedData = stats.reduce((acc, item) => {
                console.log("Item: ", item)
                const date = item.timestamp.slice(0, 3).join("-");
                if (acc[date]) {
                    acc[date].sum += item.statValue * item.quantity;
                    acc[date].count += item.quantity;
                } else {
                    acc[date] = {
                        sum: item.statValue * item.quantity,
                        count: item.quantity,
                    };
                }
                return acc;
            }, {});
            let statData = groupedData.filter(stat => stat.statType.statTypeId === statTypeId).map(stat => stat.statValue)
            console.log("statData data: ",statData)
            statData = Object.keys(statData).map((key) => {
                return {
                    timestamp: key,
                    statValue: groupedData[key].sum / groupedData[key].count,
                };
            });
            return {
                labels: stats.map((stat) => stat.timestamp),
                datasets: [
                    {
                        label: 'Percentage Thrown',
                        data: statData,
                        borderColor: 'rgba(75, 192, 192, 1)',
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    }
                ],
            };
        },
    },
    mounted() {
        this.fetchStats();
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