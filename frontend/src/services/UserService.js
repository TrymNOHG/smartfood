import axios from "axios";
import SessionToken from '@/features/SessionToken.js'



const BASE_USER_URL = "http://localhost:8080/user";
export const registerUser = async (userData) => {
    return await axios.post(`${BASE_USER_URL}/register`, userData);
}

export const loginUser = async (userLoginDTO) => {
    return axios.post(`${BASE_USER_URL}/auth/authenticate`, userLoginDTO)
}

export const getUser = async () => {
    return axios.get(`${BASE_USER_URL}/load`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    })
}

//get all members for a fridge
export const getMembers = async () => {
    return axios.get(`${BASE_USER_URL}/members`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    })
}

export const loadUserByUsername = async (username) => {
    return axios.get(`${BASE_USER_URL}/load/${username}`)
}

export const updateUser = async (userUpdateDTO) => {
    console.log(userUpdateDTO)
    return axios.put(`${BASE_USER_URL}/update/user`, userUpdateDTO, {
        headers: {
            // 'Content-Type': 'multipart/form-data',
            Authorization: `Bearer ${await SessionToken()}`
        },
    });
}

export const updateUserPassword = async (userPasswordUpdateDTO) => {
    console.log(userPasswordUpdateDTO)
    return axios.put(`${BASE_USER_URL}/update/password`, userPasswordUpdateDTO, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`
        },
    });
}