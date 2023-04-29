<template>
  <div class="item">
    <div class="product-img">
      <div class="img-container">
        <img :src="image" alt="" />
      </div>
    </div>

    <div class="description">
      <span>{{ name }}</span>
    </div>

    <div class="quantity">
      <button
        v-if="isSuperUser"
        class="minus-btn"
        type="button"
        @click="handleSubtract"
      >
        <img src="@/assets/images/minus.svg" alt="" />
      </button>

      <input
        :disabled="!isSuperUser"
        class="number-input"
        type="number"
        name="name"
        :value="quantity"
        @input="$emit('update:quantity', $event.target.value)"
        @blur="$emit('quantity-updated', $event.target.value, item)"
      />

      <button
        v-if="isSuperUser"
        class="plus-btn"
        type="button"
        @click="handleAdd"
      >
        <img src="@/assets/images/plus.svg" alt="" />
      </button>
    </div>

    <div class="buttons">
      <font-awesome-icon
        class="btn-cart"
        v-if="isSuperUser"
        icon="fa-solid fa-shopping-cart"
        @click="handleBuyItem"
      />
      <font-awesome-icon
        class="btn-trash"
        icon="fa-solid fa-trash"
        @click="handleDeleteItem"
      />
      <input
        type="checkbox"
        class="btn-checkbox"
        v-if="isSuperUser"
        :checked="item.isChecked"
        @change="handleCheckedChange"
      />
    </div>
  </div>
</template>

<script>
import { reactive, watch } from "vue";
import BasicCheckbox from "@/components/basic-components/BasicCheckbox.vue";

export default {
  props: {
    image: {
      type: String,
      required: true,
    },
    item: {
      type: Object,
      required: true,
    },
    name: {
      type: String,
      required: true,
    },
    quantity: {
      type: Number,
      required: true,
    },
    isSuperUser: {
      type: Boolean,
      required: true,
    } /**
         isChecked: {
            type: Boolean,
            required: true
        },*/,
  },
  data() {
    return {
      inputValue: "",
    };
  },
  methods: {
    handleAdd() {
      this.$emit("add");
    },
    handleSubtract() {
      this.$emit("subtract");
    },
    handleDeleteItem() {
      this.$emit("delete-item");
    },
    handleCheckedChange(event) {
      //event.target.checked
      this.$emit("handle-checked", this.item, event.target.checked);
    },
    handleBuyItem() {
      this.$emit("handle-buy", this.item);
    },
  },
  components: { BasicCheckbox },
};
</script>
<style scoped>
.product-img {
  width: 100px;
  height: 100px;
  object-fit: contain;
}

.img-container {
  max-width: 100%;
  max-height: 100%;
  overflow: hidden;
}

.img-container img {
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
  margin-right: 16px;
  object-fit: contain;
}

.item:nth-child(3) {
  border-top: 1px solid #e1e8ee;
  border-bottom: 1px solid #e1e8ee;
}

.item .description {
  overflow-wrap: break-word !important;
}

.buttons .btn {
  margin: 0 15px 0 15px;
}

.btn:hover {
  cursor: pointer;
  scale: 1.3;
}

@keyframes animate {
  0% {
    background-position: left;
  }
  50% {
    background-position: right;
  }
  100% {
    background-position: right;
  }
}

.image {
  margin-right: 50px;
}

.description {
  justify-content: center;
  align-items: center;
  align-content: center;
  text-align: center;
  display: flex;
  flex-direction: column;
  color: black;
}

.description span:last-child {
  margin-top: 8px;
  color: black;
  white-space: normal;
  margin-bottom: 5px;
}

.quantity {
  display: flex;
  align-items: center;
  padding-top: 20px;
  text-align: center;
  justify-content: center;
}

.quantity input {
  -webkit-appearance: none;
  border: none;
  text-align: center;
  width: 32px;
  font-size: 16px;
  color: #43484d;
  font-weight: 300;
}

button[class*="btn"] {
  width: 30px;
  height: 30px;
  background-color: #e1e8ee;
  border-radius: 6px;
  border: none;
  cursor: pointer;
}

.minus-btn img {
  margin-bottom: 3px;
}

.plus-btn img {
  margin-top: 2px;
}

button:focus,
input:focus {
  outline: 0;
}

input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type="number"] {
  -moz-appearance: textfield;
}

.product-img {
  width: 100px;
  height: 100px;
  object-fit: contain;
}

.btn-cart {
}

.img-container {
  max-width: 100%;
  max-height: 100%;
  overflow: hidden;
}

.img-container img {
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
  margin-right: 16px;
  object-fit: contain;
}

.item:nth-child(3) {
  border-top: 1px solid #e1e8ee;
  border-bottom: 1px solid #e1e8ee;
}

.delete-btn,
.like-btn {
  display: inline-block;
  cursor: pointer;
}

.delete-btn {
  width: 18px;
  height: 17px;
  background: url("@/assets/images/delete-icn.svg") no-repeat center;
}

.is-active {
  animation-name: animate;
  animation-duration: 0.8s;
  animation-iteration-count: 1;
  animation-timing-function: steps(28);
  animation-fill-mode: forwards;
}

@keyframes animate {
  0% {
    background-position: left;
  }
  50% {
    background-position: right;
  }
  100% {
    background-position: right;
  }
}

.image {
  margin-right: 50px;
}

.quantity {
  display: flex;
  align-items: center;
  padding-top: 20px;
  text-align: center;
  justify-content: center;
}

.quantity input {
  -webkit-appearance: none;
  border: none;
  text-align: center;
  width: 32px;
  font-size: 16px;
  color: #43484d;
  font-weight: 300;
}

button[class*="btn"] {
  width: 30px;
  height: 30px;
  background-color: #e1e8ee;
  border-radius: 6px;
  border: none;
  cursor: pointer;
}

.minus-btn img {
  margin-bottom: 3px;
}

.plus-btn img {
  margin-top: 2px;
}

button:focus,
input:focus {
  outline: 0;
}

input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type="number"] {
  -moz-appearance: textfield;
}

.product-img {
  width: 100px; /* set a fixed width for the containing div */
  height: 100px; /* set a fixed height for the containing div */
}

.img-container {
  max-width: 100%; /* ensure the image does not exceed the container width */
  max-height: 100%; /* ensure the image does not exceed the container height */
  overflow: hidden; /* hide any overflow beyond the container */
}

.img-container img {
  width: auto; /* allow the image to maintain its aspect ratio */
  height: auto; /* allow the image to maintain its aspect ratio */
  max-width: 100%; /* ensure the image does not exceed the container width */
  max-height: 100%; /* ensure the image does not exceed the container height */
}

.delete-btn {
  display: none;
}

.product-img img {
  height: 100px;
  width: 100px; /* set a fixed width for the containing div */
  object-fit: contain; /* set a fixed height for the containing div */
}

.item {
  display: flex !important;
  height: 180px;
  padding-left: 10px !important;
  padding-right: 10px !important;
  margin-top: 10px;
}

.description span:first-child {
  /*empty for now*/
}

.buttons {
  display: flex;
  justify-content: center;
  align-content: center;
  align-items: center;
  padding-left: 10px;
  padding-right: 5px;
  width: 60px;
  scale: 1.5;
}

.btn-checkbox {
  margin: 10px;
}

.btn-checkbox:hover {
  margin: 10px;
  cursor: pointer;
  scale: 1.2;
}

.btn-trash {
  margin: 10px;
}

.btn-trash:hover {
  margin: 10px;
  cursor: pointer;
  scale: 1.2;
}

.btn-cart {
  margin: 10px;
}

.btn-cart:hover {
  margin: 10px;
  cursor: pointer;
  scale: 1.2;
}

#check {
  scale: 1.5;
}

.quantity {
  padding: 5px;
}

.number-input {
  background-color: transparent;
  margin-top: 10px;
  margin-left: 0px;
  margin-right: 0px;
  padding-bottom: 5px;
  padding-right: 5px;
}

.img-container {
  max-width: 150px; /* ensure the image does not exceed the container width */
  max-height: 150px; /* ensure the image does not exceed the container height */
}

.img-container img {
  max-width: 150px;
  max-height: 150px;
}

.is-active {
  animation-name: animate;
  animation-duration: 0.8s;
  animation-iteration-count: 1;
  animation-timing-function: steps(28);
  animation-fill-mode: forwards;
}

@keyframes animate {
  0% {
    background-position: left;
  }
  50% {
    background-position: right;
  }
  100% {
    background-position: right;
  }
}

@media only screen and (max-width: 800px) {
  .product-img {
    width: 100px; /* set a fixed width for the containing div */
    height: 100px; /* set a fixed height for the containing div */
  }

  .img-container {
    max-width: 100%; /* ensure the image does not exceed the container width */
    max-height: 100%; /* ensure the image does not exceed the container height */
    overflow: hidden; /* hide any overflow beyond the container */
  }

  .img-container img {
    width: auto; /* allow the image to maintain its aspect ratio */
    height: auto; /* allow the image to maintain its aspect ratio */
    max-width: 100%; /* ensure the image does not exceed the container width */
    max-height: 100%; /* ensure the image does not exceed the container height */
  }

  .delete-btn {
    display: none;
  }

  .item {
    padding: 0px 0px;
    height: 120px;
    width: 70%;
    margin: auto;
    display: flex;
    justify-content: space-between;
  }
}

@media only screen and (min-width: 10px) and (max-width: 650px) {
  .product-img {
    width: 100px;
    height: 100px;
    object-fit: contain;
  }

  .btn-trash {
    display: none;
  }

  .btn-cart {
    display: none;
  }

  .img-container {
    max-width: 100%;
    max-height: 100%;
    overflow: hidden;
  }

  .img-container img {
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100%;
    margin-right: 16px;
    object-fit: contain;
  }

  .item {
    padding: 20px 30px;
    height: 120px;
    width: 70%;
    margin: auto;
    display: flex;
    justify-content: space-between;
  }

  .item:nth-child(3) {
    border-top: 1px solid #e1e8ee;
    border-bottom: 1px solid #e1e8ee;
  }

  .buttons {
    position: relative;
    padding-top: 30px;
    margin-right: 60px;
  }

  .like-btn {
    display: inline-block;
    cursor: pointer;
  }

  .image {
    margin-right: 50px;
  }

  .description {
    position: relative;
    display: flex;
    flex-direction: column;
    max-width: 250px;
    color: black;
    margin-right: 0px;
    width: calc(50%);
  }

  .quantity {
    display: flex;
    align-items: center;
    padding-top: 20px;
    text-align: center;
    justify-content: center;
  }

  .quantity input {
    -webkit-appearance: none;
    border: none;
    text-align: center;
    width: 32px;
    font-size: 16px;
    color: #43484d;
    font-weight: 300;
  }

  button[class*="btn"] {
    width: 30px;
    height: 30px;
    background-color: #e1e8ee;
    border-radius: 6px;
    border: none;
    cursor: pointer;
  }

  .minus-btn img {
    margin-bottom: 3px;
  }

  .plus-btn img {
    margin-top: 2px;
  }

  button:focus,
  input:focus {
    outline: 0;
  }

  input[type="number"]::-webkit-outer-spin-button,
  input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }

  input[type="number"] {
    -moz-appearance: textfield;
  }

  .product-img {
    width: 100px; /* set a fixed width for the containing div */
    height: 100px; /* set a fixed height for the containing div */
  }

  .img-container {
    max-width: 100%; /* ensure the image does not exceed the container width */
    max-height: 100%; /* ensure the image does not exceed the container height */
    overflow: hidden; /* hide any overflow beyond the container */
  }

  .img-container img {
    width: auto; /* allow the image to maintain its aspect ratio */
    height: auto; /* allow the image to maintain its aspect ratio */
    max-width: 100%; /* ensure the image does not exceed the container width */
    max-height: 100%; /* ensure the image does not exceed the container height */
  }

  .description {
  }

  .delete-btn {
    display: none;
  }

  .item {
    padding: 0px 0px;
    height: 120px;
    width: 70%;
    margin: auto;
    display: flex;
    justify-content: space-between;
  }

  .product-img img {
    width: 45px; /* set a fixed width for the containing div */
    height: 50px;
    object-fit: contain; /* set a fixed height for the containing div */
  }

  .item {
    display: flex !important;
    height: 80px;
    padding-left: 10px !important;
    padding-right: 10px !important;
    margin-top: 10px;
  }

  .buttons {
    display: inline-block;
    padding-left: 10px;
    padding-right: 5px;
    width: 65px;
    padding-top: 40px;
    scale: 1;
  }

  .btn-checkbox {
    margin: 0;
    position: absolute;
    right: 0;
    transform: translateX(62px);
  }

  .btn-checkbox .btn-trash {
    margin: 0px;
  }

  .btn-cart {
    margin: 0px;
  }

  #check {
    scale: 1.5;
  }

  .product-img {
    padding-top: 15px;
  }

  .quantity {
    padding: 5px;
    top: 35px;
    position: absolute;
    right: 0;
    transform: translateX(-25px);
  }

  .number-input {
    background-color: transparent;
    margin-top: 10px;
    margin-left: 0px;
    margin-right: 0px;
    padding-bottom: 5px;
    padding-right: 5px;
  }

  .img-container {
    max-width: 150px; /* ensure the image does not exceed the container width */
    max-height: 150px; /* ensure the image does not exceed the container height */
  }

  .img-container img {
    max-width: 150px;
    max-height: 150px;
  }

  .description {
    max-width: 100%;
    text-align: justify;
    word-break: break-all;
  }
}
</style>
