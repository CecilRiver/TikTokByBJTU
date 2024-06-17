import axios from 'axios';

const BASE_URL = 'http://your-api-url.com/api'; // 修改为你的 API 基础 URL

// 用户认证函数，type表示登录操作类型，info包含登录信息
export function apiAuth(type, info) {
  return axios.post(`${BASE_URL}/auth/login`, {
    type: type,
    email: info.email,
    password: info.password
  })
    .then(response => response.data)
    .catch(error => {
      console.error('Error during authentication:', error);
      throw error;
    });
}

export function apiCheckCode(userId, code) {
  return axios.post(`${BASE_URL}/user/checkCode`, { userId, code })
    .then(response => response.data)
    .catch(error => {
      console.error('Error checking code:', error);
      throw error;
    });
}

// 忘记密码
export function apiForgetPassword(email) {
  return axios.post(`${BASE_URL}/user/forgetPassword`, { email })
    .then(response => response.data)
    .catch(error => {
      console.error('Error in forget password:', error);
      throw error;
    });
}

// 获取验证码
export function apiGetCode(email) {
  return axios.post(`${BASE_URL}/user/getCode`, { email })
    .then(response => response.data)
    .catch(error => {
      console.error('Error getting code:', error);
      throw error;
    });
}