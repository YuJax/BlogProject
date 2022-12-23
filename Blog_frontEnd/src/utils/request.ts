import axios from "axios";
import { useUserStore } from "@/stores/user";
import { useToast } from "vue-toastification";

const toast = useToast();
const request = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  timeout: 5000,
  // validateStatus: function (status) {
  //   return status >= 200 && status < 500
  // },
});

request.interceptors.response.use(
  async (response) => {
    if (response.data.token) {
      useUserStore().setUserLogin(true);
      console.log("Set token");
      await localStorage.setItem("token", response.data.token);
    }
    return response;
  },
  (error) => {
    toast.error(error.message);
    return Promise.reject(error);
  }
);

request.interceptors.request.use(async (config: any) => {
  const token = await localStorage.getItem("token");
  console.log("Get token", token);

  if (token) {
    config.headers.Authorization = "Bearer " + token;
  }
  return config;
});

export default request;
