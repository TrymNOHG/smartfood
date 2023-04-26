<template>
  <div class="cards-container">
    <router-link to="/fridge/item" @click="storeCurrentItem(item)">
      <div class="card" :style="{ 'border-color': borderColor }">
        <div class="front-side">
          <img :src="item.image" alt="item picture">
        </div>
        <div class="back-side">
          <div class="item-detail">
            <div class="item-name">
              <h2>{{item.name}}</h2>
              <h3>Expiration date: {{new Date(item.expirationDate)
                  .toLocaleDateString(undefined, { day: 'numeric', month: 'long', year: 'numeric' }) }}</h3>
              <br>
            </div>
            <h4>Price: {{ item.price }}; kr</h4>
            <h4>Purchase date: {{ new Date(item.purchaseDate)
                .toLocaleDateString(undefined, { day: 'numeric', month: 'long', year: 'numeric' }) }}</h4>
            <h4>Expiration date: {{ new Date(item.expirationDate)
                .toLocaleDateString(undefined, { day: 'numeric', month: 'long', year: 'numeric' }) }}</h4>
            <h4>How much is Left: {{ item.quantity }}L</h4>
            <button class="delete-btn" @click.prevent="deleteCard(item)">
              <span>
                <font-awesome-icon icon="fa-solid fa-trash" class="icon delete-icon" />
              </span>
            </button>
          </div>
        </div>
      </div>
    </router-link>
  </div>

</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {number} from "yup";
import swal from "sweetalert2";
import {useItemStore} from "@/store/store";
import Swal from "sweetalert2";

export default {
  name: "BasicFridgeItem",
  components: {FontAwesomeIcon},

  props: {
    item: {
      type: Object,
      default: () => ({
        description: String,
        expirationDate: String,
        image: String,
        name: String,
        price: String,
        purchaseDate: String,
        quantity: number,
        store: String
      })
    },
  },

  methods: {

    storeCurrentItem(item){
      this.itemStore.setCurrentItem(item);
    },

    deleteCard(item) {
      let deletePercentage = null;

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
          Swal.fire({
            html: `
          <div class="swal2-content">
            <div class="swal2-text">
              ${this.$t('how_much_left')}
            </div>
            <div id="range-value-text" class="swal2-text"></div>
          </div>
        `,
            input: 'range',
            inputAttributes: {
              min: 0,
              max: 100,
              step: 1
            },
            didOpen: () => {
              deletePercentage = Swal.getInput()
              const inputNumber = Swal.getHtmlContainer().querySelector('#range-value')
              const rangeValueText = Swal.getHtmlContainer().querySelector('#range-value-text')

              deletePercentage.nextElementSibling.style.display = 'none'
              deletePercentage.style.width = '100%'

              deletePercentage.addEventListener('input', () => {
                inputNumber.value = deletePercentage.value
                rangeValueText.innerText = `${deletePercentage.value}%`
              })
            },
            showCancelButton: true,
            confirmButtonText: this.$t('confirm_button'),
            cancelButtonText: this.$t('cancel_button'),
            customClass: {
              container: 'my-swal-dialog-container'
            }
          }).then((result) => {
            if (result.isConfirmed) {
              this.$emit('delete-item', item, deletePercentage.value);
              swal.fire(
                  this.$t('success_message'),
                  '',
                  'success'
              )
            }
          })
        }
      })
    }
  },

  setup(props) {
    const itemStore = useItemStore();

    let borderColor = calculateExpirationDate(props.item.purchaseDate, props.item.expirationDate);

    function calculateExpirationDate(purchaseDate, expirationDate) {
      const currentDate = new Date();
      const purchase = new Date(purchaseDate);
      const expiration = new Date(expirationDate);

      const totalTime = expiration.getTime() - purchase.getTime();
      const remainingTime = expiration.getTime() - currentDate.getTime();
      const percentageLeft = (remainingTime / totalTime) * 100;

      let borderColor;

      if (percentageLeft >= 75) {
        borderColor = 'green'; // Green for 75% or more time left
      } else if (percentageLeft >= 50) {
        borderColor = 'orange'; // Orange for 50% to 74% time left
      } else if (percentageLeft >= 25) {
        borderColor = 'yellow'; // Yellow for 25% to 49% time left
      } else {
        borderColor = 'red'; // Red for less than 25% time left
      }

      return borderColor;
    }


    return {
      borderColor,
      itemStore,
    }
  },
}
</script>

<style scoped>

.cards-container {
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  align-content: stretch;
  justify-content: space-evenly;
}

img {
  border-radius: 25px;
  object-fit: contain;
  width: 100%;
  height: 100%;
}

.card {
  border: 4px solid;
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

@media (max-width: 650px) {
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
    background-color: white;
    color: #eee;
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