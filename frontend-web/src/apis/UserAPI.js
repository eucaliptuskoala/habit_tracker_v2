import axios from "axios";

const BASE_API_URL = import.meta.env.VITE_BASE_API_URL;
const USER_BASE_URL = BASE_API_URL + "/users";

const UserAPI = {
    createUser: (user) => axios.post(USER_BASE_URL, user)
        .then(response => response.data),
    getAllUsers: () => axios.get(USER_BASE_URL)
        .then(response => response.data),
    getUserById: (userId) => axios.get(`${USER_BASE_URL}/${userId}`)
            .then(response => response.data),
    deleteUser: (userId) => axios.delete(`${USER_BASE_URL}/${userId}`)
            .then(response => response.data),
};

export default UserAPI;