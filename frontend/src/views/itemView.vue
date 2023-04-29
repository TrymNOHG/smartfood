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
        <item-delete
            v-if="isCurrentUserSuperUser"
            :item="item" class="info-delete"
            @delete-item="deleteItem"
            @add-shopping="addShopping"/>
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
import {addItemToShoppingList} from "@/services/ItemService";
import swal from "sweetalert2";

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

    async addShopping(item) {
      const date = new Date();
      const expirationDate = new Date(date);
      expirationDate.setDate(date.getDate() + 7);

      const itemDTO = {
        "name": item.name,
        "description": item.description,
        "store": item.store,
        "price": item.price,
        "purchaseDate": date,
        "expirationDate": expirationDate,
        "image": item.image,
        "quantity": 1,
      }

      await addItemToShoppingList(itemDTO, this.fridge.fridgeId, false).then(
          async (response) => {
            console.log("response", response);
            console.warn("error1", error); //TODO: add exception handling
          }
      );
    },

    async deleteItem(item, deletePercentage) {

      swal.fire({
        title: this.$t('confirm_title'),
        text: this.$t('confirm_text'),
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#4dce38',
        cancelButtonColor: '#d33',
        confirmButtonText: this.$t('confirm_button'),
        cancelButtonText: this.$t('cancel_button'),
        customClass: {
          container: 'my-swal-dialog-container'
        }
      }).then(async (result) => {
        if (result.isConfirmed) {
          swal.fire({
            title: this.$t('buy_again'),
            text: this.$t('confirm_text'),
            icon: 'success',
            showCancelButton: true,
            confirmButtonColor: '#4dce38',
            cancelButtonColor: '#d33',
            confirmButtonText: this.$t('Yes'),
            cancelButtonText: this.$t('No'),
            customClass: {
              container: 'my-swal-dialog-container'
            }
          }).then((result) => {
            const statDeleteFromFridgeDTO = {
              "percentageThrown": parseFloat(deletePercentage),
              "price": item.price,
              "quantity": parseFloat(item.quantity),
              "itemName": item.name,
              "storeName": item.store,
              "fridgeId": this.fridge.fridgeId
            };
            const itemRemoveDTO = {
              "itemName": item.name,
              "store": item.store,
              "fridgeId": this.fridge.fridgeId,
              "quantity": item.quantity
            };
            if (result.isConfirmed) {
              this.addShopping(item);
            }
            this.itemStore.deleteItemByStats(statDeleteFromFridgeDTO).then(() => {
              this.itemStore.deleteItemByNameIdStoreQuantity(itemRemoveDTO).then(() => {
                router.push('/fridge');
              });
            });
          });
        } else {
          const statDeleteFromFridgeDTO = {
            "percentageThrown": parseFloat(deletePercentage),
            "price": item.price,
            "quantity": parseFloat(item.quantity),
            "itemName": item.name,
            "storeName": item.store,
            "fridgeId": this.fridge.fridgeId
          };
          const itemRemoveDTO = {
            "itemName": item.name,
            "store": item.store,
            "fridgeId": this.fridge.fridgeId,
            "quantity": item.quantity
          };
          this.itemStore.deleteItemByStats(statDeleteFromFridgeDTO).then(() => {
            this.itemStore.deleteItemByNameIdStoreQuantity(itemRemoveDTO).then(() => {
              router.push('/fridge');
            });
          });
        }
      });
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
  grid-column-gap: 10px;
  grid-column: 2;
}
.members-fridge:hover {
  color: #3b3b3b;
  height: 25px;
  border-radius: 5px;
  background-color: #fff;
  transition: all 0.2s ease-in-out;
}

.link {
  text-decoration: none;
  color: white;
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
