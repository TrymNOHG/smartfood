<template>
  <div class="wrapper">
    <basic-fridge-item :item="item"/>
  </div>
</template>

<script>
import BasicFridgeItem from "@/components/basic-components/BasicFridgeItem.vue";

export default {
  name: "SpecificFridgeComponent",
  components: {BasicFridgeItem},

  props: {
    item: {
      type: Object,
    }
  }
}
</script>

<style scoped>

.wrapper {
}

</style>
