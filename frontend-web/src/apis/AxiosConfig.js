import axios from "axios";
import AuthHandler from "./AuthHandler";

const api = axios.create();

api.interceptors.request.use((config) => {
    const token = AuthHandler.getToken();
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default api;
