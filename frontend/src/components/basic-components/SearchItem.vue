<template>
    <div class="search-item">
      <img class="search-image" :src="image" />
      <div class="text">{{ text }}</div>
      <div class="store">{{ store }}</div>
      <div class="price">{{ price + " KR"}}</div>
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

  .search-item {
    align-items: center;
    justify-content: center;
    padding: 8px;
  }
  
  .search-item img {
   width: 10%;
   height: 12%;
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
  }
  </style>