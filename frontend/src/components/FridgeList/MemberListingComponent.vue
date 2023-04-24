<template>
  <div class="list">
    <div v-for="(member, index) in membersList" :key="index" class="member">
      <div class="member-info">
        <img
            :src="memberPictures[index]"
            alt="Member Profile Picture"
            class="member-profile-picture"
        />
        <span class="item-text">{{ member.username }}</span>
      </div>

      <div v-if="!isAddable" class="user-role">
        <span>{{ member.isSuperUser ? $t('super_user')  : $t('limited_user') }}</span>
        <font-awesome-icon
            v-if="member.isSuperUser"
            icon="fa-solid fa-crown"
            class="icon crown-conf-icon"
        />
      </div>

      <div class="actions">
        <font-awesome-icon
            v-if="isAddable"
            icon="fa-solid fa-plus"
            @click="onAddClick(index)"
            class="icon plus-conf-icon"
        />
        <div v-if="isEditable">
          <font-awesome-icon
              v-if="member.username !== loggedInUser"
              icon="fa-solid fa-pen-to-square"
              @click="onEditClick(index)"
              class="icon edit-conf-icon"
          />
          <font-awesome-icon
              icon="fa-solid fa-trash"
              @click="onDeleteClick(index)"
              class="icon delete-icon"
          />
        </div>
      </div>
    </div>
  </div>
</template>



<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import 'sweetalert2/dist/sweetalert2.min.css';
import swal from 'sweetalert2';
import defaultProfilePicture from '@/assets/images/profiledefualt.svg';
import {getProfilePictureById} from "@/services/UserService";
import {string} from "yup";


export default {
    name: "MemberList",
    components: { FontAwesomeIcon },
    props: {
        membersList: {
            type: Array,
            required: true,
        },
        isEditable: Boolean,
        isAddable: Boolean,
        loggedInUser: string
    },
    data() {
        return{
            profilePictures: [],
            defaultProfilePicture,
        }
    },
    computed: {
        memberPictures() {
            return this.membersList.map((member, index) => {
                return this.profilePictures[index] || this.defaultProfilePicture;
            });
        },
    },


    watch: {
        membersList: {
            handler() {
                this.fetchMemberPictures();
            },
            immediate: true
        }
    },

    methods: {
        async fetchMemberPictures() {
          this.profilePictures = [];
            for (const member of this.membersList) {
                try {
                    const response = await getProfilePictureById(member.userId);
                    const imageUrl = URL.createObjectURL(new Blob([response.data], { type: 'image/jpeg' }));
                    this.profilePictures.push(imageUrl);
                } catch (error) {
                    console.error('Error fetching member picture:', error);
                    this.profilePictures.push(this.defaultProfilePicture);
                }
            }
        },

        async onEditClick(index) {
          const username = this.membersList[index].username;
          const {value: role} = await swal.fire({
            title: this.$t('select_role_title'),
            input: 'radio',
            inputValue: this.membersList[index].isSuperUser ? 'super' : 'normal',
            inputOptions: {
              normal: this.$t('limited_user'),
              super: this.$t('super_user')
            },
            inputValidator: (value) => {
              if (!value) {
                return this.$t('role_selection_error');
              }
            },
            showCancelButton: true,
            confirmButtonColor: '#4dce38',
            cancelButtonColor: '#d33',
            confirmButtonText: this.$t('confirm_button'),
            cancelButtonText: this.$t('cancel_button'),
            customClass: {
              container: 'my-swal-dialog-container'
            }
          });
          if (role) {
            const isSuperUser = role === 'super';
            this.$emit("edit-member", username, isSuperUser);
          }
        },


        async onAddClick(index) {
            const username = this.membersList[index].username;
            const { value: role } = await swal.fire({
                title: this.$t('select_role_title'),
                input: 'radio',
                inputValue: 'normal', // Set the default selected option to "normal"
                inputOptions: {
                    normal: this.$t('limited_user'),
                    super: this.$t('super_user')
                },
                inputValidator: (value) => {
                    if (!value) {
                        return this.$t('role_selection_error');
                    }
                },
                showCancelButton: true,
                confirmButtonColor: '#4dce38',
                cancelButtonColor: '#d33',
                confirmButtonText: this.$t('confirm_button'),
                cancelButtonText: this.$t('cancel_button'),
                customClass: {
                    container: 'my-swal-dialog-container'
                }
            });
            if (role) {
                const isSuperUser = role === 'super';
                this.$emit("add-member", username, isSuperUser);
            }
        },


        onDeleteClick(index) {
            swal.fire({
                title: this.$t('confirm_title'),
                text: this.$t('confirm_text'),
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#4dce38',
                cancelButtonColor: '#d33',
                confirmButtonText: this.$t('confirm_button'),
                cancelButtonText: this.$t('cancel_button'),
                customClass: {
                    container: 'my-swal-dialog-container'
                }
            }).then((result) => {
                if (result.isConfirmed) {
                    const username = this.membersList[index].username;
                    const isSuperUser = this.membersList[index].isSuperUser;
                    this.$emit('delete-member',  username, isSuperUser );
                    swal.fire(
                        this.$t('success_message_deleted_user'),
                        '',
                        'success'
                    )
                }
            })
        },
    },
};
</script>

<style scoped>



.list {
    display: flex;
    flex-direction: column;
}

.member {
    height: 60px;
    background-color: #eee;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin: 10px 5px;
    padding: 10px;
    transition: all 0.2s ease-in-out;
    position: relative;
    display: flex;
    justify-content: space-between;
    align-items: center;

}

.member:hover {
    box-shadow: 0 0 5px #ccc;
    transform: scale(1.02);
}

.member-info {
  display: flex;
  align-items: center;
  gap: 10px;
  width: 150px
}


.edit-conf-icon {
    font-size: 1.2rem;
    cursor: pointer;
    margin-right: 10px;
    color: #888;
    transition: color 0.2s ease-in-out;
}


.user-role {
  display: flex;
  align-items: center;
  width: 150px
}

.crown-conf-icon{
  pointer-events: none;
}

.user-role span {
  margin-right: 5px;
}

.actions {
  display: flex;
  align-items: center;
  width: 60px;
  flex-direction: row-reverse;
}

.icon {
  font-size: 1.2rem;
  cursor: pointer;
  margin-right: 10px;
  color: #888;
  transition: color 0.2s ease-in-out;
}

.icon:hover {
  color: #000;
}

.conf:hover {
  color: #4dce38;
}

.delete-icon {
  font-size: 1.2rem;
  cursor: pointer;
  color: #888;
  transition: color 0.2s ease-in-out;
}

.delete-icon:hover {
  color: #f00;
}



.edit-conf-icon:hover {
    color: #000;
}

.conf:hover {
    color: #4dce38;
}

.delete-icon {
    font-size: 1.2rem;
    cursor: pointer;
    color: #888;
    transition: color 0.2s ease-in-out;
}

.delete-icon:hover {
    color: #f00;
}


.member-profile-picture {
    height: 55px;
    width: 55px;
    border-radius: 50%;
    object-fit: cover;
    object-position: center;
}

@media only screen and (max-width: 860px) {
    .list {
        margin: 0;
    }

    .member {
        height: auto;
        padding: 10px;
        margin: 5px 0;
    }

    .member:hover {
        box-shadow: none;
        transform: none;
    }

    .item-text {
        margin-bottom: 5px;
        font-size: 1.2rem;
    }



    .delete-icon {
        font-size: 1.5rem;
        margin-left: 10px;
    }


}

@media only screen and (max-width: 600px) {
    .icons {
        flex-wrap: wrap;
        justify-content: space-between;
        margin-top: 10px;
    }

    .edit-input {
        width: 100%;
    }

    .edit-conf-icon {
        margin-right: 0;
        margin-bottom: 10px;
    }

    .delete-icon {
        margin-left: 0;
    }
}


</style>
