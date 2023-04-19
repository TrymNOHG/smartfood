<template>

    <div class="wrapper">
      <fridge-component/>
    </div>
  
  </template>

<script>
import FridgeComponent from "../components/FridgeComponent.vue";
export default {
  name: "FridgeView",
  components: {FridgeComponent}
}
</script>