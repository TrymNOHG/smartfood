<template>
  <div class="grey-bar">
    <h2 id="grey-header" >{{ $t('fridges') }}</h2>
    <div class="information-button">
      <img src="src/assets/images/info.svg" id="info-picture" @click="showInformation" :alt=" $t('alt_info_button') ">
    </div>
  </div>

  <div class="wrapper">
      <div class="fridgeList">
        <fridge-component/>
      </div>
  </div>
  
</template>

<script>
import FridgeComponent from "../components/FridgeList/FridgesComponent.vue";


export default {
  name: "FridgesView",
  components: {FridgeComponent},

  methods: {
    showInformation(){
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