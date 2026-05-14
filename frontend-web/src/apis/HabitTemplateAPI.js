import api from "./AxiosConfig";

const BASE_API_URL = import.meta.env.VITE_BASE_API_URL;
const HABIT_TEMPLATE_BASE_URL = BASE_API_URL + "/habit_templates";

const HabitTemplateAPI = {
    getAllHabitTemplates: () => api.get(HABIT_TEMPLATE_BASE_URL)
        .then(res => res.data),
    getPopularHabitTemplates: () => api.get(HABIT_TEMPLATE_BASE_URL + "/popular")
        .then(res => res.data),
};

export default HabitTemplateAPI;