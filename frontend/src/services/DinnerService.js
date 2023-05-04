import axios from "axios";
import SessionToken from '@/features/SessionToken.js'

const BASE_LISTING_URL = "http://10.22.58.18:8089/api/recipe";

export const loadRecipeByFridgeItemsAndDay = async (fridgeId, day, page = 0, size = 10) => {
    const token = await SessionToken();
    const response = await axios.get(`${BASE_LISTING_URL}/loadByDay`, {
        params: {
            fridge: fridgeId,
            day: day,
            page,
            size,
        },
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
    return response.data;
};

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

export const addIngredientsToShoppingList = async (recipeShoppingDTO) => {
    return await axios.post(`${BASE_LISTING_URL}/addIngredients`, recipeShoppingDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const loadRecipeSuggestions = async (fridgeId) => {
    const token = await SessionToken();
    const response = await axios.get(`${BASE_LISTING_URL}/suggestion/load`, {
        params: {
            fridge: fridgeId,
        },
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
    return response.data;
};

export const addRecipeSuggestion = async (recipeSuggestionAddDTO) => {
    const token = await SessionToken();
    const response = await axios.post(`${BASE_LISTING_URL}/suggestion/add`, recipeSuggestionAddDTO, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
    return response.data;
};

export const denyRecipeSuggestion = async (fridgeId, recipeId, userId) => {
    const token = await SessionToken();
    const response = await axios.post(`${BASE_LISTING_URL}/suggestion/deny`, null, {
        params: {
            fridge: fridgeId,
            recipe: recipeId,
            user: userId,
        },
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
    return response.data;
};

export const acceptRecipeSuggestion = async (recipeShoppingDTO, recipeId, userId) => {
    const token = await SessionToken();
    const response = await axios.post(`${BASE_LISTING_URL}/suggestion/accept`, recipeShoppingDTO, {
        params: {
            recipe: recipeId,
            user: userId,
        },
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
    return response.data;
};
