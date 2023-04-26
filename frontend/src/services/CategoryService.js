import axios from "axios";
import SessionToken from '@/features/SessionToken.js'

const BASE_USER_URL = "http://localhost:8080/category";
export const addCategory = async (categoryEditDTO) => {
    return await axios.post(`${BASE_USER_URL}/admin/save`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
        body: {
            categoryEditDTO
        }
    });
}

export const deleteCategory = async (categoryEditDTO) => {
    return axios.post(`${BASE_USER_URL}/admin/delete`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
        body: {
            categoryEditDTO
        }
    });
}

//TODO: take away the need for authentication ***
export const loadAllCategories = async (categoryId) => {
    return axios.get(`${BASE_USER_URL}/load/all?categoryId=${categoryId}`)
}

export const loadMainCategories = async () => {
    return axios.get(`${BASE_USER_URL}/load/main`)
}

export const getCategoryById = async(categoryId) => {
    return axios.get(`${BASE_USER_URL}/load/${categoryId}`)
}
