<template>
<h1>Oops!</h1>
  <h3>{{ $t('notFound') }}</h3>
  <h3>{{ $t('trythispage') }}</h3>
  <button @click="$router.push('/')">Home Page</button>
  <hr>
  <img src="@/assets/pagenotfound.jpg">
</template>

<script>

export default {
  name: "NotFound",
  props: {
    resource: {
      type: String,
      required: true,
      default: 'page'
    }
  }
}
</script>


<style scoped>

button {
  border-width: 2px;
  border-color: black;
  padding: 10px 20px;
  background-color: #FFD700;
  color: black;
  margin: 10px;
}

button:active {
  background-color: white;
  color: #1E293B;
}

button:hover {
  color: #1E293B !important;
  background-color: white !important;
}
</style>