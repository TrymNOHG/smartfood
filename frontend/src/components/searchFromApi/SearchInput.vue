<template>
    <input
        v-bind="$attrs"
        :value="modelValue"
        :placeholder="label"
        @input="$emit('update:modelValue', $event.target.value)"
        class="form-control"
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

  .form-control {
    padding: 10px 20px;
    margin-top: 10px;
    color: #333;
    font-size: 18px;
    font-weight: 500;
    border: 3px solid #555;
    box-shadow: none;
    min-height: 60px;
    border-radius: 50px 0 0 50px;
  }

  .form-control:focus {
    outline: none;
    border-color: transparent;
  }

  </style>