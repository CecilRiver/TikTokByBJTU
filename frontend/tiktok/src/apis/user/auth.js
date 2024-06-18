import axios from 'axios';

import { BASE_URL } from '../config';


// 用户认证函数，type表示登录操作类型，info包含登录信息
export function apiAuth(type, info) {
  console.log(`${BASE_URL}/login`);
  console.log(new Date().toISOString());
  return axios.post(`${BASE_URL}/login`, {
    type: type,
    email: info.email,
    password: info.password,
    avatar: 0,  // 确保这里使用合适的默认值，如果是数字类型的话
    defaultFavoritesId: 0,  // 如果是数字类型，则应该使用0
    description: '',
    each: false,  // 这应该是一个布尔值
    gmtCreated: new Date().toISOString(),  // 使用合适的日期字符串
    gmtUpdated: new Date().toISOString(),  // 使用合适的日期字符串
    id: 0,
    isDeleted: false,
    nickName: '',
    roleName: [],
    sex: false,
  })
    .then(response => {
      console.log(response);
      return response.data
    })
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