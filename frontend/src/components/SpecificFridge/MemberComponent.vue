<template>
    <div class="container">
        <member-list :members-list="memberList"  class="listing" @update-item="onUpdateItem" @delete-item="onDeleteItem" />
        <div @click="changeModal()" class="container_button">+</div>
    </div>

    <div v-if="showModal" class="modal">
        <div class="modal-content">
            <h3>{{ $t('searchMembersTitle') }}</h3>
            <div class="search-input">
                <input type="text" v-model="searchText" placeholder="Enter username" />
                <basic-button
                    :button-text="$t('searchMembersButtonText')"
                    type="submit"
                    @click="searchMembers"
                />
            </div>
            <member-list
                v-if="searchResults.length > 0"
                :members-list="searchResults"
                class="listing"
            />
            <span class="close" @click="changeModal()">X</span>
        </div>
    </div>
</template>

<script>
import List from "@/components/FridgeList/listingComponent.vue";
import BasicInput from "@/components/basic-components/BasicInput.vue";
import BasicButton from "@/components/basic-components/BasicButton.vue";
import {useFridgeStore, useLoggedInStore} from "@/store/store";
import {onMounted, ref} from "vue";
import MemberList from "@/components/FridgeList/MemberListingComponent.vue";
import {loadUsersByFridgeId} from "@/services/FridgeServices";
import {searchUserByUsername} from "@/services/UserService";

export default {
    components: {MemberList, BasicButton, BasicInput, List },
    props: {
        fridgeId: String
    },
    setup(props) {
        const memberList = ref([]);
        const searchText = ref("");
        const searchResults = ref([]);


        async function fetchUsers() {
            try {
                const response = await loadUsersByFridgeId(props.fridgeId);
                memberList.value = response.data.memberInfo;
                console.log(response.data)
            } catch (error) {
                console.error("Error fetching users:", error);
            }
        }

        async function searchMembers() {
            try {
                const response = await searchUserByUsername(searchText.value);
                searchResults.value = response.data;
            } catch (error) {
                console.error("Error searching members:", error);
            }
            console.log(searchResults)
        }

        onMounted(fetchUsers);



        return {
            searchMembers,
            memberList,
            searchText,
            searchResults,
        };
    },

    data() {
        return {
            showModal: false,
            newFridgeName: "",
        };
    },
    methods: {

        async onUpdateItem(index, name) {

        },

        async onDeleteItem(index) {

        },

        changeModal() {
            this.showModal = this.showModal === false;
        },

        async addNewFridge() {
            this.showModal = false;
        },
    },
};
</script>

<style scoped>

.listing {
    margin-top: 5%;
}

.list li {
    margin: 5px;
    padding: 5px;
    list-style: none;
    background-color: #f0f0f0;
}

.container_button {
    text-align: center;
    height: 60px;
    background-color: #eee;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin: 10px 5px;
    cursor: pointer;
    transition: all 0.2s ease-in-out;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 3em;
    font-weight: bold;
}

.container_button:hover {
    box-shadow: 0 0 5px #ccc;
    transform: scale(1.02);
}

h3 {
    font-weight: bold;
}

.modal {
    flex-direction: column;
    position: fixed;
    left: 50%;
    top: 50%;
    background-color: #fff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    padding: 20px;
    border-radius: 5px;
    z-index: 100;
    display: flex;
    transform: translate(-50%, -50%);
    width: 60%;
    max-width: 500px;
}
.modal-content {
    flex-grow: 1;
    overflow-y: auto;
}


.close {
    position: absolute;
    top: -25px;
    right: 2px;
    font-size: 1.5em;
    font-weight: bold;
    color: #ccc;
    cursor: pointer;
    transition: all 0.2s ease-in-out;
    width: 30px;
    height: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 50%;
}

label {
    margin-top: 5%;
}

.close:hover{
    background-color: lightgray;
    color: black;
    transform: scale(1.1);
}

form {
    display: flex;
    flex-direction: column;
}

input, textarea {
    margin-bottom: 10px;
    padding: 5px;
}

.modal button {
    align-self: center;
}

</style>
