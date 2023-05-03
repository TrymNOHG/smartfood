<template>
  <div class="grey-bar">
    <div id="info-and-bell">
      <InfoAndBell/>
      <div class="information-button">
        <img
            src="@/assets/images/info.svg"
            id="info-picture"
            @click="showInformation"
            :alt="$t('alt_info_button')"
        />
      </div>
    </div>
  </div>
  <div class="page-layout">
    <div class="stat-container">
      <h3>{{ $t(choosenStat) }}</h3>
      <BasicSelect
          v-if="isMobile"
          class="dropDown"
          :options="statChoice"
          v-model="choosenStat"
          @input="updateStats(choosenStat)"
      />
      <div class="display-stat">
        <div>
          <line-chart
              id="chart"
              :data="chartDataPercentage"
              :ymin="percentageThrownPerPers"
              :ymax="percentageThrownPerPers"
              :chart-label="$t('percentage_thrown')"
          />
        </div>
        <div>
          <line-chart
              id="chart"
              :data="chartDataMoney"
              :ymin="moneyUsedPerPers"
              :ymax="moneyUsedPerPers"
              :chart-label="$t('money_wasted')"
          />
        </div>
      </div>
    </div>
    <div class="sidebar">
      <h3 v-if="!isMobile">{{ $t('choose_statistics') }}</h3>
      <BasicSelect
          v-if="!isMobile"
          class="dropDown"
          :options="statChoice"
          v-model="choosenStat"
          @input="updateStats(choosenStat)"
      />
      <div class="display-details">
        <stat-card
            :value="percentageThrown + '%'"
            :name="$t('percentage_thrown_last_day')"
            class="card"
            iconName="fa-chart-simple"
        />
        <stat-card
            :value="moneyThrown + 'kr'"
            :name="$t('money_wasted_last_day')"
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
import {useFridgeStore, useStatStore} from "@/store/store";
import InfoAndBell from "@/components/basic-components/InfoAndBell.vue";

export default {
  name: "StatisticsView",
  components: {InfoAndBell, BasicSelect, StatCard, BarChart, LineChart},

  methods: {
    updateStats(stat) {
      if (stat === "Personal Statistics" || stat === "Personlig statistikk") {
        this.fetchFridgeStats();
      } else {
        this.fetchUserStats();
      }
    },
  },


  data() {
    return {
      moneyUsedPerPers: 46,
      percentageThrownPerPers: 26,
      choosenStat: this.$t('personal_statistics'),
      statChoice: [this.$t('personal_statistics'), this.$t('fridge_statistics')],
      width: window.innerWidth
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
    const fridgeStore = useFridgeStore();
    let percentageThrown = ref();
    let moneyThrown = ref();
    const fetchUserStats = async () => {
      await statStore.fetchUserStatsPercentage();
      await statStore.fetchUserStatsMoney();
      chartDataPercentage.value = statStore.getPercentageChart;
      chartDataMoney.value = statStore.getMoneyChart;

      percentageThrown.value = Math.trunc(chartDataPercentage.value
          .values[chartDataPercentage.value.values.length - 1]);
      moneyThrown.value = Math.trunc(chartDataMoney.value
          .values[chartDataMoney.value.values.length -1])


      if (isNaN(percentageThrown.value)) {
        percentageThrown.value = 0;
      }

      if (isNaN(moneyThrown.value)) {
        moneyThrown.value = 0;
      }
    };

    const fetchFridgeStats = async () => {
      await statStore.fetchFridgePercentage(fridgeStore.getCurrentFridge);
      await statStore.fetchFridgeMoney(fridgeStore.getCurrentFridge)
      chartDataPercentage.value = statStore.getPercentageChart;
      chartDataMoney.value = statStore.getMoneyChart;

      percentageThrown.value = Math.trunc(chartDataPercentage.value
          .values[chartDataPercentage.value.values.length - 1]);
      moneyThrown.value = Math.trunc(chartDataMoney.value
          .values[chartDataMoney.value.values.length -1])


      if (isNaN(percentageThrown.value)) {
        percentageThrown.value = 0;
      }

      if (isNaN(moneyThrown.value)) {
        moneyThrown.value = 0;
      }
    };

    fetchUserStats();

    return {
      chartDataPercentage,
      percentageThrown,
      moneyThrown,
      fetchFridgeStats,
      fetchUserStats,
      chartDataMoney
    };
  },

  computed: {
    isMobile() {
      return this.width < 768;
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

#info-and-bell {
  display: flex;
  flex-direction: row;
  justify-content: end;
  gap: 5%;
}


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
  text-align: center;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  align-items: center;
}

#info-and-bell {
  grid-column: 3;
}

.information-button {
  display: flex;
  grid-column: 3;
  text-align: right;
  margin-left: auto;

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
  margin: 2% 2% 20%;
}

.sidebar {
  margin: 2% 5% 5%;
}

.sidebar h3 {
  font-size: 1.5rem;
}

@media screen and (max-width: 680px) {
  #info-and-bell {
    top: 5%;
    right: 4%;
  }

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


@media only screen and (min-width: 10px) and (max-width: 650px)  {

  .grey-bar{
    display: flex;
    align-content: center;
    align-items: center;
    flex-wrap: wrap;
    justify-content: center;
    text-align: center;
    margin-top: 5px;
    background-color: #31c48d;
    max-height: 60px;
    height: 60px;
    border-radius: 20px 20px 0 0;
    width: 100%;
  }

  #grey-header{
    margin-left: 25%;
    height: 60px !important;
    background-color: white;
    border-radius: 20px 20px 0 0;
    font-weight: bold;
    text-decoration: none;
    text-shadow: none;
    color: black;
    margin-top: 20px;
    padding-top: 10px;
    width: 50%;
  }
  .stat-container{
    margin-left: 0;
  }
}

</style>