import axios from "axios";
import SessionToken from '@/features/SessionToken.js'

const BASE_LISTING_URL = "http://localhost:8089/api/recipe";


export const loadRecipeByFridgeItems = async (fridgeId, page = 0, size = 10) => {
    const token = await SessionToken();
    const response = await axios.get(`${BASE_LISTING_URL}/loadByFridge`, {
        params: {
            fridge: fridgeId,
            page,
            size,
        },
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
    return response.data;
};
