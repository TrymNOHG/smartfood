<template>
  <div>
    <button @click="loadPreviousPage" :disabled="pageIndex.value === 0">Previous Page</button>
    <button @click="loadNextPage">Next Page</button>
    <div class="wrapper">
      <meal
          :isSuperUser="true"
          v-for="(meal, index) in meals"
          :key="index"
          :meal="meal"
          :currenFridge="fridge"
          @delete-item="deleteItem"
      />
    </div>
  </div>
</template>
<script>
import meal from "@/components/dinner/MealComponent.vue";
import { loadRecipeByFridgeItems } from "../../services/DinnerService";
import { useFridgeStore } from "../../store/store";
import { ref } from "vue";

export default {
  components: {
    meal,
  },
  setup() {
    const fridgeId = useFridgeStore().getCurrentFridge.fridgeId;
    const meals = ref([]);
    let pageIndex = ref(0);

    const loadPreviousPage = async () => {
      if (pageIndex.value === 0) return;

      try {
        pageIndex.value--;
        const response = await loadRecipeByFridgeItems(fridgeId, pageIndex.value, 10);
        meals.value = response.content;
      } catch (error) {
        console.error("Failed to load previous page:", error);
        pageIndex.value++; // If failed to load, revert the pageIndex back
      }
    };

    const loadNextPage = async () => {
      try {
        const response = await loadRecipeByFridgeItems(fridgeId, pageIndex.value, 10);
        meals.value = response.content;
        pageIndex.value++;
      } catch (error) {
        console.error("Failed to load next page:", error);
      }
    };

    // Load initial data
    loadNextPage();

    return {
      meals,
      loadPreviousPage,
      loadNextPage,
      pageIndex,
    };
  },
};
</script>
<style scoped>
.wrapper {
  z-index: 0;
  margin-left: 2%;
  margin-right: 2%;
  margin-top: 2%;
  grid-template-columns: repeat(auto-fill, minmax(345px, 1fr));
  grid-row-gap: 30px;
  transition: 0.5s;
}
</style>