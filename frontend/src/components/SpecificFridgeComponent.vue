<template>
  <div class="wrapper">
    <basic-fridge-item/>
  </div>
</template>

<script>
import BasicFridgeItem from "@/components/basic-components/BasicFridgeItem.vue";

export default {
  name: "SpecificFridgeComponent",
  components: {BasicFridgeItem},

  props: {
    fridge: {
      fridgeId: null,
      fridgeName: ""
    }
  }
}
</script>

<style scoped>

.wrapper {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 200px));
  margin: 25px;
}

</style>