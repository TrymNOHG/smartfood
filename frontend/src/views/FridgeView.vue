<template>
  <div class="break-line"/>
  <div class="name-display">
    <h1 class="fridge-name">
      <router-link to="/fridges" class="link-name">
        {{ fridge.fridgeName }}
      </router-link>
    </h1>
    <div class="change-button">
      <router-link class="link-button" to="/fridges">
        Endre Kjøleskap
      </router-link>
    </div>
  </div>
  <div class="members-fridge">
    <router-link id="member" class="link" to="/members">Members</router-link>
    <router-link id="fridge" class="link" to="/fridges">Fridge</router-link>
  </div>

  <div class="dropdown">
    <SearchInput v-model="searchQuery" label="Search product" class="search-input" />
    <button class="search-btn" @click="handleSearch()">Search</button>
  </div>

  <div class="search-results">
    <vue-collapsible-panel-group accordion>
      <vue-collapsible-panel :expanded="isExpanded">
        <template #title>Search results</template>
        <template #content>
          <div class="search-item-list">
            <SearchItem
                v-for="(item, index) in searchItems"
                :key="index"
                :image="item.image"
                :text="item.name"
                :store="item.store.name"
                :price="item.current_price"
                @click="addItemToList(item)"
            />
          </div>
        </template>
      </vue-collapsible-panel>
    </vue-collapsible-panel-group>
  </div>

  <div class="wrapper">
    <basic-fridge-item v-for="(item, index) in items" :key="index" :item="item" :currenFridge="fridge" />
  </div>

</template>

<script>
import {
  VueCollapsiblePanelGroup,
  VueCollapsiblePanel,
} from "@dafcoe/vue-collapsible-panel";
import {useRoute} from "vue-router";
import MemberComponent from "@/components/FridgeList/MemberComponent.vue";
import BasicFridgeItem from "@/components/SpecificFridge/BasicFridgeItem.vue";
import SearchInput from "@/components/searchFromApi/SearchInput.vue";
import SearchItem from "@/components/searchFromApi/SearchItem.vue";
import {ref} from "vue";
import {getItems} from "@/services/ApiService";

export default {
  name: "FridgeView",
  components: {
    SearchItem,
    SearchInput,
    BasicFridgeItem,
    MemberComponent,
    VueCollapsiblePanelGroup,
    VueCollapsiblePanel
  },

  methods: {
    handleSearch() {
      console.log("clicked search");
      console.log('searching for:', this.searchQuery);

      getItems(this.searchQuery).then((response) => {
            this.searchItems = response;
            console.log(this.searchItems);
            this.isExpanded = true;
        console.log(this.isExpanded)
      })
          .catch((error) => {
            console.error(error);
          });

    },
  },

  setup() {
    const route = useRoute()
    const fridge = {
      "fridgeId": route.params.id,
      "fridgeName": route.params.name
    }

    const itemAmount = ref(1);
    const submitMessage = ref("norvegia");
    const items = ref([]);
    const searchQuery = ref('');

    return {
      fridge,
      itemAmount,
      submitMessage,
      items,
      searchQuery,
    }
  },

  data() {
    return {
      searchItems: [],
      isExpanded: false,
      items: [{
        itemId: 1,
        itemName: "Bananas",
        itemPrice: "25",
        itemBuyDate: "2023-04-19",
        itemExpirationDate: "2023-04-23",
        itemLeft: 100
      },
        {
          itemId: 2,
          itemName: "Milk",
          itemPrice: "33.99",
          itemBuyDate: "2023-04-21",
          itemExpirationDate: "2023-04-30",
          itemLeft: 53
        },
        {
          itemId: 2,
          itemName: "Milk",
          itemPrice: "33.99",
          itemBuyDate: "2023-04-21",
          itemExpirationDate: "2023-04-30",
          itemLeft: 53
        },
        {
          itemId: 2,
          itemName: "Milk",
          itemPrice: "33.99",
          itemBuyDate: "2023-04-21",
          itemExpirationDate: "2023-04-30",
          itemLeft: 53
        },
        {
          itemId: 3,
          itemName: "Eggs",
          itemPrice: "38",
          itemBuyDate: "2023-04-21",
          itemExpirationDate: "2023-04-26",
          itemLeft: 78
        }]
    }
  }
}

</script>

<style scoped>

.wrapper {
  margin-top: 5%;
  margin-left: 5%;
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
  top: 100%;
  position: relative;
  background-color: #f6f6f6;
  min-width: 230px;
  overflow: auto;
  border: 1px solid #ddd;
  z-index: 2;
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
  z-index: 1;
  background-color: white;
  border: 1px solid gray;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  padding: 0;
  margin-left: 10%;
  list-style: none;
  width: 80%;
  position: absolute;
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

.break-line {
  height: 7px;
  background-color: black;
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

.search-results {
  color: white;
}

#fridge {
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

#member {
  width: 150px;
  margin-top: 5px;
  margin-right: 50px;
}

#member:hover {
  color: #3b3b3b;
  height: 25px;
  border-radius: 5px;
  background-color: #fff;
  transition: all 0.2s ease-in-out;
}

.members-fridge {
  background-color: #6C6C6C;
  height: 35px;
  color: white;
  text-align: center;
  display: flex;
  justify-content: center;
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


@media (max-width: 650px) {

  body {
    height: 95px;
    width: 100%;
  }

  .wrapper {
    margin-left: 2.5%;
    height: 100%;
    grid-template-rows: repeat(auto-fill, minmax(95px, 95px));
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
}

</style>