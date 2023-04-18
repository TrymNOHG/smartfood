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
import * as yup from 'yup'
import {useField, useForm } from "vee-validate";
import {useLoggedInStore} from "@/store/store";
import {ref} from "vue";
import router from "@/router/router";
import {loginUser} from "@/services/UserService";
import { useStorage } from 'vue3-storage';
export default {
  name: "LoginComponent",
  setup () {
    const submitMessage = ref('');
    const storage = useStorage();
    const store = useLoggedInStore();
    const validationSchema = yup.object({
      username: yup.string()
          .required('Username is Required'),
      password: yup.string()
          .required('Password required')
          .min(8)
    })
    const { handleSubmit, errors } = useForm({ validationSchema });
    const { value: username } = useField('username');
    const { value: password } = useField('password')
    const submit = handleSubmit(async () => {
      const userLoginDTO = {
        "username": username.value,
        "password": password.value
      }
      await loginUser(userLoginDTO).then(async response => {
        if (response !== undefined) {
          store.setSessionToken(response.data.token)
          console.log(store.getSessionToken)
          await store.fetchUser()
          submitMessage.value = "Registration Successful";
          setTimeout(() => {
            submitMessage.value = "";
          }, 3000);
          await router.push("/");
        } else {
          submitMessage.value = "Something went wrong. Please try again later.";
          setTimeout(() => {
            submitMessage.value = "";
          }, 3000);
        }
      }).catch(error => {
        console.warn('error', error)
      })
    });
    return {
      password,
      username,
      errors,
      submit,
      validationSchema,
      submitMessage,
    }
  },
  computed: {
    hasErrors() {
      return !this.validationSchema.isValidSync({
        username: this.username,
        password: this.password,
      });
    },
  },
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