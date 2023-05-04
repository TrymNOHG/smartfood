<template>
  <div class="grey-bar" ref="installElement">
    <h2 id="grey-header" >{{ $t('fridges') }}</h2>
    <div class="information-button">
      <img src="@/assets/images/info.svg" id="info-picture" @click="resetSteps(); informationButton()" :alt=" $t('alt_info_button') ">
    </div>
  </div>

  <div class="wrapper">
    <div class="fridgeList">
      <fridge-component ref="usageElement" />
    </div>
  </div>

</template>

<script>
import FridgeComponent from "../components/FridgeList/FridgesComponent.vue";
import Shepherd from 'shepherd.js';
import '@/assets/tourStyle.css';
import {computed, ref} from 'vue';
import {useFridgeStore} from "@/store/store";
import { useRoute } from 'vue-router'
import { offset } from "@floating-ui/vue";
import router from "@/router/router";


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



      const information = new Shepherd.Tour({
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

      /*const applicationTour = new Shepherd.Tour({
          useModalOverlay: true,
          defaultStepOptions: {
              classes: 'shepherd-has-cancel-icon shepherd-element class-1 class-2 shepherd-enabled',
              floatingUIOptions: {
                  middleware: [offset(30)]
              },
              cancelIcon: {
                  enabled: true
              },
          },

      });*/

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
        //applicationTour,
        fridgesTour,
        installElement,
        usageElement,
        hasCurrentFridge,
        information,
      }
    },

    created() {
        if(this.init) {
            this.$ref.firstLogginTour();
        }
    },

    methods: {
      resetSteps(){
        // if(this.$ref.applicationTour.steps.length !== 0) {
        //     while (this.$ref.applicationTour.steps.length !== 0) {
        //         this.$ref.applicationTour.steps.pop()
        //     }
        // }
        if(this.fridgesTour.steps.length !== 0) {
            while (this.fridgesTour.steps.length !== 0) {
                this.fridgesTour.steps.pop()
            }
        }
          if(this.information.steps.length !== 0) {
              while (this.information.steps.length !== 0) {
                  this.information.steps.pop()
              }
          }
      },

      /*
        firstLogginTour() shows a welcome message to a new registered user, and shows the user a tour if user wants
        information about the application
       */
 /*     firstLogginTour(){
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
                useModalOverlay: true,
                floatingUIOptions: {

                },
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

            {
                id: 'headertour',
                text:this.$t('tour: view:fridgesView method:firstLogginTour id:header usage:text'),
                title:this.$t('tour: view:fridgesView method:firstLogginTour id:header usage:title'),

                attachTo: {
                    element: '.current-fringe',
                    on: 'bottom',
                },
                modalOverlayOpeningPadding:'4000',
                buttons:[
                    {
                        action: function () {
                            return this.back();
                        },
                        secondary: true,
                        text: this.$t('tour: button back'),
                    },
                    {
                        action: function () {
                            return this.next();
                        },
                        text: this.$t('tour: button next'),
                    },
                ]
            },
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
                            return this.back();
                        },
                        class: " shepherd-button ",
                        secondary: true,
                        text: this.$t('tour: button back'),
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
                            return this.back();
                        },
                        secondary: true,
                        text: this.$t('tour: button back'),
                    },
                    {
                        action: function () {
                            return this.next();
                        },
                        text: this.$t('tour: button next'),
                    },
                ]
            },
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
                            return this.back();
                        },
                        secondary: true,
                        text: this.$t('tour: button back'),
                    },
                    {
                        action: function () {
                            return this.next();
                        },
                        text: this.$t('tour: button next'),
                    },
                ]
            },
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
                            return this.back();
                        },
                        secondary: true,
                        text: this.$t('tour: button back'),
                    },
                    {
                        action: function () {
                            return this.next();
                        },
                        text: this.$t('tour: button next'),
                    },
                ]
            },

            {
                id: 'fridgesWindow',
                title: this.$t('tour: view:fridgesView method:firstLogginTour id:fridgesWindow usage:title'),
                text: this.$t('tour: view:fridgesView method:firstLogginTour id:fridgesWindow usage:text'),
                attachTo: {
                    element: 'div.router-view-container',
                    /!*on: 'bottom',*!/
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
                        action: function () {
                            return this.next();
                        },
                        text: this.$t('tour: button next'),
                    },
                ]
            },

            {
                id: 'grey-field-header',
                title: this.$t('tour: view:fridgesView method:fridgesStepTour id:grey-field-header usage:title'),
                text: this.$t('tour: view:fridgesView method:fridgesStepTour id:grey-field-header usage:text'),
                attachTo: {
                    element: '#grey-header',
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
                        action: function () {
                            return this.next();
                        },
                        text: this.$t('tour: button next'),
                    },
                ]
            },
            {
                id: 'firstLogginTour-9',
                title: this.$t('tour: view:fridgesView method:fridgesStepTour id:firstLogginTour-9 usage:title'),
                text: this.$t('tour: view:fridgesView method:fridgesStepTour id:firstLogginTour-9 usage:text'),
                attachTo: {
                    element: '#Fridge0',
                    on: 'top',
                },
                classes: '',
                advanceOn:{selector: '#Fridge0', event: 'click'},
                buttons: [
                    {
                        action: function () {
                            return this.back();
                        },
                        secondary: true,
                        text: this.$t('tour: button back'),
                    },
                ],

            },
            {
                id: 'nav-tour-fridge',
                text: this.$t('tour: view:fridgesView method:fridgesStepTour id:profile-tour usage:text'),
                title: this.$t('tour: view:fridgesView method:fridgesStepTour id:profile-tour usage:title'),
                attachTo: {
                    element: '#app-nav',
                    on: 'bottom',
                },
                classes: '',
                buttons: [
                    {
                        action: function () {
                            return this.back();
                        },
                        class: " shepherd-button ",
                        secondary: true,
                        text: this.$t('tour: button back'),
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


        ])
          this.applicationTour.start()
      },*/


      informationButton(){
        this.information.addStep(
          {
              id: 'information-pressed',
              title:`<div class="info-box"><img src="../src/assets/images/info.svg" alt="Pressed" id="tour-info-picture"/></div>`,
              text: this.$t('tour: view:fridgesView method:informationButton id:information-pressed usage:text'),
              attachTo: {
                element: '#info-picture',
                    on: 'bottom',
                },
              buttons: [
                  {
                      action: () => {
                          //router.push('/fridges');
                          this.$emit.firstLogginTour();
                          this.information.cancel();
                      },
                      secondary: true,
                      class: " shepherd-button ",
                      text: this.$t('tour: button whole site'),
                  },
                  {
                      action: () => {
                          this.fridgesStepTour();
                          this.information.cancel();

                      },
                      class: " shepherd-button ",
                      text: this.$t('tour: button this site'),
                  },
              ]
          })
        this.information.start()
      },

      /*
      stepTour() is a information-button user can press to get information about the functionality to the site
     */
      fridgesStepTour() {
          //tour for PC
          if (window.matchMedia("(min-width: 860px)").matches) {
              this.fridgesTour.addSteps([
                  //Information of profile button

                  // {
                  //     id: 'profile-tour',
                  //     text: this.$t('tour: view:fridgesView method:fridgesStepTour id:profile-tour usage:text'),
                  //     title: this.$t('tour: view:fridgesView method:fridgesStepTour id:profile-tour usage:title'),
                  //     attachTo: {
                  //         element: '#profile',
                  //         on: 'left',
                  //     },
                  //     classes: '',
                  //     buttons: [
                  //         {
                  //             action: function () {
                  //                 return this.cancel();
                  //             },
                  //             class: " shepherd-button ",
                  //             secondary: true,
                  //             text: this.$t('tour: button exit'),
                  //         },
                  //         {
                  //             action: function () {
                  //                 return this.next();
                  //             },
                  //             class: " shepherd-button ",
                  //             text: this.$t('tour: button next'),
                  //         },
                  //     ]
                  // },
                  //
                  // //Information about internationalization
                  // {
                  //     id: 'language-tour',
                  //     text: this.$t('tour: view:fridgesView method:fridgesStepTour id:language-tour usage:text'),
                  //     title: this.$t('tour: view:fridgesView method:fridgesStepTour id:language-tour usage:title'),
                  //     attachTo: {
                  //         element: '.language',
                  //         on: 'bottom',
                  //     },
                  //     classes: '',
                  //     buttons: [
                  //         {
                  //             action: function () {
                  //                 return this.cancel();
                  //             },
                  //             secondary: true,
                  //             text: this.$t('tour: button exit'),
                  //         },
                  //         {
                  //             action: function () {
                  //                 return this.next();
                  //             },
                  //             text: this.$t('tour: button next'),
                  //         },
                  //     ]
                  // },
                  //
                  // //Information about fridge name and function
                  // {
                  //     id: 'fridge-name-info',
                  //     title: this.$t('tour: view:fridgesView method:fridgesStepTour id:fridge-name-info usage:title'),
                  //     text: this.hasCurrentFridge ? this.$t('tour: view:fridgesView method:fridgesStepTour id:fridge-name-info usage:text-picked fridge') :
                  //         this.$t('tour: view:fridgesView method:fridgesStepTour id:fridge-name-info usage:text-not picked fridge'),
                  //     attachTo: {
                  //         element: '.fridge-name',
                  //         on: 'top',
                  //     },
                  //     classes: '',
                  //     buttons: [
                  //         {
                  //             action: function () {
                  //                 return this.cancel();
                  //             },
                  //             secondary: true,
                  //             text: this.$t('tour: button exit'),
                  //         },
                  //         {
                  //             action: function () {
                  //                 return this.next();
                  //             },
                  //             text: this.$t('tour: button next'),
                  //         },
                  //     ]
                  // },
                  //
                  // //Information about exit button
                  // {
                  //     id: 'exit-fridge-button-info',
                  //     title: this.$t('tour: view:fridgesView method:fridgesStepTour id:exit-fridge-button-info usage:title'),
                  //     text: this.hasCurrentFridge ? this.$t('tour: view:fridgesView method:fridgesStepTour id:exit-fridge-button-info usage:text-picked fridge ') :
                  //         this.$t('tour: view:fridgesView method:fridgesStepTour id:exit-fridge-button-info usage:text-not picked fridge'),
                  //     attachTo: {
                  //         element: '.change-button',
                  //         on: 'bottom'
                  //     },
                  //     classes: '',
                  //     buttons: [
                  //         {
                  //             action: function () {
                  //                 return this.cancel();
                  //             },
                  //             secondary: true,
                  //             text: this.$t('tour: button exit'),
                  //         },
                  //         {
                  //             action: function () {
                  //                 return this.next();
                  //             },
                  //             text: this.$t('tour: button next'),
                  //         },
                  //     ]
                  // },

                  //Information about header in grey field
                  // {
                  //     id: 'grey-field-header',
                  //     title: this.$t('tour: view:fridgesView method:fridgesStepTour id:grey-field-header usage:title'),
                  //     text: this.$t('tour: view:fridgesView method:fridgesStepTour id:grey-field-header usage:text'),
                  //     attachTo: {
                  //         element: '#grey-header',
                  //         on: 'bottom',
                  //     },
                  //     classes: 'shepherd-theme-arrows',
                  //     buttons: [
                  //         {
                  //             action: function () {
                  //                 return this.cancel();
                  //             },
                  //             secondary: true,
                  //             text: this.$t('tour: button exit'),
                  //         },
                  //         {
                  //             action: function () {
                  //                 return this.next();
                  //             },
                  //             text: this.$t('tour: button next'),
                  //         },
                  //     ]
                  // },

                  //grey-bar
                  //wrapper
                  {
                      id: 'fridgesWindow',
                      title: this.$t('tour: view:fridgesView method:firstLogginTour id:fridgesWindow usage:title'),
                      text: this.$t('tour: view:fridgesView method:firstLogginTour id:fridgesWindow usage:text'),
                      attachTo: {
                          element: 'div.router-view-container',
                          on: 'bottom',
                      },

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

                  //Information about the fridge list
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
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
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
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
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
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
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
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
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
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
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
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
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
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
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
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
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
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
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
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
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
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
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

.info-box{
    display: flex;
    background: #3b3b3b;
    border-radius: 200px;
    justify-content: center;
    align-content: center;
}

#tour-info-picture{
    height: inherit;
    width: auto;
}

.app-header{

}

.current-fringe{

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

.grey-bar {
  background-color: #6C6C6C;
  text-align: center;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  max-height: 40px;
}

#grey-header{
  grid-column: 2;
  color: white;
  font-family: Roboto, sans-serif;
}

.information-button{
  display: flex;
  justify-content: center;
  align-content: center;
  align-items: center;
  margin-left: auto;
}

#info-picture{
  height: 30px;
  width: 30px;
  cursor: pointer;
  margin: 4px;

}

#info-picture:hover{
  scale: 1.1;

}

@media only screen and (max-width: 860px) {
  .wrapper{
    grid-template-columns: 10% 80% 10%;
  }
}

@media only screen and (min-width: 10px) and (max-width: 650px) {

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