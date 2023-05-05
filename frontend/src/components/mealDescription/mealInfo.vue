<template>
  <div class="meal-info-container">
    <div class="meal-info">


      <h3>{{ $t('serving_size') }}:</h3>
      <span class="serving-size">
        <select v-model="adjustedServingSize" @change="adjustServingSize">
          <option v-for="n in 10" :key="n" :value="n">{{ n }}</option>
        </select>
      </span>
      <h3>{{ $t('cook_time') }}:</h3>
      <span class="cooking-time"> {{ $t(cookingTimeRange)  }}</span>
      <h3>{{ $t('difficulty') }}:</h3>
      <span class="difficulty-stars">{{ difficultyEmojis }}</span>
      <h3>{{ $t('author') }}:</h3>
      <span id="meal-auther">{{ meal.author }}</span>
    </div>
    <div class="allergens-container">
      <h3>{{ $t('allergens') }}:</h3>
      <ul>
        <li v-for="allergen in meal.allergens" :key="allergen.displayName">
          {{ allergen.displayName }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MealInfo',

  components: {},

  props: {
    meal: Object,
  },
  data() {
    return {
      adjustedServingSize: this.meal.servingSize,
    };
  },
  methods: {
    adjustServingSize() {
      this.$emit("serving-size-adjusted", this.adjustedServingSize);
    },
  },

  computed: {
    difficultyEmojis() {
      const maxStars = 5; // Maximum number of stars
      const filledStar = '★';
      const emptyStar = '☆';

      // Calculate the number of filled stars based on the meal's difficulty value
      const filledStars = Math.round(this.meal.difficulty * maxStars / 5);

      // Construct the string of stars
      let stars = '';
      for (let i = 0; i < filledStars; i++) {
        stars += filledStar;
      }
      for (let i = filledStars; i < maxStars; i++) {
        stars += emptyStar;
      }

      return stars;
    },
    cookingTimeRange() {
      const timeRanges = {
        1: 'Under 20 min',
        2: '20 - 40 min',
        3: '40 - 60 min',
        4: 'Over 60 min',
      };
      return timeRanges[this.meal.cookingTime] || 'Unknown';
    },
  },
};
</script>

<style scoped>
.meal-info-container {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}

.meal-info,
.allergens-container {
  padding: 20px;
}

.meal-info{

}


.difficulty-stars {
  font-size: 1.2em;
  color: #f39c12;
}
</style>
