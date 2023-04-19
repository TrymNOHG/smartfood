<template>
  <button>
    {{ buttonText }}
  </button>
</template>

<script>
export default {
  name: "BasicButton",

  props: {
    buttonText: {
      type: String,
      default: "Button"
    },
    buttonClass: {
      type: String,
      default: "basic-button"
    }
  }
};
</script>

<style scoped>
button {
  background-color: #0F1CF3;
  color: white;
  width: 100px;
  height: 33px;
  border-radius: 5px;
  font-size: 12px;
  border: none;
  outline: none;
  transition: all 0.2s ease;
}

button:hover {
  cursor: pointer;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  background-color: #1e2eff;
}
</style>
