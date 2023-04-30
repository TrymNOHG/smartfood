<template>
  <div class="grey-bar">
    <h2 id="grey-header" >{{ $t('statistics') }}</h2>
    <div class="information-button">
      <img src="@/assets/images/info.svg" id="info-picture" @click="showInformation" :alt=" $t('alt_info_button') ">
    </div>
  </div>
  <div class="page-layout">
    <div class="stat-container">
      <h3>{{choosenStat}}</h3>
      <BasicSelect
          v-if="isMobile"
          class="dropDown"
          :options="statChoice"
          v-model="choosenStat"
          @change="updateStats()"
      />
      <div class="display-stat">
        <div>
          <line-chart
              id="chart"
              :data="chartDataPercentage"
              :ymin="percentageThrown"
              :ymax="percentageThrown"
              :chart-label="'Percentage Thrown'"
          />
        </div>
        <div>
          <line-chart
              id="chart"
              :data="chartDataMoney"
              :ymin="moneyUsedPerPers"
              :ymax="moneyUsedPerPers"
              :chart-label="'Money Wasted'"
          />
        </div>
      </div>
      </div>
    <div class="sidebar">
      <h3 v-if="!isMobile">Choose Statistics</h3>
      <BasicSelect
          v-if="!isMobile"
          class="dropDown"
          :options="statChoice"
          v-model="choosenStat"
          @change="updateStats()"
      />
      <div class="display-details">
        <stat-card
            :value="percentageThrown + '%'"
            :name="'Percentage thrown last day'"
            class="card"
            iconName="fa-chart-simple"
        />
        <stat-card
            :value="moneyThrown + 'kr'"
            :name="'Money wasted last day'"
            class="card"
            iconName="fa-coins"
        />
      </div>
    </div>
  </div>
</template>

<script>
import BarChart from '@/components/statistic/BarChart.vue';
import LineChart from '@/components/statistic/LineChart.vue';
import StatCard from "@/components/statistic/StatCard.vue";
import BasicSelect from "@/components/basic-components/BasicSelect.vue";
import {ref} from "vue";
import {useStatStore} from "@/store/store";

export default {
  name: "StatisticsView",
    components: {BasicSelect, StatCard, BarChart, LineChart},

  data() {
    return {
      moneyUsedPerPers: 120,
      percentageThrownPerPers: 26,

    }
  },

  setup() {
    const chartDataPercentage = ref({
      labels: [],
      values: []
    });
    const chartDataMoney = ref({
      labels: [],
      values: []
    });
    const statStore = useStatStore();
    let percentageThrown = ref();
    let moneyThrown = ref();


    const fetchStats = async () => {
      await statStore.fetchUserStatsPercentage();
      await statStore.fetchUserStatsMoney()
      chartDataPercentage.value = statStore.getPercentageChart;
      chartDataMoney.value = statStore.getMoneyChart;
      percentageThrown.value = Math.trunc(chartDataPercentage.value
          .values[chartDataPercentage.value.values.length - 1]);
      moneyThrown.value = Math.trunc(chartDataMoney.value
          .values[chartDataMoney.value.values.length -1])

    };

    fetchStats();

    return {
      chartDataPercentage,
      percentageThrown,
      moneyThrown,
      chartDataMoney
    };
  },

  computed: {
    isMobile() {
      return this.width < 768;
    },
  },
  methods: {
    updateStats() {
      // Update the stats based on the chosen stat
    },
  },

  mounted() {
    window.addEventListener("resize", () => {
      this.width = window.innerWidth;
    });
  },
}
</script>

<style scoped>

#chart {
  width: 500px;
  height: 300px;
}

.dropDown {
  border: none;
  outline: none;
  padding: 10px;
  font-size: 16px;
  color: #333;
  background-color: #fff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  cursor: pointer;
}

.dropDown:focus {
  box-shadow: 0 0 0 2px #7EB09B;
}

h3 {
  margin: 1rem 0 0.5rem;
  color: #3bd290;
  font-size: 1.5rem;
  text-align: center;
}

.grey-bar {
  background-color: #6C6C6C;
  max-height: 35px;
  text-align: center;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  align-items: center;
}

#grey-header {
  grid-column: 2;
  color: white;
}

.information-button {
  grid-column: 3;
  text-align: right;
  padding: 2px 5px;
}

#info-picture {
  height: 30px;
  width: 30px;
  cursor: pointer;
}

.page-layout {
  display: grid;
  grid-template-columns: 60% 40%;
  margin-bottom: 80px;
}

.stat-container {
  margin-left: 2%;
}

.display-stat {
  display: grid;
  grid-template-rows: 1fr 1fr;
  align-items: center;
  grid-gap: 1rem;
  margin: 10px;
  align-content: center;
  justify-content: center;
  text-align: center;
}

.display-stat > div {
  width: 100%;
}

.display-details {
  margin: 2% 2% 2%;
  justify-content: space-evenly;
}

.card {
  height: 180px;
  margin: 2%;
}

.sidebar {
  margin: 2% 5% 5%;
}

.sidebar h3 {
  font-size: 1.5rem;
}

@media screen and (max-width: 768px) {
  #chart {
    width: 380px;
  }

  .dropDown {
    font-size: 14px;
  }

  h3 {
    font-size: 1.2rem;
  }

  .page-layout {
    display: grid;
    grid-template-columns: 100%;
  }

  .display-stat {
    grid-template-rows: 1fr;
    justify-items: stretch;
    align-items: center;
    grid-gap: 1rem;
  }

  .display-stat > div {
    margin: 0.5rem 0;
  }

  .display-details {
    display: block;
    gap: 2%;
  }

  .chart-legend li {
    font-size: 0.9rem;
  }
}

</style>