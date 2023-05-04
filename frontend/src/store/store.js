import {defineStore} from 'pinia'
import {checkSuperUserStatus, getUser} from "@/services/UserService"
import {
    addNewFridge, deleteNotification,
    deleteUserFromFridge,
    getAllFridges,
    getNotifications, removeBorder,
    updateFridge
} from "@/services/FridgeServices";
import UniqueId from '../features/UniqueId';
import {addItemToFridge, deleteItemFromFridge, getItemsFromFridge, filterFridge} from "@/services/ItemService";
import {
    addItemStats,
    deleteItemStats, getFridgeMoneyStats,
    getFridgePercentageStats,
    getUserMoneyStats,
    getUserPercentageStats
} from "@/services/StatsService";
import {keys} from "@dafcoe/vue-collapsible-panel";


const storeUUID = UniqueId();

export const useLoggedInStore = defineStore('user', {

    state: () => ({
        sessionToken: null,
        user: {
            userId: null,
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
        },
    },

    actions: {
        setSessionToken(sessionToken) {
            this.sessionToken = sessionToken;
        },
        async fetchUser() {
            await getUser()
                .then(response => {
                    // const { data : {userId, username, fullName, email, picture, role}} = response
                    // this.user = {userId, username, fullName, email, picture, role}
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
            localStorage.removeItem("currentLanguage")
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
       async fetchNotifications (fridgeId) {
           let notifications = []
           await getNotifications(fridgeId).then((response) => {
               for (const notification in response.data) {
                   const {name, expirationDate} = notification
                   notifications.push({name, expirationDate})
               }
           })
           return notifications;
        },

        async deleteNotificationUsingId (notification, fridgeId) {
           await deleteNotification(notification, fridgeId)
        },

        async removeBorderForNotification(notification, fridgeId){
           await removeBorder(notification, fridgeId)
        },

        async addNewFridgeByFridgeNameAndUsername(fridgeName) {
            await addNewFridge(fridgeName);
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
                if(fridge.fridgeId === fridgeId) {
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

    persist: {
        storage: sessionStorage,
    },

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

        async statAddItemToFridge(statAddItemToFridgeDTO) {
            await addItemStats(statAddItemToFridgeDTO);
        },

        async statAddItemListToFridge(statItemListDTO) {
            for (const statItemListDTOElement of statItemListDTO) {
                console.log(statItemListDTOElement)
                await addItemStats(statItemListDTOElement)
            }
        },


        setCurrentItem(item) {
            this.currentItem = item;
        },

        async deleteItemByStats(statDeleteFromFridgeDTO) {
            await deleteItemStats(statDeleteFromFridgeDTO)
        },

        async deleteItemByNameIdStoreAmount(itemRemoveDTO){
            await deleteItemFromFridge(itemRemoveDTO);
        },

        async fetchItemsFromFridgeById(fridgeId) {
            await getItemsFromFridge(fridgeId).then(response => {
                this.allItems = []
                this.allItems = response.data;
            })
            return this.allItems;
        },

        async filterItemsInFridge(fridgeItemSearchDTO){
            await filterFridge(fridgeItemSearchDTO).then(response => {
                this.allItems = []
                this.allItems = response.data.content;
            })
            return this.allItems
        },
    },
});

export const useStatStore = defineStore('statStore', {
    state: () => ({
        percentageChart: [],
        moneyChart: []
    }),

    getters: {
        getPercentageChart(){
            const labels = this.percentageChart.map(obj => obj.first);
            const values = this.percentageChart.map(obj => obj.second);

            return {
                labels,
                values
            }
        },

        getMoneyChart(){
            const labels = Object.keys(this.moneyChart);
            const values = Object.values(this.moneyChart);

            console.log(labels)
            console.log(values)

            return {
                labels,
                values
            }
        },
    },

    actions: {
        async fetchUserStatsPercentage() {
            this.percentageChart = []
            await getUserPercentageStats().then((response) => {
                for (const dataSet of response.data) {
                    const { first, second } = dataSet
                    this.percentageChart.push({first, second});
                }
            });
        },

        async fetchUserStatsMoney() {
            this.moneyChart = []
            await getUserMoneyStats()
                .then((response) => {
                    for (const key in response.data) {
                        this.moneyChart[key] = response.data[key];
                    }
                });
        },

        async fetchFridgePercentage(fridge) {
            this.percentageChart = []
            await getFridgePercentageStats(fridge.fridgeId)
                .then((response) => {
                    for (const dataSet of response.data) {
                        const { first, second } = dataSet
                        this.percentageChart.push({first, second});
                    }
                })
        },

        async fetchFridgeMoney(fridge) {
            this.moneyChart = []
            await getFridgeMoneyStats(fridge.fridgeId)
                .then((response) => {
                    for (const key in response.data) {
                        this.moneyChart[key] = response.data[key]
                    }
                })
        }
    },
});

export const useMealStore = defineStore('mealStore', {
    state: () => ({
        all: [],
        currentMeal: null,
    }),

    persist: {
        storage: sessionStorage,
    },

    getters: {
        getCurrentMeal(){
            return this.currentMeal;
        }
    },

    actions: {
        setCurrentMeal(meal) {
            this.currentMeal = meal;
        },
    },
});


