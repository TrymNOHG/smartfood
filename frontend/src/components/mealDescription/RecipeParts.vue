<template>
  <div class="recipe-parts">
    <div v-for="part in recipeParts" :key="part.recipePartId">
      <h3>{{ part.partTitle }}</h3>
      <p>{{ part.partDescription }}</p>
      <div class="item-info" v-for="item in part.recipeItems" :key="item.itemId">
        <h4>{{ $t('item-info') }}</h4>
        <h5>{{ item.itemId }}</h5>
        <h5>{{ item.quantity }}</h5>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'RecipeParts',

  components: {
  },

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
