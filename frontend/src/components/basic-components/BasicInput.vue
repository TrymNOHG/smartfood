<template>
  <input
      v-bind="$attrs"
      :value="modelValue"
      :placeholder="label"
      @input="$emit('update:modelValue', $event.target.value)"
      class="field"
      :style="{ borderColor: error ? 'red' : ''}"
      aria-invalid='error ? true : null'
  >
  <p
    v-if="error"
    class="errorMessage"
    aria-live="assertive"
    >
    {{ error }}
  </p>
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