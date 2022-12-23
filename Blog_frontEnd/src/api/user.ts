import request from "@/utils/request";

// user data interface
interface User {
  username: string;
  password: string;
}

interface UserRegister extends User {
  conmfirmPassword: string;
  email: string;
}

export interface UserInfo extends UserRegister {
  role: string;
}

interface UserForgetPassword {
  email: string;
}

interface UserResetPassword {
  password: string;
  confirmPassword: string;
}

interface UserTwoFactorAuth {
  verifyCode: string;
}

export function login(data: User) {
  return request({
    url: "/login",
    method: "post",
    data,
  });
}

export function register(data: UserRegister) {
  return request({
    url: "/register",
    method: "post",
    data,
  });
}

export function logout() {
  return request({
    url: "/logout",
    method: "post",
  });
}
