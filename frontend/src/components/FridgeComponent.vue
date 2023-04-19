<template>
  <div class="container">
    <list :items="fridgeList"  class="listing" @update-item="onUpdateItem" @delete-item="onDeleteItem"/>
    <div @click="changeModal()" class="container_button">+</div>
  </div>

  <div v-if="showModal" class="modal">
    <div class="modal-content">
      <h3>{{ $t('addNewFridgeTitle') }}</h3>
      <form @submit.prevent="addNewFridge()">
        <label>{{ $t('fridgeNameLabel') }}</label>
        <basic-input type="text" v-model="newFridgeName"/><br>
        <basic-button :button-text="$t('addFridgeButtonText')" type="submit"/>
        <span class="close" @click="changeModal()">X</span>
      </form>
    </div>
  </div>
</template>

<script>
import List from "@/components/basic-components/listingComponent.vue";
import BasicInput from "@/components/basic-components/BasicInput.vue";
import BasicButton from "@/components/basic-components/BasicButton.vue";
import {useFridgeStore, useLoggedInStore} from "@/store/store";

export default {
  components: {BasicButton, BasicInput, List, useFridgeStore},

  setup() {
    const userStore = useLoggedInStore();
    const fridgeStore = useFridgeStore();

    return {
      fridgeStore,
      userStore,
    }
  },

  data() {
    return {
      fridgeList: ['Fridge 1', 'Fridge 2', 'Fridge 3'],
      showModal: false,
      newFridgeName: "",
    }
  },

  methods: {
    onUpdateItem(index, name) {
      this.fridgeList[index] = name;
    },

    onDeleteItem(index) {
      this.fridgeList.splice(index, 1);
      console.log(this.fridgeList)
    },

    changeModal() {
      this.showModal = this.showModal === false;
    },

    async addNewFridge() {
      console.log(this.newFridgeName);
      this.fridgeList.push(`${this.newFridgeName}`);

      /*
      await this.userStore.fetchUser();
      const user = this.userStore.user;
      await this.fridgeStore.addNewFridgeByFridgeNameAndUsername(user.username, this.newFridgeName);
      this.fridgeList = await this.fridgeStore.fetchFridgesByUsername(user.username);

       */
    }
  }
};
</script>

<style>

.listing {
  margin-top: 5%;
}

.list li {
margin: 5px;
padding: 5px;
list-style: none;
background-color: #f0f0f0;
}

.container_button {
text-align: center;
height: 60px;
background-color: #eee;
border: 1px solid #ccc;
border-radius: 5px;
margin: 10px 5px;
cursor: pointer;
transition: all 0.2s ease-in-out;
display: flex;
justify-content: center;
align-items: center;
font-size: 3em;
font-weight: bold;
}

.container_button:hover {
box-shadow: 0 0 5px #ccc;
transform: scale(1.02);
}

h3 {
font-weight: bold;
}

.modal {
flex-direction: column;
position: fixed;
left: 50%;
top: 50%;
background-color: #fff;
box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
padding: 20px;
border-radius: 5px;
z-index: 100;
display: flex;
transform: translate(-50%, -50%);
width: 60%;
max-width: 500px;
}
.modal-content {
flex-grow: 1;
overflow-y: auto;
}


.close {
  position: absolute;
  top: -25px;
  right: 2px;
  font-size: 1.5em;
  font-weight: bold;
  color: #ccc;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  width: 30px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
}

label {
  margin-top: 5%;
}

.close:hover{
  background-color: lightgray;
  color: black;
  transform: scale(1.1);
}

form {
  display: flex;
  flex-direction: column;
}

input, textarea {
  margin-bottom: 10px;
  padding: 5px;
}

.modal button {
  align-self: center;
}

</style>
