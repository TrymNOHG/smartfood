<template>
    <div class="fridge-wrapper">
        <div class="form-box login">
            <h2>{{ $t("register") }}</h2>
            <form @submit.prevent="submit" :class="{ 'has-errors': hasErrors }" role="form">
                <div class="input-box">
          <span class="icon" aria-hidden="true">
            <font-awesome-icon icon="fa-solid fa-signature"/>
            <span class="sr-only">{{ $t("first_name_icon") }}</span>
          </span>
                    <input
                            type="text"
                            required
                            v-model.trim="firstName"
                            name="firstName"
                            id="firstName"
                            aria-labelledby="firstNameLabel"
                    />
                    <label id="firstNameLabel">{{ $t("first_name") }}</label>
                    <div v-if="errors['firstName']" class="error" role="alert" aria-describedby="firstName">
                        {{ $t(errors["firstName"]) }}
                    </div>
                </div>

                <div class="input-box">
          <span class="icon" aria-hidden="true">
            <font-awesome-icon icon="fa-solid fa-signature"/>
            <span class="sr-only">{{ $t("last_name_icon") }}</span>
          </span>
                    <input
                            type="text"
                            required
                            v-model.trim="lastName"
                            name="lastName"
                            id="lastName"
                            aria-labelledby="lastNameLabel"
                    />
                    <label id="lastNameLabel">{{ $t("last_name") }}</label>
                    <div v-if="errors['lastName']" class="error" role="alert" aria-describedby="lastName">
                        {{ $t(errors["lastName"]) }}
                    </div>
                </div>

                <div class="input-box">
          <span class="icon" aria-hidden="true">
            <font-awesome-icon icon="fa-solid fa-person"/>
            <span class="sr-only">{{ $t("username_icon") }}</span>
          </span>
                    <input
                            type="text"
                            required
                            v-model.trim="username"
                            name="username"
                            id="username"
                            aria-labelledby="usernameLabel"
                    />
                    <label id="usernameLabel">{{ $t("username") }}</label>
                    <div v-if="errors['username']" class="error" role="alert" aria-describedby="username">
                        {{ $t(errors["username"]) }}
                    </div>
                </div>

                <div class="input-box">
          <span class="icon" aria-hidden="true">
            <font-awesome-icon icon="fa-solid fa-envelope"/>
            <span class="sr-only">{{ $t("email_icon") }}</span>
          </span>
                    <input
                            type="email"
                            required
                            v-model.trim="email"
                            name="email"
                            id="email"
                            aria-labelledby="emailLabel"
                    />
                    <label id="emailLabel">{{ $t("email") }}</label>
                    <div v-if="errors['email']" class="error" role="alert" aria-describedby="email">
                        {{ $t(errors["email"]) }}
                    </div>
                </div>

                <div class="input-box">
  <span class="icon" aria-hidden="true">
    <font-awesome-icon icon="fa-solid fa-lock"/>
    <span class="sr-only">{{ $t("password_icon") }}</span>
  </span>
                    <input
                            type="password"
                            required
                            v-model.trim="password"
                            name="password"
                            id="password"
                            aria-labelledby="passwordLabel"
                    />
                    <label id="passwordLabel">{{ $t("password") }}</label>
                    <div v-if="errors['password']" class="error password-err" role="alert" aria-describedby="password">
                        {{ $t(errors["password"]) }}
                    </div>
                </div>

                <div class="input-box">
  <span class="icon" aria-hidden="true">
    <font-awesome-icon icon="fa-solid fa-lock"/>
    <span class="sr-only">{{ $t("confirm_password_icon") }}</span>
  </span>
                    <input
                            type="password"
                            required
                            v-model.trim="confirmPassword"
                            name="confirmPassword"
                            id="confirmPassword"
                            aria-labelledby="confirmPasswordLabel"
                    />
                    <label id="confirmPasswordLabel">{{ $t("confirm_password") }}</label>
                    <div
                            v-if="combinedErrors['confirm_password']"
                            class="error password-err"
                            role="alert"
                            aria-describedby="confirmPassword"
                    >
                        {{ $t(combinedErrors["confirm_password"]) }}
                    </div>
                </div>
                <h5
                        v-if="submitMessage"
                        id="submit-message"
                        aria-describedby="login-form"
                >
                    {{ $t(submitMessage) }}
                </h5>
                <BasicButton type="submit" @click="submit" :button-text="$t('register')"/>
                <div class="login-register">
                    <p>
                        {{ $t("already_have_account") }}
                        <router-link to="/login" class="register-link"
                        >{{ $t("login") }}
                        </router-link>
                    </p>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
import * as yup from "yup";
import { useField, useForm, } from "vee-validate";
import { registerUser } from "@/services/UserService";
import { computed, ref } from "vue";
import { useLoggedInStore } from "@/store/store";
import router from "@/router/router";
import { RouterLink } from 'vue-router'
import BasicButton from "@/components/basic-components/BasicButton.vue";

export default {
  name: "RegisterComponent",
  components: {
    RouterLink,
    BasicButton,
  },
  setup() {
    const store = useLoggedInStore();
    const submitMessage = ref("");

    const combinedErrors = computed(() => {
      return {
        ...errors.value,
        confirm_password:
          password.value !== confirmPassword.value
            ? "passwords_do_not_match"
            : null,
      };
    });

    const validationSchema = yup.object({
      firstName: yup.string().required("first_name_error"),
      lastName: yup.string().required("last_name_error"),
      username: yup.string().required("user_error"),
      email: yup.string().email("wrong_email_error").required("email_error"),
      password: yup
        .string()
        .required("password_error")
        .min(8, "password_length"),

      confirmPassword: yup
        .string()
        .required("password_length")
        .test("passwords-match", "passwords_do_not_match", function (value) {
          return this.parent.password === value;
        }),
    });

    const { handleSubmit, errors, setFieldTouched, setFieldValue } = useForm({
      validationSchema,
      initialValues: {
        fullName: "",
        username: "",
        email: "",
        password: "",
        confirmPassword: "",
      },
    });

    const { value: fullName } = useField("fullName");
    const { value: firstName } = useField("firstName");
    const { value: lastName } = useField("lastName");
    const { value: username } = useField("username");
    const { value: email } = useField("email");
    const { value: password } = useField("password");
    const { value: confirmPassword } = useField("confirmPassword");

    const submit = handleSubmit(async () => {
      if (!(password.value === confirmPassword.value)) {
        console.log(
          "passwords do not match: ",
          password.value,
          " and ",
          confirmPassword.value
        );
        return;
      }

      // Handle registration form submission
      const userData = {
        username: username.value,
        password: password.value,
        firstName: firstName.value,
        lastName: lastName.value,
        email: email.value,
      };
      await registerUser(userData)
        .then(async (response) => {
          if (response !== undefined) {
            store.setSessionToken(response.data.token);
            await store.fetchUser();
            await router.push("/fridges");
          }
        })
        .catch((error) => {
          submitMessage.value = "register_error";
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
      combinedErrors,
      fullName,
      firstName,
      lastName,
      username,
      email,
      password,
      errors,
      submit,
      validationSchema,
      hasErrors,
      setFieldTouched,
      setFieldValue,
      submitMessage,
      store,
      confirmPassword,
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

h5 {
  margin-top: -20px;
  margin-bottom: 5px;
}

.fridge-wrapper .form-box {
  width: 90%;
  padding: 40px;
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
  margin: -15px 0 15px;
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
