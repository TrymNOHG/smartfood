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
      <button @click="addMissingItemsToShoppingList" class="add-missing-items-btn">{{ $t('add_missing_items') }}</button>
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
import router from "../router/router";
import {addItemToShoppingList} from "../services/ItemService";
import swal from "sweetalert2";


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
  },
  computed: {
    missingItemIds() {
      const ids = [];

      this.meal.recipeParts.forEach(part => {
        part.ingredients.forEach(ingredient => {
          if (!ingredient.hasItem) {
            ids.push(ingredient.itemId);
          }
        });
      });

      return ids;
    },
  },
  methods: {
    async addMissingItemsToShoppingList() {
      try {

        for (const id of this.missingItemIds) {
          await addItemToShoppingList(id);
        }
        swal.fire({
          title: this.$t('success'),
          text: this.$t('success_add_missing_items'),
          icon: 'success',
          confirmButtonText: this.$t('ok')
        });
        router.push({name: 'cart'});
      } catch (e) {
        swal.fire({
          title: this.$t('error'),
          text: this.$t('error_add_missing_items'),
          icon: 'error',
          confirmButtonText: this.$t('ok')
        });
      }
      console.log("Adding missing items to shopping list:", this.missingItemIds);
    },
  },
};
</script>

<style scoped>

.add-missing-items-btn {
  background-color: #4CAF50;
  border: none;
  color: white;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 10px 2px;
  cursor: pointer;
  border-radius: 4px;
}


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
