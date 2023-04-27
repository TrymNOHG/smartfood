<template>
  <div class="info-window">
    <h2 style="font-weight: bold">{{ $t('item-info') }}</h2>
    <div class="text">
      <h3>Kjøpsdato: {{new Date(item.purchaseDate)
          .toLocaleDateString(undefined, { day: 'numeric', month: 'long', year: 'numeric' }) }}</h3>
      <h3>Utløpsdato: {{new Date(item.expirationDate)
          .toLocaleDateString(undefined, { day: 'numeric', month: 'long', year: 'numeric' }) }}</h3>
      <h3>Pris: {{ item.price }}kr</h3>
    </div>
  </div>
</template>

<script>
export default {
  name: "itemInfo",

  props: {
    item: Object
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
