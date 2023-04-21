<template>
  <div class="breakline"/>
  <div class="name-display">
      <h1 class="fridge-name">
        <router-link to="/fridges" class="link-name">
          {{ fridgeName }}
        </router-link>
      </h1>
      <div class="change-button">
        <router-link class="link-button" to="/fridges">
          Endre
        </router-link>
      </div>
  </div>
  <div class="members-fridge">
    <router-link id="member" class="link" to="/members">Members</router-link>
    <router-link id="fridge" class="link" to="/fridge">Fridge</router-link>
  </div>
  <specific-fridge-component/>
</template>

<script>
import {useRoute} from "vue-router";
import MemberComponent from "@/components/MemberComponent.vue";
import SpecificFridgeComponent from "@/components/SpecificFridgeComponent.vue";

export default {
  name: "FridgeView",
  components: {SpecificFridgeComponent, MemberComponent},

  setup() {
    const route = useRoute()
    const fridgeName = route.params.name
    const fridgeId = route.params.id

    return{
      fridgeName,
      fridgeId,
    }
  },


}
</script>

<style scoped>

.breakline {
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
}

.change-button {
  text-align: center;
  margin-top: 1%;
  background-color: white;
  color: black;
  height: 25px;
  width: 100px;
  margin-right: 5%;
  text-shadow: white 0 0 0;
  font-weight: 500;
  border-radius: 5px;
}

.change-button:hover {
  color: white;
  height: 25px;
  border-radius: 5px;
  background-color: #b1b1b1;
  transition: all 0.2s ease-in-out;
  cursor: pointer;
}

@media (max-width: 800px) {
  .fridge-name {
    margin-left: 30%;
    font-size: 18px;
  }

  .change-button {
    top: 20%;
    width: 50px;
  }
}

</style>