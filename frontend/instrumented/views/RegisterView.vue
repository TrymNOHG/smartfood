<template>

  <div>
    <register-component/>
  </div>

</template>

<script>
import RegisterComponent from "@/components/Authentication/RegisterComponent.vue";

export default {
  name: "RegisterView",
  components: {RegisterComponent}
}
</script>

<style scoped>

div {
  margin-top: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
}


</style>