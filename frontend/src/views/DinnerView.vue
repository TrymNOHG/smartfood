<template>
  <div class="grey-bar">
    <div class="suggestion-weekMenu">
      <div
        id="toggle-button"
        class="link"
        @click="selectedTab = 'suggestion'"
        :class="{ active: selectedTab === 'suggestion' }"
      >
        {{ $t("suggestion") }}
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
  <div class="suggestion-wrapper" v-show="selectedTab === 'suggestion'">
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
import InfoAndBell from "@/components/basic-components/InfoAndBell.vue";
export default {
  name: "DinnerView",
  components: {
    InfoAndBell,
    DinnerSuggestion,
    WeekMenu,
  },
  setup() {
    const selectedTab = ref("suggestion");

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

.suggestion-weekMenu {
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
  max-height: 35px;
  text-align: center;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
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

@media only screen and (min-width: 10px) and (max-width: 650px) {
  #info-and-bell {
    top: 5%;
  }

  .grey-bar {
    display: flex;
    align-content: center;
    align-items: center;
    justify-content: center;
    margin-top: 5px;
    background-color: #31c48d;
    max-height: 60px;
    height: 60px;
    border-radius: 20px 20px 0 0;
  }

  .suggestion-weekMenu {
    background-color: #31c48d;
    margin-top: 0;
    padding-top: 0;
    padding-right: 10px;
    text-align: center;
    align-items: center;
    align-content: center;
    justify-content: center;
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
