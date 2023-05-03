<template>
  <div class="grey-bar" ref="installElement">
    <h2 id="grey-header" >{{ $t('fridges') }}</h2>
    <div class="information-button">
      <img src="@/assets/images/info.svg" id="info-picture" @click="changeLanguage(); fridgesStepTour()" :alt=" $t('alt_info_button') ">
    </div>
  </div>

  <div class="wrapper">
    <div class="fridgeList">
      <fridge-component ref="usageElement" id="fridges"/>
    </div>
  </div>



</template>

<script>
import FridgeComponent from "../components/FridgeList/FridgesComponent.vue";
import Shepherd from 'shepherd.js';
//import 'shepherd.js/dist/css/shepherd.css';
//import '@/assets/welcome.css';
import '@/assets/tourStyle.css';
import {computed, ref} from 'vue';
import {useFridgeStore} from "@/store/store";
import { useRoute } from 'vue-router'
import {offset, computePosition, autoPlacement} from "@floating-ui/vue";


export default {
  name: "FridgesView",
  components: {FridgeComponent},


  setup() {

      const fridgeStore = useFridgeStore();
      const hasCurrentFridge = computed(() => fridgeStore.hasCurrentFridge);
      const installElement = ref(null);
      const usageElement = ref(null);
      const route = useRoute()
      const init = !(route.query.init === undefined || route.query.init === "false")


      const applicationTour = new Shepherd.Tour({
          useModalOverlay: true,
          defaultStepOptions: {
              classes: 'class-1 class-2',

          }
      });
      const fridgesTour = new Shepherd.Tour({
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
        init,
        applicationTour,
        fridgesTour,
        installElement,
        usageElement,
        hasCurrentFridge,

      }

    },

    created() {
        if(this.init) {
            this.firstLogginTour();
        }
    },

    methods: {
      changeLanguage(){
        if(this.applicationTour.steps.length !== 0) {
            while (this.applicationTour.steps.length !== 0) {
                this.applicationTour.steps.pop()
            }
        }
        if(this.fridgesTour.steps.length !== 0) {
            while (this.fridgesTour.steps.length !== 0) {
                this.fridgesTour.steps.pop()
            }
        }
      },

      /*
        firstLogginTour() shows a welcome message to a new registered user, and shows the user a tour if user wants
        information about the application
       */
      firstLogginTour(){
        this.applicationTour.addSteps([
            {
                id: 'welcome',
                text: `
                    <div class="welcomWindow">
                      <img src="./src/assets/images/smartmat_logo.png" alt="Logo" id="logo-picture"/>
                      <p id="welcomeText">${ this.$t('tour: view:fridgesView method:firstLogginTour id:welcome usage:text') }</p>
                    </div>
                `,
                title: this.$t('tour: view:fridgesView method:firstLogginTour id:welcome usage:title'),
                //classPrefix:'shepherd-enabled',
                buttons:[
                    {
                        action: function () {
                            return this.cancel();
                        },
                        secondary: true,
                        text: this.$t('tour: button cancel'),
                    },
                    {
                        action: function () {
                            return this.next();
                        },
                        text: this.$t('tour: button take-tour'),
                    },
                ]
            },
        ])
          this.applicationTour.start()
      },

      /*
      stepTour() is a information-button user can press to get information about the functionality to the site
     */
      fridgesStepTour() {
          this.fridgesTour.cancel()
          //tour for PC
          if (window.matchMedia("(min-width: 860px)").matches) {
              this.fridgesTour.addSteps([
                  //Information of profile button
                  {
                      id: 'profile-tour',
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:profile-tour usage:text'),
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:profile-tour usage:title'),
                      attachTo: {
                          element: '#profile',
                          on: 'left',
                      },
                      classes: '',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              class: " shepherd-button ",
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              class: " shepherd-button ",
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information about internationalization
                  {
                      id: 'language-tour',
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:language-tour usage:text'),
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:language-tour usage:title'),
                      attachTo: {
                          element: '.language',
                          on: 'bottom',
                      },
                      classes: '',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information about fridge name and function
                  {
                      id: 'fridge-name-info',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:fridge-name-info usage:title'),
                      text: this.hasCurrentFridge ? this.$t('tour: view:fridgesView method:fridgesStepTour id:fridge-name-info usage:text-picked fridge') :
                          this.$t('tour: view:fridgesView method:fridgesStepTour id:fridge-name-info usage:text-not picked fridge'),
                      attachTo: {
                          element: '.fridge-name',
                          on: 'top',
                      },
                      classes: '',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information about exit button
                  {
                      id: 'exit-fridge-button-info',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:exit-fridge-button-info usage:title'),
                      text: this.hasCurrentFridge ? this.$t('tour: view:fridgesView method:fridgesStepTour id:exit-fridge-button-info usage:text-picked fridge ') :
                          this.$t('tour: view:fridgesView method:fridgesStepTour id:exit-fridge-button-info usage:text-not picked fridge'),
                      attachTo: {
                          element: '.change-button',
                          on: 'bottom'
                      },
                      classes: '',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information about header in grey field
                  {
                      id: 'grey-field-header',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:grey-field-header usage:title'),
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:grey-field-header usage:text'),
                      attachTo: {
                          element: '#grey-header',
                          on: 'bottom',
                          offset: {x: 0, y: 20},
                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information the fridge list
                  {
                      id: 'list-of-fridges',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:list-of-fridges usage:title'),
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:list-of-fridges usage:text'),
                      attachTo: {
                          element: '#fridgeList',
                          on: 'top',
                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information about one fridge
                  {
                      id: 'single-fridge',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:single-fridge usage:title'),
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:single-fridge usage:text'),
                      attachTo: {
                          element: '#Fridge0',
                          on: 'top',
                      },
                      classes: '',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information about change information on a fridge
                  {
                      id: 'fridge-change-information',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:fridge-change-information usage:title'),
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:fridge-change-information usage:text'),
                      attachTo: {
                          element: '#EditFridge0',
                          on: 'bottom',
                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information about adding fridge
                  {
                      id: 'add-fridge',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:add-fridge usage:title'),
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:add-fridge usage:text'),
                      attachTo: {
                          element: '.container_button',
                          on: 'top',
                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Return to informationbutton
                  {
                      id: 'information-button',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:information-button usage:title'),
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:information-button usage:text'),
                      attachTo: {
                          element: '#info-picture',
                          on: 'left',
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
              ]);
              this.fridgesTour.start()
          }
          else if(window.matchMedia("(max-width: 860px)").matches) {

              //11
              //Done: Fjerne endre språk
              //Done: Profil må være på top
              //Done: Grått felt må være på bottom
              //Lage et nytt kjøleskap må tenkes litt på, hvis det er mange kjøleskap må den være på toppen,
              //men hvis det bare er et kjøleskap bør den være på bunn
              //Hjelp / info må flyttes til under knappen
              //Må også finne ut hvordan man setter all funksjonalitet i bakgrunnen til å være
              this.fridgesTour.addSteps([
                  //Information of profile button
                  {
                      id: 'profile-tour',
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:profile-tour usage:text'),
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:profile-tour usage:title'),
                      attachTo: {
                          element: '#profile',
                          on: 'top',
                      },
                      classes: '',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              class: " shepherd-button ",
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              class: " shepherd-button ",
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information about fridge name and function
                  {
                      id: 'fridge-name-info',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:fridge-name-info usage:title'),
                      text: this.hasCurrentFridge ? this.$t('tour: view:fridgesView method:fridgesStepTour id:fridge-name-info usage:text-picked fridge') :
                          this.$t('tour: view:fridgesView method:fridgesStepTour id:fridge-name-info usage:text-not picked fridge'),
                      attachTo: {
                          element: '.fridge-name',
                          on: 'bottom',
                      },
                      classes: '',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information about exit button
                  {
                      id: 'exit-fridge-button-info',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:exit-fridge-button-info usage:title'),
                      text: this.hasCurrentFridge ? this.$t('tour: view:fridgesView method:fridgesStepTour id:exit-fridge-button-info usage:text-picked fridge ') :
                          this.$t('tour: view:fridgesView method:fridgesStepTour id:exit-fridge-button-info usage:text-not picked fridge'),
                      attachTo: {
                          element: '.change-button',
                          on: 'bottom'
                      },
                      classes: '',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information about header in grey field
                  {
                      id: 'grey-field-header',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:grey-field-header usage:title'),
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:grey-field-header usage:text'),
                      attachTo: {
                          element: '#grey-header',
                          on: 'bottom',
                          offset: {x: 0, y: 20},
                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information the fridge list
                  {
                      id: 'list-of-fridges',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:list-of-fridges usage:title'),
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:list-of-fridges usage:text'),
                      attachTo: {
                          element: '#fridgeList',
                          on: 'top',
                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information about one fridge
                  {
                      id: 'single-fridge',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:single-fridge usage:title'),
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:single-fridge usage:text'),
                      attachTo: {
                          element: '#Fridge0',
                          on: 'top',
                      },
                      classes: '',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information about change information on a fridge
                  {
                      id: 'fridge-change-information',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:fridge-change-information usage:title'),
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:fridge-change-information usage:text'),
                      attachTo: {
                          element: '#EditFridge0',
                          on: 'bottom',
                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Information about adding fridge
                  {
                      id: 'add-fridge',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:add-fridge usage:title'),
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:add-fridge usage:text'),
                      attachTo: {
                          element: '.container_button',
                          on: 'top',
                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },

                  //Return to informationbutton
                  {
                      id: 'information-button',
                      title: this.$t('tour: view:fridgesView method:fridgesStepTour id:information-button usage:title'),
                      text: this.$t('tour: view:fridgesView method:fridgesStepTour id:information-button usage:text'),
                      attachTo: {
                          element: '#info-picture',
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
              ]);
              this.fridgesTour.start()
          }
      }

    }
}
</script>

<style>

template {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.wrapper {
  display: grid;
  grid-template-columns: 25% 50% 25%;
  z-index: 0;
}

.welcomWindow{
    display: grid;
    grid-template-rows: 1fr 2fr;
    grid-template-columns: 1fr 4fr 1fr;
    grid-row-gap: 5px;
}

#logo-picture{
    grid-row: 1;
    grid-column: 2/3;
    height: inherit;
    width: 100%;
    border: #32c48d solid 4px;
    border-radius: 200px;

}

.eirik-button-shepherd{
    background: #ffffff;
    border-top: solid 4px #16202D;
    border-radius: 0;
    color: #16202D;
    display: flex;
    flex-grow: 1;
    font-family: "GT Pressura", sans-serif;
    font-size: 1rem;
    justify-content: center;
    margin: 0;
    padding: 1rem;
    text-align: center;
    text-transform: uppercase;
}

.shepherd-element .welcomWindow{



}

#welcomeText{
    grid-row: 2;
    grid-column: 1/4;
}


.fridgeList {
  display: flex;
  grid-column: 2;
  flex-direction: column;
  align-items: stretch;
  flex-wrap: nowrap;
}

@media only screen and (max-width: 860px) {
  .wrapper{
    grid-template-columns: 10% 80% 10%;
  }
}

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
    max-height: inherit;
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