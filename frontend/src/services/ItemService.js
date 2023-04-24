import axios from "axios";
import SessionToken from '@/features/SessionToken.js'

const BASE_LISTING_URL = "http://localhost:8080/item";

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

export const deleteItemFromShoppingList = async (listingDeletionDTO, suggestion) => {
    return await axios.delete(`${BASE_LISTING_URL}/shopping/delete?suggestion=${suggestion}`, {

      headers: {
        Authorization: `Bearer ${await SessionToken()}`,
      },
      data: listingDeletionDTO
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

export const buyItemsFromShoppingList = async (itemRemoveDTOList) => {
    return await axios.post(`${BASE_LISTING_URL}/shopping/buy`,itemRemoveDTOList, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const acceptSuggestionFromShoppingList = async (itemRemoveDTO) => {
    return await axios.post(`${BASE_LISTING_URL}/shopping/suggestion`,itemRemoveDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const buyItemsFromShoppingList = async (itemRemoveDTO) => {
  return await axios.post(`${BASE_LISTING_URL}/shopping/buy`, itemRemoveDTO, {
    headers: {
      Authorization: `Bearer ${await SessionToken()}`,
    },
  });
};


export const loadAllListings = async () => {
    return await axios.get(`${BASE_LISTING_URL}/load`)
}

export const loadListingsByCategoryId = async (categoryId) => {
    return await axios.get(`${BASE_LISTING_URL}/category/${categoryId}/load`)
}

export const loadListingByItemId = async(itemId) => {
    return await axios.get(`${BASE_LISTING_URL}/load/${itemId}`)
}


export const loadImagesByItemId = async(itemId) => {
    return await axios.get(`${BASE_LISTING_URL}/load/pictures/${itemId}`)
}

export const filterByFullDesc = async(searchTerm, categoryId) => {
    return await axios.get(`${BASE_LISTING_URL}/load/filter?term=${searchTerm}&categoryId=${categoryId}`)
}
