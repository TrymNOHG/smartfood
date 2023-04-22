<template>
  <div class="item">
    <div class="product-img">
        <div class="img-container">
        <img :src="image" alt="" />
      </div>
    </div>

    <div class="description">
      <span>{{ name }}</span>
      <span>{{ date_added }}</span>
    </div>

    <div class="quantity">
      <button class="minus-btn" type="button" @click="handleSubtract">
        <img src="@/assets/images/minus.svg" alt="" />
      </button>

      <input
        class="number-input"
        type="number"
        name="name"
        :value="quantity"
        @input="$emit('update:quantity', $event.target.value)"
      />

      <button class="plus-btn" type="button" @click="handleAdd">
        <img src="@/assets/images/plus.svg" alt="" />
      </button>
    </div>


    <div class="buttons">
      <span class="delete-btn" @click="handleDeleteItem"></span>
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
    name: {
      type: String,
      required: true,
    },
    date_added:{
      type: String,
      required: true,
    },
    quantity: {
      type: Number,
      required: true,
    },
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
  },
};
</script>
<style>
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
  margin-right: 16px;
}
.item {
  padding: 20px 30px;
  height: 120px;
  width: 70%;
  margin: auto;
  display: flex;
  justify-content: space-between !important;
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
.description {
  padding-top: 10px;
  margin-right: 60px;
  width: calc(70% - 200px);
}

.description {
    position: relative;
  display: flex;
  flex-direction: column;
  max-width: 250px;
}

.description span:first-child {
  margin-bottom: 5px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.description span:last-child {
  font-weight: 300;
  margin-top: 8px;
  color: #86939e;
  white-space: normal;
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

.description {
  padding-top: 10px;
  margin-right:0px ;
  width: calc(50%);
}

.description {
    position: relative;
  display: flex;
  flex-direction: column;
  max-width: 250px;
}

.description span:first-child {
  margin-bottom: 5px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.description span:last-child {
  font-weight: 10;
  margin-top: 8px;
  color: #86939e;
  white-space: normal;
}
.delete-btn {
  width: 18px;
  height: 17px;
  margin-left: 15px;
}
.item {
  padding: 0px 0px;
  height: 120px;
  width: 70%;
  margin: auto;
  display: flex;
  justify-content: space-between !important;
}
}
@media only screen and (min-width: 350px) and (max-width: 480px) {
    .product-img img {
  width: 35px; /* set a fixed width for the containing div */
  height: 50px; /* set a fixed height for the containing div */
}

.item{
    width: 100vw;
}



.img-container {
  max-width: 150px; /* ensure the image does not exceed the container width */
  max-height: 150px; /* ensure the image does not exceed the container height */
}

.img-container img {
 
  max-width: 150px; 
  max-height: 150px; 
}




}

@media only screen and (max-width: 350px) {
.product-img img {
        margin-top: 35px;
  width: 20px; /* set a fixed width for the containing div */
  height: 30px; /* set a fixed height for the containing div */
}

.img-container {
  max-width: 100px; /* ensure the image does not exceed the container width */
  max-height: 100px; /* ensure the image does not exceed the container height */
}

.img-container img {
 
  max-width: 100px; 
  max-height: 100px; 
}

.img-container {
  max-width: 100%; /* ensure the image does not exceed the container width */
  max-height: 100%; /* ensure the image does not exceed the container height */
  overflow: hidden; /* hide any overflow beyond the container */
}



}
</style>
