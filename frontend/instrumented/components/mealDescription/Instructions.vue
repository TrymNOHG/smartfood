<template>
  <div class="instructions">
    <h2>{{ $t('instructions') }}</h2>
    <div v-for="(instruction, index) in formattedInstructions" :key="index">
      <h4>{{ $t('step') }} {{ index + 1 }}: <span v-html="instruction.instruction"></span></h4>
      <img v-if="instruction.imageLink" :src="instruction.imageLink" :alt="`${$t('step')} ${index + 1}`" />
    </div>
  </div>
</template>




<script>
export default {
  name: 'Instructions',

  computed: {
    formattedInstructions() {
      return this.$props.instructions.map((instruction) => {
        const formattedText = instruction.instruction.replace(/\*\*(.*?)\*\*/g, '<span class="bold-text">$1</span>');
        return { ...instruction, instruction: formattedText };
      });
    },
  },

  props: {
    instructions: Array,
  },
};
</script>

<style scoped>
.instructions {
  padding: 20px;
}

.instructions h4 {
  margin: 1em 0;
}

.instructions img {
  max-width: 100%;
  height: auto;
  margin: 1em 0;
}

:deep .bold-text {
  font-weight: bold;
}
</style>