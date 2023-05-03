import axios from "axios";
import SessionToken from '@/features/SessionToken.js'

//TODO: needs to be changed to https://smartmat.me/api/item in order to run on server
const BASE_LISTING_URL = "https://smartmat.me/api/item";

export const addItemToFridge = async (itemDTO, fridgeId) => {
    return await axios.post(`${BASE_LISTING_URL}/fridge/add?fridgeId=${fridgeId}`, itemDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const getItemsFromFridge = async (fridgeId) => {
    return await axios.get(`${BASE_LISTING_URL}/fridge/get?fridgeId=${fridgeId}`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const deleteItemFromFridge = async (itemRemoveDTO) => {
    return await axios.delete(`${BASE_LISTING_URL}/fridge/delete`, {

        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
        data: itemRemoveDTO
    });
};

export const deleteItemFromShoppingList = async (ItemRemoveDTO, suggestion) => {
    return await axios.delete(`${BASE_LISTING_URL}/shopping/delete?suggestion=${suggestion}`,
        {
            data: ItemRemoveDTO,
            headers: {
                Authorization: `Bearer ${await SessionToken()}`,
            },
        });
};

export const deleteItemsFromShoppingList = async (listingDeletionDTO) => {
    return await axios.post(`${BASE_LISTING_URL}/shopping/delete/all`, listingDeletionDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const addItemToShoppingList = async (itemDTO, fridgeId, suggestion) => {
    return await axios.post(`${BASE_LISTING_URL}/shopping/add?fridgeId=${fridgeId}&suggestion=${suggestion}`, itemDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};


export const getItemsFromShoppingList = async (fridgeId) => {
    return await axios.get(`${BASE_LISTING_URL}/shopping/get?fridgeId=${fridgeId}`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};


export const acceptSuggestion = async (itemRemoveDTO) => {
    return await axios.post(`${BASE_LISTING_URL}/shopping/suggestion`, itemRemoveDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const buyItemsFromShoppingList = async (itemDTOList) => {
    console.log(itemDTOList)

    return await axios.post(`${BASE_LISTING_URL}/shopping/buy`, itemDTOList, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const updateShoppingListItem = async (shoppingItemUpdateDTO) => {
    return await axios.put(`${BASE_LISTING_URL}/shopping/update`, shoppingItemUpdateDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const filterFridge = async (fridgeItemSearchDTO) => {
    const token = await SessionToken();
    return await axios.post( `${BASE_LISTING_URL}/fridge/search`,fridgeItemSearchDTO,
        {
            headers: {
                Authorization: `Bearer ${token}`,
            },

        });
}


