<template>
  <div class="info-window">
    <h2 style="font-weight: bold">Informasjon om varen</h2>
    <div class="text">
      <h3>Kjøpsdato: {{ Kjøpsdato }}</h3>
      <h3>Utløpsdato: {{ Utløpsdato }}</h3>
      <h3>Pris: {{ price }}kr</h3>
    </div>
  </div>
</template>

<script>
import {number} from "yup";

export default {
  name: "itemInfo",

  props: {
    itemId: number
  },
};
</script>

<style scoped>

.info-window {
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 20px;
  box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
  height: 100%;
}

.info-window h2 {
  margin-top: 0;
}

.text {
  margin: 2.5%;
}

.info-window .dates {
  display: flex;
  justify-content: space-between;
}
</style>
