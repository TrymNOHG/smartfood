<template>
  <h1>{{ $t('fridges') }}</h1>
  <list :items="['Item 1', 'Item 2', 'Item 3']"/>
  <basic-button/>
</template>

<script>
import List from "@/components/basic-components/listingComponent.vue";
import BasicButton from "@/components/basic-components/BasicButton.vue";

export default {
  components: {BasicButton, List}

};
</script>

<style>

</style>