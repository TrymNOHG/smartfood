<template>
  <div class="card-container" :style="{ 'border-color': borderColor }">
    <router-link to="/fridge/item" @click="storeCurrentItem(item)" id="item-link">
      <div class="card">
        <img class="item-img" :src="item.image" alt="item image"/>
        <h3>{{item.name}}</h3>
        <div class="item-info">
          <h4>Expiration date: {{new Date(item.expirationDate)
              .toLocaleDateString('nb-NO', { day: 'numeric', month: 'long', year: 'numeric' }) }}</h4>
          <h4>{{ $t('price') }}   {{item.price}}</h4>
        </div>
        <font-awesome-icon icon="fa-solid fa-trash"  @click="deleteItem" class="delete-icon icons"/>
      </div>
    </router-link>
  </div>
</template>


<script>
import {number} from "yup";
import {useItemStore} from "@/store/store";
import swal from "sweetalert2";
import Swal from "sweetalert2";

export default {
  name: "BasicFridgeList",

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
              ${this.$t('Percent-wise, how much was left?')}
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
    console.log(props.item)
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
  }
}
</script>

<style scoped>
.card {
  display: flex;
  justify-content: space-around;
  align-items: center;
  max-height: 100%;
  max-width: 100%;
  position: relative;
  background-color: white;
}

.item-img {
  max-height: 80px;
  max-width: 100%;
  object-fit: contain;
}

.item-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  padding-left: 10px;
}

#item-link {
  text-decoration: none;
  color: inherit;
  height: 100%;
  width: 100%;
}

.card-container {
  height: 100px;
  background-color: white;
  border: 3px solid white;
  border-radius: 5px;
  margin: 10px 5px;
  padding: 10px;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: auto;
}

.card-container:hover {
  box-shadow: 0 0 5px #ccc;
  transform: scale(1.02);
}

.icons {
  display: flex;
  align-items: center;
}

.delete-icon {
  font-size: 1.2rem;
  cursor: pointer;
  color: black;
  transition: color 0.2s ease-in-out;
  margin-left: auto;
}

.delete-icon:hover {
  color: #f00;
}

.swal2-modal {
  background-color: white;
  flex-direction: column;
  position: fixed;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  padding: 20px;
  border-radius: 5px;
  z-index: 100;
  display: flex;
  width: 60%;
  max-width: 500px;
}

.swal2-title {
  background-color: white;
  margin-top: 5%;
}

@media (max-width: 650px) {
  .item-img{
    margin-right: auto;
    width: 80px;
    height: 80px;
    border-radius: 5px;
    background-color: #eee;
    color: #eee;
  }
}


</style>