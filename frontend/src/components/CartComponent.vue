<template>
  <div>
    <h1>Cart</h1>
    <div id="myDropdown" class="dropdown-content">
      <input
        type="text"
        placeholder="Search.."
        id="myInput"
        v-model="searchQuery"
      />
      <button id="search-button" @click="handleSearch"></button>

      <div v-if="searchItems.length">
        <h2>Search Results:</h2>
        <ul>
          <li v-for="item in searchItems" :key="item.id">
            <img :src="item.image" />
            <p>{{ item.text }}</p>
            <p>{{ item.price }}</p>
          </li>
        </ul>
      </div>

      <a href="#about">About</a>
      <a href="#base">Base</a>
      <a href="#blog">Blog</a>
      <a href="#contact">Contact</a>
      <a href="#custom">Custom</a>
      <a href="#support">Support</a>
      <a href="#tools">Tools</a>

      <search-item
        v-for="(item, index) in searchItems"
        :key="index"
        :image="item.image"
        :text="item.text"
        :price="item.price"
      />
    </div>

    <div class="item">
      <div class="product-img">
        <img src="../assets/images/face.webp" alt="" style="width: 80px" />
      </div>

      <div class="description">
        <span>Matvare navn</span>
        <span>Vekt/Liter</span>
      </div>

      <div class="quantity">
        <button
          class="minus-btn"
          type="button"
          name="button"
          @click="handleSubtract"
        >
          <img src="../assets/images/minus.svg" alt="" />
        </button>

        <input type="number" name="name" v-model="itemAmount" />

        <button class="plus-btn" type="button" name="button" @click="handleAdd">
          <img src="../assets/images/plus.svg" alt="" />
        </button>
      </div>

      <div class="buttons">
        <span class="delete-btn" @click="handleDeleteItem"></span>
      </div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { deleteItemFromShoppingList } from "../services/ItemService";
import { addItemToShoppingList } from "../services/ItemService";
import { getItems } from "../services/ApiService";
import { useLoggedInStore } from "@/store/store";
import { ref } from "vue";
export default {
  name: "Cart",
  components: {
    FontAwesomeIcon,
  },
  setup() {
    var itemAmount = ref(1);
    var submitMessage = ref("");
    const items = ref([]); // list of items in the cart
    const searchQuery = ref(""); // search query entered by the user
    const searchItems = ref([]);
    const store = useLoggedInStore();
    const handleAdd = async () => {
      itemAmount.value += 1;
      console.log(itemAmount.value);
    };

    const handleSubtract = async () => {
      if (itemAmount.value == 1) {
        return;
      }
      itemAmount.value -= 1;
      console.log(itemAmount.value);
    };

    const handleDeleteItem = async () => {
      const itemData = {
        itemName: "Test",
        store: "Test",
        id: 100,
        quantity: itemAmount.value,
      };
      console.log(itemData);

      deleteItemFromShoppingList(itemData, false)
        .then(async (response) => {
          if (response !== undefined) {
            store.setSessionToken(response.data.token);
            await store.fetchUser();
            submitMessage.value = "Succesful request";
            setTimeout(() => {
              submitMessage.value = "";
            }, 3000);
            await router.push("/");
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

    const handleAddItemToShoppingList = async () => {
      const itemData = {
        name: "Test",
        description: "Test",
        store: "Test",
        price: 100,
        purchaseDate: "2023-04-20",
        expirationDate: "2023-04-20",
        image:
          "https://i.imgur.com/CVFCV3O_d.webp?maxwidth=520&shape=thumb&fidelity=high",
        quantity: 3,
      };
      const itemId = 1;

      addItemToShoppingList(itemData, itemId, false)
        .then(async (response) => {
          if (response !== undefined) {
            store.setSessionToken(response.data.token);
            await store.fetchUser();
            submitMessage.value = "Succesful request";
            setTimeout(() => {
              submitMessage.value = "";
            }, 3000);
            await router.push("/");
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

    function handleSearch() {
      console.log("clicked search");
      // filter the list of items based on the search query
      var items = async () => {
        return await getItems(searchQuery.value);
        console.log(searchQuery.value)
      };
      items()
        .then((response) => {
          searchItems.value = response;
          console.log(response);
        })
        .catch((error) => {
          console.error(error);
        });
    }

    return {
      itemAmount,
      handleAdd,
      handleSubtract,
      handleDeleteItem,
      submitMessage,
      items: [], // list of items in the cart
      searchQuery: "", // search query entered by the user
      searchItems,
      handleSearch,
    };
  },
};
</script>

<style scoped>
* {
  text-align: center;
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
img{
  width: 50px;
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
  height: 120px;
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
  background: url("../assets/images/delete-icn.svg") no-repeat center;
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
  .buttons {
    position: relative;
    margin-top: -20px;
    margin-right: 0;
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
