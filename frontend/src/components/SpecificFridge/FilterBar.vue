<template>
  <div class="filter-container">

    <div class="view-options">
      <font-awesome-icon icon="fa-solid fa-list" class="icon" :class="active ? 'icon2' : 'icon'" @click="listingView(true); handleClick();" />
      <font-awesome-icon icon="fa-solid fa-table-cells" class="icon" :class="active ? 'icon' : 'icon2'" @click="listingView(false); handleClick2();"/>
    </div>
  </div>
</template>


<script lang="ts">

import {onMounted, onUnmounted, ref} from "vue";

interface Filter {
  key: string;
  operator: string;
  field_type: string;
  value: string | number;
}


export default {


  data() {
    return {
      listView: false,
      active: false,

    }
  },

  methods: {
    listingView(bool){
      this.$emit("listing", bool);
    },

    handleClick() {
      this.active = true;
    },

    handleClick2() {
      this.active = false;
    }
  }
}


</script>

<style scoped>
.filter-container {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #31c48d;
  height: 100%;
  border-radius: 50px;

}


.filter {
  margin-left: 1.5%;
  margin-right: 1%;
}

.view-options {
  display: flex;
  gap: 30px;
  align-items: center;
  height: 100%;
}

.icon{
  transition: transform 0.3s ease-in-out, color 0.3s ease-in-out;
}

.icon:hover {
  transform: scale(1.2);
  cursor: pointer;

}

.icon {
  color: white;
  padding: 5px;
  scale: 1.7;

}

.icon2{
  background-color: white;
  color: #31c48d;
  border-radius: 10px;
}
</style>