import { defineStore } from 'pinia'
import { getUser } from "@/services/UserService"
import {loadAllCategories, loadMainCategories} from "@/services/CategoryService";
import {filterByFullDesc, loadListingsByCategoryId} from "@/services/ItemService";
import { ref, computed, watch } from "vue";
import {addNewFridge, deleteUserFromFridge, getAllFridges, updateFridge} from "@/services/FridgeServices";

export const useLoggedInStore = defineStore('user', {

    state: () => ({
        sessionToken: null,
        user: {
            email: null,
            firstname: null,
            lastname: null,
            username: null,
        },
    }),

    getters: {
        isLoggedIn(){
            return this.sessionToken !== null || localStorage.getItem("sessionToken") !== null;        },
        getUser() {
            return this.user;
        },
        getSessionToken() {
            if (this.sessionToken === null) return localStorage.getItem("sessionToken")
            return this.sessionToken;
        }
    },

    actions: {
        setSessionToken(sessionToken) {
            this.sessionToken = sessionToken;
            if (localStorage.getItem("sessionToken") === null) localStorage.setItem("sessionToken", sessionToken)
        },
        async fetchUser() {
            await getUser()
                .then(response => {
                    // const { data : {userId, username, fullName, email, birthDate, phone, picture, role}} = response
                    // this.user = {userId, username, fullName, email, birthDate, phone, picture, role}
                    this.user = response
                }).catch(error => {
                    console.warn('error', error)
                    //TODO: handle error
                })
        },
        logout() {
            this.sessionToken = null;
            localStorage.removeItem("sessionToken");
            this.user = {
                email: null,
                firstname: null,
                lastname: null,
                username: null,
            };
        }
    }
});

export const useFridgeStore = defineStore('fridgeStore', {
    state: () => ({
        allFridges: [{
            "fridgeId": null,
            "fridgeName": null
        }],
        currentFridge: [{
            "fridgeId": null,
            "fridgeName": null,
        }]
    }),

    getters: {
        getCurrentFridge: (state) => {
            return state.currentFridge
        }
    },

    actions: {
        async addNewFridgeByFridgeNameAndUsername(fridgename) {
            await addNewFridge(fridgename);
        },
        async fetchFridgesByUsername(username) {
            await getAllFridges(username).then(response => {
                this.allFridges = []
                for(const fridge of response.data.fridgeDTOS) {
                    const { fridgeId, fridgeName } = fridge
                    this.allFridges.push({ fridgeId, fridgeName })
                }
            })
            return this.allFridges;
        },
        async deleteUserFromFridgeByDTO(fridgeUserDTO){
            await deleteUserFromFridge(fridgeUserDTO);
        },
        async updateFridgeNameByDTO(fridgeDTO){
            await updateFridge(fridgeDTO)
        },
        // Define a mutation to update the value of currentFridge
        setCurrentFridge(state, fridge) {
            state.currentFridge = fridge
        }
    }   
});


export const useImageStore = defineStore('imageStore', {
    state: () => ({
        imageToSend: []
    }),

    getters: {
        test(){
            return this.imageToSend.at(0);
        }
    },

    actions: {
        addImage(newImage){
            this.imageToSend.unshift(newImage)
        }
    }
});