import axios from "axios";
import SessionToken from '@/features/SessionToken.js'

const BASE_LISTING_URL = "http://10.22.58.18:8089/api/fridge";

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
    })
}

export const getNotifications = async (fridgeId) => {
    return await axios.get(`${BASE_LISTING_URL}/notifications?fridgeId=${fridgeId}`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
}

export const deleteNotification = async (notification, fridgeId) => {
    await axios.delete(`${BASE_LISTING_URL}/notifications/delete?fridgeId=${fridgeId}`, {
        data: notification,
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
}

export const removeBorder = async (notification, fridgeId) => {
    await axios.put(`${BASE_LISTING_URL}/update/notification?fridgeId=${fridgeId}`, notification, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
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
    await axios.delete(`${BASE_LISTING_URL}/delete/user`, {
        data: fridgeUserDTO,
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        }
    });
}

export const loadUsersByFridgeId = async (fridgeId) => {
    return await axios.get(`${BASE_LISTING_URL}/loadAllUsers?fridgeId=${fridgeId}`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
}



