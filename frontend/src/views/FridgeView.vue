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
    <div class="searchbar-wrapper">
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
        <filter-bar @listing="listing" />
      </div>
    </div>
    <transition name="fade">
      <div
        v-if="!listView"
        class="wrapper"
        :style="{ marginTop: marginTopStyle }"
      >
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
</template>

<script lang="ts">
import {
  VueCollapsiblePanelGroup,
  VueCollapsiblePanel,
} from "@dafcoe/vue-collapsible-panel";
import MemberComponent from "@/components/SpecificFridge/MemberComponent.vue";
import BasicFridgeItem from "@/components/SpecificFridge/BasicSquareList.vue";
import { useFridgeStore, useItemStore } from "@/store/store";
import { onMounted, onUnmounted, ref } from "vue";
import SearchInput from "@/components/searchFromApi/SearchInput.vue";
import SearchItem from "@/components/searchFromApi/SearchItem.vue";
import { getItemByBarcode, getItems } from "@/services/ApiService";
import Swal from "sweetalert2";
import { addItemToShoppingList } from "@/services/ItemService";
import FilterBar from "@/components/SpecificFridge/FilterBar.vue";
import BasicFridgeList from "@/components/SpecificFridge/BasicFridgeList.vue";
import router from "../router/router";
import Quagga from "quagga";

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
    handleClick() {
      if(this.click != true) this.click = true
      this.active = !this.active;

    },

    listing(bool) {
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
            console.log("response", response);
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
          },
        },
        (err) => {
          if (err) {
            console.log(err);
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
      console.log("Detected barcode:", code);

      await getItemByBarcode(code)
        .then((response) => {
          if (response !== undefined) {
            this.searchItems = response.products;
            console.log(response.products);
            this.search = true;
            this.scannerActive = false;
          } else {
            console.log("Something went wrong");
            submitMessage.value =
              "Something went wrong. Please try again later.";
          }
        })
        .catch((error) => {
          console.warn("error1", error); //TODO: add exception handling
        });
    },
    toggleCamera() {
      console.log("toggling", this.scannerActive, this.scannerActive);
      if (this.scannerActive == true) {
        this.stopScanner();
      } else {
        this.initScanner();
      }
    },
  },

  setup() {
    const fridgeStore = useFridgeStore();
    const itemStore = useItemStore();
    const selectedTab = ref(
      router.currentRoute.value.query.selectedTab || "fridge"
    );

    history.replaceState(null, null, "/fridge");
    const currentUrl = window.location.href;

    const searchItems = ref([]);
    const search = ref(false);
    const fridgeItems = ref([]);
    const fridge = fridgeStore.getCurrentFridge;
    let scannerActive = ref(false);
    const isCameraToggled = ref(false);
    const isLoading = ref(false);
    const page = ref(0);
    const searchText = ref("");
    const selectedCategory = ref(0);
    const categories = ref<Array<{ id: number; name: string }>>([]);

    const sortOptions = ref([
      { key: "expirationDate", direction: "DESC" },
      { key: "expirationDate", direction: "ASC" },
      { key: "purchaseDate", direction: "DESC" },
      { key: "purchaseDate", direction: "ASC" },
    ]);

    const searchParamOptions = ref(["productName"]);

    const selectedSearchParam = ref(searchParamOptions.value[0]);

    const sort = ref(sortOptions.value[0]);

    itemStore.fetchItemsFromFridgeById(fridge.fridgeId).then((items) => {
      fridgeItems.value = items;
    });

    const loadMore = () => {
      if (!isLoading.value) {
        isLoading.value = true;

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
            isLoading.value = false;
          })
          .catch((error) => {
            console.error(error);
            isLoading.value = false;
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
      loadMore();
    });

    onUnmounted(() => {
      const bottomElement = document.querySelector("#bottom-element");
      const observer = new IntersectionObserver(() => {}, { threshold: 1 });
      if (bottomElement) {
        observer.unobserve(bottomElement);
      }
    });

    const itemAmount = ref(1);
    const submitMessage = ref("norvegia");
    const searchQuery = ref("");
    const active = ref(false);
    const click = ref(false);

    return {
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
    };
  },

  data() {
    return {
      isExpanded: false,
      listView: false,
    };
  },
};
</script>

<style scoped>

*{
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
  overflow-x: hidden;
  height: 79px;
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
  background-color: #f8f8f8;
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
  margin: 0; /* Reset margin */
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #fff;
  font-size: 16px;
  box-sizing: border-box; /* Fix for width and height */
}

input[type="text"] {
  width: 300px;
}

select {
  width: 250px;
  appearance: none; /* Remove default styling for consistent appearance */
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

#searchbtn:hover{
  cursor: pointer;
  background-color: #238b65;

}

#grey-header {
  grid-column: 2;
  color: white;
}

.information-button {
  display: flex;
  grid-column: 3;
  text-align: right;
  margin-left: auto;
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
  #filter-component {
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

@media only screen and (min-width: 10px) and (max-width: 650px) {
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
  .slide-in {
    display: block !important;
  }
  .slide-out {
    display: none !important;
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

  #filter-component {
    display: none !important;
  }

  .searchbar-wrapper {
    gap: 0;
    flex-wrap: wrap;
    height: unset;
  }

  #toggle {
    width: 100%;
    margin-left: 20%;
    margin-right: 20%;
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
    margin-left: 10px;
  }

  .link {
    margin: 0;
    padding-left: 5px;
    padding-right: 5px;
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
    padding-right: 5px;
    padding-left: 5px;

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
    margin-bottom: 70px;
  }
}
</style>
