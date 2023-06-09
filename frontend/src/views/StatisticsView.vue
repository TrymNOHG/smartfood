<template>
  <div class="grey-bar">
    <h2 id="grey-header" >{{ $t('statistics') }}</h2>
    <div id="info-and-bell">
      <InfoAndBell/>
      <div class="information-button">
        <img
            src="@/assets/images/info.svg"
            id="info-picture"
            :alt="$t('alt_info_button')"
            @click="resetSteps();statStepsTour()"
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
              :data="chartDataFood"
              :chart-label="$t('percentage_thrown')"
           />
        </div>
        <div>
          <line-chart
              id="chart"
              :data="chartDataMoney"
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
            :food="foodThrown + 'g'"
            :money="moneyThrown + 'kr'"
            :name="$t('percentage_thrown_last_day')"
            class="card"
            iconName="fa-chart-simple"
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
import {useFridgeStore, useLoggedInStore, useStatStore} from "@/store/store";
import InfoAndBell from "@/components/basic-components/InfoAndBell.vue";
import Shepherd from "shepherd.js";
import {offset} from "@floating-ui/vue";

export default {
  name: "StatisticsView",
  components: {InfoAndBell, BasicSelect, StatCard, BarChart, LineChart},

  methods: {
    updateStats(stat) {
      console.log(stat)
      if (stat === "personal_statistics") {
        this.fetchFridgeStats();
      } else {
        this.fetchUserStats();
      }
    },

      resetSteps(){
          if(this.statsTour.steps.length !== 0) {
              while (this.statsTour.steps.length !== 0) {
                  this.statsTour.steps.pop()
              }
          }
      },

      statStepsTour() {
          this.statsTour.addSteps([
              {
                  id: 'itemWindow',
                  title: this.$t('tour: view:StatisticsView method:StatisticsStepTour id:StatisticsWindow usage:title'),
                  text: this.$t('tour: view:StatisticsView method:StatisticsStepTour id:StatisticsWindow usage:text'),
                  attachTo: {
                      element: '.router-view-container',

                  },
                  classes: 'shepherd-theme-arrows',
                  buttons: [
                      {
                          action: function () {
                              return this.cancel();
                          },
                          class: " shepherd-button ",
                          text: this.$t('tour: button exit'),
                      },
                  ]
              },
          ])
          this.statsTour.start()

      },
  },


  data() {
    return {
      choosenStat: 'personal_statistics',
      statChoice: ['personal_statistics', 'fridge_statistics'],
      width: window.innerWidth,
    }
  },

  setup() {
    const userStore = useLoggedInStore();
    const currenUser = ref("");
    const chartDataFood = ref({
      labels: [],
      values: []
    });
    const chartDataMoney = ref({
      labels: [],
      values: []
    });
    const statStore = useStatStore();
    const fridgeStore = useFridgeStore();
    let foodThrown = ref();
    let moneyThrown = ref();
    const fetchUserStats = async () => {
      await statStore.fetchUserStatsPercentage();
      await statStore.fetchUserStatsMoney();
      chartDataFood.value = statStore.getPercentageChart;
      chartDataMoney.value = statStore.getMoneyChart;

      foodThrown.value = Math.trunc(chartDataFood.value
          .values[chartDataFood.value.values.length - 1]);
      moneyThrown.value = Math.trunc(chartDataMoney.value
          .values[chartDataMoney.value.values.length -1])


      if (isNaN(foodThrown.value)) {
        foodThrown.value = 0;
      }

      if (isNaN(moneyThrown.value)) {
        moneyThrown.value = 0;
      }
    };

    const fetchFridgeStats = async () => {
      await statStore.fetchFridgePercentage(fridgeStore.getCurrentFridge);
      await statStore.fetchFridgeMoney(fridgeStore.getCurrentFridge)
      chartDataFood.value = statStore.getPercentageChart;
      chartDataMoney.value = statStore.getMoneyChart;

      foodThrown.value = Math.trunc(chartDataFood.value
          .values[chartDataFood.value.values.length - 1]);
      moneyThrown.value = Math.trunc(chartDataMoney.value
          .values[chartDataMoney.value.values.length -1])

      if (isNaN(foodThrown.value)) {
        foodThrown.value = 0;
      }

      if (isNaN(moneyThrown.value)) {
        moneyThrown.value = 0;
      }
    };
    
    fetchUserStats();

      const statsTour = new Shepherd.Tour({
          useModalOverlay: true,
          defaultStepOptions: {
              classes: 'shepherd-has-cancel-icon shepherd-element class-1 class-2 shepherd-enabled shepherd-theme-arrows',
              arrow: true,
              floatingUIOptions: {
                  middleware: [offset(30)]
              },
              cancelIcon: {
                  enabled: true
              },
          }
      });

    return {
      chartDataFood: chartDataFood,
      foodThrown: foodThrown,
      moneyThrown,
      fetchFridgeStats,
      fetchUserStats,
      chartDataMoney,
      currenUser,
      userStore,
      fridgeStore,
        statsTour
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

#grey-header {
  height: 40px;
  grid-column: 2;
  color: white;
}

#info-and-bell {
  display: flex;
  flex-direction: row;
  justify-content: end;
  gap: 34%;
  margin-left: auto;
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
  justify-content: center;
  align-content: center;
  align-items: center;
  height: 40px;
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
  margin: 5% 2% 2%;
  justify-content: space-evenly;
}

.card {
  margin: 2% 2% 20%;
  height: 80%;
}

.sidebar {
  margin: 2% 5% 5%;
}

.sidebar h3 {
  font-size: 1.5rem;
}

@media screen and (max-width: 680px) {

  #grey-header {
    height: 60px;
    grid-column: 2;
    color: black;
  }

  #info-and-bell {
    margin-left: auto;
    gap: 50%;
  }

  #info-picture {
    top: 15%;
    height: 60px;
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
  }

  .chart-legend li {
    font-size: 0.9rem;
  }

  .information-button {
    justify-content: center;
    align-content: center;
    align-items: center;
    height: 60px;
  }
}


@media only screen and (min-width: 10px) and (max-width: 650px)  {

  .grey-bar {
    display: flex;
    align-content: center;
    justify-content: center;
    margin-top: 5px;
    background-color: #31c48d;
    max-height: 60px;
    height: 60px;
    border-radius: 20px 20px 0 0;
  }

  #info-and-bell{
    display: flex;
    margin-left: auto;
    margin-right: 5px;
    gap: 30%;
    left: 0;
    height: 60px;
  }


  #grey-header {
    display: flex;
    grid-template-columns: 1fr 1fr;
    grid-column-gap: 10px;
    grid-column: 2;
    text-align: center;

    justify-content: center;
    margin-left: 30%;
    height: 60px !important;
    background-color: white;
    font-size: 20px;
    border-radius: 20px 20px 0 0;
    font-weight: bold;
    text-decoration: none;
    text-shadow: none;
    color: black;
    margin-top: 20px;
    padding-top: 5px;
    padding-right: 5px;
    padding-left: 5px;
    width: 40%;
  }

  #info-picture{
    height: 30px;
    width: 30px;
    top: 0;
  }

  .information-button{
    margin-right: 3px !important;
  }




  .stat-container{
    margin-left: 0;
  }


}

</style>