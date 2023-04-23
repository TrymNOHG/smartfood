<template>
    <div class="list">
        <div v-for="(member, index) in membersList" :key="index" class="member">
            <img
                :src="memberPictures[index]"
                alt="Member Profile Picture"
                class="member-profile-picture"
            />
            <span class="item-text">{{ member.username }}</span>

            <div v-if="!isAddable">
                <span>{{ member.isSuperUser ? 'Super user ' : 'Limited user ' }}</span>
                <font-awesome-icon
                    v-if="member.isSuperUser"
                    icon="fa-solid fa-crown"
                    class="icon crown-conf-icon"
                />
            </div>


            <font-awesome-icon
                v-if="isAddable"
                icon="fa-solid fa-plus"
                @click="onAddClick(index)"
                class="icon plus-conf-icon"
            />
            <div v-if="isEditable" >
                <font-awesome-icon
                    v-if="!isEditing[index]"
                    icon="fa-solid fa-pen-to-square"
                    @click="onEditClick(index)"
                    class="icon edit-conf-icon"
                />
                <font-awesome-icon
                    v-else
                    icon="fa-solid fa-circle-check"
                    @click="confirmEdit(index)"
                    class="icon edit-conf-icon conf"
                />
                <font-awesome-icon
                    icon="fa-solid fa-trash"
                    @click="onDeleteClick(index)"
                    class="icon delete-icon"
                />
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


export default {
    name: "MemberList",
    components: { FontAwesomeIcon },
    props: {
        membersList: {
            type: Array,
            required: true,
        },
        isEditable: Boolean,
        isAddable: Boolean
    },
    data() {
        return{
            profilePictures: [],
            defaultProfilePicture,
            isEditing: false,
            editingFridge: {
                editingIndex: null,
                fridgeName: "",
            }
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
//todo issearched, can edit , crown ,trenger id i stor for self delete, kan superuser sette seg selv til begrenset, redigere seg selv? if id=ownid set alt til false?? HOVER HVIS IS ADABLE TRYKK PÅ FOR Å ADDE DA

    methods: {
        async fetchMemberPictures() {
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

        onEditClick(index) {
            this.editingFridge.editingIndex = index;
            this.editingFridge.fridgeName = this.fridgeList[index].fridgeName;
            this.isEditing = Array(this.fridgeList.length).fill(false);
            this.isEditing[index] = true;
        },

        confirmEdit(index) {
            this.isEditing[index] = false;
            const editedFridge = this.editingFridge;
            if (editedFridge.fridgeName !== this.fridgeList[index].fridgeName) {
                this.$emit("update-item", index, editedFridge.fridgeName);
            }
        },
        async onAddClick(index) {
            const username = this.membersList[index].username;
            const { value: role } = await swal.fire({
                title: this.$t('select_role_title'),
                input: 'radio',
                inputValue: 'normal', // Set the default selected option to "normal"
                inputOptions: {
                    normal: this.$t('normal_user'),
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
                        this.$t('success_message'),
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

.link {
    text-decoration: none;
    color: inherit;
}

.link:hover {
    text-decoration: underline;
}

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

.link-text {
    flex-grow: 1;
    color: #039be5;
    text-decoration: underline;
    cursor: pointer;
}

.link-text:hover {
    color: #0277bd;
}

.icons {
    display: flex;
    align-items: center;
}

.edit-conf-icon {
    font-size: 1.2rem;
    cursor: pointer;
    margin-right: 10px;
    color: #888;
    transition: color 0.2s ease-in-out;
}

.edit-input {
    border: none;
    outline: none;
    font-size: 16px;
    font-weight: 400;
    color: #333;
    background-color: #fff;
    border-radius: 3px;
    padding: 5px 10px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
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
.swal2-modal {
    background-color: white;
    flex-direction: column;
    position: fixed;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    padding: 20px;
    border-radius: 5px;
    z-index: 100;
    display: flex;
    width: 60%;
    max-width: 500px;
}

.swal2-title {
    background-color: white;
    margin-top: 5%;
}

.member-profile-picture {
    height: 55px;
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

    .edit-input {
        font-size: 1.2rem;
    }

    .icons {
        margin-top: 5px;
        justify-content: flex-end;
    }

    .delete-icon {
        font-size: 1.5rem;
        margin-left: 10px;
    }

    .swal2-modal {
        width: 80%;
        height: 50%;
        max-width: none;
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
