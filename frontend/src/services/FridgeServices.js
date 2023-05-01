import axios from "axios";
import SessionToken from '@/features/SessionToken.js'

const BASE_LISTING_URL = "http://localhost:8089/api/fridge";

export const getAllFridges = async (username) => {
    return await axios.get(`${BASE_LISTING_URL}/loadAll?user=${username}`);
}

export const getFridgeById = async(fridgeID) => {
    return await axios.get(`${BASE_LISTING_URL}/user/load?fridgeID=${fridgeID}`);
}

export const addNewFridge = async (fridgeName) => {
    await axios.post(`${BASE_LISTING_URL}/create?fridgeName=${fridgeName}`,{},{
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        }
    });
}

export const updateFridge = async (fridgeDTO) => {
    await axios.put(`${BASE_LISTING_URL}/update`, fridgeDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        }
    });
}

export const addUserToFridge = async (fridgeUserDTO) => {
    await axios.post(`${BASE_LISTING_URL}/add/user`, fridgeUserDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const updateUserInFridge = async (fridgeUserDTO) => {
    await axios.put(`${BASE_LISTING_URL}/update/user`, fridgeUserDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const deleteUserFromFridge = async (fridgeUserDTO) => {
    console.log(fridgeUserDTO);
    await axios.delete(`${BASE_LISTING_URL}/delete/user`, {
        data: fridgeUserDTO,
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        }
    });
}

export const loadUsersByFridgeId = async (fridgeId) => {
    const token = await SessionToken();
    return await axios.get(`${BASE_LISTING_URL}/loadAllUsers?fridgeId=${fridgeId}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
}

