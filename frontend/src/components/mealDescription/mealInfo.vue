<template>
  <div>
    <div class="meal-info">
      <h3>{{ $t('serving_size') }}: {{ meal.servingSize }}</h3>
      <h3>{{ $t('cook_time') }}: {{ meal.cookTime }} {{ $t('minutes') }}</h3>
      <h3>{{ $t('difficulty') }}: <span class="difficulty-stars">{{ difficultyEmojis }}</span></h3>
    </div>
    <div class="allergens">
      <h3>{{ $t('allergens') }}:</h3>
      <ul>
        <li v-for="allergen in meal.allergens" :key="allergen">{{ allergen }}</li>
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

  computed: {
    difficultyEmojis() {
      const emojis = ['😇', '😏', '😐', '😓', '💀'];
      const index = this.meal.difficulty - 1;

      if (index >= 0 && index < emojis.length) {
        return emojis[index];
      } else {
        return ''; // Return an empty string if the difficulty level is out of range
      }
    },
  },
};
</script>
<style scoped>
.meal-info,
.recipe-parts,
.allergens {
  padding: 20px;
}

.meal-info h3,
.allergens h3 {
  margin: 1em 0;
}

.recipe-parts h3 {
  margin-top: 1em;
}

.item-info {
  margin: 1em 0;
}

.difficulty-stars {
  font-size: 1.2em;
  color: #f39c12;
}
</style>