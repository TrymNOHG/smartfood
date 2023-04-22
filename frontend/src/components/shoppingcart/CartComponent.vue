<template>
  <div>
    <h1>Cart</h1>
    <div id="myDropdown" class="dropdown-content">
      <SearchInput
        v-model="searchQuery"
        @input="handleSearch"
        label="Search product"
      ></SearchInput>
      <button id="searchbtn" @click="handleSearch">Search</button>
      <div class="dropper">
        <vue-collapsible-panel-group accordion>
          <vue-collapsible-panel :expanded="isExpanded.value">
            <template #title> Search results </template>
            <template #content>
              <SearchItem
                v-for="(item, index) in searchItems"
                :key="index"
                :image="item.image"
                :text="item.name"
                :store="item.store.name"
                :price="item.current_price"
                style="text-align: center"
                @click="addItemToList(item)"
              />
            </template>
          </vue-collapsible-panel>
        </vue-collapsible-panel-group>
      </div>
    </div>

    <div class="cart-items">
      <CartItem
        v-for="(item, index) in items"
        :key="index"
        :image="item.image"
        :name="item.name"
        :date_added="new Date(item.purchaseDate).toISOString().split('T')[0]"
        :weight="item.weight"
        :quantity="item.quantity"
        @add="inc_dec_CartItemAmount(item, 1)"
        @subtract="inc_dec_CartItemAmount(item, -1)"
        @delete-item="handleDeleteItem(item)"
      />
    </div>
  </div>
</template>

<script>
import {
  VueCollapsiblePanelGroup,
  VueCollapsiblePanel,
} from "@dafcoe/vue-collapsible-panel";
import "@dafcoe/vue-collapsible-panel/dist/vue-collapsible-panel.css";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { deleteItemFromShoppingList } from "../services/ItemService";
import { addItemToShoppingList } from "../services/ItemService";
import { getItemsFromShoppingList } from "../services/ItemService";
import { getItems } from "../services/ApiService";
import SearchItem from "../components/basic-components/SearchItem.vue";
import BasicButton from "../components/basic-components/BasicButton.vue";
import SearchInput from "../components/basic-components/SearchInput.vue";
import CartItem from "@/components/basic-components/CartItem.vue";
import { useLoggedInStore, useFridgeStore } from "@/store/store";
import { ref, onMounted, computed, watch } from "vue";
export default {
  name: "Cart",
  components: {
    FontAwesomeIcon,
    SearchItem,
    BasicButton,
    SearchInput,
    VueCollapsiblePanelGroup,
    VueCollapsiblePanel,
    CartItem,
  },
  setup() {

    console.log(useFridgeStore().getCurrentFridge);
    var itemAmount = ref(1);
    var submitMessage = ref("norvegia");
    const items = ref([]); // list of items in the cart
    const searchQuery = ref(""); // search query entered by the user
    const searchItems = ref([]);
    const isExpanded = ref(true);
    const currentFridge = useFridgeStore().getCurrentFridge;

    onMounted(() => {
      loadItemsFromCart();
    });

     // Watch the searchItems array for changes and update the isExpanded ref accordingly
     watch(searchItems, () => {
      console.log("searchQuery: "+!searchQuery.value.length)
      isExpanded.value = !searchQuery.value.length;
    });

    

    const loadItemsFromCart = async () => {
      try {
        const response = await getItemsFromShoppingList(currentFridge.fridgeId); // replace with your API call to fetch the items from the backend
        items.value = response.data;
        console.log(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    function inc_dec_CartItemAmount(item, amount) {
      console.log(item);
      const itemDTO = {
        name: item.name,
        description: item.description,
        store: item.store,
        price: item.price,
        purchaseDate: item.purhchaseDate,
        expirationDate: item.expirationDate,
        image: item.image,
        quantity: amount,
      };
      const fridgeId = currentFridge.fridgeId;

      console.log(itemDTO);
      event.stopPropagation();  
      addItemToShoppingList(itemDTO, fridgeId, false)
        .then(async (response) => {
          if (response !== undefined) {
            await loadItemsFromCart();
          } else {
            console.log("Something went wrong");
            submitMessage.value =
              "Something went wrong. Please try again later.";
          }
        })
        .catch((error) => {
          console.warn("error1", error); //TODO: add exception handling
        });

      loadItemsFromCart();
    
    }

    const handleSubtract = async (item) => {
      if (itemAmount.value === 1) {
        return;
      }
      itemAmount.value -= 1;
      console.log(itemAmount.value);
    };

    const handleDeleteItem = async (item) => {
      const ItemRemoveDTO = {
        itemName: item.name,
        store: item.store,
        fridgeId: currentFridge.fridgeId,
        quantity: item.quantity,
      };
      console.log(ItemRemoveDTO);

      deleteItemFromShoppingList(ItemRemoveDTO, false)
        .then(async (response) => {
          if (response !== undefined) {
            await loadItemsFromCart();
            submitMessage.value = "Succesful request";
            
            setTimeout(() => {
              submitMessage.value = "";
            }, 3000);
          } else {
            console.log("Something went wrong");
            submitMessage.value =
              "Something went wrong. Please try again later.";
            setTimeout(() => {
              submitMessage.value = "";
            }, 3000);
          }
        })
        .catch((error) => {
          //submitMessage.value = error.response.data["Message:"];
          //console.log(error.response.data);
          console.warn("error1", error); //TODO: add exception handling
        });
    };

    //buy item from search
    function addItemToList(item) {
      console.log(item.name + " " + item.store.name);

      const itemDTO = {
        name: item.name,
        description: item.description,
        store: item.store.name,
        price: item.currentPrice,
        purchaseDate: "2023-04-20",
        expirationDate: "2023-04-20",
        image: item.image,
        quantity: 1,
      };
      const fridgeId = currentFridge.fridgeId;

      console.log(itemDTO);

      addItemToShoppingList(itemDTO, fridgeId, false)
        .then(async (response) => {
          if (response !== undefined) {
            await loadItemsFromCart();
          } else {
            console.log("Something went wrong");
            submitMessage.value =
              "Something went wrong. Please try again later.";
          }
        })
        .catch((error) => {
          console.warn("error1", error); //TODO: add exception handling
        });
      event.stopPropagation();
    }

    function handleSearch() {
      console.log("clicked search");
      var items = async () => {
        return await getItems(searchQuery.value);
      };
      items()
        .then((response) => {
          searchItems.value = response;
          console.log(response);
          console.log(searchQuery.value);
        })
        .catch((error) => {
          console.error(error);
        });
    }

    return {
      itemAmount,
      handleSubtract,
      handleDeleteItem,
      submitMessage,
      items,
      searchQuery,
      searchItems,
      handleSearch,
      addItemToList,
      loadItemsFromCart,
      inc_dec_CartItemAmount,
      isExpanded,
    };
  },
};
</script>

<style scoped>
* {
  text-align: center;
}
input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type="number"] {
  -moz-appearance: textfield;
}
.dropper {
  width: 70%;
  color: white;
  margin: auto;
}

.vcpg {
  --bg-color-header: #6c6c6c !important;
  --bg-color-header-hover: #6c6c6c !important;
  --bg-color-header-active: #6c6c6c !important;
  border-radius: 10px 10px 10px 10px;
}
#searchbtn {
  padding: 0 10px;
  margin-top: 10px;
  color: #fff;
  background: #6c6c6c;
  font-size: 27px;
  font-weight: 500;
  border: 3px solid #555;
  border-left: none;
  -webkit-box-shadow: none;
  box-shadow: none;
  min-height: 60px;
  height: auto;
  border-radius: 0 50px 50px 0 !important;
}
#search-button {
  width: 50px !important;
  height: 50px !important;
}
.icon {
  margin-left: 10px;
}

* {
  box-sizing: border-box;
}

html,
body {
  width: 100%;
  height: 100%;
  margin: 0;
  background-color: #7ec855;
  font-family: "Roboto", sans-serif;
}
.search-image {
  width: 0.1vw;
}
.image img {
  width: 50%;
}

#myInput {
  box-sizing: border-box;
  background-image: url("searchicon.png");
  background-position: 14px 12px;
  background-repeat: no-repeat;
  font-size: 16px;
  padding: 14px 20px 12px 45px;
  border: none;
  border-bottom: 1px solid #ddd;
}

#myInput:focus {
  outline: 3px solid #ddd;
}

.dropdown {
  position: absolute;
  display: inline-block;
}

.dropdown-content {
  top: 100%;
  position: relative;
  background-color: #f6f6f6;
  min-width: 230px;
  overflow: auto;
  border: 1px solid #ddd;
  z-index: 2;
  text-align: center;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown a:hover {
  background-color: #ddd;
}

.show {
  display: block;
}

.shopping-cart {
  width: 750px;
  height: 423px;
  margin: 80px auto;
  background: #ffffff;
  box-shadow: 1px 2px 3px 0 rgba(0, 0, 0, 0.1);
  border-radius: 6px;

  display: flex;
  flex-direction: column;
}

.title {
  height: 60px;
  border-bottom: 1px solid #e1e8ee;
  padding: 20px 30px;
  color: #5e6977;
  font-size: 18px;
  font-weight: 400;
}

.item {
  padding: 20px 30px;
  height: 120px;
  width: 70%;
  margin: auto;
  display: flex;
  justify-content: space-between;
}

.item:nth-child(3) {
  border-top: 1px solid #e1e8ee;
  border-bottom: 1px solid #e1e8ee;
}

.buttons {
  position: relative;
  padding-top: 30px;
  margin-right: 60px;
}
.delete-btn,
.like-btn {
  display: inline-block;
  cursor: pointer;
}
.delete-btn {
  width: 18px;
  height: 17px;
  background: url("../../assets/images/delete-icn.svg") no-repeat center;
}

.is-active {
  animation-name: animate;
  animation-duration: 0.8s;
  animation-iteration-count: 1;
  animation-timing-function: steps(28);
  animation-fill-mode: forwards;
}

@keyframes animate {
  0% {
    background-position: left;
  }
  50% {
    background-position: right;
  }
  100% {
    background-position: right;
  }
}

.image {
  margin-right: 50px;
}
.description {
  padding-top: 10px;
  margin-right: 60px;
  width: 115px;
}

.description span {
  display: block;
  font-size: 14px;
  color: #43484d;
  font-weight: 400;
}

.description span:first-child {
  margin-bottom: 5px;
}
.description span:last-child {
  font-weight: 300;
  margin-top: 8px;
  color: #86939e;
}

.quantity {
  padding-top: 20px;
  text-align: center;
  justify-content: center;
}
.quantity input {
  -webkit-appearance: none;
  border: none;
  text-align: center;
  width: 32px;
  font-size: 16px;
  color: #43484d;
  font-weight: 300;
}

button[class*="btn"] {
  width: 30px;
  height: 30px;
  background-color: #e1e8ee;
  border-radius: 6px;
  border: none;
  cursor: pointer;
}
.minus-btn img {
  margin-bottom: 3px;
}
.plus-btn img {
  margin-top: 2px;
}

button:focus,
input:focus {
  outline: 0;
}

@media only screen and (max-width: 800px) {
  header {
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 80px;
  }

  .item {
    width: 98vw;
  }
  .dropper {
    width: 97vw;
  }

  header img {
    height: 40px;
    margin-right: 0;
    margin-bottom: 10px;
  }

  h1 {
    font-size: 1rem;
    letter-spacing: 2px;
  }

  nav {
    display: flex;
    align-items: center;
    justify-content: space-evenly;
    position: fixed;
    bottom: 0;
    width: 100%;
    height: 70px;
    background-color: #31c48d;
    box-shadow: 0 -2px 6px rgba(0, 0, 0, 0.1);
  }

  nav ul {
    display: flex;
    justify-content: space-between;
    width: 80%;
  }

  nav ul li {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 50px;
    height: 50px;
  }

  nav ul li a {
    font-size: 0.6rem;
    color: white;
    text-align: center;
    text-decoration: none;
  }

  nav ul li a .icon {
    margin-bottom: 5px;
    font-size: 2em;
  }

  nav ul li.active a {
    color: #fcfbfb;
    background-color: #218838;
    border-radius: 50%;
  }

  nav ul li.active a .icon {
    color: #fcfbfb;
  }

  .buttons {
    position: relative;
    margin-right: 0;
  }

  .quantity {
    padding-top: 25px;
    margin-left: 0;
    display: flex;
  }
}

@media only screen and (min-width: 350px) and (max-width: 480px) {
  .buttons {
    position: relative;
    margin-left: 20px;
    margin-right: 0;
  }

  .dropper {
    width: 100vw;
  }
  .item {
    width: 100vw;
  }
  .quantity {
    padding-top: 25px;
    display: flex;
  }
  .quantity input {
    -webkit-appearance: none;
    border: none;
    text-align: center;
    width: 32px;
    font-size: 16px;
    color: #43484d;
    font-weight: 300;
  }
}

@media only screen and (max-width: 350px) {
  .item {
    width: 100vw;
  }
  .buttons {
    position: relative;
    margin-top: -20px;
    margin-right: 0;
  }
  .dropper {
    width: 100vw;
  }
  .quantity {
    padding-top: 0;
    margin-top: 5px;
    display: block;
  }
  .quantity input {
    -webkit-appearance: none;
    border: none;
    text-align: center;
    width: 32px;
    font-size: 16px;
    color: #43484d;
    font-weight: 300;
  }
}
</style>
