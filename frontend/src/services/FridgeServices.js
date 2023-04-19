import axios from "axios";
import SessionToken from '@/features/SessionToken.js'

const BASE_LISTING_URL = "http://localhost:8080/fridge";

export const getAllFridges = async (username) => {
    return await axios.get(`${BASE_LISTING_URL}/user/load?username=${username}`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        }
    });
}

export const getFridgeById = async(fridgeID) => {
    return await axios.get(`${BASE_LISTING_URL}/user/load?fridgeID=${fridgeID}}`);
}
