import {jwtDecode} from "jwt-decode";

const AuthHandler = {

    saveToken: (token)=> { {
        localStorage.setItem("jwt", token);
    }},

    getToken: () => { {
        return localStorage.getItem("jwt");
    }},

    clearToken: () => { {
        localStorage.removeItem("jwt");
    }},

    getUserId: () =>{
        const token = localStorage.getItem("jwt");
        if(!token) return null;
        const decoded = jwtDecode(token);
        return decoded.userId;
    },

    tokenExists: ()=>{
        const token = localStorage.getItem("jwt");
        return token ? true : false;
    }
}

export default AuthHandler;