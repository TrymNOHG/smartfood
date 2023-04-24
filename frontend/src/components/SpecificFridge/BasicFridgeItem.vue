<template>
  <div class="cards-container">
    <router-link :to="{ name: 'itemView', params: { itemName: item.itemName, itemId: item.itemId}}">
      <div class="card" :style="{ 'border-color': borderColor }">
        <div class="front-side">
          <img src="@/assets/images/Large.jpg" alt="item picture">
        </div>
        <div class="back-side">
          <div class="item-detail">
            <div class="item-name">
              <h2>{{item.itemName}}</h2>
              <h3>Expiration date: {{ item.itemExpirationDate }}</h3>
              <br>
            </div>
            <h4>Price: {{ item.itemPrice }}; kr</h4>
            <h4>Buy date: {{ item.itemBuyDate }}</h4>
            <h4>Expiration date: {{ item.itemExpirationDate }}</h4>
            <h4>How much is Left: {{ item.itemLeft }}L</h4>
            <button class="delete-btn" @click="deleteCard">
            <span>
              <font-awesome-icon icon="fa-solid fa-trash" @click="deleteCard()" class="icon delete-icon" />
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

export default {
  name: "BasicFridgeItem",
  components: {FontAwesomeIcon},

  props: {
    item: {
      type: Object,
      default: () => ({
        itemId: "",
        itemName: "",
        itemPicture: null,
        itemPrice: "",
        itemBuyDate: "2023-04-15",
        itemExpirationDate: "2023-04-16",
        itemLeft: ""
      })
    },
  },

  setup(props) {
    function calculateExpirationDate(itemBuyDate, itemExpirationDate) {
      let borderColor = '';
      const daysUntilExpiration = (new Date(itemExpirationDate) - new Date()) / (1000 * 60 * 60 * 24);
      console.log(daysUntilExpiration);
      if (daysUntilExpiration <= 3) {
        borderColor = 'red';
      } else if (daysUntilExpiration <= 8) {
        borderColor = 'orange'
      } else {
        borderColor = 'green'
      }
      return borderColor;
    }

    const borderColor = calculateExpirationDate(props.item.itemBuyDate, props.item.itemExpirationDate);

    function deleteCard() {
    }

    return {
      borderColor,
      deleteCard
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
  object-fit: cover;
  width: 100%;
  height: 100%;
}

.card {
  border: 4px solid;
  border-radius: 23px;
  text-align: center;
  width: 325px;
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
  background-color: white;
}

.icon {
  color: black;
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
    font-size: 15px;
  }

  .front-side {
    display: flex;
    flex-direction: column;
    justify-content: center;
    border-radius: 5px;
  }

  img {
    margin-right: auto;
    width: 100px;
    height: 100px;
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
    font-size: 18px;
    margin-top: 10px;
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
    font-size: 18px;
    margin: 10px 0;
  }

  h4 {
    display: none;
  }
}


</style>