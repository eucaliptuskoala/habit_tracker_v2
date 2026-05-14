import api from "./AxiosConfig";

const BASE_API_URL = import.meta.env.VITE_BASE_API_URL;
const NOTE_BASE_URL = BASE_API_URL + "/notes";

const NoteAPI = {
    createNote: (note) => api.post(NOTE_BASE_URL, note).then(res => res.data),
    getNotes: () => api.get(NOTE_BASE_URL + "/my").then(res => res.data),
    updateNote: (request, noteId) => api.put(`${NOTE_BASE_URL}/${noteId}`, request).then(res => res.data),
    deleteNote: (id) => api.delete(`${NOTE_BASE_URL}/${id}`).then(res => res),
    getForYouContent: () => api.get(NOTE_BASE_URL + "/fyp").then(res => res.data),
};

export default NoteAPI;