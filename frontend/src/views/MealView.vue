<template>
  <div class="grey-bar">
    <div class="information-button">
      <img src="@/assets/images/info.svg" id="info-picture" @click="showInformation" :alt=" $t('alt_info_button') ">
    </div>
  </div>

  <div class="item-wrapper">
    <div class="item">
      <meal-header :meal="meal"/>
      <div class="info-delete-wrapper">
        <meal-info :meal="meal"/>
        <div class="spacer"></div>
        <div class="additional-info">
          <p id="description">{{meal.description}}</p>
          <recipe-parts :recipe-parts="meal.recipeParts"></recipe-parts>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useMealStore } from "../store/store";
import mealHeader from "@/components/mealDescription/mealHeader.vue";
import MealInfo from "../components/mealDescription/mealInfo.vue";
import recipeParts from "../components/mealDescription/RecipeParts.vue";


export default {
  components:{
    mealHeader,
    MealInfo,
    recipeParts,
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
}

.additional-info {
  flex-grow: 1;
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

#description{
  padding: 20px;

}

@media (max-width: 650px) {
  .item-wrapper {
    grid-template-columns: 1fr;
  }

  .info-delete-wrapper {
    flex-direction: column;
  }

  .spacer {
    height: 20px;
  }
}
</style>
