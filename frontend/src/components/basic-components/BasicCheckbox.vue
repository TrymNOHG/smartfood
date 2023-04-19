<template>
  <input
      type="checkbox"
      :checked="modelValue"
      @change="$emit('update:modelValue', $event.target.checked)"
      class="field"
      :style="{ borderColor: error ? 'red' : ''}"
      aria-invalid='error ? true : null'
  />
  <p v-if="error" class="errorMessage" aria-live="assertive">{{ error }}</p>
</template>

<script>
export default {
  props: {
    label: {
      type: String,
      default: ''
    },
    modelValue: {
      type: Boolean,
      default: false
    },
    error: {
      type: String,
      default: ''
    }
  }
}
</script>