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