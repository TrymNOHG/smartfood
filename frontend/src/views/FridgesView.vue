<template>
  <div class="grey-bar" ref="installElement">
    <h2 id="grey-header" >{{ $t('fridges') }}</h2>
    <div class="information-button">
      <img src="@/assets/images/info.svg" id="info-picture" @click="showInformation" :alt=" $t('alt_info_button') ">
    </div>
  </div>

  <div class="wrapper">
    <div class="fridgeList">
      <fridge-component ref="usageElement" id="fridges"/>
    </div>
  </div>

  <!--  <div ref="el">-->
  <!--    Testing-->
  <!--  </div>-->

</template>

<script>
import FridgeComponent from "../components/FridgeList/FridgesComponent.vue";
import Shepherd from 'shepherd.js';
import 'shepherd.js/dist/css/shepherd.css';
import {computed, ref} from 'vue';
import {useFridgeStore} from "@/store/store";

export default {
  name: "FridgesView",
  components: {FridgeComponent},


  setup() {

      const fridgeStore = useFridgeStore();
      const hasCurrentFridge = computed(() => fridgeStore.hasCurrentFridge);
      const installElement = ref(null);
      const usageElement = ref(null);

      const tour = new Shepherd.Tour({
        useModalOverlay: true,
        defaultStepOptions: {
          classes: 'shepherd-theme-arrows',

        }
      });

      tour.addSteps([

        //Information of profile button
        {
          id: 'profile-tour',
          text: 'This is the profile button',
          attachTo: {
            element: '#profile',
            on: 'left',
            offset: {x: 40, y: 0},
          },
          classes:'shepherd-theme-arrows',
          buttons:[
            {
              action: function () {
                return this.cancel();
              },
              secondary: true,
              text: 'Exit',
            },
            {
              action: function () {
                return this.next();
              },
              text: 'Next',
            },
          ]
        },

          //Information about internationalization
        {
          id: 'language-tour',
          text: 'Here you can change the language of the page. Choose between English and Norwegian',
          attachTo: {
            element: '.language',
            on: 'bottom',
            offset: {x: 40, y: 0},
          },
          classes:'shepherd-theme-arrows',
          buttons:[
            {
              action: function () {
                return this.cancel();
              },
              secondary: true,
              text: 'Exit',
            },
            {
              action: function () {
                return this.next();
              },
              text: 'Next',
            },
          ]
        },

        //Information about fridge name and function
        {
          id: 'fridge-name-info',
          text: hasCurrentFridge ?
              'This is the name of the fridge your are in. \n\n Press on the name, and you will navigate to your fridge'
              : 'Here, the name of the fridge you choose will be displayed \n\n Press on the name, and you will navigate to your fridge',
          attachTo: {
            element: '.fridge-name',
            on: 'top',
            offset: {x: 0, y: 20},
          },
          classes:'shepherd-theme-arrows',
          buttons:[
            {
              action: function () {
                return this.cancel();
              },
              secondary: true,
              text: 'Exit',
            },
            {
              action: function () {
                return this.next();
              },
              text: 'Next',
            },
          ]
        },

        //Information about exit button
        {
          id: 'exit-fridge-button-info',
          text: hasCurrentFridge ?
              'This button can you press to exit or "escape" the fridge and see the list of all your fridges'
              : 'This button can you press to exit or "escape" the fridge and see the list of all your fridges \n\n ' +
              'But as you see now, the man wants to enter a fridge, so please choose a fridge to continue',
          attachTo: {
            element: '.change-button',
            on: 'bottom'
          },
          classes:'shepherd-theme-arrows',
          buttons:[
            {
              action: function () {
                return this.cancel();
              },
              secondary: true,
              text: 'Exit',
            },
            {
              action: function () {
                return this.next();
              },
              text: 'Next',
            },
          ]
        },

        //Information about header in grey field
        {
          id: 'grey-field-header',
          text: `This grey section will show you the header of pages, like \" $t('fridges') \" \n\n and it shows you the intern navigation bar within a main page`,
          attachTo: {
            element: '#grey-header',
            on: 'left',
            offset: {x: 0, y: 20},
          },
          classes:'shepherd-theme-arrows',
          buttons:[
            {
              action: function () {
                return this.cancel();
              },
              secondary: true,
              text: 'Exit',
            },
            {
              action: function () {
                return this.next();
              },
              text: 'Next',
            },
          ]
        },

        //Information the fridge list
        {
          id: 'fridge-tour',
          text: 'Here you can see the list of all your fridges',
          attachTo: {
            element: '#fridgeList',
            on: 'top',
            offset: {x: 0, y: 20},
          },
          classes:'shepherd-theme-arrows',
          buttons:[
            {
              action: function () {
                return this.cancel();
              },
              secondary: true,
              text: 'Exit',
            },
            {
              action: function () {
                return this.next();
              },
              text: 'Next',
            },
          ]
        },

        //Information about one fridge
        {
          id: 'fridge-tour',
          text: 'Here is your first fridge. You enter the fridge by clicking on this fridge. The functions inside the fridge is: \n\n ' +
              '- See all items in it. \d\d\d\d' +
              '- Make grocery-list. \d\d' +
              '- Add members to share the fridge with. \n\n' +
              '- See suggestions on meals you can make with the food in the fridge, and see the recipe. \n\n' +
              '- Get a suggestion on weekly menu. \n\n' +
              '- See statistics on what you have thrown in the garbage, and match it up with others. \n\n' +
              '\n\n\n\n Can you make a difference?',
          attachTo: {
            element: '#Fridge0',
            on: 'bottom',
            offset: {x: 0, y: 20},
          },
          classes:'shepherd-theme-arrows',
          buttons:[
            {
              action: function () {
                return this.cancel();
              },
              secondary: true,
              text: 'Exit',
            },
            {
              action: function () {
                return this.next();
              },
              text: 'Next',
            },
          ]
        },

        //Information about change information on a fridge
        {
          id: 'fridge-change-tour',
          text: 'To change the name of the fridge, you can press the "change button" or you can delete the fridge by clicking the bin',
          attachTo: {
            element: '#EditFridge0',
            on: 'bottom',
            offset: {x: 0, y: 20},
          },
          classes:'shepherd-theme-arrows',
          buttons:[
            {
              action: function () {
                return this.cancel();
              },
              secondary: true,
              text: 'Exit',
            },
            {
              action: function () {
                return this.next();
              },
              text: 'Next',
            },
          ]
        },

        //Information about adding fridge
        {
          id: 'add-fridge-tour',
          text: 'You can add a new fridge to, by clicking this plus button, and type in the name of the fridge',
          attachTo: {
            element: '.container_button',
            on: 'top',
            offset: {x: 0, y: 20},
          },
          classes:'shepherd-theme-arrows',
          buttons:[
            {
              action: function () {
                return this.cancel();
              },
              secondary: true,
              text: 'Exit',
            },
            {
              action: function () {
                return this.next();
              },
              text: 'Next',
            },
          ]
        },

        //Return to informationbutton
        {
          id: 'information-button',
          text: 'You can click on the information-button all the time to get a tour on the current page',
          attachTo: {
            element: '#info-picture',
            on: 'top',
            offset: {x: 0, y: 20},
          },
          classes:'shepherd-theme-arrows',
          buttons:[
            {
              action: function () {
                return this.cancel();
              },
              text: 'Exit',
            },
          ]
        },

      ]);

      return {
        tour,
        installElement,
        usageElement,
        hasCurrentFridge,
      }
  },

  methods: {
    showInformation(){
      this.tour.start();
      //TODO: INFORMATION FRIDGES put information API in here
    },
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