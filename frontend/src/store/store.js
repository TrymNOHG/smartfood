import { defineStore } from 'pinia'
import { getUser } from "@/services/UserService"
import {loadAllCategories, loadMainCategories} from "@/services/CategoryService";
import {filterByFullDesc, loadListingsByCategoryId} from "@/services/ItemService";
import { ref, computed, watch } from "vue";

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

export const useItemStore = defineStore('item', {
    state: () => ({
        item: {
            "username": "Eirik",
            "briefDesc": "Razor computer",
            "fullDesc" : null,
            "address" : "Tjome",
            "county" :  "Vestfold",
            "categoryId" : 2,
            "price" : 3000,
            "thumbnail" : null,
            "keyInfoList" : []
        },
        items: [],
        currentCategoryId: null
    }),

    getters: {
        getItems() {
            return this.items;
        }
    },

    actions: {
        async fetchItemsByCategoryId(categoryId) {
            if(this.currentCategoryId === categoryId) return this.items
            await loadListingsByCategoryId(categoryId)
                .then(response => {
                    this.items = [];

                    for(let {itemId, username, briefDesc, fullDesc,
                            address, county, categoryId, price,
                            listingStatus, thumbnail, keyInfoList} of response.data){

                        thumbnail = this.convertImageBackToUrl(thumbnail);

                        this.items.push({itemId, username, briefDesc, fullDesc,
                            address, county, categoryId, price,
                            listingStatus, thumbnail, keyInfoList})
                    }

                    this.currentCategoryId = categoryId
                }).catch(error => {
                    console.warn('error', error)
                    //TODO: handle error
                })
        },
        nonSyncFetchItemsByCategoryId(categoryId) {
            loadListingsByCategoryId(categoryId)
                .then(response => {
                    this.items = [];

                    for(let {itemId, username, briefDesc, fullDesc,
                        address, county, categoryId, price,
                        listingStatus, thumbnail, keyInfoList} of response.data){

                        thumbnail = this.convertImageBackToUrl(thumbnail);

                        this.items.push({itemId, username, briefDesc, fullDesc,
                            address, county, categoryId, price,
                            listingStatus, thumbnail, keyInfoList})
                    }

                    this.currentCategoryId = categoryId
                }).catch(error => {
                    console.warn('error', error)
                    //TODO: handle error
                })
        },
        convertImageBackToUrl(image) {
            return `data:image/png;base64,${image}`;
        },
        filterItemsBySearchTerm(searchTerm, categoryId){
            filterByFullDesc(searchTerm, categoryId)
                .then(response => {
                    this.items = [];

                    for(let {itemId, username, briefDesc, fullDesc,
                        address, county, categoryId, price,
                        listingStatus, thumbnail, keyInfoList} of response.data){

                        thumbnail = this.convertImageBackToUrl(thumbnail);

                        this.items.push({itemId, username, briefDesc, fullDesc,
                            address, county, categoryId, price,
                            listingStatus, thumbnail, keyInfoList})
                    }

                    this.currentCategoryId = categoryId
                }).catch(error => {
                    console.warn('error', error)
                    //TODO: handle error
                })
        }
    }
});

export const useCategoryStore = defineStore('categoryStore', {
    state: () => ({
        mainCategories: [],
        chosenCategory: null,
        chosenCategoryId: null
    }),

    getters: {
        allCategoryNames(){
            let categoryNames = []
            this.mainCategories.forEach(category => categoryNames.push(category.categoryName))
            return categoryNames;

        },
        getMainCategories() {
            return this.mainCategories;
        }
    },

    actions: {
        async fetchMainCategories() {
            await loadMainCategories().then(response => {
                this.mainCategories = []
                for(const category of response.data) {
                    const { categoryId, categoryName, subCategories } = category
                    this.mainCategories.push({ categoryId, categoryName, subCategories })
                }
            }).catch(error => {
                console.log('error' , error)
            })
        },
        async fetchSubCategoriesByMainId(mainCategoryId) {
            await loadAllCategories(mainCategoryId).then(response => {
                this.categoryList = []
                const { categoryId, categoryName, subCategories } = response.data
                this.categoryList.push({ categoryId, categoryName, "mainCategoryId" : null })
                for(const category of subCategories) {
                    const { categoryId, categoryName, subCategories} = category
                    if(categoryId === mainCategoryId){
                        this.categoryList.push({ categoryId, categoryName, subCategories })
                        console.log(categoryName)
                    }
                }
            })
        },

        getCategoryId(){
            for (let i = 0; i < this.mainCategories.length; i++) {
                if (this.mainCategories[i].categoryName === this.chosenCategory){
                    this.chosenCategoryId = this.mainCategories[i].categoryId;
                    return this.chosenCategoryId;
                }
            }
        },

    }
});


export const useCountyStore = defineStore('countyStore', {
    state: () => ({
        county: {
            countyName: "",
        },
        countyList: [
            'None',
            'Troms og Finnmark',
            'Nordland',
            'Trøndelag',
            'Møre og Romsdal',
            'Vestland',
            'Rogaland',
            'Agder',
            'Vestfold og Telemark',
            'Viken',
            'Oslo',
            'Innlandet'
        ]
    }),

    getters: {
        allCounties(){
            return this.countyList;
        },
    },
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