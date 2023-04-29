<template>
  <div class="card" style="overflow-y: auto">
    <div class="icon-wrapper">
      <font-awesome-icon :icon="`fa-solid ${iconName}`" class="icon" />
    </div>
    <h4 class="description"> Percentage Thrown </h4>
    <figure class="stats">
      32%
    </figure>
  </div>
</template>
<script>
export default {
  name: "StatCard",

  props: {
    stats: {
      type: Object
    },
    iconName: {
      type: String,
      required: true,
    }
  }
}
</script>

<style scoped>
.icon {
  font-weight: bold;
  font-size: 150%;
  top: 10%;
}

.icon-wrapper {
  align-self: start;
  color: white;
  background: linear-gradient(to right, #3bd290, #5cb8a5, #8fd6b2);
  width: 18%;
  height: 18%;
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  text-align: center;
}

h4 {
  font-size: 18px;
  font-weight: bold;
  margin-top: 5px;
  color: #3b3b3b;
  text-align: start;
}

.card {
  display: flex;
  flex-direction: column;
  align-content: center;
  background-color: white;
  border-radius: 10px;
  cursor: pointer;
  transition: transform 0.2s ease-in-out;
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
}

.card:hover {
  transform: translateY(-5px);
}

.stats {
  border-radius: 10px;
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  text-align: center;
  margin: 1rem 0 0.5rem;
  color: #3bd290;
  font-size: 2rem;
}
</style>
