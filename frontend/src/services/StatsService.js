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

export const getUserPercentageStats = async () => {
    return await axios.get(`${BASE_LISTING_URL}/get/user-stats/avg-thrown-per-day`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const getUserMoneyStats = async () => {
    return await axios.get(`${BASE_LISTING_URL}/get/user-stats/money-wasted-per-day`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const getFridgePercentageStats = async (fridgeId) => {
    return await axios.get(`${BASE_LISTING_URL}/get/fridge-stats/avg-thrown-per-day/${fridgeId}`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const getFridgeMoneyStats = async (fridgeId) => {
    return await axios.get(`${BASE_LISTING_URL}/get/fridge-stats/money-wasted-per-day/${fridgeId}`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};