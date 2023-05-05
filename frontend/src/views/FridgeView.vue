<template>
  <div class="grey-bar">
    <div class="members-fridge">
      <div
          id="toggle-button"
          class="link toggle-members"
          @click="selectedTab = 'members'"
          :class="{ active: selectedTab === 'members' }"
      >
        {{ $t("members") }}
      </div>
      <div
          id="toggle-button"
          class="link"
          @click="selectedTab = 'fridge'"
          :class="{ active: selectedTab === 'fridge' }"
      >
        {{ $t("fridge") }}
      </div>
    </div>

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

  <!--TODO: add infinite scroller or pagination-->
  <div class="fridge-wrapper" v-show="selectedTab === 'fridge'">
    <div id="myDropdown" class="dropdown-content">
      <figure id="backBlack"></figure>
      <div id="backGreen">
        <div id="barcode-scanner">
          <div v-show="scannerActive" id="interactive" class="viewport"></div>
        </div>
        <div id="searchbar">
          <SearchInput
              v-model="searchQuery"
              @input="handleSearch"
              :label="$t('add_item')"
              @receipt-upload="toggleCamera"
          ></SearchInput>
          <button id="searchbtn" @click="handleSearch">
            {{ $t("search") }}
          </button>
        </div>
      </div>


    </div>

    <div class="searchbar-wrapper" >
      <button id="toggle" @click="handleClick">Filter</button>
      <div
        v-if="click"
        id="filter"
        class="slide-in"
        :class="active ? 'slide-in' : 'slide-out'"
      >
        <div id="search-wrapper">
          <input
            type="text"
            v-model="searchText"
            @input="searchHandler()"
            :placeholder="$t('search') + '...'"
          />
        </div>

        <div id="sort-wrapper">
          <select v-model="sort" @change="searchHandler()">
            <option :value="sortOptions[0]">
              {{ $t("expiry-desc") }}
            </option>
            <option :value="sortOptions[1]">
              {{ $t("expiry-asc") }}
            </option>
            <option :value="sortOptions[2]">
              {{ $t("purchase-desc") }}
            </option>
            <option :value="sortOptions[3]">
              {{ $t("purchase-asc") }}
            </option>
          </select>
        </div>
      </div>

      <div
          v-if="click"
          id="filter-component"
          class="slide-in"
          :class="active ? 'slide-in' : 'slide-out'"
      >
        <filter-bar @listing="changeListing" />
      </div>
    </div>
      <div class="dropper" v-if="search">
          <vue-collapsible-panel-group>
              <vue-collapsible-panel :expanded="isExpanded.value">
                  <template #content>
                      <SearchItem
                              v-for="(item, index) in searchItems"
                              :key="index"
                              :image="item.image"
                              :text="item.name"
                              :store="item.store.name"
                              :price="
                  typeof item.current_price === 'number'
                    ? item.current_price
                    : item.current_price.price
                "
                              style="text-align: center"
                              @click="addItemToFridge(this.fridge.fridgeId, item)"
                      />
                      <div ref="scrollTarget" id="scrollTarget"></div>
                  </template>
              </vue-collapsible-panel>
          </vue-collapsible-panel-group>
      </div>
    <transition name="fade">
      <div v-if="!listView" class="wrapper">
        <basic-fridge-item
            :isSuperUser="isCurrentUserSuperUser"
            v-for="(item, index) in fridgeItems"
            :key="index"
            :item="item"
            :currenFridge="fridge"
            @delete-item="deleteItem"
            @add-shopping="addShopping"
        />
      </div>
      <div v-else class="list-wrapper">
        <basic-fridge-list
            :isSuperUser="isCurrentUserSuperUser"
            v-for="(item, index) in fridgeItems"
            :key="index"
            :item="item"
            :currenFridge="fridge"
            @delete-item="deleteItem"
            @add-shopping="addShopping"
        />
      </div>
    </transition>
  </div>
  <div class="members-wrapper" v-show="selectedTab === 'members'">
    <member-component />
  </div>
  <div id="bottom-element"></div>
</template>

<script lang="ts">

import {VueCollapsiblePanel, VueCollapsiblePanelGroup,} from "@dafcoe/vue-collapsible-panel";
import MemberComponent from "@/components/SpecificFridge/MemberComponent.vue";
import BasicFridgeItem from "@/components/SpecificFridge/BasicSquareList.vue";
import { useFridgeStore, useItemStore } from "@/store/store";
import {
  getCurrentInstance,
  onBeforeUnmount,
  onMounted,
  onUnmounted,
  ref,
} from "vue";
import SearchInput from "@/components/searchFromApi/SearchInput.vue";
import SearchItem from "@/components/searchFromApi/SearchItem.vue";
import {
  getItemByBarcode,
  getItems,
  getItemsByPage,
} from "@/services/ApiService";
import Swal from "sweetalert2";
import {addItemToShoppingList} from "@/services/ItemService";
import FilterBar from "@/components/SpecificFridge/FilterBar.vue";
import BasicFridgeList from "@/components/SpecificFridge/BasicFridgeList.vue";
import router from "../router/router";
import { StreamBarcodeReader } from "vue-barcode-reader";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import Quagga from "quagga";
import InfoAndBell from "../components/basic-components/InfoAndBell.vue";
import { offset } from "@floating-ui/vue";
import Shepherd from 'shepherd.js';
import '@/assets/tourStyle.css';

interface Filter {
  fridgeId: number;
  productName: string;
  sortField: string;
  sortOrder: string;
  page: number;
  pageSize: number;
}

export default {
  name: "FridgeView",
  components: {
    InfoAndBell,
    FontAwesomeIcon,
    StreamBarcodeReader,
    BasicFridgeList,
    FilterBar,
    SearchItem,
    SearchInput,
    BasicFridgeItem,
    MemberComponent,
    VueCollapsiblePanelGroup,
    VueCollapsiblePanel,
  },

  computed: {
    isCurrentUserSuperUser() {
      return useFridgeStore().getIsSuperUser;
    },
  },

  methods: {
    handleClick() {
      if (this.click != true) this.click = true;
      this.active = !this.active;
    },

    changeListing(bool) {
      this.listView = bool;
    },

    async addShopping(item) {
      const date = new Date();
      const expirationDate = new Date(date);
      expirationDate.setDate(date.getDate() + 7);
      const fridge = this.fridgeStore.getCurrentFridge;

      const itemDTO = {
        name: item.name,
        description: item.description,
        store: item.store,
        price: item.price,
        purchaseDate: date,
        expirationDate: expirationDate,
        image: item.image,
        EAN: item.ean,
        quantity: 1,
      };

      await addItemToShoppingList(itemDTO, fridge.fridgeId, false).then(
        async (response) => {}
      );
    },

    handleSearch() {
      this.search = this.searchQuery.length >= 2;
      getItems(this.searchQuery)
          .then((response) => {
            this.searchItems = response;
            this.isExpanded = true;
          })
          .catch((error) => {
            console.error(error);
          });
    },

    async deleteItem(itemToDelete, amountToBeDeleted) {
      let amountDeleted = null;
      const stkStandard = 250;

      if (itemToDelete.unit === "g" || itemToDelete.unit === "ml") {
        amountDeleted = amountToBeDeleted;
      } else {
        amountDeleted = Math.floor(amountToBeDeleted * stkStandard);
      }

      const statDeleteFromFridgeDTO = {
        amountDeleted: amountDeleted,
        itemName: itemToDelete.name,
        storeName: itemToDelete.store,
        fridgeId: this.fridge.fridgeId,
      };

      const itemRemoveDTO = {
        itemName: itemToDelete.name,
        store: itemToDelete.store,
        fridgeId: this.fridge.fridgeId,
        quantity: 0,
      };

      await this.itemStore.deleteItemByStats(statDeleteFromFridgeDTO);
      await this.itemStore.deleteItemByNameIdStoreAmount(itemRemoveDTO);
      await this.itemStore
          .fetchItemsFromFridgeById(this.fridge.fridgeId)
          .then((items) => {
            this.fridgeItems = items;
          });
    },

    async addItemToFridge(fridgeId, item) {
      this.search = false;
      const { value: confirmed } = await Swal.fire({
        title: this.isCurrentUserSuperUser
            ? this.$t("add_to_fridge")
            : this.$t("add_to_shopping_list"),
        icon: "question",
        showCancelButton: true,
        confirmButtonText: "Yes",
        cancelButtonText: "No",
      });

      if (!confirmed) {
        return;
      }

      const date = new Date();
      const expirationDate = new Date();

      const statAddItemToFridgeDTO = {
        price: item.current_price,
        quantity: 1,
        itemName: item.name,
        storeName: item.store.name,
        fridgeId: this.fridge.fridgeId,
      };

      const itemDTO = {
        name: item.name,
        description: item.description,
        store: item.store.name,
        price: item.current_price,
        purchaseDate: date,
        expirationDate: expirationDate,
        image: item.image,
        EAN: item.ean,
        quantity: 1,
      };

      if (typeof item.current_price.price === "number" && itemDTO) {
        itemDTO.price = item.current_price.price;
        if (statAddItemToFridgeDTO) {
          statAddItemToFridgeDTO.price = item.current_price.price;
        }
      }

      if (!this.isCurrentUserSuperUser) {
        await addItemToShoppingList(itemDTO, fridgeId, true).then(
          async (response) => {
            //TODO: add exception handling
          }
        );
      } else {
        await this.itemStore.statAddItemToFridge(statAddItemToFridgeDTO);
        await this.itemStore.addItemToFridgeById(this.fridge.fridgeId, itemDTO);
        await this.itemStore
            .fetchItemsFromFridgeById(this.fridge.fridgeId)
            .then((items) => {
              this.fridgeItems = items;
            });
      }
    },

    showInformation() {
      if (this.selectedTab === "fridge") {
        //TODO: INFORMATION FRIDGE put information API in here
      } else {
        //TODO: INFORMATION MEMBERS put information API in here
      }
    },

    initScanner() {
      Quagga.init(
          {
            inputStream: {
              name: "Live",
              type: "LiveStream",
              target: document.querySelector("#interactive.viewport"),
            },
            decoder: {
              readers: ["ean_reader", "code_128_reader", "code_39_reader"],
              debug: {
                drawBoundingBox: true,
                showFrequency: true,
                drawScanline: true,
                showPattern: true,
              },
              multiple: false,
              frequency: 5,
            },
          },
          (err) => {
            if (err) {
              return;
            }
            this.scannerActive = true;
            Quagga.start();
          }
      );

      Quagga.onDetected(this.onDetected);
    },

    stopScanner() {
      Quagga.offDetected(this.onDetected);
      Quagga.stop();
      this.scannerActive = false;
    },

    async onDetected(result) {
      const code = result.codeResult.code;

      await getItemByBarcode(code)
          .then((response) => {
            if (response !== undefined) {
              this.searchItems = response.products;
              this.search = true;
              this.stopScanner();
            } else {
              this.submitMessage =
                  "Something went wrong. Please try again later.";
            }
          })
          .catch((error) => {
            console.warn("error1", error); //TODO: add exception handling
          });
    },

    toggleCamera() {
      if (this.scannerActive == true) {
        this.stopScanner();
      } else {
        this.initScanner();
      }
    },

      resetSteps(){
          if(this.fridgeTour.steps.length !== 0) {
              while (this.fridgeTour.steps.length !== 0) {
                  this.fridgeTour.steps.pop()
              }
          }
          if(this.information.steps.length !== 0) {
              while (this.information.steps.length !== 0) {
                  this.information.steps.pop()
              }
          }
      },

      //Eirik

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
                          action: function() {
                              //router.push('/fridges');
                              //this.$emit.firstLogginTour();
                              //this.information.cancel();
                          },
                          secondary: true,
                          class: " shepherd-button ",
                          text: this.$t('tour: button whole site'),
                      },
                      {
                          action: () => {
                              this.fridgeViewStepsTour();
                              this.information.cancel();

                          },
                          class: " shepherd-button ",
                          text: this.$t('tour: button this site'),
                      },
                  ]
              }])
          this.information.start()
      },

      fridgeViewStepsTour() {
          this.fridgeViewTour.addSteps([
          {
              id: 'grey-field-header',
              title: this.$t('tour: view:fridgeView method:fridgeViewStepTour id:grey-field-header usage:title'),
              text: this.$t('tour: view:fridgeView method:fridgeViewStepTour id:grey-field-header usage:text'),
              attachTo: {
                  element: '.grey-bar',
                  on: 'bottom',
              },
              classes: 'shepherd-theme-arrows',
                  buttons: [
              {
                  action: function () {
                      return this.back();
                  },
                  secondary: true,
                  text: this.$t('tour: button back'),
              },
              {
                  action: () => {
                      if(this.selectedTab === "fridge"){
                          this.fridgeStepsTour();
                          this.fridgeViewTour.cancel();
                      } else{
                          this.memberStepsTour();
                          this.fridgeViewTour.cancel();
                      }


                  },
                  class: " shepherd-button ",
                  text: this.$t('tour: button next'),
              },
              ]
            },
          ])
          this.fridgeViewTour.start()
      },

      memberStepsTour() {
          if (window.matchMedia("(min-width: 860px)").matches) {
              this.memberTour.addSteps([
                  {
                      id: 'grey-field-header-member',
                      title: this.$t('tour: view:fridgeView method:memberStepTour id:grey-field-header-member usage:title'),
                      text: this.$t('tour: view:fridgeView method:memberStepTour id:grey-field-header-member usage:text'),
                      attachTo: {
                          element: '.router-view-container',

                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              class: " shepherd-button ",
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },
                  {
                      id: 'single-member',
                      title: this.$t('tour: view:fridgeView method:memberStepTour id:single-member usage:title'),
                      text: this.$t('tour: view:fridgeView method:memberStepTour id:single-member usage:text'),
                      attachTo: {
                          element: '.member',
                          on:'bottom',

                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              class: " shepherd-button ",
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },
                  {
                      id: 'edit-user',
                      title: this.$t('tour: view:fridgeView method:memberStepTour id:edit-user usage:title'),
                      text: this.$t('tour: view:fridgeView method:memberStepTour id:edit-user usage:text'),
                      attachTo: {
                          element: '.actions',
                          on:'bottom',

                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              class: " shepherd-button ",
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },
                  {
                      id: 'add-user',
                      title: this.$t('tour: view:fridgeView method:memberStepTour id:add-user usage:title'),
                      text: this.$t('tour: view:fridgeView method:memberStepTour id:add-user usage:text'),
                      attachTo: {
                          element: '.container_button',
                          on:'top',
                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
                          },
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
              this.memberTour.start()
          }
          else if(window.matchMedia("(max-width: 860px)").matches) {
              this.memberTour.addSteps([
                  {
                      id: 'grey-field-header-member',
                      title: this.$t('tour: view:fridgeView method:memberStepTour id:grey-field-header-member usage:title'),
                      text: this.$t('tour: view:fridgeView method:memberStepTour id:grey-field-header-member usage:text'),
                      attachTo: {
                          element: '.router-view-container',

                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              class: " shepherd-button ",
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },
                  {
                      id: 'single-member',
                      title: this.$t('tour: view:fridgeView method:memberStepTour id:single-member usage:title'),
                      text: this.$t('tour: view:fridgeView method:memberStepTour id:single-member usage:text'),
                      attachTo: {
                          element: '.member',
                          on:'bottom',

                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              class: " shepherd-button ",
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },
                  {
                      id: 'edit-user',
                      title: this.$t('tour: view:fridgeView method:memberStepTour id:edit-user usage:title'),
                      text: this.$t('tour: view:fridgeView method:memberStepTour id:edit-user usage:text'),
                      attachTo: {
                          element: '.actions',
                          on:'bottom',

                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              class: " shepherd-button ",
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },
                  {
                      id: 'add-user',
                      title: this.$t('tour: view:fridgeView method:memberStepTour id:add-user usage:title'),
                      text: this.$t('tour: view:fridgeView method:memberStepTour id:add-user usage:text'),
                      attachTo: {
                          element: '.container_button',
                          on:'top',
                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
                          },
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
              this.memberTour.start()
          }
      },


      fridgeStepsTour() {
          if (window.matchMedia("(min-width: 860px)").matches) {
              this.fridgeTour.addSteps([
                  {
                      id: 'grey-field-header-fridge',
                      title: this.$t('tour: view:fridgeView method:fridgeStepsTour id:grey-field-header-fridge usage:title'),
                      text: this.$t('tour: view:fridgeView method:fridgeStepsTour id:grey-field-header-fridge usage:text'),
                      attachTo: {
                          element: '.router-view-container ',
                      },

                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button cancel'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },
                  {
                      id: 'items',
                      title: this.$t('tour: view:fridgeView method:fridgeStepsTour id:items usage:title'),
                      text: this.$t('tour: view:fridgeView method:fridgeStepsTour id:items usage:text'),
                      attachTo: {
                          element: '.wrapper',
                          on: 'top',
                      },

                      buttons: [
                          {
                              action: function () {
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },
                  {
                      id: 'search',
                      title: this.$t('tour: view:fridgeView method:fridgeStepsTour id:search usage:title'),
                      text: this.$t('tour: view:fridgeView method:fridgeStepsTour id:search usage:text'),
                      attachTo: {
                          element: '#searchbar',
                          on: 'top',
                      },

                      buttons: [
                          {
                              action: function () {
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
                          },
                          {
                              action: function ()  {
                                  return this.next();

                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },
                  {
                      id: 'filterstep',
                      title: this.$t('tour: view:fridgeView method:fridgeStepsTour id:filter usage:title'),
                      text: this.$t('tour: view:fridgeView method:fridgeStepsTour id:filter usage:text'),
                      attachTo: {
                          element: '.searchbar-wrapper',
                      },
                      beforeShowPromise: function() {
                          return new Promise(function(resolve, reject) {
                              const button = document.querySelector('#toggle');
                              button.click();
                              resolve();
                          });
                      },

                      buttons: [
                          {
                              action: function () {
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
                          },
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              text: this.$t('tour: button exit'),
                          },
                      ]
                  },
              ]);
              this.fridgeTour.start()
          }
          else if(window.matchMedia("(max-width: 860px)").matches) {
              this.fridgeTour.addSteps([
                  {
                      id: 'grey-field-header-fridge',
                      title: this.$t('tour: view:fridgeView method:fridgeStepsTour id:grey-field-header-fridge usage:title'),
                      text: this.$t('tour: view:fridgeView method:fridgeStepsTour id:grey-field-header-fridge usage:text'),
                      attachTo: {
                          element: '.grey-bar',
                          on: 'bottom',
                      },
                      arrow:'true ',

                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },
                  {
                      id: 'fridgesWindow',
                      title: this.$t('tour: view:fridgesView method:firstLogginTour id:fridgesWindow usage:title'),
                      text: this.$t('tour: view:fridgesView method:firstLogginTour id:fridgesWindow usage:text'),
                      attachTo: {
                          element: 'div.router-view-container',
                          on: 'bottom',
                      },

                      buttons: [
                          {
                              action: function () {
                                  return this.cancel();
                              },
                              secondary: true,
                              text: this.$t('tour: button exit'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },
                  {
                      id: 'grey-field-kjøleskap',
                      title: this.$t('tour: view:fridgesView method:firstLogginTour id:grey-field-kjøleskap usage:title'),
                      text: this.$t('tour: view:fridgesView method:firstLogginTour id:grey-field-kjøleskap usage:text'),
                      attachTo: {
                          element: '#grey-header',
                          on: 'bottom',
                          offset: {x: 0, y: 20},
                      },
                      classes: 'shepherd-theme-arrows',
                      buttons: [
                          {
                              action: function () {
                                  return this.back();
                              },
                              secondary: true,
                              text: this.$t('tour: button back'),
                          },
                          {
                              action: function () {
                                  return this.next();
                              },
                              text: this.$t('tour: button next'),
                          },
                      ]
                  },
              ])
              this.fridgeTour.start()
          }
      },
  },

  setup() {
    const fridgeStore = useFridgeStore();
    const itemStore = useItemStore();
    const selectedTab = ref(
        router.currentRoute.value.query.selectedTab || "fridge"
    );

    history.replaceState(history.state, '', "/fridge");

    const searchItems = ref([]);
    const search = ref(false);
    const fridgeItems = ref([]);
    const fridge = fridgeStore.getCurrentFridge;
    let scannerActive = ref(false);
    const isLoadingItems = ref(false);
    const page = ref(0);
    const searchText = ref("");
    const selectedCategory = ref(0);
    const categories = ref<Array<{ id: number; name: string }>>([]);
    const scrollTarget = ref(null);
    const isLoading = ref(false);
    let nextPage = 1;

    const sortOptions = ref([
      { key: "expirationDate", direction: "DESC" },
      { key: "expirationDate", direction: "ASC" },
      { key: "purchaseDate", direction: "DESC" },
      { key: "purchaseDate", direction: "ASC" },
    ]);

    const searchParamOptions = ref(["productName"]);

    const selectedSearchParam = ref(searchParamOptions.value[0]);

    const sort = ref(sortOptions.value[0]);

    const loadMore = () => {
      if (!isLoadingItems.value) {
        isLoadingItems.value = true;

        const filters: Filter[] = [
          {
            fridgeId: fridge.fridgeId,
            productName: searchText.value,
            sortField: sort.value.key,
            sortOrder: sort.value.direction,
            page: page.value,
            pageSize: 15,
          },
        ];

        const itemSearch = {
          fridgeId: fridge.fridgeId,
          productName: searchText.value,
          sortField: sort.value.key,
          sortOrder: sort.value.direction,
          page: page.value,
          pageSize: 15,
        };

        itemStore
          .filterItemsInFridge(itemSearch)
          .then((response) => {
            page.value++;
            fridgeItems.value = [...fridgeItems.value, ...response];
            isLoadingItems.value = false;
          })
          .catch((error) => {
            console.error(error);
            isLoadingItems.value = false;
          });
      }
    };

    const searchHandler = () => {
      page.value = 0;
      fridgeItems.value = [];
      loadMore();
    };

    const observeBottom = () => {
      const bottomElement = document.querySelector("#bottom-element");
      const observer = new IntersectionObserver(
          (entries) => {
            entries.forEach((entry) => {
              if (entry.isIntersecting) {
                loadMore();
              }
            });
          },
          { threshold: 1 }
      );
      if (bottomElement) {
        observer.observe(bottomElement);
      }
    };

    onMounted(async () => {
      await observeBottom();
      window.addEventListener("scroll", handleScroll);
      loadMore();
    });

    onUnmounted(() => {
      const bottomElement = document.querySelector("#bottom-element");
      const observer = new IntersectionObserver(() => {}, { threshold: 1 });
      if (bottomElement) {
        observer.unobserve(bottomElement);
      }
      window.removeEventListener("scroll", handleScroll);
    });

    const handleScroll = async () => {
      if (scrollTarget.value !== null) {
        const bottomOfWindow =
          Math.ceil(scrollTarget.value.getBoundingClientRect().bottom) <=
          (window.innerHeight || document.documentElement.clientHeight);
        if (bottomOfWindow) {
          await loadMoreSearchItems();
        }
      }
    };

    async function loadMoreSearchItems() {
      if (isLoading.value) return;
      isLoading.value = true;
      try {
        let response = await getItemsByPage(searchQuery.value, nextPage);
        nextPage++;
        if (response) {
          searchItems.value = [...searchItems.value, ...response];
        }
      } catch (error) {
        console.error("Error loading search items", error); //TODO: add swal.fire ....
      }

      isLoading.value = false;
    }

    const instance = getCurrentInstance();
    onBeforeUnmount(() => {
      if (instance && instance.proxy && scannerActive.value == true) {
        instance.proxy.stopScanner();
      }
    });
    const itemAmount = ref(1);
    const submitMessage = ref("");
    const searchQuery = ref("");
    const active = ref(false);
    const click = ref(false);

    //Eirik
      const fridgeTour = new Shepherd.Tour({
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

      const fridgeViewTour = new Shepherd.Tour({
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

      const memberTour = new Shepherd.Tour({
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
      scrollTarget,
      active,
      click,
      fridge,
      searchItems,
      fridgeItems,
      selectedTab,
      itemAmount,
      submitMessage,
      searchQuery,
      fridgeStore,
      search,
      itemStore,
      scannerActive,
      searchText,
      searchHandler,
      selectedCategory,
      categories,
      sort,
      sortOptions,
      selectedSearchParam,
      searchParamOptions,
      fridgeTour,
      information,
      fridgeViewTour,
      memberTour,
    }
  },

  data() {
    return {
      isExpanded: false,
      showNotifications: false,
      listView: false,
    };
  },
};
</script>

<style scoped>
textarea {
  margin-bottom: 10px;
  padding: 5px;
}

template {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.wrapper {
  display: grid;
  grid-template-columns: 25% 50% 25%;
  z-index: 0;
}

#info-and-bell {
  display: flex;
  flex-direction: row;
  justify-content: end;
  gap: 34%;
  margin-left: auto;
}

* {
  font-family: Roboto, sans-serif;
}
#barcode-scanner {
  overflow-x: hidden;
  overflow-y: hidden;
}
#interactive {
  text-align: center;
  width: 95vw;
  height: 380px;
  margin: auto;

  transform: translate(25%);
}

.viewport video {
  width: 400px;
  height: 300px;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.25s ease;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.searchbar-wrapper {
  display: flex;
  align-items: center;
  gap: 40px;
  background-color: white;
  border-radius: 8px;
  height: 79px;
  overflow-x: hidden;
}

#toggle {
  margin-top: 10px;
  margin-left: 10px;
  height: 40px;
  width: 8%;
  border-radius: 20px;
  background-color: #31c48d;
  border: 0;
}

#toggle:hover {
  background-color: #238b65;
  cursor: pointer;
}

#filter {
  display: flex;
  align-content: center;
  justify-content: center;
  gap: 25px;
  padding: 16px;
  margin-top: 10px;
  border-radius: 50px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 0;
  width: 70%;
  background-color: #31c48d;
  transform: translateX(2000px);
  -webkit-transform: translateX(2000px);
}

#filter-component {
  transform: translateX(2000px);
  -webkit-transform: translateX(2000px);
  margin-top: 10px;
  height: 86%;
  width: 13%;
  margin-left: auto;
  margin-right: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 50px;
  background-color: transparent;
}

#search-wrapper {
  display: flex;
  align-items: center;
  gap: 8px;
  width: 60%;
  border-radius: 20px;
}

#search-wrapper input {
  width: 100%;
  border-radius: 50px;
}

#sort-wrapper {
  display: flex;
  align-items: center;
  gap: 8px;
  width: 30%;
  border-radius: 20px;
}

#sort-wrapper select {
  width: 100%;
  border-radius: 50px;
}

.slide-in {
  animation: slide-in 0.5s forwards;
  -webkit-animation: slide-in 0.5s forwards;
}

.slide-out {
  animation: slide-out 0.5s forwards;
  -webkit-animation: slide-out 0.5s forwards;
}

@keyframes slide-in {
  100% {
    transform: translateX(0%);
    opacity: 1;
    pointer-events: all;
  }
}

@-webkit-keyframes slide-in {
  100% {
    -webkit-transform: translateX(0%);
    opacity: 1;
    pointer-events: all;
  }
}

@keyframes slide-out {
  0% {
    transform: translateX(0%);
  }
  100% {
    transform: translateX(2000px);
  }
}

@-webkit-keyframes slide-out {
  0% {
    -webkit-transform: translateX(0%);
  }
  100% {
    -webkit-transform: translateX(2000px);
  }
}

input[type="text"],
select {
  padding: 8px 12px;
  margin: 0;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #fff;
  font-size: 16px;
  box-sizing: border-box;
}

input[type="text"] {
  width: 300px;
}

select {
  width: 250px;
  appearance: none;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.list-wrapper {
  display: grid;
  grid-template-columns: 50% 50%;
  z-index: 0;
  margin: 2% 2% 80px;
}

.grey-bar {
  background-color: #6c6c6c;
  text-align: center;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
}

#searchbar {
  display: flex;
  background-color: #6c6c6c;
  margin: 0;
  border: 0;
  width: 100%;
}

#searchbtn {
  padding: 0 10px;
  margin-top: 0;
  color: #fff;
  background: #31c48d;
  font-size: 27px;
  font-weight: 500;
  border: 0 solid #555;
  border-left: none;
  -webkit-box-shadow: none;
  box-shadow: none;
  height: 40px;
  margin-right: 10px;
  border-radius: 0 50px 50px 0 !important;
}

#searchbtn:hover {
  cursor: pointer;
  background-color: #238b65;
}

#grey-header {
  grid-column: 2;
  color: white;
}

.information-button {
  justify-content: center;
  align-content: center;
  align-items: center;
  height: 40px;
}

#info-picture {
  height: 30px;
  width: 30px;
  cursor: pointer;
}

.fridge-wrapper {
  display: grid;
  flex-direction: column;
}

.vcpg {
  --bg-color-header: #6c6c6c !important;
  --bg-color-header-hover: #6c6c6c !important;
  --bg-color-header-active: #6c6c6c !important;
  border-radius: 10px 10px 10px 10px;
}

.dropdown a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.search-results li {
  padding: 8px;
}

input[type="text"]:focus {
  display: block;
}

.dropdown a:hover {
  background-color: #ddd;
}

.link {
  text-decoration: none;
  line-height: 25px;
  color: white;
}

#toggle-button:hover {
  cursor: pointer;
  font-size: x-large;
}

.wrapper {
  z-index: 0;
  margin-left: 2%;
  margin-right: 2%;
  margin-top: 2%;
  grid-template-columns: repeat(auto-fill, minmax(345px, 1fr));
  grid-template-rows: repeat(auto-fill, minmax(250px, 1fr));
  transition: 0.5s;
}

.grey-bar .link.active {
  height: 25px;
}

.active {
  background-size: 25px;
  background-color: #b1b1b1;
  border-radius: 5px;
  font-weight: bold;
  text-decoration: black;
  text-shadow: black 0 0 2px;
}

.members-fridge {
  background-color: #6c6c6c;
  height: 35px;
  color: white;
  font-size: 1.5em;
  padding-top: 5px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-column-gap: 20px;
  grid-column: 2;
}

.dropper {
  color: white;
  display: flex;
  justify-content: space-evenly;
  overflow-y: scroll;
}

.vcpg {
  --bg-color-header: transparent !important;
  border: transparent;
  width: 100%;
  color: black;
  background-color: white;
  border-radius: 0;
  overflow-y: scroll;
}

#backGreen {
  background-color: #6c6c6c;
}

@media (max-width: 1350px) {
  #interactive {
    transform: none;
    position: relative;
    overflow: hidden;
  }
}

@media (max-width: 860px) {
  .list-wrapper {
    display: grid;
    grid-template-columns: 1fr;
  }
}

@media (max-width: 650px) {
  .information-button {
    justify-content: center;
    align-content: center;
    align-items: center;
    height: 60px;
  }

  #filter-component {
    width: 100%;
  }

  #info-and-bell {
    display: flex;
    flex-direction: row;
    justify-content: end;
    left: 8%;
    gap: 40%;
  }

  .fridge-wrapper {
    height: 100%;
    grid-template-rows: repeat(auto-fill, minmax(95px, 95px));
  }



  .wrapper {
    z-index: 0;
    margin-bottom: 80px;
    overflow-y: auto;
    grid-template-columns: repeat(auto-fill, minmax(355px, 1fr));
    grid-template-rows: repeat(auto-fill, minmax(95px, 95px));
  }
}

@media only screen and (min-width: 10px) and (max-width: 650px) {
  #toggle-button:hover {
    cursor: pointer;
    font-size: initial;
  }

  #searchbtn {
    display: none;
  }

  .list-wrapper {
    z-index: -1;
    overflow-y: scroll;
  }

  #searchbtn {
    display: none;
  }

  .slide-in {
    display: block !important;
  }
  .slide-out {
    display: none !important;
  }

  .marg{
    margin-bottom: 20%;
  }

  #filter {
    all: unset;
    width: 100%;
    margin: 10px;
    padding: 10px;
    background-color: #31c48d;
    border-radius: 20px;
  }

  #filter input {
    width: 100%;
    border-radius: 50px;
  }

  #filter select {
    width: 100%;
    border-radius: 50px;
  }

  #search-wrapper {
    width: 100%;
    margin-bottom: 10px;
  }

  #sort-wrapper {
    width: 100%;
  }

  #filter-component{
    display: none !important;
  }

  .searchbar-wrapper{
    position: relative;
    display: flex;
    gap: 0;
    flex-wrap: wrap;
    overflow-x: unset;
    height: unset;
  }

  #toggle{
    width: 100%;
    margin-left: 20%;
    margin-right: 20%;
    color: black;
    font-size: 15px;
  }
  .wrapper {
    z-index: -1;
    grid-template-rows: 1fr;
    margin-bottom: 0;
    overflow-y: scroll;
  }
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

  .information-button{
    margin-right: 3px !important;
  }

  #searchbar {
    display: flex;
    position: fixed;
    bottom: 70px;
    width: 100%;
    z-index: 2;
    background-color: transparent;
  }

  #searchbtn {
    display: none !important;
  }

  #backGreen {
    height: 0;
    width: 100%;
    padding: 0 10px 0 10px;
    z-index: 2;
  }

  #myDropdown {
    position: fixed;
    z-index: 1;
  }

  .fridge-wrapper {
    display: flex;
    width: 100%;
    z-index: 0;
    padding-bottom: 90px;
  }

  .vcpg{
      max-height: 150vh;
  }

  #scrollTarget{
      margin-bottom: 30px;
  }
}
</style>
