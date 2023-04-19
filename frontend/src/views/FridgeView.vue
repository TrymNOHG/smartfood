<template>
  <h2>{{ $t('fridges') }}</h2>
  <div class="wrapper">
      <div class="fridgeList">
        <fridge-component/>
      </div>
    </div>
  
  </template>

<script>
import FridgeComponent from "../components/FridgeComponent.vue";
export default {
  name: "FridgeView",
  components: {FridgeComponent}
}
</script>

<style>

template {
  display: flex;
  flex-direction: column;
  align-items: center;
}

h2 {
  background-color: #6C6C6C;
  color: white;
  text-align: center;
}

.wrapper {
  display: grid;
  grid-template-columns: 25% 50% 25%;
}

.fridgeList {
  display: flex;
  grid-column: 2;
  flex-direction: column;
  align-items: stretch;
  flex-wrap: nowrap;
}

@media only screen and (max-width: 600px) {
  .wrapper{
    grid-template-columns: 10% 80% 10%;
  }
}

</style>