<template>

    <div>
      <dinner-component/>
    </div>
  
  </template>

<script>
import DinnerComponent from "../components/DinnerComponent.vue";
export default {
  name: "DinnerView",
  components: {DinnerComponent}
}
</script>