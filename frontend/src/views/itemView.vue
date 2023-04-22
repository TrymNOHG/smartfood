<template>
  <div>
    <h1>Item View</h1>
    <p>Current Item Name: {{ $route.params.itemName }}</p>
    <p>Current Item ID: {{ $route.params.itemId }}</p>
  </div>
</template>

<script>
export default {
  name: "itemView"
}
</script>

<style scoped>

</style>
