<template>
  <component
      v-for="option in options"
      :key="option.value"
      :is="vertical ? 'div' : 'span'"
      :class="{
      'horizontal': !vertical
    }"
  >
    <BasicRadio
        :label="option.label"
        :value="option.value"
        :modelValue="modelValue"
        :name="name"
        @update:modelValue="$emit('update:modelValue', $event)"
    />
  </component>
</template>

<script>
import BasicRadio from "@/components/basicInputComponents/BasicRadio.vue";

export default {
  components: {BasicRadio},
  props: {
    options: {
      type: Array,
      required: true
    },
    name: {
      type: String,
      required: true
    },
    modelValue: {
      type: [String, Number],
      required: true
    },
    vertical: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style scoped>
.horizontal {
  margin-right: 20px;
}
</style>