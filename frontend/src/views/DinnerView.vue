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
        class="link weekMenu"
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
            @click="resetSteps(); runTour()"
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
import { offset } from "@floating-ui/vue";
import Shepherd from 'shepherd.js';
import '@/assets/tourStyle.css';

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

        const tipsTour = new Shepherd.Tour({
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
        const weeklyTour = new Shepherd.Tour({
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
        const tour = new Shepherd.Tour({
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
            selectedTab,
            tipsTour,
            weeklyTour,
            tour
        };
    },
    methods: {
        resetSteps() {
            if (this.tipsTour.steps.length !== 0) {
                while (this.tipsTour.steps.length !== 0) {
                    this.tipsTour.steps.pop()
                }
            }
            if (this.weeklyTour.steps.length !== 0) {
                while (this.weeklyTour.steps.length !== 0) {
                    this.weeklyTour.steps.pop()
                }
            }
            if (this.tour.steps.length !== 0) {
                while (this.tour.steps.length !== 0) {
                    this.tour.steps.pop()
                }
            }
        },

        runTour() {
            this.tour.addSteps([
                {
                    id: 'grey-field-header',
                    title: this.$t('tour: view:dinnerView method:tour id:tipsWindow usage:title'),
                    text: this.$t('tour: view:dinnerView method:tour id:tipsWindow usage:text'),
                    attachTo: {
                        element: '.grey-bar',
                        on: 'bottom',
                    },
                    classes: 'shepherd-theme-arrows',
                    buttons: [
                        {
                            action: function () {
                                return this.back();
                            },
                            secondary: true,
                            text: this.$t('tour: button back'),
                        },
                        {
                            action: () => {
                                if (this.selectedTab === "weekMenu") {
                                    this.weeklyViewStepsTour();
                                    this.tour.cancel();

                                } else {
                                    this.tipsViewStepsTour();
                                    this.tour.cancel();
                                }


                            },
                            class: " shepherd-button ",
                            text: this.$t('tour: button next'),
                        },
                    ]
                },
            ])
            this.tour.start()
        },

        tipsViewStepsTour() {
            this.tipsTour.addSteps([
                {

                    id: 'grey-field-header',
                    title: this.$t('tour: view:dinnerView method:tipsViewStepTour id:tipsWindow usage:title'),
                    text: this.$t('tour: view:dinnerView method:tipsViewStepTour id:tipsWindow usage:text'),
                    attachTo: {
                        element: '.grey-bar',
                        on: 'bottom',
                    },
                    classes: 'shepherd-theme-arrows',
                    buttons: [
                        {
                            action: function () {
                                return this.cancel();
                            },
                            text: this.$t('tour: button exit'),
                        },
                    ]
                },
            ])
            this.tipsTour.start()
        },
        weeklyViewStepsTour() {
            this.weeklyTour.addSteps([
                {
                    id: 'grey-field-header',
                    title: this.$t('tour: view:dinnerView method:weeklyStepTour id:tipsWindow usage:title'),
                    text: this.$t('tour: view:dinnerView method:weeklyStepTour id:tipsWindow usage:text'),
                    attachTo: {
                        element: '.grey-bar',
                        on: 'bottom',
                    },
                    classes: 'shepherd-theme-arrows',
                    buttons: [
                        {
                            action: function () {
                                return this.cancel();
                            },
                            text: this.$t('tour: button exit'),
                        },
                    ]
                },
            ])
            this.weeklyTour.start()
        },

    },
}
</script>

<style scoped>

#info-and-bell {
  display: flex;
  flex-direction: row;
  justify-content: end;
  gap: 34%;
  margin-left: auto;
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
  justify-content: center;
  align-content: center;
  align-items: center;
  height: 40px;
}

#info-picture {
  width: 30px;
  cursor: pointer;
  height: 30px;
}

@media only screen and (min-width: 10px) and (max-width: 650px) {
  .information-button {
    justify-content: center;
    align-content: center;
    align-items: center;
    height: 60px;
  }
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
  }
  .tips-weekMenu {
    background-color: #31c48d;
    color: white;
    font-size: 20px;
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-column-gap: 10px;
    grid-column: 2;
    margin-top: 0;
    padding-top: 0;
    padding-right: 10px;
    text-align: center;
    align-items: center;
    align-content: center;
    justify-content: center;
    margin-left: 10px;
    width: 70%;
  }

  .link.active {
    height: 60px !important;
    background-color: white;
    font-size: 20px;
    border-radius: 20px 20px 0 0;
    font-weight: bold;
    text-decoration: none;
    text-shadow: none;
    color: black;
    margin-top: 20px;
    padding-top: 8px;
    padding-right: 5px;
    padding-left: 5px;
  }

  .weekMenu.active {
    border-bottom-left-radius: 20px ;
    border-bottom-right-radius: 20px ;
    z-index: 4 ;
  }

  .link {
    margin: 0;
    padding-left: 5px;
    padding-right: 5px;
    font-size: 20px;
  }
  .information-button{
    margin-right: 3px !important;
  }

  #toggle-button:hover {
    cursor: pointer;
    font-size: unset;
    font-size: 20px;
  }



}
</style>
