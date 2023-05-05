<template>
  <div class="info-window">
    <h2 style="font-weight: bold">{{ $t('item-info') }}</h2>
    <div class="text">
      <h3>{{ $t('buy_date') }}: {{new Date(item.purchaseDate)
          .toLocaleDateString('nb-NO', { day: 'numeric', month: 'long', year: 'numeric' }) }}</h3>
      <h3>{{ $t('expire_date') }}: {{new Date(item.expirationDate)
          .toLocaleDateString('nb-NO', { day: 'numeric', month: 'long', year: 'numeric' }) }}</h3>
      <h3>{{ $t('price') }} {{ item.price }}kr</h3>
      <h3>{{ $t('amount') }}: {{ item.amount}} {{ item.unit }}</h3>
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
  display: flex;
  justify-content: space-around;
  flex-direction: column;

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

h3 {
  margin: 1%;
}

.text {
  display: flex;
  flex-direction: column;
  align-items: start;
  justify-content: space-evenly;
  margin: 2.5%;
}

</style>
