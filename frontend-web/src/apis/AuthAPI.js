import axios from "axios";

const BASE_API_URL = import.meta.env.VITE_BASE_API_URL;
const AUTH_BASE_URL = BASE_API_URL + "/auth";

const AuthAPI = {
    signIn: (request) => axios.post(AUTH_BASE_URL + "/sign_in", request)
        .then(response => response.data),
};

export default AuthAPI;