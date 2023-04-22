<template>

</template>

<script>
import {useRoute} from 'vue-router';
import { useFridgeStore } from '../store/store';

export default {

  name: "FridgeView",
  setup(){
    const route = useRoute();
    useFridgeStore().setCurrentFridgeById(route.params.id)

    return {

    }
  }
}
</script>

<style scoped>

</style>