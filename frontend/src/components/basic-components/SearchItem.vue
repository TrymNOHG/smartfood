<template>
    <div class="search-item">
      <img :src="image" />
      <div class="text">{{ text }}</div>
      <div class="price">{{ price }}</div>
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
    },
  };
  </script>
  
  <style>
  .search-item {
    display: flex;
    align-items: center;
    padding: 8px;
  }
  
  .search-item img {
    width: 50px;
    height: 50px;
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