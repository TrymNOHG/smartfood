<template>
  <input
    v-bind="$attrs"
    :value="modelValue"
    :placeholder="label"
    @input="$emit('update:modelValue', $event.target.value)"
    class="form-control"
    :style="{ borderColor: error ? 'red' : '' }"
    aria-invalid="error ? true : null"
  />
  <font-awesome-icon
    id="receipt-btn"
    icon="fa-solid fa-receipt"
    style="color: #2c831b; scale: 2"
    @click="uploadReceiptButton"
  />

  <p v-if="error" class="errorMessage" aria-live="assertive">
    {{ error }}
  </p>
  <div id="shadow"></div>
</template>

<script>
export default {
  props: {
    label: {
      type: String,
      default: "",
    },
    modelValue: {
      type: [String, Number],
      default: "",
    },
    error: {
      type: String,
      default: "",
    },
  },
  methods: {
    uploadReceiptButton() {
      this.$emit("receipt-upload");
    },
  },
};
</script>

<style scoped>
#receipt-btn {
  transform: translate(-100%, 45%);
}

#receipt-btn:hover {
  transform: translate(-100%, 45%);
  cursor: pointer;
}

#searchbar {
  display: flex;
}

.form-control {
  padding: 0px 10px 0 20px;
  color: #333;
  font-size: 20px;
  width: 100%;
  font-weight: 500;
  border: 0px solid #555;
  box-shadow: none;
  height: 40px;
  border-radius: 50px 0 0 50px;
  margin-left: 10px;
  margin-right: -20px;
}

@media only screen and (min-width: 10px) and (max-width: 650px) {
  #receipt-btn {
    transform: none;
    position: absolute;
    margin: -40px 0 0 80vw;
  }

  #searchbar {
    display: inline-block;
  }

  .form-control {
    display: flex;
    align-items: flex-end;
    justify-content: space-evenly;
    position: fixed;
    bottom: 62px;
    width: 95%;
    height: 60px;
    font-size: 20px;
    background-color: white;
    border: 7px solid #31c48d;
    margin-left: 0;
    border-radius: 50px 50px 50px 50px !important;
  }

  #shadow {
    display: flex;
    align-items: center;
    justify-content: space-evenly;
    position: fixed;
    bottom: 70px;
    width: 95%;
    height: 30px;
    font-size: 20px;
    background-color: #31c48d;
    z-index: -2;
  }
}
</style>
