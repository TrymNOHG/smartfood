<template>
    <div>
      <h1>Cart</h1>
      <div>
        <h2>Shopping List</h2>
        <ul>
          <li v-for="item in items" :key="item.id">
            {{ item.name }}
            <span class="icon">
              <font-awesome-icon icon="fa-cart-plus" />
            </span>
          </li>
        </ul>
        <form @submit.prevent="addItem">
          <input type="text" v-model="newItem" placeholder="Add item to list">
          <button type="submit">Add</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
  export default {
    name: 'Cart',
    components: {
      FontAwesomeIcon
    },
    data() {
      return {
        items: [],
        newItem: ''
      };
    },
    methods: {
      addItem() {
        if (this.newItem) {
          this.items.push({
            id: Date.now(),
            name: this.newItem
          });
          this.newItem = '';
        }
      }
    }
  };
  </script>
  
  <style scoped>
  * {
    text-align: center;
  }
  
  .icon {
    margin-left: 10px;
  }
  </style>
  