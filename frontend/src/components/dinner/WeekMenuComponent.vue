<template>
  <section class="weekly-menu">
    <h1>{{ $t("weekly_menu") }}</h1>
    <div class="wrapper">
      <div class="day" v-for="day in weekdays" :key="day">
        <h2>{{ $t(day) }}</h2>
        <meal
            v-if="getMealForDay(day)"
            :isSuperUser="false"
            :meal="{
            ...getMealForDay(day),
            description: getMealForDay(day).description
          }"
        />
      </div>
    </div>
  </section>
</template>

<script>
import meal from "@/components/dinner/MealComponent.vue";
import { ref, onMounted } from "vue";
import {loadRecipeByFridgeItems, loadRecipeByFridgeItemsAndDay} from "@/services/DinnerService";
import { useFridgeStore } from "@/store/store";

export default {
  components: {
    meal,
  },
  setup() {
    const fridgeId = useFridgeStore().getCurrentFridge.fridgeId;
    const meals = ref([]);
    const weekdays = [
      "Monday",
      "Tuesday",
      "Wednesday",
      "Thursday",
      "Friday",
      "Saturday",
      "Sunday",
    ];
    let pageIndex = ref(0);

    onMounted(async () => {
      try {
        let i = 0
        for(let day of weekdays) {
          const response = await loadRecipeByFridgeItemsAndDay(
              fridgeId,
              day.toUpperCase(),
              pageIndex.value,
              7
          );


          for(let newMeal of response.content) {
            if (
                !meals.value.find(
                    (meal) => meal.recipeName === newMeal.recipeName
                )
            ) {
              meals.value[i] = newMeal
              break;
            }
          }



          meals.value[i].dayOfWeek = day;
          i++
        }

      } catch (error) {
        console.error("Failed to load:", error);
      }
    });



    function getMealForDay(day) {
      let result = {};
      for (let i = 0; i < meals.value.length; i++) {
        if (meals.value[i].dayOfWeek == day) {
          result = meals.value[i];
          return result;
        }
      }
      return false;
    }

    return {
      meals,
      weekdays,
      getMealForDay,
    };
  },
};
</script>


<style scoped>
.weekly-menu {
  font-family: 'Roboto', sans-serif;
  background: linear-gradient(180deg, #31C48D 0%, #FFFFFF 100%);
  padding: 3rem 0;
}

h1 {
  text-align: center;
  color: #ffffff;
  font-size: 3.5rem;
  margin-bottom: 2rem;
  font-weight: 700;
}

.wrapper {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  max-width: 100vw;
  margin: auto;
}

.day {
  flex: 0 1 calc(100% / 7);
  margin-bottom: 30px;
  padding: 2rem;
  background-color: #ffffff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.day h2 {
  margin-bottom: 1rem;
  color: #31C48D;
  font-size: 1.5rem;
  font-weight: 600;
  text-align: center;
}

@media only screen and (max-width: 650px) {
  .wrapper {
    flex-direction: column;
    align-items: center;
  }


  h1 {
    font-size: 2.5rem;
  }

  .day {
    flex: 1;
    max-width: 100%;
    padding: 1.5rem;
    border-top: 1px solid #31C48D;
    border-bottom: 1px solid #31C48D;
  }

  .day h2 {
    font-size: 1.2rem;
  }

}
</style>