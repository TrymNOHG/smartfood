<template>
  <div>
    <input
        v-bind="$attrs"
        v-model="formattedPhoneNumber"
        type="tel"
        :placeholder="'Phone Number'"
        @input="$emit('update:modelValue', formattedPhoneNumber)"
        class="field"
        :style="{ borderColor: error ? 'red' : ''}"
        aria-invalid='error ? true : null'
    >
    <label v-if="label">{{ label }}</label>
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
      formattedPhoneNumber: '',
      unformattedPhoneNumber: ''
    }
  },
  watch: {
    modelValue: {
      immediate: true,
      handler(newVal) {
        this.formattedPhoneNumber = this.formatPhoneNumber(newVal);
        this.unformattedPhoneNumber = newVal;
      }
    }
  },
  methods: {
    formatPhoneNumber(phoneNumber) {
      phoneNumber = phoneNumber.replace(/\D/g, '');
      if (phoneNumber.startsWith('47')) {
        phoneNumber = phoneNumber.replace(/^47/, '');
      }
      return `${phoneNumber.slice(0, 2)} ${phoneNumber.slice(2, 4)} ${phoneNumber.slice(4, 6)} ${phoneNumber.slice(6, 8)}`;
    }
  },
  computed: {
    isValid() {
      return /^\d{8}$/.test(this.unformattedPhoneNumber);
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