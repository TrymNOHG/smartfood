<template>
  <div class="filter-container">
    <div class="view-options">
      <font-awesome-icon icon="fa-solid fa-list" class="icon" @click="listingView(true)"/>
      <font-awesome-icon icon="fa-solid fa-table-cells" class="icon" @click="listingView(false)"/>
    </div>
    <div class="filter">
      <font-awesome-icon icon="fa-solid fa-filter" class="icon"/>
    </div>
  </div>
</template>


<script>

export default {

  data() {
    return {
      listView: false,
    }
  },

  methods: {
    listingView(bool){
      this.$emit("listing", bool);
    }
  }
}


</script>

<style scoped>
.filter-container {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  background-color: #6C6C6C;
  padding: 5px;
}

.filter {
  margin-left: 1.5%;
  margin-right: 1%;
}

.view-options {
  display: flex;
  align-items: center;
}

.icon{
  transition: transform 0.3s ease-in-out, color 0.3s ease-in-out;
}

.icon:hover {
  transform: scale(1.2);
  color: #31c48d;
}

.icon {
  margin: 5px;
  color: white;
}
</style>