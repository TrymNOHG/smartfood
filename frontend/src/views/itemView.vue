<template>
  <div class="grey-bar">
    <div class="members-fridge">
      <router-link class="link" :to="{ name: 'fridgeView', query: { selectedTab: 'members' } }" >{{ $t('members') }}</router-link>
      <router-link class="link fridge" :to="{ name: 'fridgeView'}">{{ $t('fridge') }}</router-link>    </div>
    <div id="info-and-bell">
      <InfoAndBell/>
      <div class="information-button">
        <img
            src="@/assets/images/info.svg"
            id="info-picture"
            @click="resetSteps(); informationButton()"
            :alt="$t('alt_info_button')"
        />
      </div>
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
            @add-shopping="addShopping"
            @update-item="updateItem"/>
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
import InfoAndBell from "@/components/basic-components/InfoAndBell.vue";
import Shepherd from "shepherd.js";
import {offset} from "@floating-ui/vue";

export default {
  name: "itemView",
  components: {InfoAndBell, ItemDelete, ItemInfo, ItemHeader},

  setup() {
    const itemStore = useItemStore();
    const fridgeStore = useFridgeStore();
    const fridge = fridgeStore.currentFridge;
    const item = itemStore.getCurrentItem;

      const itemTour = new Shepherd.Tour({
          useModalOverlay: true,
          defaultStepOptions: {
              classes: 'shepherd-has-cancel-icon shepherd-element class-1 class-2 shepherd-enabled shepherd-theme-arrows',
              arrow: true,
              floatingUIOptions: {
                  middleware: [offset(30)]
              },
              cancelIcon: {
                  enabled: true
              },
          }
      });

      const information = new Shepherd.Tour({
          useModalOverlay: true,
          defaultStepOptions: {
              classes: 'shepherd-has-cancel-icon shepherd-element class-1 class-2 shepherd-enabled shepherd-theme-arrows',
              arrow: true,
              floatingUIOptions: {
                  middleware: [offset(30)]
              },
              cancelIcon: {
                  enabled: true
              },
          }
      });


    return {
      item,
      fridge,
      itemStore,
      itemTour,
      information,
    };
  },
  methods: {

      resetSteps(){
          if(this.itemTour.steps.length !== 0) {
              while (this.itemTour.steps.length !== 0) {
                  this.itemTour.steps.pop()
              }
          }
          if(this.information.steps.length !== 0) {
              while (this.information.steps.length !== 0) {
                  this.information.steps.pop()
              }
          }
      },

      informationButton(){
          this.information.addSteps([
              {
                  id: 'information-pressed',
                  title:`<div class="info-box"><img src="../src/assets/images/info.svg" alt="Pressed" id="tour-info-picture"/></div>`,
                  text: this.$t('tour: view:fridgesView method:informationButton id:information-pressed usage:text'),
                  attachTo: {
                      element: '#info-picture',
                      on: 'bottom',
                  },
                  buttons: [
                      {
                          action: () => {
                              router.push('/fridges?appTour=true');
                              this.information.cancel();
                          },
                          secondary: true,
                          class: " shepherd-button ",
                          text: this.$t('tour: button whole site'),
                      },
                      {
                          action: () => {
                              this.itemStepsTour();
                              this.information.cancel();

                          },
                          class: " shepherd-button ",
                          text: this.$t('tour: button this site'),
                      },
                  ]
              }])
          this.information.start()
      },
      itemStepsTour() {
          this.itemTour.addSteps([
              {
                  id: 'itemWindow',
                  title: this.$t('tour: view:itemView method:itemStepTour id:itemWindow usage:title'),
                  text: this.$t('tour: view:itemView method:itemStepTour id:itemWindow usage:text'),
                  attachTo: {
                      element: '.item-wrapper',

                  },
                  classes: 'shepherd-theme-arrows',
                  buttons: [
                      {
                          action: function () {
                              return this.cancel();
                          },
                          class: " shepherd-button ",
                          text: this.$t('tour: button exit'),
                      },
                  ]
              },
          ])
          this.itemTour.start()

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
            console.warn("error1", error); //TODO: add exception handling
          }
      );
    },

    async deleteItem(item, amountToBeDeleted) {

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
            let amountDeleted;
            const stkStandard = 250;

            if (item.unit === "g" || item.unit === "ml") {
              amountDeleted = amountToBeDeleted;
            } else {
              amountDeleted = Math.floor(amountToBeDeleted * stkStandard);
            }

            const statDeleteFromFridgeDTO = {
              amountDeleted: amountDeleted,
              itemName: item.name,
              storeName: item.store,
              fridgeId: this.fridge.fridgeId,
            };

            const itemRemoveDTO = {
              itemName: item.name,
              store: item.store,
              fridgeId: this.fridge.fridgeId,
              quantity: 0,
            };

            if (result.isConfirmed) {
              this.addShopping(item);
            }
            this.itemStore.deleteItemByStats(statDeleteFromFridgeDTO).then(() => {
              this.itemStore.deleteItemByNameIdStoreAmount(itemRemoveDTO).then(() => {
                router.push('/fridge');
              });
            });
          });
        }
      });
    },

    //TODO: add amount to info on thing where amount is chosen and remember to read other todos

    async updateItem(item, newAmount) {

      if(newAmount === 0){
        await this.deleteItem(item, newAmount)
      }

      swal.fire({
        title: this.$t('update_title'),
        icon: 'success',
        confirmButtonColor: '#4dce38',
        confirmButtonText: this.$t('confirmButtonText'),
        customClass: {
          container: 'my-swal-dialog-container'
        }
      })
      const itemRemoveDTO = {
        "itemName": item.name,
        "store": item.store,
        "fridgeId": this.fridge.fridgeId,
        "quantity": newAmount
      };

      this.itemStore.deleteItemByNameIdStoreAmount(itemRemoveDTO).then(() => {
        router.push('/fridge');
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

#info-and-bell {
  display: flex;
  flex-direction: row;
  justify-content: end;
  gap: 34%;
  margin-left: auto;
}

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
  background-color: #6c6c6c;
  text-align: center;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
}

.information-button{
  display: flex;
  grid-column: 3;
  text-align: right;
  height: 40px;
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

.link {
  text-decoration: none;
  color: white;
}

@media (max-width: 650px) {



  body {
    height: 95px;
    width: 100%;
  }
  #info-and-bell {
    display: flex;
    flex-direction: row;
    justify-content: end;
    left: 8%;
    gap: 40%;
  }

  .info-delete-wrapper{
    margin-top: 15%;
    display: grid;
    grid-template-columns: 100%;
    grid-template-rows: 45% 5% 45%;
    align-items: center;
  }

}

@media only screen and (min-width: 1px) and (max-width: 480px) {
  .grey-bar {
    display: flex;
    align-content: center;
    justify-content: center;
    margin-top: 5px;
    background-color: #31c48d;
    max-height: 60px;
    height: 60px;
    border-radius: 20px 20px 0 0;
  }

  #info-and-bell{
    display: flex;
    margin-left: auto;
    margin-right: 5px;
    gap: 30%;
    left: 0;
    height: 60px;
  }
  .members-fridge {
    background-color: #31c48d;
    color: white;
    font-size: 20px;
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-column-gap: 10px;
    grid-column: 2;
    margin-top: 0;
    padding-top: 0;
    padding-right: 10px;
    text-align: center;
    align-items: center;
    align-content: center;
    justify-content: center;
    margin-left: 10px;
    width: 70%;
  }

  .link.active {
    height: 60px !important;
    background-color: white;
    font-size: 20px;
    border-radius: 20px 20px 0 0;
    font-weight: bold;
    text-decoration: none;
    text-shadow: none;
    color: black;
    margin-top: 20px;
    padding-top: 8px;
    padding-right: 5px;
    padding-left: 5px;
  }

  .link {
    margin: 0;
    padding-left: 5px;
    padding-right: 5px;
  }

  #toggle-button:hover {
    cursor: pointer;
    font-size: unset;
  }

  .fridge {
    height: 60px !important;
    background-color: white;
    font-size: 20px;
    border-radius: 20px 20px 0 0;
    font-weight: bold;
    text-decoration: none;
    text-shadow: none;
    color: black;
    margin-top: 20px;
    padding-top: 8px;
    padding-right: 5px;
    padding-left: 5px;
  }

  .information-button{
    margin-right: 3px !important;
    height: 60px;
  }
}
</style>
