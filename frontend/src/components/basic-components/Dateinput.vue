<template>
  <div>
    <label v-if="label">{{ label }}</label>
    <input
        v-bind="$attrs"
        v-model="selectedDate"
        type="date"
        :placeholder="label"
        @input="$emit('update:modelValue', selectedDate)"
        class="field"
        :style="{ borderColor: error ? 'red' : ''}"
        aria-invalid='error ? true : null'
    >
    <p v-if="error" class="errorMessage" aria-live="assertive">{{ error }}</p>
  </div>
</template>

<script>
export default {
  props: {
    label: {
      type: String,
      default: ''
    },
    modelValue: {
      type: [String, Number],
      default: ''
    },
    error: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      selectedDate: ''
    }
  },
  watch: {
    modelValue: {
      immediate: true,
      handler(newVal) {
        this.selectedDate = newVal;
      }
    }
  }
}
</script>

<style scoped>
input {
  padding: 5px;
}

p {
  color: red;
  font-size: 10px;
}
</style>