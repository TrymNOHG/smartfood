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
      },
      {
        "recipeId": 4,
        "recipeName": "Classic Caesar Salad",
        "description": "A classic Caesar salad made with crisp romaine lettuce, crunchy croutons, and a creamy homemade Caesar dressing.",
        "author": "Bon Appétit",
        "servingSize": 4,
        "difficulty": 3,
        "thumbnailLink": "https://media-cdn.tripadvisor.com/media/photo-s/14/7d/00/27/awful-caesar-salad.jpg",
        "cookTime": 15.0,
        "recipeParts": [
          {
            "recipePartId": 1,
            "partTitle": "Prepare the Caesar Dressing",
            "partDescription": "Step 1. In a bowl, whisk together minced garlic, anchovy paste, lemon juice, Dijon mustard, Worcestershire sauce, and egg yolk. Slowly whisk in olive oil until emulsified. Stir in grated parmesan cheese and season with salt and pepper.",
            "recipeItems": [
              {
                "itemId": 1,
                "quantity": 1,
                "unit": "clove"
              },
              {
                "itemId": 2,
                "quantity": 1,
                "unit": "tsp"
              },
              {
                "itemId": 3,
                "quantity": 1,
                "unit": "tbsp"
              },
              {
                "itemId": 4,
                "quantity": 1,
                "unit": "tsp"
              },
              {
                "itemId": 5,
                "quantity": 1,
                "unit": "tsp"
              },
              {
                "itemId": 6,
                "quantity": 1,
                "unit": "yolk"
              },
              {
                "itemId": 7,
                "quantity": 1,
                "unit": "cup"
              },
              {
                "itemId": 8,
                "quantity": 1,
                "unit": "cup"
              }
            ]
          },
          {
            "recipePartId": 2,
            "partTitle": "Assemble the Caesar Salad",
            "partDescription": "Step 2. In a large bowl, toss together chopped romaine lettuce, croutons, and Caesar dressing. Top with shaved parmesan cheese and freshly ground black pepper.",
            "recipeItems": [
              {
                "itemId": 9,
                "quantity": 2,
                "unit": "heads"
              },
              {
                "itemId": 10,
                "quantity": 2,
                "unit": "cups"
              },
              {
                "itemId": 11,
                "quantity": 1,
                "unit": "cup"
              }
            ]
          }
        ],
        "allergens": ["Dairy", "Eggs", "Fish"]
      },
      {
        "recipeId": 6,
        "recipeName": "Croquembouche",
        "description": "A stunning French dessert composed of choux pastry puffs filled with pastry cream, piled into a tall, elegant cone, and bound with threads of caramel.",
        "author": "Julia Child",
        "servingSize": 12,
        "difficulty": 5,
        "thumbnailLink": "https://www.thespruceeats.com/thmb/it5eT0krZrG8y4wBP_0TKR8nRfA=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/a-classic-croquembouche-recipe-1375168-hero-01-462e46ef235d4004bf1e8894ff42426f.jpg",
        "cookTime": 120.0,
        "recipeParts": [
          {
            "recipePartId": 1,
            "partTitle": "Prepare the Choux Pastry",
            "partDescription": "Step 1. Preheat oven to 400°F. In a saucepan, combine water, butter, sugar, and salt. Bring to a boil, then add flour and stir until a dough forms. Cook for a few minutes, then transfer to a bowl and mix in eggs, one at a time.",
            "recipeItems": [
              {
                "itemId": 1,
                "quantity": 1,
                "unit": "cup"
              },
              {
                "itemId": 2,
                "quantity": 8,
                "unit": "tbsp"
              },
              {
                "itemId": 3,
                "quantity": 1,
                "unit": "tsp"
              },
              {
                "itemId": 4,
                "quantity": 0.5,
                "unit": "tsp"
              },
              {
                "itemId": 5,
                "quantity": 1,
                "unit": "cup"
              },
              {
                "itemId": 6,
                "quantity": 4,
                "unit": ""
              }
            ]
          },
          {
            "recipePartId": 2,
            "partTitle": "Bake the Choux Pastry Puffs",
            "partDescription": "Step 2. Pipe the choux pastry onto a baking sheet lined with parchment paper, creating small mounds. Bake for 25-30 minutes, until golden and puffed. Remove from the oven and let cool.",
            "recipeItems": []
          },
          {
            "recipePartId": 3,
            "partTitle": "Prepare the Pastry Cream",
            "partDescription": "Step 3. In a saucepan, heat milk and vanilla bean until steaming. In a separate bowl, whisk together sugar, egg yolks, and cornstarch. Temper the egg mixture with the hot milk, then return to the saucepan and cook until thickened. Remove from heat, add butter, and let cool.",
            "recipeItems": [
              {
                "itemId": 7,
                "quantity": 2,
                "unit": "cups"
              },
              {
                "itemId": 8,
                "quantity": 1,
                "unit": ""
              },
              {
                "itemId": 9,
                "quantity": 0.5,
                "unit": "cup"
              },
              {
                "itemId": 10,
                "quantity": 4,
                "unit": ""
              },
              {
                "itemId": 11,
                "quantity": 3,
                "unit": "tbsp"
              },
              {
                "itemId": 12,
                "quantity": 2,
                "unit": "tbsp"
              }
            ]
          },
          {
            "recipePartId": 4,
            "partTitle": "Assemble the Croquembouche",
            "partDescription": "Step 4. Fill the cooled choux pastry puffs with pastry cream using a piping bag. In a saucepan, make a caramel by heating sugar and water until golden. Carefully dip each filled puff into the caramel, then arrange them into a tall cone shape, using the caramel to bind them together.",
            "recipeItems": [
              {
                "itemId": 13,
                "quantity": 1,
                "unit": "cup"
              },
              {
                "itemId": 14,
                "quantity": 0.25,
                "unit": "cup"
              }
            ]
          },
          {
            "recipePartId": 5,
            "partTitle": "Decorate the Croquembouche",
            "partDescription": "Step 5. Use a fork to drizzle any remaining caramel over the croquembouche, creating fine strands of caramel around the tower. Optionally, decorate with edible flowers or sugar decorations. Let set before serving.",
            "recipeItems": []
          }
        ],
        "allergens": ["Dairy", "Eggs", "Wheat", "Gluten"]
      },
      {
        "recipeId": 7,
        "recipeName": "Beef Wellington",
        "description": "A classic and elegant dish featuring a tender filet mignon wrapped in puff pastry and baked to perfection. Served with a delicious mushroom duxelles and prosciutto.",
        "author": "Gordon Ramsay",
        "servingSize": 6,
        "difficulty": 4,
        "thumbnailLink": "https://whatscookingrr.files.wordpress.com/2016/08/wp-image-1428485514jpeg.jpeg",
        "cookTime": 90.0,
        "recipeParts": [
          {
            "recipePartId": 1,
            "partTitle": "Prepare the Filet Mignon",
            "partDescription": "Step 1. Season the filet mignon generously with salt and pepper. In a hot skillet, sear all sides of the beef until browned. Remove from heat and let cool.",
            "recipeItems": [
              {
                "itemId": 1,
                "quantity": 2,
                "unit": "lb"
              }
            ]
          },
          {
            "recipePartId": 2,
            "partTitle": "Make the Mushroom Duxelles",
            "partDescription": "Step 2. In a food processor, pulse mushrooms until finely chopped. In a skillet, cook the mushrooms with minced shallots and garlic, seasoning with salt and pepper. Cook until all the moisture has evaporated. Remove from heat and let cool.",
            "recipeItems": [
              {
                "itemId": 2,
                "quantity": 1,
                "unit": "lb"
              },
              {
                "itemId": 3,
                "quantity": 2,
                "unit": ""
              },
              {
                "itemId": 4,
                "quantity": 2,
                "unit": "cloves"
              }
            ]
          },
          {
            "recipePartId": 3,
            "partTitle": "Assemble the Beef Wellington",
            "partDescription": "Step 3. Lay out a sheet of plastic wrap and place the prosciutto slices on it, overlapping slightly. Spread the cooled mushroom duxelles evenly over the prosciutto. Place the seared filet mignon in the center and roll the prosciutto around it, using the plastic wrap to help. Chill for 30 minutes.",
            "recipeItems": [
              {
                "itemId": 5,
                "quantity": 6,
                "unit": "slices"
              }
            ]
          },
          {
            "recipePartId": 4,
            "partTitle": "Wrap in Puff Pastry",
            "partDescription": "Step 4. Preheat oven to 425°F. Roll out the puff pastry to a large rectangle, large enough to encase the filet. Remove the plastic wrap from the filet, and place it in the center of the puff pastry. Fold the pastry around the filet, sealing the edges with egg wash. Place the wrapped filet seam-side down on a baking sheet lined with parchment paper. Brush the entire surface with egg wash and score the pastry lightly with a knife.",
            "recipeItems": [
              {
                "itemId": 6,
                "quantity": 1,
                "unit": "lb"
              },
              {
                "itemId": 7,
                "quantity": 1,
                "unit": ""
              }
            ]
          },
          {
            "recipePartId": 5,
            "partTitle": "Bake the Beef Wellington",
            "partDescription": "Step 5. Bake the Beef Wellington in the preheated oven for 40-45 minutes, or until the pastry is golden brown and the internal temperature of the beef reaches 125°F for medium-rare. Remove from the oven and let rest for 10 minutes before slicing and serving.",
            "recipeItems": []
          }
        ],
        "allergens": ["Dairy", "Wheat", "Gluten"]
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