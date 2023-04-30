<template>
  <h1>{{ $t("weekly_menu") }}</h1>
  <div class="wrapper">
    <MenuComponent
      :isSuperUser="true"
      v-for="(menu, index) in menus"
      :key="index"
      :menu="menu"
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
        name: "Grilled Chicken",
        description:
          "Delicious grilled chicken with a side of roasted vegetables",
        price: 12.99,
        image:
          "https://www.simplyrecipes.com/thmb/WXzv7XkTQvFEpYnyyk4x5HRMtVc=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Simply-Recipes-Grilled-Chicken-LEAD-SEO-Horizontal-1b86ef1e115444ba8b6fb216f2810c7c.jpg",
        day: "Monday",
      },
      {
        name: "Tomato Soup",
        description: "Creamy tomato soup with fresh basil and croutons",
        price: 6.99,
        image:
          "https://cdn.loveandlemons.com/wp-content/uploads/2023/01/tomato-soup-recipe.jpg",
        day: "Tuesday",
      },
      {
        name: "Bacon Cheeseburger",
        description: "Savory beef burger with bacon and cheese",
        price: 10.99,
        image:
          "https://leitesculinaria.com/wp-content/uploads/2022/07/skillet-bacon-cheeseburger-with-crispy-fried-onions.jpg",
        day: "Wednesday",
      },
      {
        name: "Bacon Cheeseburger",
        description: "Savory beef burger with bacon and cheese",
        price: 10.99,
        image:
          "https://leitesculinaria.com/wp-content/uploads/2022/07/skillet-bacon-cheeseburger-with-crispy-fried-onions.jpg",
        day: "Thursday",
      },
      {
        name: "Bacon Cheeseburger",
        description: "Savory beef burger with bacon and cheese",
        price: 10.99,
        image:
          "https://leitesculinaria.com/wp-content/uploads/2022/07/skillet-bacon-cheeseburger-with-crispy-fried-onions.jpg",
        day: "Friday",
      },
      {
        name: "Bacon Cheeseburger",
        description: "Savory beef burger with bacon and cheese",
        price: 10.99,
        image:
          "https://leitesculinaria.com/wp-content/uploads/2022/07/skillet-bacon-cheeseburger-with-crispy-fried-onions.jpg",
        day: "Saturday",
      },
      {
        name: "Bacon Cheeseburger",
        description: "Savory beef burger with bacon and cheese",
        price: 10.99,
        image:
          "https://leitesculinaria.com/wp-content/uploads/2022/07/skillet-bacon-cheeseburger-with-crispy-fried-onions.jpg",
        day: "Sunday",
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
  grid-template-columns: repeat(auto-fill, minmax(345px, 1fr));
  grid-row-gap: 30px;
  transition: 0.5s;
  max-width: 690px; /* added to limit the total width of the grid */
  margin: auto;
}
.wrapper {
  z-index: 0;
  grid-template-columns: repeat(auto-fill, minmax(345px, 1fr));
  grid-row-gap: 30px;
  transition: 0.5s;
  max-width: 690px;
  margin: auto;
}

.wrapper > *:only-child,
.wrapper > *:last-child:nth-child(odd) {
  margin: 0 auto;
}
</style>
