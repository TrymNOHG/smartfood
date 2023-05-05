<template>

  <div>
    <login-component/>
  </div>

</template>

<script>
import LoginComponent from "@/components/Authentication/LoginComponent.vue";

export default {
  name: "LoginView",
  components: {LoginComponent}
}
</script>

<style scoped>

div {

  margin-top: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
}


</style>