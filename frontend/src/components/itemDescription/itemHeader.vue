<template>
  <div class="item-header">
    <div class="item-image">
      <img src="@/assets/images/large.jpg" alt="item picture">
    </div>
    <h1 class="item-name">{{ itemName }}</h1>
  </div>
</template>

<script>
export default {
  name: "itemHeader",

  props: {
    itemName: null,
    itemId: null,
  }
}
</script>

<style scoped>
.item-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.item-image {
  width: 200px;
  height: 150px;
  margin-right: 50px;
  background-color: #ccc;
  box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
  position: relative;
}

.item-image img {
  width: 95%;
  height: 95%;
  object-fit: cover;
  position: absolute;
  bottom: 0;
  right: 0;
}

.item-name {
  font-size: 24px;
  font-weight: bold;
  border-bottom: 2px solid black;
  padding-bottom: 5px;
  display: flex;
  flex-direction: row-reverse;
  justify-content: flex-start;
}

@media only screen and (max-width: 768px) {
  .item-header {
    margin-bottom: 10px;
  }

  .item-image {
    width: 200px;
    height: 150px
  }

  .item-name {
    font-size: 20px;
  }
}
</style>
