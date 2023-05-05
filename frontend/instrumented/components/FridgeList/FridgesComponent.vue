<template>
  <div class="container" id="fridgeList">
    <list :fridgeList="fridgeList"  class="listing" @update-item="onUpdateItem" @delete-item="onDeleteItem"/>
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
import List from "@/components/FridgeList/listingComponent.vue";
import BasicInput from "@/components/basic-components/BasicInput.vue";
import BasicButton from "@/components/basic-components/BasicButton.vue";
import {useFridgeStore, useLoggedInStore} from "@/store/store";
import {ref} from "vue";
import swal from "sweetalert2";

export default {
  components: {BasicButton, BasicInput, List },
  setup() {
    const fridgeList = ref([]);
    const userStore = useLoggedInStore();
    const fridgeStore = useFridgeStore();
    fridgeStore.removeCurrentFridge();

    userStore.fetchUser();
    const user = userStore.getUser.data;

    fridgeStore.fetchFridgesByUsername(user.username)
        .then((fridges) => {
          fridgeList.value = fridges;
        });

    return {
      fridgeStore,
      user,
      userStore,
      fridgeList,
    };
  },

  data() {
    return {
      showModal: false,
      newFridgeName: "",
    };
  },
  methods: {

    async onUpdateItem(index, name) {
      const fridgeToChange = this.fridgeList[index];

      const fridgeDTO = {
        "fridgeId": fridgeToChange.fridgeId,
        "fridgeName": name
      }

      let err = null
      await this.fridgeStore.updateFridgeNameByDTO(fridgeDTO)
          .catch((errors) => {
            err = errors
          });

      if (err){
        await swal.fire({
          title: this.$t('errorTitle'),
          text: this.$t('errorText'),
          icon: "error",
          confirmButtonColor: this.$t('confirmButtonColorError'),
          confirmButtonText: this.$t('confirmButtonText'),
          customClass: {
            container: "my-swal-dialog-container",
          },
        });
      } else {
        await swal.fire({
          title: this.$t('updateTitle'),
          text: this.$t('updateText', { oldName: fridgeToChange.fridgeName, newName: name }),
          icon: "success",
          confirmButtonColor: this.$t('confirmButtonColorSuccess'),
          confirmButtonText: this.$t('confirmButtonText'),
          customClass: {
            container: "my-swal-dialog-container",
          },
        });
      }
      this.fridgeList = await this.fridgeStore.fetchFridgesByUsername(this.user.username);
    },

    async onDeleteItem(index) {
      const deleteFridge = this.fridgeList[index];

      const fridgeUserDTO = {
        "fridgeId": deleteFridge.fridgeId,
        "username": this.user.username,
        "isSuperUser": false
      }

      await this.fridgeStore.deleteUserFromFridgeByDTO(fridgeUserDTO);
      this.fridgeList = await this.fridgeStore.fetchFridgesByUsername(this.user.username);
    },

    changeModal() {
      this.showModal = this.showModal === false;
    },

    async addNewFridge() {
      this.showModal = false;
      if (this.newFridgeName.isEmpty || this.newFridgeName === ""){
        await swal.fire({
          title: this.$t('errorTitle'),
          text: "Fridge name cannot be empty or null",
          icon: "error",
          confirmButtonColor: this.$t('confirmButtonColorError'),
          confirmButtonText: this.$t('confirmButtonText'),
          customClass: {
            container: "my-swal-dialog-container",
          },
        });
        return;
      }
      await this.fridgeStore.addNewFridgeByFridgeNameAndUsername(this.newFridgeName);
      await swal.fire({
        title: this.$t('updateTitle'),
        text: this.$t('new_fridge', { name: this.newFridgeName }),
        icon: "success",
        confirmButtonColor: this.$t('confirmButtonColorSuccess'),
        confirmButtonText: this.$t('confirmButtonText'),
        customClass: {
          container: "my-swal-dialog-container",
        },
      });
      this.fridgeList = await this.fridgeStore.fetchFridgesByUsername(this.user.username);
    },


  },
};
</script>

<style>

*{
  font-family: Roboto, sans-serif;
}

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
