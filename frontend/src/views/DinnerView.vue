<template>
  <div class="grey-bar">
    <div class="tips-weekMenu">
      <div
        id="toggle-button"
        class="link"
        @click="selectedTab = 'tips'"
        :class="{ active: selectedTab === 'tips' }"
      >
        {{ $t("tips") }}
      </div>
      <div
        id="toggle-button"
        class="link"
        @click="selectedTab = 'weekMenu'"
        :class="{ active: selectedTab === 'weekMenu' }"
      >
        {{ $t("weekly_menu") }}
      </div>
    </div>
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
  <div class="tips-wrapper" v-show="selectedTab === 'tips'">
    <dinner-suggestion />
  </div>
  <div class="weekMenu-wrapper" v-show="selectedTab === 'weekMenu'">
    <week-menu />
  </div>
</template>

<script>
import DinnerSuggestion from "../components/dinner/DinnerSuggestionComponent.vue";
import WeekMenu from "../components/dinner/WeekMenuComponent.vue";

import { ref } from "vue";
import router from "../router/router";
import InfoAndBell from "@/components/basic-components/InfoAndBell.vue";
export default {
  name: "DinnerView",
  components: {
    InfoAndBell,
    DinnerSuggestion,
    WeekMenu,
  },
  setup() {
    const selectedTab = ref(router.currentRoute.value.query.selectedTab || 'tips');

    const url = '/dinner';
    history.replaceState(history.state, null, url);

    return {
      selectedTab,
    };
  },
  methods: {
    showInformation() {
      //TODO: INFORMATION DINNER put information API in here
    },
  },
};
</script>

<style scoped>

#info-and-bell {
  display: flex;
  flex-direction: row;
  justify-content: end;
  gap: 5%;
}
.tips-weekMenu {
  background-color: #6c6c6c;
  height: 35px;
  color: white;
  font-size: 1.5em;
  padding-top: 5px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-column-gap: 20px;
  grid-column: 2;
}

template {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.link {
  text-decoration: none;
  line-height: 25px;
  color: white;
}

#toggle-button {
  width: 150px;
}

#toggle-button:hover {
  cursor: pointer;
  font-size: x-large;
}

.active {
  background-size: 25px;
  background-color: #b1b1b1;
  border-radius: 5px;
  font-weight: bold;
  text-decoration: black;
  text-shadow: black 0 0 2px;
}

.grey-bar .link.active {
  height: 25px;
}

.grey-bar {
  background-color: #6c6c6c;
  text-align: center;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
}

.information-button {
  display: flex;
  text-align: right;
}

#info-picture {
  width: 30px;
  cursor: pointer;
  height: 40px;
  bottom: 10%;
}

@media only screen and (min-width: 10px) and (max-width: 650px) {
  #info-and-bell {
    gap: 15%;
  }

  .information-button{
    height: 60px
  }

  .grey-bar {
    display: flex;
    align-content: center;
    align-items: center;
    justify-content: flex-end;
    margin-top: 5px;
    background-color: #31c48d;
    max-height: 60px;
    height: 60px;
    border-radius: 20px 20px 0 0;
    flex-direction: row;
    flex-wrap: nowrap;
  }

  .tips-weekMenu {
    background-color: #31c48d;
    margin-top: 0;
    padding-top: 0;
    padding-right: 10px;
    text-align: center;
    align-items: center;
    align-content: center;
    justify-content: center;
    margin-left: 10px;
  }

  .link {
    margin: 0;
  }

  .link.active {
    height: 60px !important;
    background-color: white;
    border-radius: 20px 20px 0 0;
    font-weight: bold;
    text-decoration: none;
    text-shadow: none;
    color: black;
    margin-top: 20px;
    padding-top: 10px;
  }

}
</style>
