<template>

  <div class="members-fridge">
      <div id="toggle-button" class="link" @click="selectedTab = 'members'" :class="{ active: selectedTab === 'members' }">Members</div>
      <div id="toggle-button" class="link" @click="selectedTab = 'fridge'" :class="{ active: selectedTab === 'fridge' }">Fridge</div>
  </div>
  <!--TODO: add infinite scroller or pagination-->
    <div class="fridge-wrapper" v-show="selectedTab === 'fridge'">
        <basic-fridge-item v-for="(item, index) in items" :key="index" :item="item" :currenFridge="fridge" />
  </div>
    <div class="members-wrapper" v-show="selectedTab === 'members'">
        <member-component/>
  </div>

</template>

<script>
import {useRoute} from "vue-router";
import MemberComponent from "@/components/SpecificFridge/MemberComponent.vue";
import BasicFridgeItem from "@/components/SpecificFridge/BasicFridgeItem.vue";
import {useFridgeStore} from "@/store/store"
import {ref} from "vue";

export default {
  name: "FridgeView",
  components: {BasicFridgeItem, MemberComponent},

  setup() {
    const selectedTab = ref("fridge");
    const route = useRoute()
    const fridge = {
      "fridgeId": route.params.id,
      "fridgeName": route.params.name
    }
    useFridgeStore().setCurrentFridgeById(route.params.id)

    return {
      fridge,
      selectedTab
    }
  },

  data() {
    return {
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

.fridge-wrapper {
  margin-top: 2%;
  margin-left: 5%;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(345px, 300px));
  grid-template-rows: repeat(auto-fill, minmax(250px, 225px));
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
  height: 25px;
  border-radius: 5px;
  background-color: #fff;
  transition: all 0.2s ease-in-out;
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




.members-fridge:hover .fridge-name {
  color: #3b3b3b;
  height: 25px;
  border-radius: 5px;
  background-color: #fff;
  transition: all 0.2s ease-in-out;
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


}

</style>