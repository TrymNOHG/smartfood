<template>
  <div class="cards-container">
    <router-link to="/fridge/item" @click="storeCurrentItem(item)" id="item-link">
      <div class="card" :style="{ 'border-color': borderColor }">
        <div class="front-side">
          <img :src="item.image" alt="item picture">
        </div>
        <div class="back-side">
          <div class="item-detail">
            <div class="item-name">
              <h2 id="item-name-h2">{{item.name}}</h2>
              <h3 id="item-expiration-date">{{ $t('expire_date') }}: {{new Date(item.expirationDate)
                  .toLocaleDateString('nb-NO', { day: 'numeric', month: 'long', year: 'numeric' }) }}</h3>
              <br>
            </div>
            <h4 id="item-price">{{ $t('price') }}: {{ item.price }}; kr</h4>
            <h4 id="item-purchase-date">{{ $t('buy_date') }}: {{ new Date(item.purchaseDate)
                .toLocaleDateString('nb-NO', { day: 'numeric', month: 'long', year: 'numeric' }) }}</h4>
            <h4>{{ $t('expire_date') }}: {{ new Date(item.expirationDate)
                .toLocaleDateString('nb-NO', { day: 'numeric', month: 'long', year: 'numeric' }) }}</h4>
            <h4 id="item-quantity">{{ $t('Amount') }}: {{ item.amount.toFixed(3) }} {{ item.unit }}</h4>
            <button v-if="isSuperUser" class="delete-btn" @click.prevent="deleteCard(item)">
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
import {addItemToShoppingList} from "@/services/ItemService";

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
    isSuperUser: {
      type: Boolean,
      default: false
    }
  },

  methods: {
    storeCurrentItem(item) {
      this.itemStore.setCurrentItem(item);
    },

    deleteCard(item) {
      let deletePercentage = null;
      Swal.fire({
        html: `
         <div class="swal2-content">
           <div class="swal2-text">
             ${this.$t('percent_wise_how_much')}
           </div>
           <div id="range-value" class="swal2-text"></div>
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
          const rangeValueText = Swal.getHtmlContainer().querySelector('#range-value')

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
          swal.fire({
            title: this.$t('buy_again'),
            icon: 'success',
            showCancelButton: true,
            confirmButtonColor: '#4dce38',
            cancelButtonColor: '#d33',
            confirmButtonText: this.$t('Yes'),
            cancelButtonText: this.$t('No'),
            customClass: {
              container: 'my-swal-dialog-container'
            }
          }).then((result) => {
            if (result.isConfirmed) {
              this.$emit('add-shopping', item)
            }
            this.$emit('delete-item', item, deletePercentage.value);
          })
        }
      })
    },
  },


  setup(props) {
    const itemStore = useItemStore();

    let borderColor = calculateExpirationDate(props.item.purchaseDate, props.item.expirationDate);
    console.log(props.item)
    function calculateExpirationDate(purchaseDate, expirationDate) {
      const currentDate = new Date();
      const expiration = new Date(expirationDate);

      const remainingDays = Math.ceil((expiration.getTime() - currentDate.getTime()) / (1000 * 3600 * 24));
      console.log(remainingDays)

      let borderColor;

      if (remainingDays >= 5) {
        borderColor = 'green'; // Green for 7 or more days left
      } else if (remainingDays >= 3 && remainingDays < 5) {
        borderColor = 'orange'; // Orange for 1 to 2 days left
      } else {
        borderColor = 'red'; // Red for less than 1 day left
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
