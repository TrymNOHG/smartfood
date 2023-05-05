<template>
  <div class="grey-bar">
    <h2 id="grey-header" >{{ $t('profile') }}</h2>
    <div class="information-button">
      <img src="@/assets/images/info.svg" id="info-picture" @click=" resetSteps(); profileStepsTour()" :alt=" $t('alt_info_button') ">
    </div>
  </div>
  <div>
    <profile-component/>
  </div>

</template>

<script>
import ProfileComponent from "../components/ProfileComponent.vue";
import Shepherd from "shepherd.js";
import {offset} from "@floating-ui/vue";
export default {
  name: "ProfileView",
  components: {ProfileComponent},

  setup() {
      const profileTour = new Shepherd.Tour({
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
          profileTour
      }
  },


  methods: {
      resetSteps() {
          if (this.profileTour.steps.length !== 0) {
              while (this.profileTour.steps.length !== 0) {
                  this.profileTour.steps.pop()
              }
          }
      },
      profileStepsTour() {
          this.profileTour.addSteps([
              {
                  id: 'itemWindow',
                  title: this.$t('tour: view:ProfileView method:StatisticsStepTour id:StatisticsWindow usage:title'),
                  text: this.$t('tour: view:ProfileView method:StatisticsStepTour id:StatisticsWindow usage:text'),
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
          this.profileTour.start()
      },
  },
}
</script>

<style scoped>

  .grey-bar {
    background-color: #6C6C6C;

    text-align: center;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
  }

  #grey-header{
    grid-column: 2;
    color: white;
  }

  .information-button{
    display: flex;
    grid-column: 3;
    text-align: right;
    margin-left: auto;
  }

  #info-picture{
    height: 30px;
    width: 30px;
    cursor: pointer;

  }

  @media only screen and (min-width: 10px) and (max-width: 650px) {

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

    .information-button {
      display: flex;
      margin-left: auto;
      margin-right: 8px;
      gap: 30%;
      left: 0;
      height: 60px;
    }
  }

</style>