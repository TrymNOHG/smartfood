<template>
  <div class="fridge-wrapper">
    <div class="form-box login">
      <h2>{{ $t("login") }}</h2>
      <form @submit.prevent="submit" :class="{ 'has-errors': hasErrors }">
        <div class="input-box">
          <span class="icon"
            ><font-awesome-icon icon="fa-solid fa-envelope"
          /></span>
          <input
            type="text"
            required
            v-model.trim="email"
            name="email"
            id="email-input"
            aria-required="true"
            aria-labelledby="email-input"
          />
          <label for="email-input">{{ $t("email") }}</label>
          <div v-if="errors && errors['email']" class="error">
            {{ $t(errors["email"]) }}
          </div>
        </div>
        <div class="input-box">
          <span class="icon"
            ><font-awesome-icon icon="fa-solid fa-lock"
          /></span>
          <input
            type="password"
            required
            v-model.trim="password"
            name="password"
            id="password-input"
            aria-required="true"
            aria-labelledby="password-input"
          />
          <label for="password-input">{{ $t("password") }}</label>
          <div v-if="errors && errors['password']" class="error">
            {{ $t(errors["password"]) }}
          </div>
        </div>

        <h5 v-if="submitMessage" class="submit-message">
          {{ $t(submitMessage) }}
        </h5>



        <BasicButton type="submit" @click="submit" id="login-btn" :button-text="$t('login')" />
        <div class="login-register">
          <p>
            {{ $t("dont_have_account") }}
            <router-link to="/register" class="register-link">{{
              $t("register")
            }}</router-link>
          </p>
        </div>
      </form>
    </div>
  </div>
</template>
<script>
import * as yup from "yup";
import { useField, useForm } from "vee-validate";
import { useLoggedInStore } from "@/store/store";
import { ref } from "vue";
import router from "@/router/router";
import { loginUser } from "@/services/UserService";
import { useStorage } from "vue3-storage";
import { RouterLink } from 'vue-router'
import BasicButton from "@/components/basic-components/BasicButton.vue";


export default {
  name: "LoginComponent",
  components: {
    BasicButton,
    RouterLink
  },
  setup() {
    const submitMessage = ref("");
    const storage = useStorage();
    const store = useLoggedInStore();
    const validationSchema = yup.object({
      email: yup.string().required("email_error"),
      password: yup
        .string()
        .required("error_password")
        .min(8, "password_length"),
    });
    const { handleSubmit, errors, setFieldTouched, setFieldValue } = useForm({
      validationSchema,
      initialValues: {
        email: "",
        password: "",
      },
    });
    const { value: email } = useField("email");
    const { value: password } = useField("password");
    const submit = handleSubmit(async () => {
      const request = {
        email: email.value,
        password: password.value,
      };
      await loginUser(request)
        .then(async (response) => {
          if (response !== undefined) {
            store.setSessionToken(response.data.token);
            await store.fetchUser();
            await router.push("/fridges");
          }
        })
        .catch((error) => {
          submitMessage.value = 'login_error';
          setTimeout(() => {
            submitMessage.value = "";
          }, 2000);
          console.warn("error", error);
        });
    });
    const hasErrors = () => {
      const keys = Object.keys(errors.value);
      return keys.length > 0;
    };

    return {
      password,
      email,
      errors,
      submit,
      validationSchema,
      submitMessage,
      hasErrors,
      setFieldTouched,
      setFieldValue,
    };
  },
};
</script>

<style scoped>
.fridge-wrapper {
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

.fridge-wrapper .form-box {
  width: 90%;
  padding: 40px;
}
h5{
  margin-top: -40px;
}
.form-box {
  font-size: 1rem;
  text-align: center;
}

h2 {
  font-weight: bold;
}

.input-box {
  position: relative;
  width: 100%;
  height: 50px;
  border-bottom: 2px solid black;
  margin: 30px 50px 50px 0;
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

input {
  width: 100%;
  height: 100%;
  background: transparent;
  border: none;
  outline: none;
  font-weight: 500;
  padding: 0 40px 0 5px;
}

.has-errors input[type="email"],
.error {
  font-size: 12px;
  margin: 5px;
  position: relative;
}

.icon {
  position: absolute;
  right: 8px;
  line-height: 57px;
}

.remember-forgot {
  font-weight: 400;
  margin: 12px -15px 0 15px;
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
  .fridge-wrapper .form-box {
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
