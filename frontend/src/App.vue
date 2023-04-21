<template>
  <header>
    <img src="@/assets/images/smartmat.png" alt="Logo">
    <h1>{{ $t('matsmart') }}</h1>
    <nav>
      <ul>
        <li>
          <RouterLink to="/dinner">
            <span class="icon">
              <font-awesome-icon icon="fa-solid fa-utensils" />
            </span>
            <span class="text">{{ $t('dinner') }}</span>
          </RouterLink>
        </li>
        <li>
          <RouterLink to="/fridges">
            <span class="icon">
              <font-awesome-icon icon="fa-solid fa-kitchen-set" />
            </span>
            <span class="text">{{ $t('fridge') }}</span>
          </RouterLink>
        </li>
        <li>
          <RouterLink to="/statistics">
            <span class="icon">
              <font-awesome-icon icon="fa-solid fa-chart-pie" />
            </span>
            <span class="text">{{ $t('statistics') }}</span>
          </RouterLink>
        </li>
        <li>
          <RouterLink to="/cart">
            <span class="icon">
              <font-awesome-icon icon="fa-solid fa-cart-shopping" />
            </span>
            <span class="text">{{ $t('shopping_cart') }}</span>
          </RouterLink>
        </li>
        <li>
          <RouterLink to="/login">
            <span class="icon">
              <font-awesome-icon icon="fa-solid fa-circle-user" />
            </span>
            <span class="text">{{ $t('profile') }}</span>
          </RouterLink>
        </li>
        <div class="language" @click="changeLanguage()">{{language}}</div>
      </ul>
    </nav>
  </header>
  <RouterView/>
</template>


<script>
import {computed} from "vue";
import i18n from "@/locales/i18n";

export default {
  data() {
    return {
      language: "NO",
    }
  },

  methods: {
    changeLanguage() {
      if (this.language === "NO") {
        this.language = "EN";
        i18n.global.locale = "en"
      } else {
        i18n.global.locale = "no"
        this.language = "NO"
      }
    }
  }
}
</script>



<style scoped>
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

nav {
  display: flex;
  align-items: center;
  max-width: 100%;
  overflow: hidden;
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

  header img {
    height: 40px;
    margin-right: 0;
    margin-bottom: 10px;
  }

  h1 {
    font-size: 1rem;
    letter-spacing: 2px;
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