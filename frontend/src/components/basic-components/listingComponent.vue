<template>
  <div class="list">
    <div v-for="(item, index) in items" :key="index" class="item" @click="onItemClick">
      <span class="item-text">{{ item }}</span>
      <div class="icons">
        <font-awesome-icon icon="fa-solid fa-pen-to-square" @click="onEditClick" class="icon edit-icon" />
        <font-awesome-icon icon="fa-solid fa-trash" @click="onDeleteClick" class="icon delete-icon" />
      </div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

export default {
  name: "List",
  components: { FontAwesomeIcon },
  props: {
    items: {
      type: Array,
      required: true,
    },
  },
  methods: {
    onItemClick() {
      console.log("Item clicked");
      // Add your code to handle the click event here
    },
    onEditClick() {
      console.log("Edit clicked");
      // Add your code to handle the edit click event here
    },
    onDeleteClick() {
      console.log("Delete clicked");
      // Add your code to handle the delete click event here
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
  height: 60px;
  background-color: #eee;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin: 10px 5px;
  padding: 10px;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.item:hover {
  box-shadow: 0 0 5px #ccc;
  transform: scale(1.02);
}

.item-text {
  flex-grow: 1;
}

.icons {
  display: flex;
  align-items: center;
}

.edit-icon {
  font-size: 1.2rem;
  cursor: pointer;
  margin-right: 10px;
  color: #888;
  transition: color 0.2s ease-in-out;
}

.edit-icon:hover {
  color: #000;
}

.delete-icon {
  font-size: 1.2rem;
  cursor: pointer;
  color: #888;
  transition: color 0.2s ease-in-out;
}

.delete-icon:hover {
  color: #f00;
}

@media only screen and (max-width: 600px) {
  .item {
    height: 80px;
    padding: 20px;
  }

  .item:hover {
    box-shadow: none;
    transform: none;
  }

  .item-text {
    margin-bottom: 10px;
  }

  .icons {
    margin-top: 10px;
  }
}

</style>
