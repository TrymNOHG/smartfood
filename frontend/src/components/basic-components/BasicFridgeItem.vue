<template>
  <body>
  <div class="card" :style="{ 'border-color': borderColor }">
    <div class="front-side">
      <img src="@/assets/images/Large.jpg" alt="item picture">
    </div>
    <div class="back-side">
      <div class="item-name">
        <div class="item-detail">
          <h2>Egg <br><span>12.12.2012</span></h2>
          <h4>Price: 420; kr</h4>
          <h4>Buy date: 10.12.2012</h4>
          <h4>Expiration date: 10.12.2012</h4>
          <h4>How much is Left: 0.5L</h4>
        </div>
      </div>
    </div>
  </div>
  </body>
</template>

<script>
export default {
  name: "BasicFridgeItem",

  props: {
    item: {
      type: Object,
      default: () => ({
        itemName: "",
        itemPicture: null,
        itemPrice: "",
        itemBuyDate: "2023-04-15",
        itemExpirationDate: "2023-04-16",
        itemLeft: ""
      })
    }
  },

  setup(props) {
    function calculateExpirationDate(itemBuyDate, itemExpirationDate) {
      let borderColor = '';
      const daysUntilExpiration = (new Date(itemExpirationDate) - new Date(itemBuyDate)) / (1000 * 60 * 60 * 24);
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
    return {
      borderColor
    }
  },
}
</script>

<style scoped>

body {
  margin: 0;
  padding: 0;
}

img {
  border-radius: 25px;
  object-fit: cover;
  width: 100%;
  height: 100%;
}

.card {
  position: absolute;
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
  background-color: whitesmoke;
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

@media (max-width: 800px) {
  .card {
    border-radius: 20px;
    border: 2px solid;
    box-shadow: none;
    position: static;
    width: 100%;;
  }
  .front-side {
    display: block;
  }
  img{
    border-radius: 0;
    right: 40%;
    width: 50px;
    height: 50px;
  }
  .back-side {
    display: none;
  }
  h2 {
    font-size: 18px;
    margin: 10px 0;
  }
}

</style>