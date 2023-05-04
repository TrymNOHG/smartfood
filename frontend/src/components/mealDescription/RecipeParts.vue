<template>
  <div class="recipe-parts">
    <div v-for="part in recipeParts" :key="part.partName">
      <h3>{{ part.partName || $t('default_part_name') }}</h3>
      <div
          v-for="item in part.ingredients"
          :key="item.itemId"
          :class="{ 'has-item': item.fridgeAmount >= item.quantity, 'no-item': item.fridgeAmount < item.quantity }"
      >
        <span class="item-status">{{ item.fridgeAmount >= item.quantity ? '✓' : '✗' }}</span>
        <h4>{{ item.name }}</h4>
        <p>{{ item.quantity }} {{ item.unitOfMeasurement }}</p>
        <p>{{ $t('you_have') }}: {{ getItemQuantity(item) }} {{ item.unitOfMeasurement }}</p>
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

  methods: {
    getItemQuantity(ingredient) {
      let itemQuantity = ingredient.fridgeAmount;
      if (ingredient.itemOriginalUnit === "pieces" && ingredient.unitOfMeasurement !== "c") {
        itemQuantity = itemQuantity * 250;
      }
      return itemQuantity;
    },
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
  display: flex;
  align-items: center;
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

.item-info h4,
.item-info p {
  margin: 0;
  margin-right: 1em;
}

.item-info p:last-child {
  margin-right: 0;
}
</style>
