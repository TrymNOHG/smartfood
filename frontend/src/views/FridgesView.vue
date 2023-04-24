<template>
  <h2 class="h2_fridge">{{ $t('fridges') }}</h2>
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
  components: {FridgeComponent}
}
</script>

<style>

template {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.h2_fridge {
  background-color: #6C6C6C;
  color: white;
  text-align: center;
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

</style>