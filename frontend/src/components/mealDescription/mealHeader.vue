<template>
  <div class="meal-header">
    <div class="meal-image">
      <img :src="meal.thumbnailLink" alt="meal picture">
    </div>
    <h1 class="meal-name">{{ meal.recipeName }}</h1>
  </div>
</template>

<script>
import {number} from "yup";

export default {
  name: "mealHeader",

  props: {
    meal: {
      type: Object,
    },
  }
}
</script>

<style scoped>
.meal-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}


.meal-image {
  width: 200px;
  height: 150px;
  margin-right: 50px;

  position: relative;
}

.meal-image img {
  width: 95%;
  height: 95%;
  object-fit: contain;
  position: absolute;
  bottom: 0;
  right: 0;
}

.meal-name {
  font-size: 24px;
  font-weight: bold;
  border-bottom: 2px solid black;
  padding-bottom: 5px;
  display: flex;
  flex-direction: row-reverse;
  justify-content: flex-start;
}

@media only screen and (max-width: 768px) {
  .meal-header {
    margin-bottom: 10px;
  }

  .meal-image {
    width: 200px;
    height: 150px
  }

  .meal-name {
    font-size: 20px;
  }
}

@media only screen and (min-width: 350px) and (max-width: 480px) {



}
</style>
