import axios from "axios";
import SessionToken from '@/features/SessionToken.js'



const BASE_USER_URL = "http://192.168.0.101:8080/user";
export const registerUser = async (userData) => {
    return await axios.post(`${BASE_USER_URL}/register`, userData);
}

export const loginUser = async (userLoginDTO) => {
    return axios.post(`${BASE_USER_URL}/login`, userLoginDTO)
}

export const getUser = async () => {
    return axios.get(`${BASE_USER_URL}/get/info`, {
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    })
}

export const getProfilePicture = async () => {
    return axios.get(`${BASE_USER_URL}/get/picture`, {
        responseType: 'arraybuffer', // To receive the image as binary data
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const getProfilePictureById = async (id) => {
    return axios.get(`${BASE_USER_URL}/get/picture/${id}`, {
        responseType: 'arraybuffer', // To receive the image as binary data
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const updateProfilePicture = async (pictureFile) => {
    const formData = new FormData();
    formData.append('picture', pictureFile);

    return axios.put(`${BASE_USER_URL}/update/picture`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

export const deleteProfilePicture = async () => {
        await axios.delete(`${BASE_USER_URL}/delete/picture`, {
            headers: {
                Authorization: `Bearer ${await SessionToken()}`,
            },
        });

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
    return axios.put(`${BASE_USER_URL}/update/info`, userUpdateDTO, {
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

export const searchUserByUsername = async (username) => {
    return axios.get(`${BASE_USER_URL}/search/${username}`)
}

export const checkSuperUserStatus = async (fridgeId) => {
    return axios.get(`${BASE_USER_URL}/superuser`, {
        params: { fridgeId },
        headers: {
            Authorization: `Bearer ${await SessionToken()}`,
        },
    });
};

