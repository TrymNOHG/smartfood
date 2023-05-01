<template>
  <div class="grey-bar">
    <div class="information-button">
      <img src="@/assets/images/info.svg" id="info-picture" @click="showInformation" :alt=" $t('alt_info_button') ">
    </div>
  </div>

  <div class="item-wrapper">
    <div class="item">
      <meal-header :meal="meal"/>
      <meal-info :meal="meal"/>
      <div class="info-delete-wrapper">
          <recipe-parts id="recipe-parts" :recipe-parts="meal.recipeParts"></recipe-parts>
          <instructions id="instructions" :instructions="meal.instructions"></instructions>
      </div>
    </div>
  </div>
</template>

<script>
import { useMealStore } from "../store/store";
import mealHeader from "@/components/mealDescription/mealHeader.vue";
import MealInfo from "../components/mealDescription/mealInfo.vue";
import recipeParts from "../components/mealDescription/RecipeParts.vue";
import Instructions from "../components/mealDescription/Instructions.vue";


export default {
  components:{
    mealHeader,
    MealInfo,
    recipeParts,
    Instructions
  },
  setup() {
    const mealStore = useMealStore();
    const meal = mealStore.getCurrentMeal;
    return {
      meal
    };
  }
};
</script>

<style scoped>
.item {
  margin-top: 5%;
  grid-column: 2;
}

.item-wrapper {
  display: grid;
  grid-template-columns: 10% 80% 10%;
}

.info-delete-wrapper {
  display: flex;
  width: 100%;
}

#instructions {
  flex-grow: 1;
  width: 70%;
}

#recipe-parts {
  width: 30%;
  margin-right: 20px; /* Add margin to separate the two sections */
}

.grey-bar {
  background-color: #6C6C6C;
  height: 35px;
  text-align: center;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
}

.information-button {
  grid-column: 3;
  text-align: right;
  padding: 2px 5px;
  max-height: 35px;
}

#info-picture {
  height: 30px;
  width: 30px;
  cursor: pointer;
}


@media only screen and (max-width: 768px) {

  .info-delete-wrapper {
    flex-direction: column;
  }

  #instructions {
    flex-grow: 1;
    width: 100%;
  }

  #recipe-parts {
    width: 100%;
    margin-right: 0px; /* Add margin to separate the two sections */
  }
}


@media (max-width: 650px) {
  .item-wrapper {
    grid-template-columns: 1fr;
  }

  .info-delete-wrapper {
    flex-direction: column;
  }

}
</style>
