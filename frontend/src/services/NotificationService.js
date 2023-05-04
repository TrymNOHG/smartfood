import axios from "axios";
import SessionToken from "@/features/SessionToken";

const BASE_LISTING_URL = "http://localhost:8089/api/notification"

export const deleteNotification = async (notification, fridgeId) => {
    await axios.delete(`${BASE_LISTING_URL}/notifications/delete?fridgeId=${fridgeId}`, {
        data: notification,
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
}

export const getNotifications = async () => {
    return await axios.get(`${BASE_LISTING_URL}/get`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
}

export const setNotifications = async () => {
    return await axios.get(`${BASE_LISTING_URL}/get`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
}


export const removeBorder = async () => {
    await axios.put(`${BASE_LISTING_URL}/read/all`, {}, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
}
