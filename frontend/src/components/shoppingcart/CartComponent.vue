<template>
  <div>
    <div id="myDropdown" class="dropdown-content">
      <figure id="backBlack"></figure>

      <div id="backGreen">
        <div class="grey-bar">
          <h2 id="grey-header">{{ $t("shopping_cart") }}</h2>
          <div class="information-button">
            <img
              src="@/assets/images/info.svg"
              id="info-picture"
              @click="showInformation"
              :alt="$t('alt_info_button')"
            />
          </div>
        </div>
        <div id="barcode-scanner">
          <div v-show="scannerActive" id="interactive" class="viewport"></div>
        </div>

        <div id="searchbar">
          <SearchInput
            v-model="searchQuery"
            @input="handleSearch"
            :label="$t('search_item')"
            @receipt-upload="toggleCamera"
          ></SearchInput>
          <button id="searchbtn" @click="handleSearch">
            {{ $t("search") }}
          </button>
        </div>
        <CartControl
          v-if="isCurrentUserSuperUser"
          @check-all="handleMarkAll"
          @buy="handleBuy"
          @delete="handleDelete"
        ></CartControl>
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
                @click="addItemToList(item)"
                @item-checked="handleItemChecked"
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
        :quantity="item.quantity"
        :item="item"
        :isSuperUser="isCurrentUserSuperUser"
        @add="inc_CartItemAmount(item)"
        @subtract="dec_CartItemAmount(item)"
        @delete-item="handleDeleteItem(item)"
        @handle-checked="handleCheckedItem"
        @handle-buy="handleBuyItem"
        @quantity-updated="set_CartItemAmount"
      >
      </CartItem>
    </div>
    <figure id="forslagBlack"></figure>

    <h1 id="sugTitle">{{ $t("suggestion") }}</h1>
    <div class="cart-items">
      <CartSuggestion
        v-for="(item, index) in suggestedItems"
        :key="index"
        :image="item.image"
        :name="item.name"
        :quantity="item.quantity"
        :item="item"
        :isSuperUser="isCurrentUserSuperUser"
        @accept-suggestion="handleAcceptSuggestion(item)"
        @delete-suggestion="handleDeleteSuggestion(item)"
      >
      </CartSuggestion>
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
import {
  acceptSuggestion,
  deleteItemFromShoppingList,
  updateShoppingListItem,
} from "@/services/ItemService";
import { addItemToShoppingList } from "@/services/ItemService";
import { getItemsFromShoppingList } from "@/services/ItemService";
import { buyItemsFromShoppingList } from "@/services/ItemService";
import { deleteItemsFromShoppingList } from "@/services/ItemService";
import { getItems } from "@/services/ApiService";
import { getItemByBarcode } from "@/services/ApiService";
import SearchItem from "@/components/searchFromApi/SearchItem.vue";
import BasicButton from "@/components/basic-components/BasicButton.vue";
import SearchInput from "@/components/searchFromApi/SearchInput.vue";
import CartItem from "@/components/shoppingcart/CartItem.vue";
import CartSuggestion from "@/components/shoppingcart/CartSuggestion.vue";
import CartControl from "@/components/shoppingcart/CartControl.vue";
import BasicCheckBox from "@/components/basic-components/BasicCheckbox.vue";
import { useLoggedInStore, useFridgeStore, useItemStore } from "@/store/store";
import { ref, onMounted, computed, watch, onBeforeUnmount } from "vue";
import "sweetalert2/dist/sweetalert2.min.css";
import swal from "sweetalert2";
import Quagga from "quagga";

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
    CartControl,
    BasicCheckBox,
    CartSuggestion,
  },
  computed: {
    isCurrentUserSuperUser() {
      return useFridgeStore().getIsSuperUser;
    },
  },
  setup() {
    console.log(useFridgeStore().getCurrentFridge);
    var itemAmount = ref(1);
    var submitMessage = ref("norvegia");
    const items = ref([]); // list of items in the cart
    const searchQuery = ref(""); // search query entered by the user
    const searchItems = ref([]);
    const isExpanded = ref(true);
    const search = ref(false);
    const currentFridge = useFridgeStore().getCurrentFridge;
    let checkAll_b = ref(false);
    const suggestedItems = ref([]);
    const itemStore = useItemStore();
    let scannerActive = ref(false);

    function initScanner() {
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
          scannerActive.value = true;
          Quagga.start();
        }
      );

      Quagga.onDetected(onDetected);
    }

    function stopScanner() {
      Quagga.offDetected(onDetected);
      Quagga.stop();
      scannerActive.value = false;
    }

    async function onDetected(result) {
      const code = result.codeResult.code;
      console.log("Detected barcode:", code);

      await getItemByBarcode(code)
        .then((response) => {
          if (response !== undefined) {
            searchItems.value = response.products;

            console.log(response.products);
            search.value = true;
            stopScanner();
          } else {
            console.log("Something went wrong");
            submitMessage.value =
              "Something went wrong. Please try again later.";
          }
        })
        .catch((error) => {
          console.warn("error1", error); //TODO: add exception handling
        });
    }

    function toggleCamera() {
      console.log("toggling", scannerActive.value, scannerActive);
      if (scannerActive.value == true) {
        stopScanner();
      } else {
        initScanner();
      }
    }

    async function handleAcceptSuggestion(item) {
      const ItemRemoveDTO = {
        itemName: item.name,
        store: item.store,
        fridgeId: currentFridge.fridgeId,
        quantity: item.quantity,
      };
      try {
        await acceptSuggestion(ItemRemoveDTO);
        await loadItemsFromCart();
      } catch (error) {
        console.error(error);
      }
    }

    async function handleDeleteSuggestion(item) {
      const ItemRemoveDTO = {
        itemName: item.name,
        store: item.store,
        fridgeId: currentFridge.fridgeId,
        quantity: item.quantity,
      };
      try {
        console.log(ItemRemoveDTO);
        await deleteItemFromShoppingList(ItemRemoveDTO, true);
        await loadItemsFromCart();
      } catch (error) {
        console.error(error);
        console.log(error.response.data["Message: "]);
      }
    }

    function handleMarkAll() {
      checkAll_b.value = !checkAll_b.value;
      items.value.forEach((obj) => {
        obj.isChecked = checkAll_b.value;
      });
    }

    async function handleCheckedItem(item, isChecked) {
      item.isChecked = isChecked;
      // console.log(item.isChecked)
    }

    async function handleDelete() {
      const selectedItems = [];
      items.value.forEach((item) => {
        if (item.isChecked) {
          selectedItems.push(item);
        }
      });

      const itemRemoveDTOList = [{}];
      selectedItems.forEach((item) => {
        const ItemRemoveDTO = {
          itemName: item.name,
          store: item.store,
          fridgeId: currentFridge.fridgeId,
          quantity: item.quantity,
        };
        itemRemoveDTOList.push(ItemRemoveDTO);
      });
      try {
        itemRemoveDTOList.shift();
        await deleteItemsFromShoppingList(itemRemoveDTOList);
        await loadItemsFromCart();
        await swal.fire("Deleted items", "", "success");
      } catch (error) {
        console.error(error);
        await swal.fire(error.response.data["Message:"], "", "error");
      }
    }

    async function handleBuyItem(item) {
      const selectedItems = [];
      selectedItems.push(item);
      const itemRemoveDTOList = [{}];
      const itemStatDTOList = [{}];

      selectedItems.forEach((item) => {
        console.log(item);

        const statAddItemToFridgeDTO = {
          price: item.price,
          quantity: 1,
          itemName: item.name,
          storeName: item.store,
          fridgeId: currentFridge.fridgeId,
        };

        const ItemRemoveDTO = {
          itemId: item.itemId,
          fridgeId: currentFridge.fridgeId,
        };

        itemStatDTOList.push(statAddItemToFridgeDTO);
        itemRemoveDTOList.push(ItemRemoveDTO);
      });

      try {
        itemStatDTOList.shift();
        itemRemoveDTOList.shift();

        await itemStore.statAddItemListToFridge(itemStatDTOList);
        await buyItemsFromShoppingList(itemRemoveDTOList);
        await loadItemsFromCart();
      } catch (error) {
        await swal.fire(error.response.data["Message:"], "", "error");
      }
    }

    async function handleBuy() {
      const selectedItems = [];
      items.value.forEach((item) => {
        if (item.isChecked) {
          selectedItems.push(item);
        }
      });

      const itemRemoveDTOList = [{}];
      const itemStatDTOList = [{}];

      selectedItems.forEach((item) => {
        const statAddItemToFridgeDTO = {
          price: item.price,
          quantity: 1,
          itemName: item.name,
          storeName: item.store,
          fridgeId: currentFridge.fridgeId,
        };

        const ItemRemoveDTO = {
          itemId: item.itemId,
          fridgeId: currentFridge.fridgeId,
        };

        itemStatDTOList.push(statAddItemToFridgeDTO);
        itemRemoveDTOList.push(ItemRemoveDTO);
      });
      try {
        itemStatDTOList.shift();
        itemRemoveDTOList.shift();

        await itemStore.statAddItemListToFridge(itemStatDTOList);
        await buyItemsFromShoppingList(itemRemoveDTOList);
        await loadItemsFromCart();
        await swal.fire("Added to fridge", "", "success");
      } catch (error) {
        await swal.fire(error.response.data["Message:"], "", "error");
      }
    }

    onMounted(async () => {
      await loadItemsFromCart();
    });

    onBeforeUnmount(() => {
      if (scannerActive.value == true) {
        stopScanner();
      }
    });
    // Watch the searchItems array for changes and update the isExpanded ref accordingly
    watch(searchItems, () => {
      console.log("searchQuery: " + !searchQuery.value.length);
      isExpanded.value = !searchQuery.value.length;
    });

    const loadItemsFromCart = async () => {
      try {
        const response = await getItemsFromShoppingList(currentFridge.fridgeId);
        items.value = response.data;

        suggestedItems.value = [];
        items.value = [];
        response.data.forEach((item) => {
          if (item.suggestion) {
            suggestedItems.value.push(item);
            return;
          }
          items.value.push(item);
        });
        console.log(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    async function inc_CartItemAmount(item) {
      console.log(item);
      const itemDTO = {
        name: item.name,
        description: item.description,
        store: item.store,
        price: item.price,
        image: item.image,
        EAN: item.ean,
        quantity: 1,
      };
      const fridgeId = currentFridge.fridgeId;

      event.stopPropagation();
      addItemToShoppingList(itemDTO, fridgeId, !useFridgeStore().isSuperUser)
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

      await loadItemsFromCart();
    }

    async function dec_CartItemAmount(item) {
      const itemRemoveDTO = {
        itemName: item.name,
        store: item.store,
        fridgeId: currentFridge.fridgeId,
        quantity: 1,
      };

      event.stopPropagation();
      deleteItemFromShoppingList(itemRemoveDTO, false)
        .then(async (response) => {
          if (response !== undefined) {
            await loadItemsFromCart();
          } else {
            submitMessage.value =
              "Something went wrong. Please try again later.";
          }
        })
        .catch((error) => {
          console.warn("error1", error); //TODO: add exception handling
        });

      //TODO: fix so that items do not need to be loaded again continuously........
      await loadItemsFromCart();
    }

    async function set_CartItemAmount(newQuantity, item) {
      //TODO: add exception handling.........
      if (newQuantity < 1) {
        await loadItemsFromCart();
        return;
      }

      const shoppingItemUpdateDTO = {
        itemId: item.itemId,
        fridgeId: currentFridge.fridgeId,
        suggestion: null,
        quantity: newQuantity,
      };

      event.stopPropagation();
      updateShoppingListItem(shoppingItemUpdateDTO)
        .then(async (response) => {
          if (response !== undefined) {
            await loadItemsFromCart();
          } else {
            submitMessage.value =
              "Something went wrong. Please try again later.";
          }
        })
        .catch((error) => {
          console.warn("error1", error); //TODO: add exception handling
        });

      await loadItemsFromCart();
    }

    const handleSubtract = async (item) => {
      if (itemAmount.value === 1) {
        return;
      }
      itemAmount.value -= 1;
    };

    const handleDeleteItem = async (item) => {
      const ItemRemoveDTO = {
        itemName: item.name,
        store: item.store,
        fridgeId: currentFridge.fridgeId,
        quantity: item.quantity,
      };

      deleteItemFromShoppingList(ItemRemoveDTO, false)
        .then(async (response) => {
          if (response !== undefined) {
            await loadItemsFromCart();
            submitMessage.value = "Succesful request";

            setTimeout(() => {
              submitMessage.value = "";
            }, 3000);
          } else {
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
      search.value = false;

      const itemDTO = {
        name: item.name,
        description: item.description,
        store: item.store.name,
        price: item.current_price,
        purchaseDate: new Date(),
        expirationDate: new Date(),
        image: item.image,
        EAN: item.ean,
        quantity: 1,
      };
      if (typeof item.current_price.price === "number") {
        itemDTO.price = item.current_price.price;
        console.log(itemDTO.price);
      }
      const fridgeId = currentFridge.fridgeId;

      console.log(itemDTO);

      addItemToShoppingList(itemDTO, fridgeId, !useFridgeStore().isSuperUser)
        .then(async (response) => {
          if (response !== undefined) {
            await loadItemsFromCart();
          } else {
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
      search.value = true;
      // filter the list of items based on the search query
      var items = async () => {
        return await getItems(searchQuery.value);
      };
      if (searchQuery.value.length < 2) search.value = false;
      items()
        .then((response) => {
          searchItems.value = response;
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
      items, // list of items in the cart
      searchQuery, // search query entered by the user
      searchItems,
      handleSearch,
      addItemToList,
      loadItemsFromCart,
      inc_CartItemAmount,
      dec_CartItemAmount,
      isExpanded,
      search,
      handleBuy,
      handleMarkAll,
      handleCheckedItem,
      checkAll_b,
      handleDelete,
      handleBuyItem,
      currentFridge,
      suggestedItems,
      handleAcceptSuggestion,
      handleDeleteSuggestion,
      set_CartItemAmount,
      scannerActive,
      toggleCamera,
      onDetected,
      initScanner,
      stopScanner,
    };
  },
};
</script>

<style scoped>
* {
  text-align: center;
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

input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type="number"] {
  -moz-appearance: textfield;
}

.dropper {
  display: flex;
  width: 98vw;
  justify-content: space-evenly;
  overflow-y: scroll;
  overflow-x: hidden;
  margin-bottom: 20px;
  margin: auto;
  color: white;
}

.dropper::-webkit-scrollbar {
  display: none;
}

.dropper:hover {
  color: #5e6977;
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

#shopList {
  color: white;
  font-size: 25px;
}

#searchbar {
  display: flex;
  background-color: #6c6c6c;
  margin: 0;
  border: 0;
  padding-top: 10px;
  width: 100%;
}

#myDropdown {
  padding: 0;
  margin: 0;
  border: 0;
}

#searchbtn {
  border: 0;
  padding: 0px 10px;
  margin-top: 0px;
  color: #fff;
  background: #31c48d;
  font-size: 27px;
  font-weight: 500;
  border: 0px solid #555;
  border-left: none;
  -webkit-box-shadow: none;
  box-shadow: none;
  height: 40px;
  margin-right: 10px;
  border-radius: 0 50px 50px 0 !important;
}

.grey-bar {
  background-color: #6c6c6c;
  text-align: center;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
}

#grey-header {
  grid-column: 2;
  color: white;
  height: 35px;
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

#searchbtn:hover {
  background-color: #1e7655;
  cursor: pointer;
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
  box-shadow: 1px 2px 3px 0px rgba(0, 0, 0, 0.1);
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
  height: 150px;
  width: 70%;
  margin: auto;
  display: flex;
  justify-content: space-between;
}

.item:nth-child(3) {
  border-top: 1px solid #e1e8ee;
  border-bottom: 1px solid #e1e8ee;
}

#forslagBlack {
  height: 6px;
  background-color: white;
}

#forslagGreen {
  background-color: #31c48d;

  width: 100%;
  height: 0px;
}

#sugTitle {
  border-radius: 20px 20px 20px 20px;
  background-color: #31c48d;
  color: white;
  font-size: 25px;
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
@media (max-width: 1350px) {
  #interactive {
    transform: none;
    position: relative;
    overflow: hidden;
  }
}
@media only screen and (min-width: 50px) and (max-width: 650px) {
  .buttons {
    position: relative;
    margin-left: 20px;
    margin-right: 0;
  }

  #searchbtn {
    display: none !important;
  }

  .grey-bar {
    all: unset;
    text-align: center;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    background-color: #31c48d;
    height: 50px;
    align-content: center;
  }

  #grey-header {
    all: unset;
    grid-column: 2;
    color: white;
    font-size: 25px;
    margin-top: 10px;
  }

  #backBlack {
    height: 0px;
    background-color: white;
  }

  #backGreen {
    background-color: #31c48d;

    width: 100%;
    padding: 5px 10px 10px 10px;
    border-radius: 20px 20px 20px 20px;
  }

  #forslagBlack {
    height: 6px;
    background-color: white;
  }

  #forslagGreen {
    background-color: #31c48d;

    width: 100%;
    padding: 10px 10px 10px 10px;
    border-radius: 20px 20px 20px 20px;
  }

  #shopList {
    border-radius: 20px 20px 0px 0px;
    background-color: #31c48d;
    color: white;
  }

  #sugTitle {
    border-radius: 20px 20px 20px 20px;
    background-color: #31c48d;
  }

  h1 {
    z-index: 1;
    background-color: white;
    color: white;
    font-size: 20px;
    font-weight: bold;
    letter-spacing: 2px;
  }

  #searchbar {
    display: flex;
    position: fixed;
    bottom: 70px;
    width: 100%;
    z-index: 1;
    background-color: transparent;
  }

  nav {
    z-index: 1;
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

  #searchbtn {
    display: none;
  }

  .dropper {
    display: flex;
    width: 100vw;
    justify-content: space-evenly;
    position: fixed;
    top: 160px;
    overflow-y: scroll;
    margin-bottom: 20px;
    margin: auto;
    color: white;
  }

  .cart-control {
    border-radius: 20px 20px 20px 20px;
  }

  #myDropdown {
    border: 0;
  }

  .vcpg {
    --bg-color-header: transparent !important;
    border: transparent;
    width: 100%;
    overflow-y: scroll;
    max-height: 150vw;
    color: black;
    background-color: white;
  }

  header {
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 80px;
  }

  header img {
    height: 40px;
    margin-right: 0;
    margin-bottom: 10px;
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

  .quantity {
    padding-top: 25px;
    margin-left: 0;
    display: flex;
  }

  .item {
    width: 100vw;
    padding: 20px 30px;
    height: 120px;
    margin: auto;
    display: flex;
    justify-content: space-between;
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

  * {
    text-align: center;
    box-sizing: border-box;
  }

  input[type="number"]::-webkit-outer-spin-button,
  input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }

  input[type="number"] {
    -moz-appearance: textfield;
  }

  .icon {
    margin-left: 10px;
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
    background-color: white;
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
    box-shadow: 1px 2px 3px 0px rgba(0, 0, 0, 0.1);
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

  .item:nth-child(3) {
    border-top: 1px solid #e1e8ee;
    border-bottom: 1px solid #e1e8ee;
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
}
</style>
