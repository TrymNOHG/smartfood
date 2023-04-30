<template>
  <div class="item-header">
    <div class="item-image">
      <img :src="item.image" alt="item picture" />
    </div>
    <h1 class="item-name">{{ item.name }}</h1>
  </div>
</template>

<script>
import { number } from "yup";

export default {
  name: "itemHeader",

  props: {
    item: {
      type: Object,
      default: () => ({
        description: String,
        expirationDate: String,
        image: String,
        name: String,
        price: String,
        purchaseDate: String,
        quantity: number,
        store: String,
      }),
    },
  },
};
</script>

<style scoped>
.item-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.item-image {
  width: 200px;
  height: 150px;
  margin-right: 50px;

  position: relative;
}

.item-image img {
  width: 95%;
  height: 95%;
  object-fit: contain;
  position: absolute;
  bottom: 0;
  right: 0;
}

.item-name {
  font-size: 24px;
  font-weight: bold;
  border-bottom: 2px solid black;
  padding-bottom: 5px;
  display: flex;
  flex-direction: row-reverse;
  justify-content: flex-start;
}

@media only screen and (max-width: 768px) {
  .item-header {
    margin-bottom: 10px;
  }

  .item-image {
    width: 200px;
    height: 150px;
  }

  .item-name {
    font-size: 20px;
  }
}

@media only screen and (min-width: 10px) and (max-width: 650px) {
}
</style>
