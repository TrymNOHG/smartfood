  <template>
    <div>
      <div class="wrapper">
        <div class="suggestions">
          <h3>Suggestions:</h3>
          <div v-for="(suggestion, index) in suggestions" :key="index">
            <meal
                :isSuperUser="isCurrentUserSuperUser"
                :meal="suggestion.recipeLoadDTO"
                @delete-item="denySuggestion(suggestion)"
                @accept-item="acceptSuggestion($event, suggestion)"
            />
          </div>
        </div>
        <meal
            :isSuperUser="false"
            v-for="(meal, index) in meals"
            :key="index"
            :meal="meal"
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
  import swal from "sweetalert2";
  import {acceptRecipeSuggestion, denyRecipeSuggestion, loadRecipeSuggestions} from "../../services/DinnerService";

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
      isCurrentUserSuperUser() {
        return this.fridgeStore.getIsSuperUser;
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
      const fridgeStore = useFridgeStore();
      const fridgeId = fridgeStore.getCurrentFridge.fridgeId;
      const meals = ref([]);
      const suggestions = ref([]);
      let pageIndex = ref(0);


      const loadSuggestions = async () => {
        try {
          const response = await loadRecipeSuggestions(fridgeId);
          suggestions.value = response;
          console.log("Suggestions loaded:", suggestions.value)
        } catch (error) {
          console.error("Failed to load suggestions:", error);
        }
      };

      loadSuggestions();


      const acceptSuggestion = async (missingIds, suggestion) => {
        const recipeShoppingDTO = {
          fridgeId: fridgeId,
          itemIds: missingIds
        }
        const recipeId = suggestion.recipeLoadDTO.recipeId
        const userId = suggestion.UserId
        try {

          await acceptRecipeSuggestion(recipeShoppingDTO, recipeId, userId);
          // Remove the suggestion from the list
          suggestions.value = suggestions.value.filter(s => s.id !== suggestion.id);
        } catch (error) {
          console.error("Failed to accept suggestion:", error);
        }
      };

      const denySuggestion = async (suggestion) => {
        try {
          const recipeId = suggestion.recipeLoadDTO.recipeId
          const userId = suggestion.UserId
          await denyRecipeSuggestion(fridgeId, recipeId, userId);
          // Remove the suggestion from the list
          suggestions.value = suggestions.value.filter(s => s.id !== suggestion.id);
        } catch (error) {
          console.error("Failed to deny suggestion:", error);
        }
      };

      const loadPreviousPage = async () => {
        if (pageIndex.value === 0) return;
        swal.fire({
          title: 'Loading...',
          allowEscapeKey: false,
          allowOutsideClick: false,
          showConfirmButton: false,
          didOpen: () => {
            swal.showLoading();
          },
        });
        try {
          pageIndex.value--;
          const response = await loadRecipeByFridgeItems(fridgeId, pageIndex.value, 8);
          meals.value = response.content;
        } catch (error) {
          console.error("Failed to load previous page:", error);
          pageIndex.value++;
        }finally {
          swal.close();
        }
      };

      const loadNextPage = async () => {
        swal.fire({
          title: 'Loading...',
          allowEscapeKey: false,
          allowOutsideClick: false,
          showConfirmButton: false,
          didOpen: () => {
            swal.showLoading();
          },
        });
        try {
          const response = await loadRecipeByFridgeItems(fridgeId, pageIndex.value, 8);
          meals.value = response.content;
          pageIndex.value++;
        } catch (error) {
          console.error("Failed to load next page:", error);
        } finally {
          swal.close();
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
        fridgeStore,
        meals,
        loadMore,
        observeBottom,
        loadPreviousPage,
        loadNextPage,
        pageIndex,
        suggestions,
        loadSuggestions,
        acceptSuggestion,
        denySuggestion,
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