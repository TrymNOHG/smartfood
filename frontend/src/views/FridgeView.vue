<template>
  <div class="members-fridge">
    <div id="toggle-button" class="link" @click="selectedTab = 'members'" :class="{ active: selectedTab === 'members' }">{{ $t('toggle_members') }}</div>
    <div id="toggle-button" class="link" @click="selectedTab = 'fridge'" :class="{ active: selectedTab === 'fridge' }">{{ $t('toggle_fridge') }}</div>
  </div>
  <!--TODO: add infinite scroller or pagination-->
  <div class="fridge-wrapper" v-show="selectedTab === 'fridge'">
    <div class="search-container">
      <div class="dropdown">
        <SearchInput @input="handleSearch()" v-model="searchQuery" :label="$t('search_product')" class="search-input" />
        <button class="search-btn" @click="handleSearch()">{{ $t('search') }}</button>
      </div>
      <div class="search-overlay" v-show="isExpanded" @click="isExpanded = false"></div>
      <div class="search-results" v-show="isExpanded">
        <vue-collapsible-panel-group>
          <vue-collapsible-panel :expanded="isExpanded">
            <template #title>{{ $t('search_results') }}</template>
            <template #content style="overflow-y: auto;">
              <div class="search-item-list" style="overflow-y: auto; max-height: 250px">
                <SearchItem
                    v-for="(item, index) in searchItems"
                    :key="index"
                    :image="item.image"
                    :text="item.name"
                    :store="item.store.name"
                    :price="item.current_price"
                    @click="addItemToFridge(this.fridge.fridgeId, item)"
                />
              </div>
            </template>
          </vue-collapsible-panel>
        </vue-collapsible-panel-group>
      </div>
    </div>
    <div class="wrapper" :style="{marginTop: marginTopStyle}">
      <basic-fridge-item v-for="(item, index) in fridgeItems" :key="index" :item="item" :currenFridge="fridge"
                         @delete-item="deleteItem"/>
    </div>
  </div>
  <div class="members-wrapper" v-show="selectedTab === 'members'">
    <member-component/>
  </div>
</template>

<script>
import {
  VueCollapsiblePanelGroup,
  VueCollapsiblePanel,
} from "@dafcoe/vue-collapsible-panel";
import {useRoute} from "vue-router";
import MemberComponent from "@/components/SpecificFridge/MemberComponent.vue";
import BasicFridgeItem from "@/components/SpecificFridge/BasicFridgeItem.vue";
import {useFridgeStore, useItemStore} from "@/store/store"
import {ref} from "vue";
import SearchInput from "@/components/searchFromApi/SearchInput.vue";
import SearchItem from "@/components/searchFromApi/SearchItem.vue";
import {getItems} from "@/services/ApiService";
import Swal from 'sweetalert2';


export default {
  name: "FridgeView",
  components: {
    SearchItem,
    SearchInput,
    BasicFridgeItem,
    MemberComponent,
    VueCollapsiblePanelGroup,
    VueCollapsiblePanel,
  },

  computed: {
    marginTopStyle(){
      return this.isExpanded ? "5%" : "0"
    }
  },

  methods: {
    handleSearch() {
      getItems(this.searchQuery).then((response) => {
        this.searchItems = response;
        this.isExpanded = true;
      })
          .catch((error) => {
            console.error(error);
          });

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
      await this.itemStore.deleteItemByStats(statDeleteFromFridgeDTO);
      await this.itemStore.deleteItemByNameIdStoreQuantity(itemRemoveDTO);
      await this.itemStore.fetchItemsFromFridgeById(this.fridge.fridgeId).then((items) => {
        this.fridgeItems = items;
      });
    },

    async addItemToFridge(fridgeId, item) {
      const { value: confirmed } = await Swal.fire({
        title: 'Add item to fridge?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        cancelButtonText: 'No'
      });

      if (!confirmed) {
        return;
      }

      const date = new Date();
      const expirationDate = new Date(date);
      expirationDate.setDate(date.getDate() + 7);

      const statAddItemToFridgeDTO = {
        "price": item.current_price,
        "quantity": 1,
        "itemName": item.name,
        "storeName": item.store.name,
        "fridgeId": this.fridge.fridgeId
      }

      const itemDTO = {
        "name": item.name,
        "description": item.description,
        "store": item.store.name,
        "price": item.current_price,
        "purchaseDate": date,
        "expirationDate": expirationDate,
        "image": item.image,
        "quantity": 1
      }

      await this.itemStore.statAddItemToFridge(statAddItemToFridgeDTO)
      await this.itemStore.addItemToFridgeById(this.fridge.fridgeId, itemDTO);
      await this.itemStore.fetchItemsFromFridgeById(this.fridge.fridgeId).then((items) => {
        this.fridgeItems = items;
      });
    }

  },

  setup() {
    const fridgeStore = useFridgeStore();
    const itemStore = useItemStore();
    const selectedTab = ref("fridge");
    const searchItems = ref([]);
    const fridgeItems = ref([]);
    const fridge = fridgeStore.getCurrentFridge

    itemStore.fetchItemsFromFridgeById(fridge.fridgeId).then((items) => {
      fridgeItems.value = items;
    });

    const itemAmount = ref(1);
    const submitMessage = ref("norvegia");
    const searchQuery = ref('');

    return {
      fridge,
      searchItems,
      fridgeItems,
      selectedTab,
      itemAmount,
      submitMessage,
      searchQuery,
      fridgeStore,
      itemStore,
    }
  },

  data() {
    return {
      isExpanded: false,
    }
  },
}
</script>

<style scoped>



.fridge-wrapper {
  display: grid;
}

.vcpg {
  --bg-color-header: #6c6c6c !important;
  --bg-color-header-hover: #6c6c6c !important;
  --bg-color-header-active: #6c6c6c !important;
  border-radius: 10px 10px 10px 10px;
}

.dropdown {
  display: flex;
  justify-content: center;
}

.search-btn {
  padding: 0 10px;
  margin-top: 10px;
  color: #fff;
  background: #6c6c6c;
  font-size: 25px;
  font-weight: 500;
  border: 3px solid #555;
  border-left: none;
  -webkit-box-shadow: none;
  box-shadow: none;
  max-height: 60px;
  border-radius: 0 50px 50px 0 !important;
}

.dropdown {
  width: 100%;
  position: relative;
  text-align: center;
}

.dropdown a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.search-results {
  left: 0;
  background-color: white;
  border: 1px solid gray;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  padding: 0;
  margin-left: 10%;
  list-style: none;
  width: 80%;
  z-index: 2;
}

.search-results li {
  padding: 8px;
}

input[type="text"]:focus + .search-results {
  display: block;
}

input[type="text"]:not(:focus) + .search-results {
  display: none;
}

.dropdown a:hover {
  background-color: #ddd;
}

.link {
  text-decoration: none;
  color: white;
}

#toggle-button {
  width: 150px;
  margin-top: 5px;
  margin-right: 50px;
}

#toggle-button:hover {
  color: #3b3b3b;
}

.search-results {
  position: absolute;
  top: 95%;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 1;
  background-color: white;
  color: white;
}

.wrapper {
  z-index: 0;
  margin-left: 2%;
  margin-right: 2%;
  grid-template-columns: repeat(auto-fill, minmax(345px, 1fr));
  grid-template-rows: repeat(auto-fill, minmax(250px, 1fr));
  transition: 0.5s;
}

.active {
    height: 25px;
    width: 150px;
    background-color: #b1b1b1;
    border-radius: 5px;
    font-weight: bold;
    text-decoration: black;
    text-shadow: black 0 0 2px;
    margin-left: 50px;
    margin-top: 5px;
}

.members-fridge {
  background-color: #6C6C6C;
  height: 35px;
  color: white;
  text-align: center;
  display: flex;
  justify-content: center;
}

@media (max-width: 650px) {

  .fridge-wrapper {
    height: 100%;
    grid-template-rows: repeat(auto-fill, minmax(95px, 95px));
  }

  .link {
    margin: 10px 0;
  }

  .wrapper {
    margin-bottom: 80px;
    overflow-y: auto;
    grid-template-columns: repeat(auto-fill, minmax(355px, 1fr));
    grid-template-rows: repeat(auto-fill, minmax(95px, 95px));

  }
}

</style>