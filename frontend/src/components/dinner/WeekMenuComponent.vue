<template>
  <h1>{{ $t("weekly_menu") }}</h1>
  <meal
    :isSuperUser="true"
    v-for="(meal, index) in meals"
    :key="index"
    :meal="meal"
    :currenFridge="fridge"
    @delete-item="deleteItem"
  />
</template>

<script>
import meal from "@/components/dinner/MealComponent.vue";
export default {
  components: {
    meal,
  },
  setup() {
    const meals = [
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

    return {
      meals,
    };
  },
};
</script>

<style scoped>
* {
  text-align: center;
}
</style>
