<template>
  <div class="wrapper">
    <div class="form-box login">
      <h2>{{ $t('register') }}</h2>
      <form @submit.prevent="submit" :class="{ 'has-errors': hasErrors }">
        <div class="input-box">
          <span class="icon"><font-awesome-icon icon="fa-solid fa-signature" /></span>
          <input type="text" required v-model.trim="fullName" name="fullName">
          <label>{{ $t('full_name') }}</label>
          <div v-if="errors['fullName']" class="error">{{ $t(errors['fullName']) }}</div>
        </div>
        <div class="input-box">
          <span class="icon"><font-awesome-icon icon="fa-solid fa-person" /></span>
          <input type="text" required v-model.trim="username" name="username">
          <label>{{ $t('username') }}</label>
          <div v-if="errors['username']" class="error">{{ $t(errors['username']) }}</div>
        </div>
        <div class="input-box">
          <span class="icon"><font-awesome-icon icon="fa-solid fa-envelope" /></span>
          <input type="email" required v-model.trim="email" name="email">
          <label>{{ $t('email') }}</label>
          <div v-if="errors['email']" class="error">{{ $t(errors['email']) }}</div>
        </div>
        <div class="input-box">
          <span class="icon"><font-awesome-icon icon="fa-solid fa-lock" /></span>
          <input type="password" required v-model.trim="password" name="password">
          <label>{{ $t('password') }}</label>
          <div v-if="errors['password']" class="error">{{ $t(errors['password']) }}</div>
        </div>
        <button type="submit" @click="submit">{{ $t('register') }}</button>
        <div class="login-register">
          <p>{{ $t('already_have_account') }}<a href="/login" class="register-link">{{ $t('login') }}</a></p>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import * as yup from 'yup';
import { useField, useForm } from "vee-validate";
import {registerUser} from "@/services/UserService";

export default {
  name: "RegisterComponent",
  setup() {
    const validationSchema = yup.object({
      fullName: yup
        .string()
        .required("full_name_error"),
      username: yup
        .string()
        .required("user_error"),
      email: yup
        .string()
        .email("wrong_email_error")
        .required("email_error"),
      password: yup
        .string()
        .required("password_error")
        .min(8, "password_length"),
    });

    const { handleSubmit, errors, setFieldTouched, setFieldValue } = useForm({
      validationSchema,
      initialValues: {
        fullName: "",
        username: "",
        email: "",
        password: "",
      },
    });

    const { value: fullName } = useField("fullName");
    const { value: username } = useField("username");
    const { value: email } = useField("email");
    const { value: password } = useField("password");

    const submit = handleSubmit(async () => {
      // Handle registration form submission
      console.log("Registering")
      const userData = {
        username: username.value,
        password: password.value,
        firstName: 'first name',
        lastName: 'last name',
        email:  email.value,
        picture: null,
      }
      console.log(userData)
      await registerUser(userData).then(async response => {
        if (response !== undefined) {
          store.setSessionToken(response.data.token)
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
        alert(error.body) //TODO: format!!!!
        console.warn('error1', error) //TODO: add exception handling
      })
    });

    const hasErrors = () => {
      const keys = Object.keys(errors.value);
      return keys.length > 0;
    };

    return {
      fullName,
      username,
      email,
      password,
      errors,
      submit,
      validationSchema,
      hasErrors,
      setFieldTouched,
      setFieldValue,
    };
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
  margin-top: 10px;
}

.error {
  font-size: 12px;
  position: relative;
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

@media screen and (max-width: 800px) {
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