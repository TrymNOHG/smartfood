<template>
  <div>
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
    <div class="pagination-buttons" v-if="!isMobile">
      <BasicButton @click="loadPreviousPage" :disabled="pageIndex.value === 0" :button-text="$t('previous_page')"/>
      <BasicButton @click="loadNextPage" :button-text="$t('next_page')"/>
    </div>
    <div id="bottom"></div>
  </div>
</template>
<script>
import meal from "@/components/dinner/MealComponent.vue";
import { loadRecipeByFridgeItems } from "@/services/DinnerService";
import { useFridgeStore } from "@/store/store";
import {onMounted, onUnmounted, ref} from "vue";
import BasicButton from "@/components/basic-components/BasicButton.vue";

export default {
  components: {
    BasicButton,
    meal,
  },

  data() {
    return {
      width: window.innerWidth
    }
  },

  computed: {
    isMobile() {
      return this.width < 768;
    },
  },

  async mounted() {
    window.addEventListener("resize", () => {
      this.width = window.innerWidth;
    });

    if (this.isMobile) {
      await this.observeBottom();
    }
  },

  setup() {
    const fridgeId = useFridgeStore().getCurrentFridge.fridgeId;
    const meals = ref([]);
    let pageIndex = ref(0);

    const loadPreviousPage = async () => {
      if (pageIndex.value === 0) return;

      try {
        pageIndex.value--;
        const response = await loadRecipeByFridgeItems(fridgeId, pageIndex.value, 8);
        meals.value = response.content;
      } catch (error) {
        console.error("Failed to load previous page:", error);
        pageIndex.value++;
      }
    };

    const loadNextPage = async () => {
      try {
        const response = await loadRecipeByFridgeItems(fridgeId, pageIndex.value, 8);
        meals.value = response.content;
        pageIndex.value++;
      } catch (error) {
        console.error("Failed to load next page:", error);
      }
    };

    const loadMore = async () => {
      try {
        const response = await loadRecipeByFridgeItems(fridgeId, pageIndex.value, 8);
        console.log(response.content)
        meals.value = [ ...meals.value, ...response.content]
        pageIndex.value++;
      } catch (error) {
        console.error("Failed to load next page:", error);
      }
    };

    // Load initial data
    loadNextPage();

    const observeBottom = () => {
      const bottomElement = document.querySelector("#bottom");
      const observer = new IntersectionObserver(
          (entries) => {
            entries.forEach((entry) => {
              if (entry.isIntersecting) {
                loadMore();
              }
            });
          },
          { threshold: 1 }
      );
      if (bottomElement) {
        observer.observe(bottomElement);
      }
    };

    onUnmounted(() => {
      const bottomElement = document.querySelector("#bottom");
      const observer = new IntersectionObserver(() => {}, { threshold: 1 });
      if (bottomElement) {
        observer.unobserve(bottomElement);
      }
    });


    return {
      meals,
      loadMore,
      observeBottom,
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
  grid-template-columns: repeat(auto-fill, minmax(345px, 1fr));
  grid-row-gap: 30px;
  transition: 0.5s;
  margin: 2% 2% 2%;
}

.pagination-buttons {
  display: flex;
  justify-content: center;
  align-content: center;
  flex-direction: row;
  width: 250px;
  margin: 2% auto;
  gap: 25%;
}

</style>