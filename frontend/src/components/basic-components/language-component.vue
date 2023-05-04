<template>
  <select v-model="currentLanguage" @change="changeLanguage">
    <option v-for="(language, index) in supportedLanguages" :key="index" :value="language">{{ language }}</option>
  </select>
</template>

<script>
import {defineComponent, ref, watch} from 'vue'
import { useI18n } from 'vue-i18n'
import {useLoggedInStore} from "@/store/store";

export default defineComponent({
  setup() {
    const { locale } = useI18n()
    const userStore = useLoggedInStore()

    const supportedLanguages = ['NO', 'EN']
    const currentLanguage = ref(locale.value)

    const changeLanguage = () => {
      locale.value = currentLanguage.value;
    };

    watch(
        () => locale.value,
        (newLocale) => {
          currentLanguage.value = newLocale
        }
    )

    return {
      supportedLanguages,
      currentLanguage,
      changeLanguage
    }
  }
})
</script>

<style>
select {
  background-color: #f2f2f2;
  color: #333;
  padding: 8px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

option {
  background-color: #f2f2f2;
  color: #333;
  padding: 8px;
  cursor: pointer;
}

select:hover {
  background-color: #e6e6e6;
}

select:focus {
  outline: none;
  box-shadow: 0 0 3px #ddd;
}
</style>