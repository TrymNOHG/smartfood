<template>
  <div class="search-item">
    <img class="item-var" id="search-image" :src="image" />
    <div class="item-var" id="text">{{ text }}</div>
    <div class="item-var" id="store">{{ store }}</div>
    <div class="item-var" id="price">{{ price + " KR" }}</div>
    <font-awesome-icon
      icon="fa-solid fa-cart-shopping"
      class="item-var"
      id="cart-icon"
    />
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

<style scoped>
.item-info:hover {
  box-shadow: 0 0 5px #ccc;
  transform: scale(1.02);
  cursor: pointer;
  transition: all 0.3s ease;
}
#cart-icon {
}

.item-action {
  margin-left: 1rem;
}

.search-item img {
  width: 5%;
  height: 9%;
  max-height: 120px;
  max-width: 200px;
}

img {
  object-fit: contain;
}

.search-item #text {
  flex: 1;
  font-weight: bold;
}

.search-item #price {
  font-weight: bold;
}

.item-info:hover {
  box-shadow: 0 0 5px #ccc;
  transform: scale(1.02);
  cursor: pointer;
  transition: all 0.3s ease;
}
#cart-icon {
}

.search-item {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding: 15px;
  width: 100%;
  color: #444444 !important;
}

.item-action {
  margin-left: 1rem;
}

.search-item img {
  width: 5%;
  height: 9%;
  max-height: 120px;
  max-width: 200px;
}

img {
  object-fit: contain;
}

.search-item {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
  height: 100px;
  margin-bottom: 20px;
}

.item-var {
  padding-right: 15px;
  padding-left: 15px;
}

#text {
  line-height: 1.5;
  position: relative;
  display: flex;
  flex-direction: column;
}

.search-item img {
  width: 100px;
  height: 100px;
  object-fit: contain;
}

#cart-icon {
  height: 25px;
}

@media only screen and (min-width: 10px) and (max-width: 480px) {
  .item-info:hover {
    box-shadow: 0 0 5px #ccc;
    transform: scale(1.02);
    cursor: pointer;
    transition: all 0.3s ease;
  }
  #cart-icon {
  }

  .search-item {
    display: flex;
    justify-content: center;
    align-items: flex-start;
    padding: 15px;
    width: 100%;
    color: #444444 !important;
  }

  .item-action {
    margin-left: 1rem;
  }

  .search-item img {
    width: 5%;
    height: 9%;
    max-height: 120px;
    max-width: 200px;
  }

  img {
    object-fit: contain;
  }

  .search-item #text {
    flex: 1;
    font-weight: bold;
  }

  .search-item #price {
    font-weight: bold;
  }
  .search-item {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0;
    height: 80px;
    margin-bottom: 10px;
  }

  .item-var {
    padding-right: 7px;
    padding-left: 7px;
  }

  #text {
    line-height: 1.5;
    position: relative;
    display: flex;
    flex-direction: column;
  }

  .search-item img {
    width: 60px;
    height: 60px;
    object-fit: contain;
  }

  #cart-icon {
    height: 25px;
  }
}
</style>
