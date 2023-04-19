import { defineStore } from 'pinia'
import { getUser } from "@/services/UserService"
import {loadAllCategories, loadMainCategories} from "@/services/CategoryService";
import {filterByFullDesc, loadListingsByCategoryId} from "@/services/ItemService";
import { ref, computed, watch } from "vue";
import {addNewFridge, getAllFridges} from "@/services/FridgeServices";

export const useLoggedInStore = defineStore('user', {

    state: () => ({
        sessionToken: null,
        user: {
            userId: null,
            username: null,
            fullName: null,
            email: null,
            birthDate: null,
            phone: null,
            picture: null,
            role: null,
        },
    }),

    getters: {
        isLoggedIn(){
            return this.sessionToken !== null;
        },
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
    }
});

export const useFridgeStore = defineStore('fridgeStore', {
    state: () => ({
        allFridges: []
    }),

    getters: {
        async fetchFridgesByUsername(username) {
            await getAllFridges(username).then(response => {
                this.allFridges = [];
                for(const fridge of response.data) {
                    const { fridgeId, fridgeName } = fridge
                    this.allFridges.push({fridgeId, fridgeName})
                }
            })
            return this.allFridges;
        }
    },

    actions: {
        async addNewFridgeByFridgeNameAndUsername(username, fridgename) {
            await addNewFridge(fridgename, username);
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