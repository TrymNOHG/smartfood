import axios from "axios";
import SessionToken from "@/features/SessionToken";

const BASE_LISTING_URL = "http://localhost:8089/api/notification"

export const deleteNotification = async (notificationId) => {
    await axios.delete(`http://localhost:8089/api/notification/delete/${notificationId}`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const getNotifications = async () => {
    return await axios.get(`${BASE_LISTING_URL}/get`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
}

export const setNotifications = async () => {
    await axios.get(`${BASE_LISTING_URL}/update`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};


export const removeBorder = async () => {
    await axios.put(`${BASE_LISTING_URL}/read/all`, {}, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
}
