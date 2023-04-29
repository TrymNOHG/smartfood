<template>
  <div class="wrapper">
    <meal :isSuperUser="true" v-for="(meal, index) in meals" :key="index" :meal="meal" :currenFridge="fridge"
                       @delete-item="deleteItem"/>
  </div>>
</template>

<script>
import meal from "@/components/dinner/MealComponent.vue"

export default {
  components: {
    meal
  },
  setup(){

    const meals = [
      {
        "recipeId": 1,
        "recipeName": "Grønnsakslasagne med søtpotet, aubergine og grønnkål",
        "description": "En vegetarisk oppskrift på grønnsakslasagne full av smak. Her er kjøttdeigen byttet ut med søtpotet og aubergine, ostesausen med en blomkålpuré og lasagneplatene med ulike grønnsaker i tynne skiver. Perfekt vegetarlasagne hvor du kan bruke en rekke grønnsaksrester.\n",
        "author": "Meny",
        "servingSize": 5,
        "difficulty": 1,
        "thumbnailLink": "https://www.bama.no/contentassets/58a7b9f95db04faaa20cd9bde6bd47d8/8adc932ab02342aa876b6d733d1e915c2.jpg?width=750&height=750&mode=crop",
        "cookTime": 50.0,
        "recipeParts": [
          {
            "recipePartId": 1,
            "partTitle": "Make dough",
            "partDescription": "Step 1. Knead dough",
            "recipeItems": [{
              "itemId" : 1,
              "quantity": 1,
              "unit" : "L"
            }]
          }
        ],
        "allergens": ["Dairy"]
      },
      {
        "recipeId": 2,
        "recipeName": "Baked Salmon with Lemon and Dill",
        "description": "This easy baked salmon recipe is flavored with fresh lemon and dill and is perfect for a healthy weeknight dinner. Simply season the salmon fillets with salt and pepper, drizzle with olive oil, and bake in the oven until tender and flaky.",
        "author": "Food Network",
        "servingSize": 4,
        "difficulty": 2,
        "thumbnailLink": "https://www.oppskriftskroken.no/wp-content/uploads/2017/01/Posjert-laks-med-agurksalat-.jpg",
        "cookTime": 25.0,
        "recipeParts": [
          {
            "recipePartId": 1,
            "partTitle": "Prepare the Salmon",
            "partDescription": "Step 1. Preheat the oven to 400 degrees F. Season the salmon fillets with salt and pepper on both sides. Drizzle with olive oil and sprinkle with fresh dill.",
            "recipeItems": [
              {
                "itemId": 1,
                "quantity": 4,
                "unit": "fillets"
              },
              {
                "itemId": 2,
                "quantity": 1,
                "unit": "tbsp"
              }
            ]
          },
          {
            "recipePartId": 2,
            "partTitle": "Bake the Salmon",
            "partDescription": "Step 2. Place the salmon fillets on a baking sheet lined with parchment paper. Bake for 15-20 minutes, or until the salmon is cooked through and flakes easily with a fork.",
            "recipeItems": []
          }
        ],
        "allergens": ["Fish"]
      },
      {
        "recipeId": 3,
        "recipeName": "Spaghetti Carbonara",
        "description": "This classic Italian pasta dish features spaghetti coated in a creamy sauce made from eggs, parmesan cheese, and bacon. It's quick, easy, and delicious!",
        "author": "Delish",
        "servingSize": 4,
        "difficulty": 5,
        "thumbnailLink": "https://i.ytimg.com/vi/dhBpU3Z1Qps/maxresdefault.jpg",
        "cookTime": 25.0,
        "recipeParts": [
          {
            "recipePartId": 1,
            "partTitle": "Cook the Spaghetti",
            "partDescription": "Step 1. Bring a large pot of salted water to a boil. Add spaghetti and cook until al dente, about 8-10 minutes. Reserve 1 cup of pasta water and drain spaghetti.",
            "recipeItems": [
              {
                "itemId": 1,
                "quantity": 8,
                "unit": "oz"
              }
            ]
          },
          {
            "recipePartId": 2,
            "partTitle": "Make the Carbonara Sauce",
            "partDescription": "Step 2. In a bowl, whisk together eggs, grated parmesan cheese, and black pepper. In a large skillet over medium heat, cook bacon until crispy. Remove bacon and set aside. Add garlic to skillet and cook until fragrant, about 1 minute. Add cooked spaghetti, reserved pasta water, and egg mixture to skillet. Toss everything together until spaghetti is coated in sauce and everything is heated through.",
            "recipeItems": [
              {
                "itemId": 2,
                "quantity": 4,
                "unit": "eggs"
              },
              {
                "itemId": 3,
                "quantity": 1,
                "unit": "cup"
              },
              {
                "itemId": 4,
                "quantity": 4,
                "unit": "slices"
              },
              {
                "itemId": 5,
                "quantity": 1,
                "unit": "clove"
              }
            ]
          }
        ],
        "allergens": ["Dairy", "Eggs", "Pork"]
      }



    ]

    return{
      meals
    }
  }
}

</script>

<style scoped>


.wrapper {
  z-index: 0;
  margin-left: 2%;
  margin-right: 2%;
  margin-top: 2%;
  grid-template-columns: repeat(auto-fill, minmax(345px, 1fr));
  grid-row-gap: 30px;
  transition: 0.5s;
}
</style>