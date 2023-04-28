<template>
  <h1>{{ $t("weekly_menu") }}</h1>
  <div class="wrapper">
    <MenuComponent
      :isSuperUser="true"
      v-for="(menu, index) in menus"
      :key="index"
      :meal="menu"
      :currenFridge="fridge"
      @delete-item="deleteItem"
    />
  </div>
</template>

<script>
import MenuComponent from "@/components/dinner/MenuComponent.vue";

export default {
  components: {
    MenuComponent,
  },
  setup() {
    const menus = [
      {
        description:
          "Delicious grilled chicken with a side of roasted vegetables",
        image:
          "https://www.simplyrecipes.com/thmb/WXzv7XkTQvFEpYnyyk4x5HRMtVc=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Simply-Recipes-Grilled-Chicken-LEAD-SEO-Horizontal-1b86ef1e115444ba8b6fb216f2810c7c.jpg",
        name: "Grilled Chicken",
        price: 12.99,
      },
      {
        description: "Creamy tomato soup with fresh basil and croutons",
        image:
          "https://cdn.loveandlemons.com/wp-content/uploads/2023/01/tomato-soup-recipe.jpg",
        name: "Tomato Soup",
        price: 6.99,
      },
      {
        description: "Savory beef burger with bacon and cheese",
        image:
          "https://leitesculinaria.com/wp-content/uploads/2022/07/skillet-bacon-cheeseburger-with-crispy-fried-onions.jpg",
        name: "Bacon Cheeseburger",
        price: 10.99,
      },
      {
        description:
          "Delicious grilled chicken with a side of roasted vegetables",
        image:
          "https://www.simplyrecipes.com/thmb/WXzv7XkTQvFEpYnyyk4x5HRMtVc=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Simply-Recipes-Grilled-Chicken-LEAD-SEO-Horizontal-1b86ef1e115444ba8b6fb216f2810c7c.jpg",
        name: "Grilled Chicken",
        price: 12.99,
      },
      {
        description: "Creamy tomato soup with fresh basil and croutons",
        image:
          "https://cdn.loveandlemons.com/wp-content/uploads/2023/01/tomato-soup-recipe.jpg",
        name: "Tomato Soup",
        price: 6.99,
      },
      {
        description: "Savory beef burger with bacon and cheese",
        image:
          "https://leitesculinaria.com/wp-content/uploads/2022/07/skillet-bacon-cheeseburger-with-crispy-fried-onions.jpg",
        name: "Bacon Cheeseburger",
        price: 10.99,
      },
      {
        description:
          "Delicious grilled chicken with a side of roasted vegetables",
        image:
          "https://www.simplyrecipes.com/thmb/WXzv7XkTQvFEpYnyyk4x5HRMtVc=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Simply-Recipes-Grilled-Chicken-LEAD-SEO-Horizontal-1b86ef1e115444ba8b6fb216f2810c7c.jpg",
        name: "Grilled Chicken",
        price: 12.99,
      },
      {
        description: "Creamy tomato soup with fresh basil and croutons",
        image:
          "https://cdn.loveandlemons.com/wp-content/uploads/2023/01/tomato-soup-recipe.jpg",
        name: "Tomato Soup",
        price: 6.99,
      },
      {
        description: "Savory beef burger with bacon and cheese",
        image:
          "https://leitesculinaria.com/wp-content/uploads/2022/07/skillet-bacon-cheeseburger-with-crispy-fried-onions.jpg",
        name: "Bacon Cheeseburger",
        price: 10.99,
      },
      {
        description:
          "Delicious grilled chicken with a side of roasted vegetables",
        image:
          "https://www.simplyrecipes.com/thmb/WXzv7XkTQvFEpYnyyk4x5HRMtVc=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Simply-Recipes-Grilled-Chicken-LEAD-SEO-Horizontal-1b86ef1e115444ba8b6fb216f2810c7c.jpg",
        name: "Grilled Chicken",
        price: 12.99,
      },
      {
        description: "Creamy tomato soup with fresh basil and croutons",
        image:
          "https://cdn.loveandlemons.com/wp-content/uploads/2023/01/tomato-soup-recipe.jpg",
        name: "Tomato Soup",
        price: 6.99,
      },
      {
        description: "Savory beef burger with bacon and cheese",
        image:
          "https://leitesculinaria.com/wp-content/uploads/2022/07/skillet-bacon-cheeseburger-with-crispy-fried-onions.jpg",
        name: "Bacon Cheeseburger",
        price: 10.99,
      },
      {
        description:
          "Delicious grilled chicken with a side of roasted vegetables",
        image:
          "https://www.simplyrecipes.com/thmb/WXzv7XkTQvFEpYnyyk4x5HRMtVc=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Simply-Recipes-Grilled-Chicken-LEAD-SEO-Horizontal-1b86ef1e115444ba8b6fb216f2810c7c.jpg",
        name: "Grilled Chicken",
        price: 12.99,
      },
      {
        description: "Creamy tomato soup with fresh basil and croutons",
        image:
          "https://cdn.loveandlemons.com/wp-content/uploads/2023/01/tomato-soup-recipe.jpg",
        name: "Tomato Soup",
        price: 6.99,
      },
      {
        description: "Savory beef burger with bacon and cheese",
        image:
          "https://leitesculinaria.com/wp-content/uploads/2022/07/skillet-bacon-cheeseburger-with-crispy-fried-onions.jpg",
        name: "Bacon Cheeseburger",
        price: 10.99,
      },
      {
        description:
          "Delicious grilled chicken with a side of roasted vegetables",
        image:
          "https://www.simplyrecipes.com/thmb/WXzv7XkTQvFEpYnyyk4x5HRMtVc=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Simply-Recipes-Grilled-Chicken-LEAD-SEO-Horizontal-1b86ef1e115444ba8b6fb216f2810c7c.jpg",
        name: "Grilled Chicken",
        price: 12.99,
      },
      {
        description: "Creamy tomato soup with fresh basil and croutons",
        image:
          "https://cdn.loveandlemons.com/wp-content/uploads/2023/01/tomato-soup-recipe.jpg",
        name: "Tomato Soup",
        price: 6.99,
      },
      {
        description: "Savory beef burger with bacon and cheese",
        image:
          "https://leitesculinaria.com/wp-content/uploads/2022/07/skillet-bacon-cheeseburger-with-crispy-fried-onions.jpg",
        name: "Bacon Cheeseburger",
        price: 10.99,
      },
    ];
    const days = [
      "Monday",
      "Tuesday",
      "Wednesday",
      "Thursday",
      "Friday",
      "Saturday",
      "Sunday",
    ];

    return {
      menus,
      days,
    };
  },
};
</script>

<style scoped>
* {
  text-align: center;
}

.wrapper {
  z-index: 0;
  margin-left: 2%;
  margin-right: 2%;
  margin-top: 2%;
  grid-template-columns: repeat(auto-fill, minmax(345px, 1fr));
  grid-row-gap: 30px;
  transition: 0.5s;
  max-width: 690px; /* added to limit the total width of the grid */
  margin: 2% auto;
}
</style>
