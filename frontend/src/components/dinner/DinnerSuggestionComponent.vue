<template>
  <div>
    <div class="suggestions-wrapper">
      <h1 id="sugTitle">{{ $t("suggestion") }}</h1>
      <div class="suggestions-grid">
        <div v-for="(suggestion, index) in suggestions" :key="index" class="suggestion">
          <div class="suggestion-user">
            <img
                :src="getProfilePicture(suggestion.UserId)"
                alt="Member Profile Picture"
                class="suggestion-profile-picture"
            />
            <div class="username-bubble">
              <div class="chat-bubble-text">
                {{ getUsername(suggestion.UserId) + " " + $t("wants_this") }}
              </div>
              <div class="chat-bubble-triangle"></div>
            </div>
          </div>
          <meal
              :isSuperUser="isCurrentUserSuperUser"
              :meal="suggestion.recipeLoadDTO"
              @delete-item="denySuggestion(suggestion)"
              @accept-item="acceptSuggestion($event, suggestion)"
          />
          </div>
        </div>
      </div>
      <h1 id="sugTitle">{{ $t("tips") }}</h1>
      <div class="meals-grid">
        <meal
            :isSuperUser="false"
            v-for="(meal, index) in meals"
            :key="index"
            :meal="meal"
        />
      </div>
        <div class="pagination-buttons" v-if="!isMobile">
          <BasicButton @click="loadPreviousPage" :button-text="$t('previous_page')" :disabled="pageIndex.value <= 0"/>
          <div class="page-index">{{ pageIndex + 1}}</div>
          <BasicButton @click="loadNextPage" :button-text="$t('next_page')"/>
        </div>
        <div id="bottom"></div>
      </div>
    </template>
    <script>
    import meal from "@/components/dinner/MealComponent.vue";
    import { loadRecipeByFridgeItems } from "@/services/DinnerService";
    import { useFridgeStore } from "@/store/store";
    import defaultProfilePicture from '@/assets/images/profiledefualt.svg';
    import { onUnmounted, ref} from "vue";
    import BasicButton from "@/components/basic-components/BasicButton.vue";
    import swal from "sweetalert2";
    import {acceptRecipeSuggestion, denyRecipeSuggestion, loadRecipeSuggestions} from "../../services/DinnerService";
    import {getProfilePictureById} from "../../services/UserService";
    import {loadUsersByFridgeId} from "../../services/FridgeServices";
    import { useI18n } from 'vue-i18n';

    export default {
      components: {
        BasicButton,
        meal,
      },

      data() {
        return {
          width: window.innerWidth,
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


      methods : {
        getProfilePicture(userId) {
          return this.profilePictures[userId];
        },
        getUsername(userId) {
          const user = this.memberList.find((member) => member.userId === userId);
          return user ? user.username : "";
        },
      },

      setup() {
        const { t } = useI18n();
        const fridgeStore = useFridgeStore();
        const fridgeId = fridgeStore.getCurrentFridge.fridgeId;
        const meals = ref([]);
        const suggestions = ref([]);
        let pageIndex = ref(-1);
        const profilePictures = ref({});
        const memberList = ref([]);

        const fetchUsers = async () => {
          try {
            const response = await loadUsersByFridgeId(fridgeId);
            memberList.value = response.data.memberInfo;
          } catch (error) {
            console.error("Error fetching users:", error);
          }
        };

        const fetchProfilePictures = async () => {
          const uniqueUserIds = [...new Set(suggestions.value.map(s => s.UserId))];
          for (const userId of uniqueUserIds) {
            try {
              const response = await getProfilePictureById(userId);
              const imageUrl = URL.createObjectURL(new Blob([response.data], { type: 'image/jpeg' }));
              profilePictures.value[userId] = imageUrl;
            } catch (error) {
              profilePictures.value[userId] = defaultProfilePicture; // Fallback image
            }
          }
        };


        const loadSuggestions = async () => {
          try {
            const response = await loadRecipeSuggestions(fridgeId);
            suggestions.value = response;
            await fetchProfilePictures();
            await fetchUsers();
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
            // Remove the suggestion from the
            loadSuggestions();
            swal.fire(
                'Accepted',
                'The missing item IDs have been accepted.',
                'success'
            );
          } catch (error) {
            swal.fire({
              title: this.$t('error'),
              text: this.$t('error_add_missing_items'),
              icon: 'error',
              confirmButtonText: this.$t('ok')
            });
          }
        };

        const denySuggestion = async (suggestion) => {
          try {
            const recipeId = suggestion.recipeLoadDTO.recipeId
            const userId = suggestion.UserId
            await denyRecipeSuggestion(fridgeId, recipeId, userId);
            // Remove the suggestion from the list
            loadSuggestions();
            swal.fire(
                this.$t('success_message'),
                '',
                'success'
            )
          } catch (error) {
            swal.fire({
              title: this.$t('error'),
              text: this.$t('delete_error'),
              icon: 'error',
              confirmButtonText: this.$t('ok')
            });
          }
        };

        const loadPreviousPage = async () => {
          if (pageIndex.value == 0) return;
          swal.fire({
            title: t("loading") + " ...",
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
            title: t("loading") + " ...",
            allowEscapeKey: false,
            allowOutsideClick: false,
            showConfirmButton: false,
            didOpen: () => {
              swal.showLoading();
            },
          });
          try {
            pageIndex.value++;
            const response = await loadRecipeByFridgeItems(fridgeId, pageIndex.value, 8);
            meals.value = response.content;
          } catch (error) {
            pageIndex.value--
            console.error("Failed to load next page:", error);
          } finally {
            swal.close();
          }
        };

        const loadMore = async () => {
          try {
            const response = await loadRecipeByFridgeItems(fridgeId, pageIndex.value, 8);
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
          profilePictures,
          memberList,
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
    .suggestions-wrapper {
      margin-bottom: 2rem;
    }

    .suggestions-grid,
    .meals-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(345px, 1fr));
      grid-gap: 30px;
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

    .pagination-buttons {
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: row;
      gap: 15px;
      margin: 2% auto;
    }

    .page-index {
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 18px;
      padding: 5px 10px;
      border: 2px solid #31c48d;
      border-radius: 4px;
      margin: 0 1rem;
    }


    #sugTitle {
      margin-top: 10px;
      border-radius: 20px 20px 20px 20px;
      background-color: #31c48d;
      color: white;
      font-size: 25px;
      text-align: center;
    }

    .suggestion-profile-picture {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      margin-right: 10px;
      object-fit: cover;
    }

    .suggestion {
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    .suggestion-user {
      display: flex;
      flex-direction: row;
      align-items: center;
      margin-bottom: 10px;
    }

    .username-bubble {
      position: relative;
      display: flex;
    }

    .chat-bubble-text {
      background-color: #31c48d;
      border-radius: 15px;
      color: white;
      font-size: 14px;
      padding: 5px 10px;
    }

    .chat-bubble-triangle {
      position: absolute;
      left: 2px;
      top: 20px;
      transform: translateX(-50%);
      width: 0;
      height: 0;
      border-style: solid;
      border-width: 0 12px 12px 12px;
      border-color: transparent transparent #31c48d transparent;
    }




    </style>