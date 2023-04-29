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
              :data="chartData"
              :ymin="55"
              :ymax="55"
          />
        </div>
        <div>
          <line-chart
              id="chart"
              :data="chartData"
              :ymin="55"
              :ymax="55"
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
            class="card"
            iconName="fa-chart-simple"
        />
        <stat-card
            class="card"
            iconName="fa-coins"
        />
        <stat-card
            class="card"
            iconName="fa-flag"
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

export default {
  name: "StatisticsView",
    components: {BasicSelect, StatCard, BarChart, LineChart },

  data() {
    return {
      chartData: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        values: [50, 45, 55, 60, 65, 40, 35],
      },
      statChoice: ["Personal Statistics", "Fridge Statistics"],
      choosenStat: "Personal Statistics",
      width: window.innerWidth,
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