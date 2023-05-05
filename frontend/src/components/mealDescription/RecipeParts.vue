<template>
  <div class="recipe-parts">
    <div v-for="part in recipeParts" :key="part.partName" class="recipe-parts">
      <h3>{{ part.partName || $t('default_part_name') }}</h3>
      <div
          v-for="item in part.ingredients"
          :key="item.itemId"
          class="item-info"
          :class="{ 'has-item': getItemQuantity(item) >= item.quantity, 'no-item': getItemQuantity(item) < item.quantity }"
      >
        <span class="item-status">{{ getItemQuantity(item) >= item.quantity ? '✓' : '✗' }}</span>
        <h4>{{ item.name }}</h4>
        <p>{{ item.quantity }} {{ $t(item.unitOfMeasurement) }}</p>
        <p>{{ $t('you_have') }}: {{ getItemQuantity(item) }} {{ $t(item.unitOfMeasurement) }}</p>
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
  border-radius: 8px;
  border: 2px solid #ccc;
  background-color: #fff;
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
