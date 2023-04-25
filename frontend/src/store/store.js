import { defineStore } from 'pinia'
import { getUser } from "@/services/UserService"
import {addNewFridge, deleteUserFromFridge, getAllFridges, updateFridge} from "@/services/FridgeServices";
import UniqueId from '../features/UniqueId';
import {addItemToFridge, getItemsFromFridge, deleteItemFromFridge} from "@/services/ItemService";
import {ref} from "vue";
import {checkSuperUserStatus} from "../services/UserService";

const storeUUID = UniqueId();

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

    persist: {
        storage: sessionStorage,
    },

    getters: {
        isLoggedIn(){
            return this.sessionToken !== null},
        getUser() {
            return this.user;
        },
        getSessionToken() {
            return this.sessionToken;
        }
    },

    actions: {
        setSessionToken(sessionToken) {
            this.sessionToken = sessionToken;
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
            useFridgeStore().removeCurrentFridge()
        }
    }
});

export const useFridgeStore = defineStore('fridgeStore', {
    state: () => ({
        allFridges: [],
        currentFridge: {
            "fridgeId": null,
            "fridgeName": "kjøleskap",
        },
        isSuperUser: false,
    }),

    persist: {
        storage: sessionStorage,
    },

    getters: {
        getCurrentFridge(){
            return this.currentFridge
        },
        hasCurrentFridge() {
            return this.currentFridge.fridgeId !== null;
        },
        getIsSuperUser() {
            return this.isSuperUser;
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
        async setCurrentFridgeById(fridgeId) {
            for(let fridge of this.allFridges) {
                if(fridge.fridgeId == fridgeId) {
                    this.currentFridge = fridge;
                    this.isSuperUser = await this.checkSuperUserStatus(fridgeId)
                    console.log(this.isSuperUser)
                    return;
                }
            }
        },

        async checkSuperUserStatus(fridgeId) {
            try {
                const response = await checkSuperUserStatus(fridgeId);
                return response.data
            } catch (error) {
                console.error(error);
            }
        },

        removeCurrentFridge() {
            this.currentFridge = {
                "fridgeId": null,
                "fridgeName": "kjøleskap",
            }
            this.isSuperUser = false;
        },

    }
});


export const useItemStore = defineStore('itemStore', {
    state: () => ({
        allItems: [],
        currentItem: null,
    }),

    getters: {
        getCurrentItem(){
            console.log(this.currentItem)
            return this.currentItem;
        }
    },

    actions: {
        async addItemToFridgeById(fridgeId, itemDTO) {
            await addItemToFridge(itemDTO, fridgeId);
        },

        setCurrentItem(item) {
            this.currentItem = item;
        },

        async deleteItemByNameIdStoreQuantity(itemRemoveDTO){
            await deleteItemFromFridge(itemRemoveDTO);
        },

        async fetchItemsFromFridgeById(fridgeId) {
            await getItemsFromFridge(fridgeId).then(response => {
                this.allItems = []
                this.allItems = response.data;
            })
            return this.allItems;
        }
    },
});


