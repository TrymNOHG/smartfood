import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedState from "pinia-plugin-persistedstate"

import App from './App.vue'
import './assets/main.css'
import router from "../src/router/router";
/* Icons imports */
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import {
    faHouse,
    faKitchenSet,
    faShoppingCart,
    faEnvelope,
    faLock,
    faUtensils,
    faUserCircle,
    faPerson,
    faSignature,
    faPieChart,
    faPenToSquare,
    faTrash,
    faCircleCheck,
    faPlus,
    faCrown,
    faCheck, faTableCells, faList, faFilter,
    faReceipt, faChartSimple, faCoins, faFlag,
} from "@fortawesome/free-solid-svg-icons";

/* Imports fr multiple languages */
import i18n from "@/locales/i18n";

/* add icons to the library */
library.add(
    faUserCircle, faHouse,
    faKitchenSet, faShoppingCart,
    faEnvelope, faLock,
    faUtensils, faUserCircle,
    faPerson, faSignature,
    faPieChart, faPenToSquare,
    faTrash, faCircleCheck,
    faPlus, faCrown, faCheck,
    faTableCells, faList, faFilter,
    faReceipt, faChartSimple, faCoins, faFlag
)

const pinia = createPinia();

pinia.use(piniaPluginPersistedState);

createApp(App)
  .use(pinia)
  .use(i18n)
  .use(router)
  .component("font-awesome-icon", FontAwesomeIcon)
  .mount("#app");
