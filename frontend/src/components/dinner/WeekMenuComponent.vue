<template>
  <h1>{{ $t("weekly_menu") }}</h1>

  <div class="wrapper2">
    <div v-for="day in weekdays" :key="day">
      <h2>{{ day }}</h2>
      <meal
        v-if="getMealForDay(day)"
        :isSuperUser="true"
        :meal="getMealForDay(day)"
        :currentFridge="fridge"
        @delete-item="deleteItem"
      />
    </div>
  </div>
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
          console.log(day.toUpperCase())
          const response = await loadRecipeByFridgeItemsAndDay(
              fridgeId,
              day.toUpperCase(),
              pageIndex.value,
              10
          );
          console.log("tf")
          console.log(response.content)

          meals.value = [...meals.value, ...response.content];
          console.log("meals ", meals.value);

          meals.value[i].dayOfWeek = weekdays[i];
          console.log(meals.value[i].dayOfWeek, " ", weekdays[i]);

          i++
        }

      } catch (error) {
        console.error("Failed to load:", error);
      }
    });



    function getMealForDay(day) {
      console.log(day);
      console.log("SHOOOOOOOOW ME PLEEEEEEEASE");
      console.log(meals.value);
      let result = {};
      for (let i = 0; i < meals.value.length; i++) {
        if (meals.value[i].dayOfWeek == day) {
          console.log("WOOOHOOO MATCH FOUND!!!!");
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
* {
  text-align: center;
}
.wrapper {
  z-index: 0;
  grid-template-columns: repeat(auto-fill, minmax(345px, 1fr));
  grid-row-gap: 30px;
  transition: 0.5s;
  margin: 2% 2% 2%;
}

.wrapper {
  z-index: 0;
  grid-template-columns: repeat(auto-fill, minmax(345px, 1fr));
  grid-row-gap: 30px;
  transition: 0.5s;
  max-width: 80vw;
  margin: auto;
}

@media only screen and (min-width: 10px) and (max-width: 650px) {


  .wrapper2 {
    z-index: 0;
    grid-template-columns: repeat(auto-fill, minmax(345px, 1fr));
    grid-row-gap: 30px;
    transition: 0.5s;
    width: 100%;
    padding: 0;
  }




}
</style>
