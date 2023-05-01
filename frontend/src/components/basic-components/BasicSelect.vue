<template>
  <label v-if="label">{{ label }}</label>
  <select
      class="field"
      :value="modelValue"
      v-bind="{
      ...$attrs,
      onChange: ($event) => { $emit('update:modelValue', $event.target.value) }
    }"
  >
    <option
        v-for="option in options"
        :value="option"
        :key="option"
        :selected="option === modelValue"
    >{{ option }}</option>
  </select>
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
    options: {
      type: Array,
      required: true
    }
  }
}
</script>


<style scoped>
.field {
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 6px 10px;
  font-size: 16px;
  width: 100%;
  margin-top: 8px;
}

label {
  display: block;
  font-size: 14px;
  margin-bottom: 4px;
}

</style>