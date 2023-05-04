<template>
  <div class="grey-bar">
    <div class="tips-weekMenu">
      <router-link
          class="link"
          :to="{ name: 'dinner', query: { selectedTab: 'tips' } }"
          :class="{ active: selectedTab === 'tips' }"
            >{{ $t('tips') }}
      </router-link>
      <router-link
          class="link"
          :to="{ name: 'dinner', query: { selectedTab: 'weekMenu' } }"
          :class="{ active: selectedTab === 'weekMenu' }"
      >{{ $t('weekly_menu') }}
      </router-link>
    </div>
    <div class="information-button">
      <img src="@/assets/images/info.svg" id="info-picture" @click="showInformation" :alt=" $t('alt_info_button') ">
    </div>
  </div>

  <div class="item-wrapper">
    <div class="item">
      <meal-header :meal="meal"/>
      <meal-info :meal="meal" @serving-size-adjusted="adjustServingSize" />
      <BasicButton
          v-if="isCurrentUserSuperUser"
          @click="addMissingItemsToShoppingList"
          class="add-missing-items-btn"
          :button-text="$t('add_missing_items')"
      />
      <BasicButton
          v-if="!isCurrentUserSuperUser"
          @click="addRecipeToSuggestions"
          class="add-missing-items-btn"
          :button-text="$t('suggest_dinner')"
      />
      <div class="info-delete-wrapper">
          <recipe-parts id="recipe-parts" :recipe-parts="meal.recipeParts"/>
          <instructions id="instructions" :instructions="meal.instructions"/>
      </div>
    </div>
  </div>
</template>

<script>
import {useFridgeStore, useLoggedInStore, useMealStore} from "../store/store";
import mealHeader from "@/components/mealDescription/mealHeader.vue";
import MealInfo from "../components/mealDescription/mealInfo.vue";
import recipeParts from "../components/mealDescription/RecipeParts.vue";
import Instructions from "../components/mealDescription/Instructions.vue";
import router from "../router/router";
import swal from "sweetalert2";
import {addIngredientsToShoppingList, addRecipeSuggestion} from "../services/DinnerService";
import BasicButton from "../components/basic-components/BasicButton.vue";
import {ref} from "vue";


export default {
  components:{
    mealHeader,
    MealInfo,
    recipeParts,
    Instructions,
    BasicButton
  },


  setup() {
    const mealStore = useMealStore();
    const meal = mealStore.getCurrentMeal;
    const fridgeStore = useFridgeStore();
    const fridgeId = fridgeStore.getCurrentFridge.fridgeId;
    const selectedTab = ref(router.currentRoute.value.query.selectedTab || 'tips');
    const userId = useLoggedInStore().getUser.data.userId;

    //history.replaceState(null, null, '/dinner');
    return {
      fridgeStore,
      meal,
      fridgeId,
      selectedTab,
      userId
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
    isCurrentUserSuperUser() {
      return this.fridgeStore.getIsSuperUser;
    },
  },
  methods: {
    async addMissingItemsToShoppingList() {

      const RecipeShoppingDTO  = {
        "fridgeId" : this.fridgeId,
        "itemIds" : this.missingItemIds
      }
      try {
        await addIngredientsToShoppingList(RecipeShoppingDTO);
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
    },

    adjustServingSize(newServingSize) {
      this.meal.recipeParts.forEach((part) => {
        part.ingredients.forEach((ingredient) => {
          ingredient.quantity *= newServingSize / this.meal.servingSize;
        });
      });
      this.meal.servingSize = newServingSize;
    },

    async addRecipeToSuggestions() {
      const recipeSuggestionAddDTO = {
        "fridgeId": this.fridgeId,
        "recipeId": this.meal.recipeId,
        "userId": this.userId
      }
      await addRecipeSuggestion((recipeSuggestionAddDTO));
      swal.fire({
        title: this.$t('success'),
        text: this.$t('success_add_recipe'),
        icon: 'success',
        confirmButtonText: this.$t('ok')
      });
    },
  },
};
</script>

<style scoped>


.add-missing-items-btn {
  width: 30%;
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
  text-align: center;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
}

.information-button {
  display: flex;
  grid-column: 3;
  text-align: right;
  margin-left: auto;
  padding: 0;
  height: 40px;
}

#info-picture {
  height: 30px;
  width: 30px;
  cursor: pointer;
}

.tips-weekMenu {
  background-color: #6C6C6C;
  height: 35px;
  color: white;
  font-size: 1.5em;
  padding-top: 5px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-column-gap: 10px;
  grid-column: 2;
}

.link {
  text-decoration: none;
  line-height: 25px;
  color: white;
}

.link:hover {
  cursor: pointer;
  font-size: x-large;
}

.grey-bar .link {
  height: 25px;
}

.active {
  background-size: 25px;
  background-color: #b1b1b1;
  border-radius: 5px;
  font-weight: bold;
  text-decoration: black;
  text-shadow: black 0 0 2px;
}

.grey-bar .link.active {
  height: 25px;
}


@media only screen and (max-width: 768px) {

  .add-missing-items-btn {
    width: 100%;
  }

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

@media only screen and (min-width: 10px) and (max-width: 650px) {
  .information-button {
    justify-content: center;
    align-content: center;
    align-items: center;
    height: 60px;
    display: flex;
    margin-left: auto;
    margin-right: 5px;
    gap: 30%;
    left: 0;
  }
  .grey-bar {
    display: flex;
    align-content: center;
    justify-content: center;
    margin-top: 5px;
    background-color: #31c48d;
    max-height: 60px;
    height: 60px;
    border-radius: 20px 20px 0 0;
  }

  .tips-weekMenu {
    background-color: #31c48d;
    color: white;
    font-size: 20px;
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-column-gap: 10px;
    grid-column: 2;
    margin-top: 0;
    padding-top: 0;
    padding-right: 10px;
    text-align: center;
    align-items: center;
    align-content: center;
    justify-content: center;
    margin-left: 10px;
    width: 70%;
  }

  .link.active {
    height: 60px !important;
    background-color: white;
    font-size: 20px;
    border-radius: 20px 20px 0 0;
    font-weight: bold;
    text-decoration: none;
    text-shadow: none;
    color: black;
    margin-top: 20px;
    padding-top: 8px;
    padding-right: 5px;
    padding-left: 5px;
  }

  .link {
    margin: 0;
    padding-left: 5px;
    padding-right: 5px;
    font-size: 20px;
  }

  #toggle-button:hover {
    cursor: pointer;
    font-size: unset;
    font-size: 20px;
  }

  .information-button{
    margin-right: 8px !important;
    height: 60px;
  }
}
</style>
