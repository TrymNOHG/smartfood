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
  }
};
</script>

<style scoped>

button {
  width: 100%;
  height: 40px;
  background: #181818;
  border: none;
  outline: none;
  border-radius: 5px;
  cursor: pointer;
  color: white;
}

button:hover {
  cursor: pointer;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  background-color: #1e2eff;
}

</style>
