import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPersist from 'pinia-plugin-persist'
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
    faSignature
} from "@fortawesome/free-solid-svg-icons";

/* Imports fr multiple languages */

/* add icons to the library */
library.add(
    faUserCircle,
    faHouse,
    faKitchenSet,
    faShoppingCart,
    faEnvelope,
    faLock,
    faUtensils,
    faUserCircle,
    faPerson,
    faSignature,
)

const pinia = createPinia()
pinia.use(piniaPersist)


createApp(App)
    .use(pinia)
    .use(router)
    .component('font-awesome-icon', FontAwesomeIcon)
    .mount('#app');
