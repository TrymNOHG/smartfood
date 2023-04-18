<template>
  <div class="wrapper">
    <div class="form-box login">
      <h2>Login</h2>
      <form action="#">
        <div class="input-box">
          <span class="icon"><font-awesome-icon icon="fa-solid fa-envelope" /></span>
          <input type="email" required>
          <label>Email</label>
        </div>
        <div class="input-box">
          <span class="icon"><font-awesome-icon icon="fa-solid fa-lock" /></span>
          <input type="password" required>
          <label>Password</label>
        </div>
        <div class="remember-forgot">
          <a href="#">Forgot Password?</a>
        </div>
        <button type="submit">Login</button>
        <div class="login-register">
          <p>Don't have an account? <a href="/register" class="register-link">Register</a></p>
        </div>
      </form>
    </div>
  </div>
</template>
<script>
export default {
  name: "LoginComponent"
}
</script>
<style scoped>

.wrapper {
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  max-width: 400px;
  background: transparent;
  border: 2px solid rgba(255, 255, 255, 0.5);
  border-radius: 20px;
  backdrop-filter: blur(20px);
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.5);
}

.wrapper .form-box {
  width: 90%;
  padding: 40px;
}

.form-box {
  font-size: 1rem;
  text-align: center;
}

h2{
  font-weight: bold;
}

.input-box {
  position: relative;
  width: 100%;
  height: 50px;
  border-bottom: 2px solid black;
  margin: 30px 0;
  transition: transform 0.5s ease-in-out;
}

label {
  position: absolute;
  top: 50%;
  left: 0;
  transform: translateY(-200%);
  font-weight: 400;
  transition: border-radius 0.5s ease-in-out;
  pointer-events: none;
}


input {
  width: 100%;
  height: 100%;
  background: transparent;
  border: none;
  outline: none;
  font-weight: 500;
  padding: 0 40px 0 5px;
}

.icon {
  position: absolute;
  right: 8px;
  line-height: 57px;
}

.remember-forgot {
  font-weight: 400;
  margin:  -15px 0 15px;
  display: flex;
  justify-content: center;
}

button {
  width: 100%;
  height: 40px;
  background: #181818;
  border: none;
  outline: none;
  border-radius: 5px;
  cursor: pointer;
  color: white;
}

@media screen and (max-width: 500px) {
  .wrapper .form-box {
    padding: 20px;
  }

  .input-box {
    margin: 1.5rem 0;
  }

  .form-box {
    font-size: 0.9em;
  }

  h2 {
    font-size: 1.2em;
  }
}

</style>