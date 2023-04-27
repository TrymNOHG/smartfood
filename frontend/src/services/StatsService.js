import axios from "axios";
import SessionToken from "@/features/SessionToken";

const BASE_LISTING_URL = "http://localhost:8080/stat";
export const deleteItemStats = async (statDeleteFromFridgeDTO) => {
    return await axios.post(`${BASE_LISTING_URL}/add/delete-item`, statDeleteFromFridgeDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const addItemStats = async (statAddItemToFridgeDTO) => {
    return await axios.post(`${BASE_LISTING_URL}/add/bought-item`, statAddItemToFridgeDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const getUserStats = async () => {
    var response = await axios.get(`${BASE_LISTING_URL}/get/user-stats`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
    console.log("get user response: ", response)
    return response;
};

export const getFridgeStats = async (fridgeId) => {
    return await axios.get(`${BASE_LISTING_URL}/get/fridge-stats/${fridgeId}`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};