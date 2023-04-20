<template>
  <div>
    <h1>Cart</h1>
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
import { ref } from "vue";
export default {
  name: "Cart",
  components: {
    FontAwesomeIcon,
  },
  setup() {
    var itemAmount = ref(1);
    var submitMessage = ref('');

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
        params: {
          suggestion: false,
        },
      };
      console.log(itemData);

      deleteItemFromShoppingList(itemData, false)
        .then(async (response) => {
          if (response !== undefined) {
            store.setSessionToken(response.data.token);
            await store.fetchUser();
            submitMessage.value = "Registration Successful";
            setTimeout(() => {
              submitMessage.value = "";
            }, 3000);
            await router.push("/");
          } else {
            console.log("Something went wrong registering");
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

    const sendAmountToSever = async () => {};

    return {
      itemAmount,
      handleAdd,
      handleSubtract,
      handleDeleteItem,
      submitMessage,
    };
  },
};
</script>

<style scoped>
* {
  text-align: center;
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
.image img {
  width: 50%;
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
    padding-top: 30px;

    margin-right: 0;
  }

  .quantity {
    padding-top: 0;
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
