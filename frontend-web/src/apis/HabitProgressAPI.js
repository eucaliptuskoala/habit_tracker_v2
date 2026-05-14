import api from "./AxiosConfig";

const BASE_API_URL = import.meta.env.VITE_BASE_API_URL;
const HABIT_PROGRESS_BASE_URL = BASE_API_URL + "/habits_progress";

const HabitProgressAPI = {
    getProgressByUser: () => api.get(HABIT_PROGRESS_BASE_URL).then(res => res.data),
};

export default HabitProgressAPI;