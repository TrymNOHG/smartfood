  <template>
    <div class="user-profile">

      <div class="profile-picture-container">
        <img :src="profileData.picture" alt="Profile Picture" class="profile-picture" />
        <div class="profile-picture-overlay">
          <img class="editIcon" src="../assets/images/edit.svg" alt="Change" @click="changePicture" />
          <input id="picture" type="file" @change="onPictureChange" style="display: none;" />
          <img
              v-show="profileData.picture !== defaultPicture"
              class="deleteIcon"
              src="../assets/images/delete-icn.svg"
              alt="Delete"
              @click="onDeletePicture"
          />
        </div>
      </div>
      <div v-if="!isEditing && !isChangingPassword" class="info-section">
        <p id="fullName"><strong>{{ $t("name") }}:</strong> {{ profileData.firstName + " " + profileData.lastName}}</p>
        <p id="username"><strong>{{ $t("username") }}:</strong> {{ profileData.username }}</p>
        <p id="email"><strong>{{ $t("email") }}:</strong> {{ profileData.email }}</p>
        <language-component/>
        <button class="basic-button edit-btn" @click="isEditing = true">{{ $t('edit') }}</button>
        <button class="basic-button change-password-btn" @click="isChangingPassword = true">{{ $t('changePassword') }}</button>
        <button class="basic-button logout-btn" @click="logout">{{ $t('logout') }}</button>
      </div>
      <form v-else-if="isEditing" @submit.prevent="updateUserProfile" :class="{ 'has-errors': hasErrors }">        <div class="input-box">
          <label for="firstName">{{ $t("first_name") }}</label>
          <input
              id="firstName"
              v-model="profileData.firstName"
              type="text"
              required
          />
          <span class="icon"
          ><font-awesome-icon icon="fa-solid fa-signature"
          /></span>
        </div>
        <div class="input-box">
          <label for="lastName">{{ $t("last_name") }}</label>
          <input
              id="lastName"
              v-model="profileData.lastName"
              type="text"
              required
          />
          <span class="icon"
          ><font-awesome-icon icon="fa-solid fa-signature"
          /></span>
        </div>
        <div class="input-box">

          <label for="username">{{ $t("username") }}</label>
          <input
              id="username"
              v-model="profileData.username"
              type="text"
              required
          />
          <span class="icon"
          ><font-awesome-icon icon="fa-solid fa-person"
          /></span>
        </div>
        <div class="input-box">
          <label for="email">{{ $t("email") }}</label>
          <input
              id="email"
              v-model="profileData.email"
              type="email"
              required
          />
          <span class="icon"
          ><font-awesome-icon icon="fa-solid fa-envelope"
          /></span>
        </div>
        <div class="button-container">
          <button class="basic-button" type="submit">{{ $t('save_changes') }}</button>
          <button class="basic-button" type="button" @click="isEditing = false">{{ $t('cancel') }}</button>
        </div>
      </form>
      <form v-else-if="isChangingPassword" @submit.prevent="updatePassword" :class="{ 'has-errors': hasErrors }">
        <div class="input-box">
          <label for="currentPassword">{{ $t("current_password") }}</label>
          <input id="currentPassword" v-model="passwordData.oldPassword" type="password" required />
          <span class="icon"><font-awesome-icon icon="fa-solid fa-key" /></span>
          <span class="icon"><font-awesome-icon icon="fa-solid fa-lock"/></span>
        </div>
        <div class="input-box">
          <label for="newPassword">{{ $t("new_password") }}</label>
          <input id="newPassword" v-model="passwordData.newPassword" type="password" required />
          <span class="icon"><font-awesome-icon icon="fa-solid fa-lock"/></span>
        </div>
        <div class="input-box">
          <label for="confirmPassword">{{ $t("confirm_password") }}</label>
          <input id="confirmPassword" v-model="passwordData.confirmPassword" type="password" required />
          <span class="icon"><font-awesome-icon icon="fa-solid fa-lock"/></span>
        </div>
        <div class="button-container">
          <button class="basic-button" type="submit" @click="updatePassword">{{ $t('save_changes') }}</button>
          <button class="basic-button" type="button" @click="isChangingPassword = false">{{ $t('cancel') }}</button>
        </div>
      </form>
    </div>
  </template>

  <script>
  import BasicButton from '@/components/basic-components/BasicButton.vue';
  import { ref, watch } from 'vue';
  import { useLoggedInStore } from '@/store/store';
  import { useRouter } from 'vue-router';
  import { updateUser } from '@/services/UserService';
  import {deleteProfilePicture, getProfilePicture, updateProfilePicture, updateUserPassword} from "@/services/UserService";
  import defaultProfilePicture from '@/assets/images/profiledefualt.svg';
  import Swal from "sweetalert2";
  import {useI18n} from "vue-i18n";
  import i18n from "@/locales/i18n";
  import LanguageComponent from "@/components/basic-components/language-component.vue";

  export default {
    name: 'UserProfile',
    components: {
      LanguageComponent,
      BasicButton,
    },
    setup() {
      const { t } = useI18n(i18n)
      const userStore = useLoggedInStore();
      const router = useRouter();

      const isEditing = ref(false);
      const isChangingPassword = ref(false);

      const passwordData = ref({
        oldPassword: '',
        newPassword: '',
        confirmPassword: '',
      });

      userStore.fetchUser();
      const user = userStore.getUser;
      console.log(userStore.isLoggedIn);
      console.log(userStore.getSessionToken);

      const defaultPicture = defaultProfilePicture;

      const profileData = ref({ ...user.data });

      watch(user, () => {
        profileData.value = { ...user };
      });

      const updateUserProfile = async () => {
        try {
          await updateUser(profileData.value)
              .then((response) => {
                userStore.setSessionToken(response.data);
              });
          await userStore.fetchUser();
          await Swal.fire({
            title: t('user-updated'),
            icon: 'success',
            confirmButtonText: 'Ok',
          });
          isEditing.value = false;
        } catch (e) {
          console.warn(e)
        }
      };

      const updatePassword = async () => {
        if (passwordData.value.newPassword === passwordData.value.confirmPassword) {
          try {
            await updateUserPassword(passwordData.value);
            isChangingPassword.value = false;
            await Swal.fire({
              title: t('password-updated'),
              icon: 'success',
              confirmButtonText: 'Ok',
            });
          } catch (error) {
            await Swal.fire({
              title: t('password-failed'),
              icon: 'error',
              confirmButtonText: 'Ok',
            });
          }
        }else {
          await Swal.fire({
            title: t('password-match-fail'),
            icon: 'error',
            confirmButtonText: 'Ok',
          });
        }
      };


      const fetchProfilePicture = async () => {
        try {
          console.log("fetching profile picture")
          const response = await getProfilePicture();
          const imageUrl = URL.createObjectURL(new Blob([response.data], { type: 'image/jpeg' }));
          profileData.value.picture = imageUrl;
        } catch (error) {
          console.error('Error fetching profile picture:', error);
          // Handle the error (e.g., display an error message)
            profileData.value.picture = defaultPicture;
        }
      };

      fetchProfilePicture();

      const changePicture = () => {
        const input = document.getElementById("picture");
        input.click();
      };

      const onPictureChange = async (event) => {
        const file = event.target.files[0];
        try {
          await updateProfilePicture(file);
          const response = await getProfilePicture();
          const imageUrl = URL.createObjectURL(new Blob([response.data], {type: 'image/jpeg'}));
          profileData.value.picture = imageUrl;
        } catch (error) {
          console.error('Error updating profile picture:', error);
          // Handle the error (e.g., display an error message)
        }
      };


      const onDeletePicture = async () => {
        try {
          await deleteProfilePicture();
          profileData.value.picture = defaultProfilePicture;
        } catch (error) {
          console.error('Error deleting profile picture:', error);
          // Handle the error (e.g., display an error message)
        }
      };


      const logout = () => {
        userStore.logout();
        router.push('/');
      };


      return {
        profileData,
        updateUserProfile,
        onPictureChange,
        logout,
        isEditing,
        onDeletePicture,
        changePicture,
        defaultPicture,
        isChangingPassword,
        passwordData,
        updatePassword,
      };
    },
  };
  </script>

  <style scoped>
  .user-profile {
    max-width: 600px;
    margin: 0 auto;
    padding: 1rem;
    background-color: #f8f8f8;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  }

  h1 {
    text-align: center;
    margin-bottom: 2rem;
  }

  .profile-picture-container {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 1rem;
  }

  .profile-picture {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    object-fit: cover;
  }

  .profile-picture-overlay {
    position: absolute;
    width: 150px;
    height: 150px;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
    align-items: center;
    border-radius: 50%;
    opacity: 0;
    transition: opacity 0.3s;
    cursor: pointer;
  }

  .profile-picture-overlay img {
    width: 24px;
    height: 24px;
    cursor: pointer;
  }

  .profile-picture-overlay img.editIcon {
    filter: invert(100%) sepia(100%) saturate(0%) hue-rotate(300deg) brightness(100%) contrast(100%);
  }

  .profile-picture-overlay img.deleteIcon {
    filter: brightness(0) saturate(100%) invert(100%) sepia(100%) saturate(0%) hue-rotate(300deg) brightness(100%) contrast(100%);
  }

  .profile-picture-container:hover .profile-picture-overlay,
  .profile-picture-overlay:focus {
    opacity: 1;
  }

  .profile-picture-overlay input[type="file"] {
    display: none;
  }

  .info-section {
    display: grid;
    gap: 0.5rem;
  }

  .user-profile p {
    font-size: 1.1rem;
    margin-bottom: 0.25rem;
  }

  .user-profile form {
    display: grid;
    gap: 0.5rem;
  }

  .user-profile form label {
    font-size: 1.1rem;
    display: block;
    margin-bottom: 0.25rem;
  }



  .input-box {
    position: relative;
    margin-bottom: 0.5rem;
  }

  .input-box input {
    background-color: transparent;
    border: none;
    border-bottom: 2px solid black;
    outline: none;
    width: 100%;
    padding: 0.5rem 0;
    font-size: 1rem;
  }

  .input-box label {
    font-size: 1.1rem;
    display: block;
    margin-bottom: 0.25rem;
  }

  .input-box .icon {
    position: absolute;
    top: calc(50% + 21px);
    right: 0;
    transform: translateY(-50%);
    pointer-events: none;
  }

  .button-container {
    display: flex;
    justify-content: space-between;
    gap: 0.5rem;
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

  @media (max-width: 768px) {
    .user-profile {
      padding: 1rem;
    }
  }

  @media (max-width: 500px) {
    h1 {
      font-size: 1.5rem;
    }

    .profile-picture {
      width: 100px;
      height: 100px;
    }

    .profile-picture-overlay {
      width: 100px;
      height: 100px;
    }
  }
  </style>
