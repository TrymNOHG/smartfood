<template>
  <div class="meal-header">
    <div class="meal-image">
      <img :src="meal.thumbnail" alt="meal picture">
    </div>

    <div class="meal-info">
      <h1 class="meal-name">{{ meal.recipeName }}</h1>
      <hr>
      <p id="description" v-html="formattedDescription"></p>
    </div>
  </div>
</template>

<script>
export default {
  name: "mealHeader",
  props: {
    meal: {
      type: Object,
    },
  },
  computed: {
    formattedDescription() {
      const baseUrl = "https://meny.no";
      return this.meal.description.replace(/\[(.*?)\]\((\/.*?)\)/g, (match, text, url) => {
        return `<a href="${baseUrl}${url}" target="_blank">${text}</a>`;
      });
    },
  },
};


</script>

<style scoped>
.meal-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.meal-image {
  margin-right: 50px;
}

.meal-image img {
  object-fit: contain;
  width: 100%;
}

.meal-info {
  display: flex;
  flex-direction: column;
}

.meal-name {
  font-size: 24px;
  font-weight: bold;
  display: flex;
  justify-content: flex-start;
}

hr {
  border: 0;
  border-bottom: 2px solid black;
  width: 100%;
  margin-top: 5px;
  margin-bottom: 10px;
}

#description {
  font-size: 18px;
}

  @media only screen and (max-width: 768px) {
    .meal-header {
      flex-direction: column;
      align-items: center;
    }

    .meal-image {
      margin-right: 0;
      margin-bottom: 10px;
      width: 100%;
      max-width: 400px;
    }

    .meal-name {
      font-size: 20px;
      text-align: center;
    }

    #description {
      font-size: 16px;
      text-align: center;
    }
  }

@media only screen and (min-width: 350px) and (max-width: 480px) {

}
</style>