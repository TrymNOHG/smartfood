import axios from "axios";
import SessionToken from '@/features/SessionToken.js'

const BASE_LISTING_URL = "http://localhost:8080/fridge";

export const getAllFridges = async (username) => {
    console.log(username)
    return await axios.get(`${BASE_LISTING_URL}/loadAll?user=${username}`);
}

export const getFridgeById = async(fridgeID) => {
    return await axios.get(`${BASE_LISTING_URL}/user/load?fridgeID=${fridgeID}`);
}

export const addNewFridge = async (username, fridgeName) => {
    await axios.post(`${BASE_LISTING_URL}/user/add?username=${username}`, {
        fridgeName: fridgeName
    }, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        }
    });
}

export const changeFridge = async (username, newName) => {
    await axios.post(`${BASE_LISTING_URL}/user/add?username=${username}`, {
        fridgeName: newName
    }, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        }
    });
}

export const deleteFridge = async (fridgeName) => {
    await axios.post(`${BASE_LISTING_URL}/user/delete`, fridgeName);
}

