<template>
  <div class="grey-bar">
    <div class="members-fridge">
      <div
        id="toggle-button"
        class="link"
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
    <div class="information-button">
      <img
        src="@/assets/images/info.svg"
        id="info-picture"
        @click="showInformation"
        :alt="$t('alt_info_button')"
      />
    </div>
  </div>

  <!--TODO: add infinite scroller or pagination-->
  <div class="fridge-wrapper" v-show="selectedTab === 'fridge'">
    <div id="myDropdown" class="dropdown-content">
      <figure id="backBlack"></figure>
      <div id="backGreen">
        <div id="searchbar">
          <SearchInput
            v-model="searchQuery"
            @input="handleSearch"
            :label="$t('add_item')"
            @receipt-upload="handleReceiptUpload"
          ></SearchInput>
          <button id="searchbtn" @click="handleSearch">
            {{ $t("search") }}
          </button>
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
                :price="item.current_price"
                style="text-align: center"
                @click="addItemToFridge(this.fridge.fridgeId, item)"
              />
            </template>
          </vue-collapsible-panel>
        </vue-collapsible-panel-group>
      </div>
    </div>
    <div class="filter-component">
      <filter-bar  @filter="filtering" @listing="listing"/>
    </div>
    <div v-if="!listView" class="wrapper" :style="{ marginTop: marginTopStyle }">
      <basic-fridge-item
        :isSuperUser="isCurrentUserSuperUser"
        v-for="(item, index) in fridgeItems"
        :key="index"
        :item="item"
        :currenFridge="fridge"
        @delete-item="deleteItem"
      />
  </div>
    <div v-else class="list-wrapper">
      <basic-fridge-list  v-for="(item, index) in fridgeItems" :key="index" :item="item" :currenFridge="fridge"
                          @delete-item="deleteItem"/>
    </div>
    <div class="members-wrapper" v-show="selectedTab === 'members'">
      <member-component />
    </div>
  </div>
</template>

<script>
import {
  VueCollapsiblePanelGroup,
  VueCollapsiblePanel,
} from "@dafcoe/vue-collapsible-panel";
import { useRoute } from "vue-router";
import MemberComponent from "@/components/SpecificFridge/MemberComponent.vue";
import BasicFridgeItem from "@/components/SpecificFridge/BasicSquareList.vue";
import { useFridgeStore, useItemStore } from "@/store/store";
import { ref } from "vue";
import SearchInput from "@/components/searchFromApi/SearchInput.vue";
import SearchItem from "@/components/searchFromApi/SearchItem.vue";
import { getItems } from "@/services/ApiService";
import Swal from "sweetalert2";
import { addItemToShoppingList } from "@/services/ItemService";
import FilterBar from "@/components/SpecificFridge/FilterBar.vue";
import BasicFridgeList from "@/components/SpecificFridge/BasicFridgeList.vue";

export default {
  name: "FridgeView",
  components: {
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
    marginTopStyle() {
      return this.isExpanded ? "1%" : "1%";
    },
    isCurrentUserSuperUser() {
      return useFridgeStore().getIsSuperUser;
    },
  },

  methods: {

    filtering(bool){
      this.listView = bool;
    },

    listing(bool){
      this.listView = bool;
    },


    handleReceiptUpload() {},
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

    async deleteItem(itemToDelete, deletePercentage) {
      const statDeleteFromFridgeDTO = {
        percentageThrown: parseFloat(deletePercentage),
        price: itemToDelete.price,
        quantity: parseFloat(itemToDelete.quantity),
        itemName: itemToDelete.name,
        storeName: itemToDelete.store,
        fridgeId: this.fridge.fridgeId,
      };

      const itemRemoveDTO = {
        itemName: itemToDelete.name,
        store: itemToDelete.store,
        fridgeId: this.fridge.fridgeId,
        quantity: itemToDelete.quantity,
      };

      console.log(statDeleteFromFridgeDTO);
      await this.itemStore.deleteItemByStats(statDeleteFromFridgeDTO);
      await this.itemStore.deleteItemByNameIdStoreQuantity(itemRemoveDTO);
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
      const expirationDate = new Date(date);
      expirationDate.setDate(date.getDate() + 7);

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
        quantity: 1,
      };

      if (!this.isCurrentUserSuperUser) {
        await addItemToShoppingList(itemDTO, fridgeId, true).then(
          async (response) => {
            console.log("response", response);
            console.warn("error1", error); //TODO: add exception handling
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

  },

  setup() {
    const fridgeStore = useFridgeStore();
    const itemStore = useItemStore();
    const selectedTab = ref("fridge");
    const searchItems = ref([]);
    const search = ref(false);
    const fridgeItems = ref([]);
    const fridge = fridgeStore.getCurrentFridge;

    itemStore.fetchItemsFromFridgeById(fridge.fridgeId).then((items) => {
      fridgeItems.value = items;
    });

    const itemAmount = ref(1);
    const submitMessage = ref("norvegia");
    const searchQuery = ref("");

    return {
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
    };
  },

  data() {
    return {
      isExpanded: false,
      listView: false,
    }
  },
};
</script>

<style scoped>

.list-wrapper {
  display: grid;
  grid-template-columns: 50% 50%;
  margin: 2%;
  z-index: 0;
}

.grey-bar {
  background-color: #6c6c6c;
  max-height: 35px;
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

#grey-header {
  grid-column: 2;
  color: white;
}

.information-button {
  grid-column: 3;
  text-align: right;
  padding: 2px 5px;
  max-height: 35px;
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

input[type="text"]:not(:focus) {
  display: none;
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
}

#backGreen {
  background-color: #6c6c6c;
}

@media (max-width: 860px) {

  .list-wrapper {
    display: grid;
    grid-template-columns: 1fr;
  }

}


@media (max-width: 650px) {

  .filter-component {
    width: 100%;
  }

  .fridge-wrapper {
    height: 100%;
    grid-template-rows: repeat(auto-fill, minmax(95px, 95px));
  }

  .link {
    margin: 10px 0;
  }

  .wrapper {
    z-index: 0;
    margin-bottom: 80px;
    overflow-y: auto;
    grid-template-columns: repeat(auto-fill, minmax(355px, 1fr));
    grid-template-rows: repeat(auto-fill, minmax(95px, 95px));
  }
}

@media only screen and (min-width: 350px) and (max-width: 480px) {
  #searchbtn {
    display: none;
  }

  .list-wrapper {
    z-index: -1;
    overflow-y: scroll;
  }


  #searchbtn{
    display: none;
  }

  .grey-bar {
    display: flex;
    align-content: center;
    align-items: center;
    justify-content: center;
    margin-top: 5px;
    background-color: #31c48d;
    max-height: 60px;
    height: 60px;
    border-radius: 20px 20px 0 0;
  }

  .wrapper {
    z-index: -1;
    grid-template-rows: 1fr;
    margin-bottom: 0;
    overflow-y: scroll;
  }

  .members-fridge {
    background-color: #31c48d;
    margin-top: 0;
    padding-top: 0;
    padding-right: 10px;
    text-align: center;
    align-items: center;
    align-content: center;
    justify-content: center;
  }

  .link {
    margin: 0;
  }

  .link.active {
    height: 60px !important;
    background-color: white;
    border-radius: 20px 20px 0 0;
    font-weight: bold;
    text-decoration: none;
    text-shadow: none;
    color: black;
    margin-top: 20px;
    padding-top: 10px;
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
    height: 0px;
    width: 100%;
    padding: 0px 10px 0px 10px;
    z-index: 2;
  }

  #myDropdown {
    position: fixed;
    z-index: 0;
  }

  .fridge-wrapper {
    display: flex;
    width: 100%;
    z-index: 0;
    margin-bottom: 150px;
  }
}
</style>
