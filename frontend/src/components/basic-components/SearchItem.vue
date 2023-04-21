<template>
    <div class="search-item">
      <div class="item-info" @click="$emit('click')">
        <img class="search-image" :src="image" />
        <div class="text">product: <br>{{ text }}</div>
        <div class="store">store: <br>{{ store }}</div>
        <div class="price">price: <br>{{ price + " KR"}}</div>
        <font-awesome-icon icon="fa-solid fa-cart-shopping" class="cart-icon" />
      </div>
    </div>
  </template>
<script>
export default {
  props: {
    image: {
      type: String,
      required: true,
    },
    text: {
      type: String,
      required: true,
    },
    price: {
      type: Number,
      required: true,
    },
    store: {
      type: String,
      required: true,
    },
  },
};
</script>

<style>
.item-info:hover {
  box-shadow: 0 0 5px #ccc;
  transform: scale(1.02);
  cursor: pointer;
  transition: all 0.3s ease;
}
.cart-icon{
    margin-left: 5%;
    margin-top: 20px;
}

.search-item {
  display: flex;
  justify-content: space-between;
  align-items: center;  
  justify-content: center;
  padding: 15px;
  color: #444444 !important;
}

.item-info {
  display: flex;
  align-items:flex-start;
  width: 70vw;

}

.item-action {
  margin-left: 1rem; /* adjust as needed */
}

.search-item img {
  width: 5%;
  height: 9%;
  max-height: 120px;
  max-width: 200px;
  margin-right: 16px;
}


.search-item .text {
  flex: 1;
  font-weight: bold;
  
}

.search-item .price {
  font-weight: bold;
  margin-left: 2vw;
}
</style>
