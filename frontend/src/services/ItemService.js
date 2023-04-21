import axios from "axios";
import SessionToken from '@/features/SessionToken.js'

const BASE_LISTING_URL = "http://localhost:8080/item";
export const createNewListing = async (listingDTO) => {
    return await axios.post(`${BASE_LISTING_URL}/user/create`, listingDTO, {
        headers: {
            'Content-Type': 'multipart/form-data',
            Authorization: `Bearer ${await SessionToken()}`,
        }
    })
}

export const deleteListing = async (listingDeletionDTO) => {
    return await axios.post(`${BASE_LISTING_URL}/user/delete`, listingDeletionDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        }
    })
}

export const deleteItemFromShoppingList = async (listingDeletionDTO, suggestion) => {
    return await axios.delete(`${BASE_LISTING_URL}/shopping/delete?suggestion=${suggestion}`, {
      data: listingDeletionDTO,
      headers: {
        Authorization: `Bearer ${await SessionToken()}`,
      },
    });
  };

  export const addItemToShoppingList = async (listingAdditionDTO, fridgeId, suggestion) => {
    return await axios.post(`http://localhost:8080/item/shopping/add?fridgeId=1&suggestion=false`, listingAdditionDTO, {
      headers: {
        Authorization: `Bearer ${await SessionToken()}`,
      },
    });
  };
  


export const updateListing = async (listingUpdateDTO) => {
    console.log(listingUpdateDTO)
    return await axios.put(`${BASE_LISTING_URL}/user/update`, listingUpdateDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        }
    })
}
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