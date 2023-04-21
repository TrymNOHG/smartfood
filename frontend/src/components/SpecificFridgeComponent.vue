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
}

</style>