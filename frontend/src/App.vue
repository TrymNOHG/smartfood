<template>
  <header v-if="!isOnRootPage">
    <router-link to="/fridges">
      <img src="@/assets/images/smartmat.png" alt="Logo">
    </router-link>
    <h1 class="matprat-title" :class="{ 'centered': isOnAuthPage }">{{ $t('matsmart') }}</h1>
    <nav :class="{ 'center-profile': !hasCurrentFridge }">
      <ul v-if="!isOnAuthPage">
        <li v-if="hasCurrentFridge">
          <RouterLink to="/dinner" >
            <span class="icon">
              <font-awesome-icon icon="fa-solid fa-utensils" />
            </span>
            <span class="text">{{ $t('dinner') }}</span>
          </RouterLink>
        </li>
        <li v-if="hasCurrentFridge">
          <RouterLink to="/fridge" >
            <span class="icon">
              <font-awesome-icon icon="fa-solid fa-kitchen-set" />
            </span>
            <span class="text">{{ $t('fridge') }}</span>
          </RouterLink>
        </li>
        <li v-if="hasCurrentFridge">
          <RouterLink to="/statistics" v-if="hasCurrentFridge">
            <span class="icon">
              <font-awesome-icon icon="fa-solid fa-chart-pie" />
            </span>
            <span class="text">{{ $t('statistics') }}</span>
          </RouterLink>
        </li>
        <li v-if="hasCurrentFridge">
          <RouterLink to="/cart" v-if="hasCurrentFridge">
            <span class="icon">
              <font-awesome-icon icon="fa-solid fa-cart-shopping" />
            </span>
            <span class="text">{{ $t('shopping_cart') }}</span>
          </RouterLink>
        </li>
        <li>
          <RouterLink to="/profile">
            <span class="icon">
              <font-awesome-icon icon="fa-solid fa-circle-user" />
            </span>
            <span class="text">{{ $t('profile') }}</span>
          </RouterLink>
        </li>
      </ul>
      <div class="language" @click="changeLanguage()">{{language}}</div>
    </nav>

  </header>
  <div class="current-fringe" v-if="!isOnAuthPage && !isOnRootPage">
    <div class="break-line"/>
    <div class="name-display" >
      <h1 class="fridge-name">
        <router-link to="/fridge" class="link-name" v-if="hasCurrentFridge">
          {{ currentFridge.fridgeName }}
        </router-link>
        <router-link to="/fridges" class="link-name" v-else>
          {{ $t('select_fridge') }}
        </router-link>
      </h1>
      
      <router-link class="change-button" to="/fridges" >
        <img src="@/assets/images/exit_change_fridge.png" style="max-height: 100%">
      </router-link>
    </div>
  </div>
  <RouterView class="router-view-container"/>
</template>


<script>
import {computed, ref, watch} from "vue";
import {useRoute} from "vue-router";
import i18n from "@/locales/i18n";
import {useFridgeStore} from "./store/store";

export default {
  setup() {
    const route = useRoute();

    const fridgeStore = useFridgeStore();
    const currentFridge = computed(() => fridgeStore.getCurrentFridge);
    const hasCurrentFridge = computed(() => fridgeStore.hasCurrentFridge);

    const isOnRootPage = computed(() => {
      return route.path === "/";
    });

    const isOnAuthPage = computed(() => {
      return route.path === "/register" || route.path === "/login";
    });


    const changeLanguage = () => {
      if (language.value === "NO") {
        language.value = "EN";
        i18n.global.locale = "en";
      } else {
        i18n.global.locale = "no";
        language.value = "NO";
      }
    };

    const language = ref("NO");

    return {
      isOnAuthPage,
      language,
      changeLanguage,
      isOnRootPage,
      hasCurrentFridge,
      currentFridge,

    };
  },
};
</script>



<style scoped>
.break-line {
  height: 7px;
  background-color: black;
}

.name-display {
  text-align: start;
  background-color: #31c48d;
  color: white;
  width: 100%;
  height: 50px;
  text-shadow: black 1px 1px 2px;
  display: flex;
  justify-content: space-evenly;
}

.fridge-name {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 50px;
  margin-left: 28%;
}

.link-name{
  text-decoration: none;
  color: white;
}



.change-button {
  text-align: center;
  color: black;
  height: 35px;
  margin-top: 0.5%;
  margin-right: 5%;
  text-shadow: white 0 0 0;
  font-weight: 500;
  border-radius: 5px;
  text-decoration: none;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

:root {
  color: white;
}

header {
  background-color: #31c48d;
  line-height: 1.5;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  z-index: 1;
}

header img {
  height: 100px;
  width: auto;
  margin-right: 300px;
}

.language {
  color: white;
  margin-left: 20px;
  cursor: pointer;
}

h1 {
  color: white;
  margin: 0;
  font-size: 1.8rem;
  letter-spacing: 3px;
}

.matprat-title {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}

.matprat-title.centered {
  left: 50%;
}

nav {
  display: flex;
  align-items: center;
  max-width: 100%;
  overflow-x: hidden;
  overflow-y: hidden;
}

nav ul {
  display: flex;
  list-style: none;
}

nav ul li {
  position: relative;
  width: 80px;
  height: 70px;
  z-index: 1;
}

nav ul li a {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 100px;
  text-align: center;
  font-weight: 500;
}

nav ul li a .icon {
  position: relative;
  display: block;
  line-height: 75px;
  font-size: 1.5em;
  text-align: center;
  transition: 0.5s;
  color: white;
}

nav ul li:hover a .icon {
  transform: translateY(-5px);
}

nav ul li a .text {
  position: absolute;
  font-weight: 400;
  font-size: 0.75em;
  letter-spacing: 0.05em;
  transition: 0.5s;
  opacity: 0;
  color: white;
  transform: translateY(20px);
}

nav ul li:hover a .text {
  opacity: 1;
  transform: translateY(15px);
}

@media only screen and (max-width: 860px) {
  header {
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 80px;
  }

  .fridge-name {
    margin-left: 30%;
    font-size: 18px;
  }

  .change-button {
    top: 10%;
    font-size: 0.7rem;
  }

  .router-view-container {
    padding-bottom: 80px;
  }

  header img {
    display: none;
  }

  h1 {
    font-size: 1rem;
    letter-spacing: 2px;
  }

  nav.center-profile ul {
    justify-content: center;
  }

  nav.center-profile ul li {
    flex-grow: 0;
  }

  nav {
    display: flex;
    align-items: center;
    justify-content: space-evenly;
    position: fixed;
    bottom: 0;
    width: 100%;
    height: 70px;
    background-color: #31c48d;
    box-shadow: 0 -2px 6px rgba(0, 0, 0, 0.1);
  }

  nav ul {
    display: flex;
    justify-content: space-between;
    width: 80%;
  }

  nav ul li {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 50px;
    height: 50px;
  }

  .language {
    display: none;
  }

  nav ul li a {
    font-size: 0.6rem;
    color: white;
    text-align: center;
    text-decoration: none;
  }

  nav ul li a .icon {
    margin-bottom: 5px;
    font-size: 2em;
  }

  nav ul li.active a {
    color: #fcfbfb;
    background-color: #218838;
    border-radius: 50%;
  }

  nav ul li.active a .icon {
    color: #fcfbfb;
  }
}

@media only screen and (max-width: 1055px) and (min-width: 860px) {
  header img {
    height: 40px;
  }

  h1 {
    font-size: 2rem;
  }

  nav ul li {
    width: 50px;
    height: 50px;
  }

  nav ul li a .icon {
    line-height: 55px;
    font-size: 1.5em;
  }

  nav ul li a .text {
    font-size: 0.6em;
  }

}
</style>


