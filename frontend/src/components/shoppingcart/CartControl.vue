<template>
  <div class="cart-control">
    <button class="control-buttons" type="button" @click="handleMarkAll">
      {{ $t("mark_all") }}
    </button>

    <button class="control-buttons" id="buy" type="button" @click="handleBuy">
      {{ $t("buy") }}
    </button>

    <button class="control-buttons" type="button" @click="handleDelete">
      {{ $t("delete") }}
    </button>
  </div>
</template>

<script>
export default {
  props: {},
  methods: {
    handleMarkAll() {
      this.$emit("check-all");
    },
    handleBuy() {
      this.$emit("buy");
    },
    handleDelete() {
      this.$emit("delete");
    },
  },
};
</script>
<style scoped>
.cart-control {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px 16px 10px;
  background-color: #6c6c6c;
}

.control-buttons {
  display: inline-block;
  margin: 0;
  padding: 8px 16px;
  font-size: 16px;
  font-weight: bold;
  text-align: center;
  text-transform: uppercase;
  color: #fff;
  background-color: #007aff;
  border: none;
  border-radius: 20px; /* add rounded edges to the buttons */
  cursor: pointer;
}

#buy{
  margin-left: auto;
  margin-right: 3%;
}

.control-buttons:hover {
  background-color: #0061b5;
}

.control-buttons:nth-child(2) {
  background-color: #34c759;
}

.control-buttons:nth-child(2):hover {
  background-color: #23863c;
}

.control-buttons:last-child {
  background-color: red;
}

.control-buttons:last-child:hover {
  background-color: #570000;
}

@media only screen and (min-width: 10px) and (max-width: 650px) {
  .cart-control {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 4px 0 4px;
    background-color: white;
    border-radius: 4px;
  }

  .control-buttons {
    display: inline-block;
    margin: 0;
    padding: 10px 16px;
    font-size: 13px;
    font-weight: bold;
    text-align: center;
    text-transform: uppercase;
    color: #fff;
    background-color: #007aff;
    border: none;
    border-radius: 20px; /* add rounded edges to the buttons */
    cursor: pointer;
  }

  .control-buttons:hover {
    background-color: #0061b5;
  }

  .control-buttons:nth-child(2) {
    background-color: #34c759;
  }

  .control-buttons:last-child {
    background-color: red;
  }

  .control-buttons:last-child:hover {
    background-color: #30b34e;
  }
}
</style>
