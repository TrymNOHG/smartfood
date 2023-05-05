<template>
  <div class="cards-container">
    <router-link to="/dinner/meal" @click="storeCurrentMeal(meal)" id="item-link">
      <div class="card">
        <div class="front-side">
          <img :src="meal.thumbnail" alt="item picture">
          <div class="recipe-name-desktop">
            <h2>{{ meal.recipeName }}</h2>
          </div>
        </div>
        <div class="back-side">
          <div class="item-detail">
            <div class="item-description">
              <p ref="descriptionRef">{{ meal.description }}</p>
            </div>
            <div class="item-name">
              <h2 id="item-name-h2">{{ meal.recipeName }}</h2>
              <br>
            </div>
          </div>
          <button v-if="isSuperUser" class="delete-btn" @click.prevent="deleteCard(meal)">
              <span>
                <font-awesome-icon icon="fa-solid fa-trash" class="icon delete-icon" />
              </span>
          </button>
          <button v-if="isSuperUser" class="accept-btn" @click.prevent="acceptCard">
            <span>
              <font-awesome-icon icon="fa-solid fa-check" class="icon accept-icon" />
            </span>
          </button>
        </div>
      </div>
    </router-link>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import swal from "sweetalert2";
import {useMealStore} from "../../store/store";
import {object} from "yup";

export default {
  name: "BasicFridgeItem",
  components: {FontAwesomeIcon},

  props: {
    meal: {
      type: Object,
    },
    isSuperUser: {
      type: Boolean,
      default: false
    },
  },

  computed: {
    missingItemIds() {
      const ids = [];
      this.meal.recipeParts.forEach(part => {
        part.ingredients.forEach(ingredient => {
          const recipyQuantity = ingredient.quantity;
          let hasQuantity = ingredient.fridgeAmount;
          let itemQuantity = ingredient.itemOriginalAmount;
          if (ingredient.itemOriginalUnit ===  "pieces" && ingredient.unitOfMeasurement !== "c") {
            itemQuantity = itemQuantity * 250;
            hasQuantity = hasQuantity * 250;
          }
          while (hasQuantity < recipyQuantity) {
            ids.push(ingredient.itemId);
            hasQuantity += itemQuantity;
          }
        });
      });
      return ids;
    },
  },

  methods: {

    storeCurrentMeal(meal){
      this.mealStore.setCurrentMeal(meal);
    },

    deleteCard(item) {
      swal.fire({
        title: this.$t('confirm_title'),
        text: this.$t('confirm_text'),
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#4dce38',
        cancelButtonColor: '#d33',
        confirmButtonText: this.$t('confirm_button'),
        cancelButtonText: this.$t('cancel_button'),
        customClass: {
          container: 'my-swal-dialog-container'
        }
      }).then((result) => {
        if (result.isConfirmed) {
          this.$emit('delete-item', item);
          swal.fire(
              this.$t('success_message'),
              '',
              'success'
          )
        }
      })
    },

    acceptCard() {
      swal.fire({
        title: 'Confirm Accept',
        text: 'Are you sure you want to accept this meal?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#4dce38',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        customClass: {
          container: 'my-swal-dialog-container'
        }
      }).then((result) => {
        if (result.isConfirmed) {
          this.$emit('accept-item', this.missingItemIds);
          swal.fire(
              'Accepted',
              'The missing item IDs have been accepted.',
              'success'
          );
        }
      });
    },

    truncateDescription() {
      const elem = this.$refs.descriptionRef;
      const lineHeight = parseFloat(getComputedStyle(elem).lineHeight);
      const maxHeight = lineHeight * 6; // For 6 lines

      if (elem.offsetHeight > maxHeight) {
        let content = this.meal.description;

        while (elem.offsetHeight > maxHeight) {
          content = content.slice(0, -1);
          elem.textContent = content + '...';
        }
      }
    },
  },

  watch: {
    meal: {
      immediate: true,
      handler() {
        this.$nextTick(() => {
          this.truncateDescription();
        });
      },
    },
  },

  setup(props) {
    const mealStore = useMealStore()
    return {
      mealStore,
    }
  },
}
</script>

<style scoped>

.accept-btn {
  position: absolute;
  top: 5px;
  left: 5px;
  background-color: white;
  color: white;
  border: none;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  font-size: 18px;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
  opacity: 100%;
}

.accept-btn:hover {
  transform: scale(1.2);
  background-color: green;
}

.accept-icon {
  color: black;
}

.accept-icon:hover {
  color: white;
}

.cards-container {
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  align-content: stretch;
  justify-content: space-evenly;
}

img {
  border-radius: 20px;
  object-fit: cover;
  width: 100%;
  height: 100%;
}

.card {
  border: 4px solid green;
  border-radius: 23px;
  text-align: center;
  width: 320px;
  height: 225px;
  perspective: 600px;
  transition: .5s;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);
}

.card .front-side {
  border-radius: 20px;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: white;
  backface-visibility: hidden;
  transform: rotateY(0deg);
  transition: 1s;
}

.card:hover .front-side {
  transform: rotateY(-180deg);
}

.card:hover .back-side {
  transform: rotateY(0deg);
}

.card .back-side {
  display: flex;
  flex-direction: column;
  justify-content: center;
  border-radius: 20px;
  position: absolute;
  text-align: center;
  opacity: 60%;
  width: 100%;
  height: 100%;
  color: white;
  background-color: black;
  backface-visibility: hidden;
  transform: rotateY(180deg);
  transition: 1s;
}

.delete-btn {
  position: absolute;
  top: 5px;
  right: 5px;
  background-color: white;
  color: white;
  border: none;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  font-size: 18px;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
  opacity: 100%;
}

.delete-btn:hover {
  transform: scale(1.2);
  background-color: red;
}

.icon {
  color: black;
}

.icon:hover {
  color: white;
}

.recipe-name-desktop {
  display: none;
}

.item-name {
  display: none;
}

.item-description{
  display: none;
}

@media (min-width: 651px) {
  .recipe-name-desktop {
    display: block;
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    text-align: center;
    color: white;
    background-color: rgba(0, 0, 0, 0.7);
    padding: 5px;
    border-radius: 0 0 20px 20px;
  }

  .item-description {
    display: block;
  }
}

@media (max-width: 650px) {

    .item-name {
      display: block;
    }


  body{
    height: 80px;
  }
  .card {
    display: flex;
    justify-content: end;
    width: 350px;
    height: 80px;
    background-color: #eee;
    border: 1px solid #ccc;
    border-radius: 5px;
    cursor: pointer;
  }

  h3 {
    font-weight: normal;
    font-size: 10px;
  }


  .front-side {
    display: flex;
    flex-direction: column;
    justify-content: center;
    border-radius: 5px;
  }

  img {
    margin-right: auto;
    width: 80px;
    height: 80px;
    border-radius: 5px;
    background-color: #eee;
    color: #eee;
  }

  .delete-btn {
    background-color: white;
    width: 25px;
    height: 25px;
    top: 13px;
    right: 2px;
  }
  .icon{
    color: black;
    font-size: 15px;
  }

  .item-name {
    display: block;
    font-size: 10px;
  }

  .card:hover .front-side {
    transform: rotateY(0deg);
  }

  .card:hover .back-side {
    transform: rotateY(0deg);
  }

  .card .back-side {
    margin-right: auto;
    backface-visibility: visible;
    transform: rotateY(0deg);
    color: black;
    opacity: 100%;
    width: 220px;
    background-color: #eee;
    border-radius: 5px;
  }

  h2 {
    font-size: 15px;
    margin: 10px 0;
  }

  h4 {
    display: none;
  }
}

@media only screen and (min-width: 350px) and (max-width: 480px) {
  body{
    height: 100%;
  }

  .cards-container{

    height: 100px;
    margin: 10px;
    padding-top: 10px;
    padding-bottom: 10px;
  }
  .card {
    display: flex;
    justify-content: end;
    width: 350px;
    height: 100px;
    background-color: white;
    border: 2px solid #ccc;
    border-radius: 5px;
    cursor: pointer;

  }

  h3 {
    font-weight: normal;
    font-size: 10px;
  }


  .front-side {
    display: flex;
    flex-direction: column;
    justify-content: center;
    border-radius: 5px;
  }

  img {
    margin-right: auto;
    width: 80px;
    height: 80px;
    border-radius: 5px;
    background-color: white;
    color: white;
    margin-left: 10px;
  }

  .delete-btn {
    background-color: white;
    width: 25px;
    height: 25px;
    top: 5px;
    right: 5px;
  }
  .icon{
    color: black;
    font-size: 15px;
    margin-right: 5px;
  }

  .item-name {
    display: block;
    font-size: 10px;
  }

  .card:hover .front-side {
    transform: rotateY(0deg);
  }

  .card:hover .back-side {
    transform: rotateY(0deg);
  }


  .card .back-side {
    margin-right: auto;
    backface-visibility: visible;
    transform: rotateY(0deg);
    color: black;
    opacity: 100%;
    width: 220px;
    background-color: #eee;
    border-radius: 5px;
  }

  h2 {
    font-size: 15px;
    margin: 10px 0;
  }

  h4 {
    display: none;
  }
}

</style>
