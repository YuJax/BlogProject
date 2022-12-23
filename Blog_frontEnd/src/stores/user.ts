import { defineStore } from "pinia";

export const useUserStore = defineStore({
  id: "user",
  state: () => ({
    userLogin: false,
  }),
  actions: {
    setUserLogin(userLogin: boolean) {
      console.log("Set user login");
      this.userLogin = userLogin;
    },
  },
  getters: {
    getUserLogin(): boolean {
      return this.userLogin;
    },
  },
});
