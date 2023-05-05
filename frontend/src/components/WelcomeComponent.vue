<template>
  <div class="welcome-view">
    <img class="logo" src="@/assets/images/smartmat_logo_text.png" :alt="$t('smartmat_logo_text')">
    <div class="buttons">
        <router-link to="/register" class="btn register-btn" aria-label="{{ $t('register') }} button">{{ $t('register') }}</router-link>
        <router-link to="/login" class="btn login-btn" aria-label="{{ $t('login') }} button">{{ $t('login') }}</router-link>

    </div>
  </div>
</template>

<script>
export default {
  name: "WelcomeComponent"
};
</script>

<style scoped>
.welcome-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #31c48d;
}

.logo {
  height: 300px;
  max-width: 100%;
  object-fit: contain;
}

.buttons {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
  max-width: 300px;
  margin-top: 40px;
}

.btn {
  width: 100%;
  height: 40px;
  background: #181818;
  border: none;
  outline: none;
  border-radius: 5px;
  cursor: pointer;
  color: white;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
  text-decoration: none;
  transition: 0.3s;
}

.btn:hover {
  cursor: pointer;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  background-color: #1e2eff;
}

</style>

