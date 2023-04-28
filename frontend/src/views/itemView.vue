<template>
  <div class="grey-bar">
    <div class="members-fridge">
      <router-link class="link" to="/members">{{ $t('members') }}</router-link>
      <router-link class="link fridge" to="/fridge">{{ $t('fridge') }}</router-link>
    </div>
    <div class="information-button">
      <img src="@/assets/images/info.svg" id="info-picture" @click="showInformation" :alt=" $t('alt_info_button') ">
    </div>
  </div>

  <div class="item-wrapper">
    <div class="item">
      <item-header :item="item"/>
      <div class="info-delete-wrapper">
        <item-info :item="item" class="info-delete"/>
        <div></div>
        <item-delete v-if="isCurrentUserSuperUser" :item="item" class="info-delete" @delete-item="deleteItem"/>
      </div>
    </div>
  </div>
</template>

<script>
import { useRoute } from 'vue-router';
import ItemHeader from "@/components/itemDescription/itemHeader.vue";
import ItemInfo from "@/components/itemDescription/itemInfo.vue";
import ItemDelete from "@/components/itemDescription/itemDelete.vue";
import {useFridgeStore, useItemStore} from "@/store/store";
import router from "@/router/router";

export default {
  name: "itemView",
  components: {ItemDelete, ItemInfo, ItemHeader},

  setup() {
    const itemStore = useItemStore();
    const fridgeStore = useFridgeStore();
    const fridge = fridgeStore.currentFridge;
    const item = itemStore.getCurrentItem;

    return {
      item,
      fridge,
      itemStore
    };
  },
  methods: {

    showInformation(){
      //TODO: INFORMATION PROFILE put information API in here
    },

    async deleteItem(itemToDelete, deletePercentage) {

      const statDeleteFromFridgeDTO = {
        "percentageThrown": parseFloat(deletePercentage),
        "price": itemToDelete.price,
        "quantity": parseFloat(itemToDelete.quantity),
        "itemName": itemToDelete.name,
        "storeName": itemToDelete.store,
        "fridgeId": this.fridge.fridgeId
      }

      const itemRemoveDTO = {
        "itemName": itemToDelete.name,
        "store": itemToDelete.store,
        "fridgeId": this.fridge.fridgeId,
        "quantity": itemToDelete.quantity
      }

      console.log(statDeleteFromFridgeDTO)
      console.log(itemRemoveDTO)
      await this.itemStore.deleteItemByStats(statDeleteFromFridgeDTO);
      await this.itemStore.deleteItemByNameIdStoreQuantity(itemRemoveDTO);
      await router.push('/fridge')
    }
  },
  computed: {
    isCurrentUserSuperUser() {
      return useFridgeStore().getIsSuperUser;
    },
  },
}
</script>

<style scoped>

.item {
  margin-top: 5%;
  grid-column: 2;
}

.item-wrapper {
  display: grid;
  grid-template-columns: 10% 80% 10%;
}

.info-delete-wrapper{
  display: grid;
  grid-template-columns: 45% 5% 45%;
}


.grey-bar {
  background-color: #6C6C6C;
  max-height : 35px;
  text-align: center;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
}

.information-button{
  grid-column: 3;
  text-align: right;
  padding: 2px 5px;
  max-height: 35px;
}

#info-picture{
  height: 30px;
  width: 30px;
  cursor: pointer;
}

.link {
  text-decoration: none;
  line-height: 25px;
  color: white;
}

.link:hover {
  cursor: pointer;
  font-size: x-large;
}

.grey-bar .link {
  height: 25px;
}

.fridge {
  background-size: 25px;
  background-color: #b1b1b1;
  border-radius: 5px;
  font-weight: bold;
  text-decoration: black;
  text-shadow: black 0 0 2px;
}

.members-fridge {
  background-color: #6C6C6C;
  height: 35px;
  color: white;
  font-size: 1.5em;
  padding-top: 5px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-column-gap: 20px;
  grid-column: 2;
}

.name-display {
  text-align: start;
  background-color: #31c48d;
  color: white;
  width: 100%;
  height: 50px;
  text-shadow: black 1px 1px 2px;
  display: flex;
  justify-content: space-evenly;
}

.fridge-name {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 50px;
  margin-left: 28%;
}
.members-fridge:hover .fridge-name {
  color: #3b3b3b;
  height: 25px;
  border-radius: 5px;
  background-color: #fff;
  transition: all 0.2s ease-in-out;
}

.change-button {
  text-align: center;
  background-color: white;
  color: black;
  height: 35px;
  width: 20%;
  margin-top: 0.5%;
  margin-right: 5%;
  text-shadow: white 0 0 0;
  font-weight: 500;
  border-radius: 5px;
}

.change-button:hover {
  color: white;
  border-radius: 5px;
  background-color: #b1b1b1;
  transition: all 0.2s ease-in-out;
  cursor: pointer;
}

.link {
  text-decoration: none;
  color: white;
}
.link-name{
  text-decoration: none;
  color: white;
}

.link-button{
  text-decoration: none;
  color: black;
}

.break-line {
  height: 7px;
  background-color: black;
}

@media (max-width: 650px) {

  body {
    height: 95px;
    width: 100%;
  }

  .info-delete-wrapper{
    margin-top: 15%;
    display: grid;
    grid-template-columns: 100%;
    grid-template-rows: 45% 5% 45%;
    align-items: center;
  }

  .fridge-name {
    margin-left: 30%;
    font-size: 18px;
  }

  .change-button {
    width: 20%;
    top: 20%;
    font-size: 0.7rem;
  }

  .name-display {
    font-size: 1.2rem;
  }

}


</style>
