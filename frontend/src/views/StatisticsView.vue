<template>
  <div>
    <h2>Test Stats</h2>
      <BarChart />
    <div>
      <h2>User Stats</h2>
      <line-chart :chart-data="userStatsChartData" :options="chartOptions"></line-chart>
    </div>
  </div>
</template>

<script>
import BarChart from '@/components/statistic/BarChart.vue';
import LineChart from '@/components/statistic/LineChart.vue';
import { getUserStats } from '@/services/StatsService.js';

export default {
  name: 'StatisticsView',
  components: { BarChart, LineChart },
  data() {
    return {
      userStats: [],
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
      },
    };
  },
  computed: {
    userStatsChartData() {
      return this.createChartData(this.userStats);
    },
  },
  methods: {
    async fetchStats() {
      try {
        const userStatsResponse = await getUserStats();
        this.userStats = userStatsResponse.data;
      } catch (error) {
        console.error('Error fetching statistics:', error);
      }
    },
    createChartData(stats) {
      console.log("Inserted stats from createChartData: ", stats)
      return {
        labels: stats.map((stat) => stat.timestamp),
        datasets: [
          {
            label: 'Percentage Thrown',
            data: stats.filter(stat => stat.statType.statTypeId === 1).map(stat => stat.statValue),
            borderColor: 'rgba(75, 192, 192, 1)',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
          },
          {
            label: 'Money Saved',
            data: [10, 29],
            borderColor: 'rgba(255, 206, 86, 1)',
            backgroundColor: 'rgba(255, 206, 86, 0.2)',
          },
          {
            label: 'Bought Price',
            data: stats.filter(stat => stat.statType.statTypeId === 3).map(stat => stat.statValue),
            borderColor: 'rgba(255, 99, 132, 1)',
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
          },
        ],
      };
    },
  },
  mounted() {
    this.fetchStats();
    //TODO: add this back
  },
};
</script>
