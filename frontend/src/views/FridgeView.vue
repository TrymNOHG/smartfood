<template>
  <div class="break-line"/>
  <div class="name-display">
    <h1 class="fridge-name">
      <router-link to="/fridges" class="link-name">
        {{ fridgeName }}
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
  <!--TODO: add infinite scroller or pagination-->
  <div class="wrapper">
    <basic-fridge-item v-for="(item, index) in items" :key="index" :item="item" :currenFridge="fridge" />
  </div>

</template>

<script>
import {useRoute} from "vue-router";
import MemberComponent from "@/components/FridgeList/MemberComponent.vue";
import BasicFridgeItem from "@/components/SpecificFridge/BasicFridgeItem.vue";

export default {
  name: "FridgeView",
  components: {BasicFridgeItem, MemberComponent},

  setup() {
    const route = useRoute()
    const fridge = {
      "fridgeId": route.params.id,
      "fridgeName": route.params.name
    }

    return {
      fridge
    }
  },

  data() {
    return {
      items: [{
        itemId: 1,
        itemName: "Bananas",
        itemPrice: "2.50",
        itemBuyDate: "2023-04-19",
        itemExpirationDate: "2023-04-23",
        itemLeft: "4"
      },
        {
          itemId: 2,
          itemName: "Milk",
          itemPrice: "3.99",
          itemBuyDate: "2023-04-21",
          itemExpirationDate: "2023-04-30",
          itemLeft: "1"
        },
        {
          itemId: 3,
          itemName: "Eggs",
          itemPrice: "1.99",
          itemBuyDate: "2023-04-21",
          itemExpirationDate: "2023-04-26",
          itemLeft: "1"
        }]
    }
  }
}

</script>

<style scoped>

.wrapper {
  margin-top: 2%;
  margin-left: 5%;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(345px, 300px));
  grid-template-rows: repeat(auto-fill, minmax(250px, 225px));
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