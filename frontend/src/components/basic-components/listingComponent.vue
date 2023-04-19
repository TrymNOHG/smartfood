<template>
  <div class="list">
    <div v-for="(item, index) in items" :key="index" class="item">
      {{ item }}
    </div>
  </div>
</template>

<script>
export default {
  name: "List",
  props: {
    items: {
      type: Array,
      required: true,
    },
  },
};
</script>

<style>
.list {
  display: flex;
  flex-direction: column;
}

.item {
  background-color: #eee;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin: 5px;
  padding: 10px;
}
</style>