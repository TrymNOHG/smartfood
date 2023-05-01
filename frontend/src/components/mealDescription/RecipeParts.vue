<template>
  <div class="recipe-parts">
    <div v-for="part in recipeParts" :key="part.partName">
      <h3>{{ part.partName || $t('default_part_name') }}</h3>
      <div class="item-info" v-for="item in part.ingredients" :key="item.itemId">
        <h4>{{ item.name }}</h4>
        <p>{{ item.quantity }} {{ item.unitOfMeasurement }}</p>
        <p>{{ $t('price') }}: {{ item.price }}</p>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'RecipeParts',

  components: {},

  props: {
    recipeParts: Array,
  },
};
</script>
<style scoped>
.recipe-parts {
  padding: 20px;
}

.recipe-parts h3 {
  margin-top: 1em;
}

.item-info {
  margin: 1em 0;
}
</style>