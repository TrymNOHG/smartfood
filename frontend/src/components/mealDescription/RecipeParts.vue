<template>
  <div class="recipe-parts">
    <div v-for="part in recipeParts" :key="part.partName">
      <h3>{{ part.partName || $t('default_part_name') }}</h3>
      <div class="item-info" v-for="item in part.ingredients" :key="item.itemId" :class="{ 'has-item': item.hasItem, 'no-item': !item.hasItem }">
        <span class="item-status">{{ item.hasItem ? '✓' : '✗' }}</span>
        <h4>{{ item.name }}</h4>
        <p>{{ item.quantity }} {{ item.unitOfMeasurement }}</p>
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
  padding: 1em;
}

.item-status {
  font-size: 1.2em;
  margin-right: 0.5em;
  vertical-align: middle;
}

.has-item .item-status {
  color: green;
}

.no-item .item-status {
  color: red;
}

.has-item {
  border: 2px solid green;
}

.no-item {
  border: 2px solid red;
}
</style>