<template>

    <div>
      <cart-component/>
    </div>
  
  </template>

<script>
import CartComponent from "../components/shoppingcart/CartComponent.vue";

export default {
  name: "CartView",
  components: {CartComponent},
  
}
</script>