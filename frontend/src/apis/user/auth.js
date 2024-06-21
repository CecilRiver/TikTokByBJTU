import axios from 'axios';

import { BASE_URL } from '../config';


// 用户认证函数，type表示登录操作类型，info包含登录信息
export function apiAuth(type, info) {
  console.log(`${BASE_URL}/login`);
  return axios.post(`${BASE_URL}/login`, {
    type: type,
    email: info.email,
    password: info.password,
    avatar: 0,
    defaultFavoritesId: 0,
    description: '',
    each: false,
    gmtCreated: new Date().toISOString(),
    gmtUpdated: new Date().toISOString(),
    id: 0,
    isDeleted: false,
    nickName: '',
    roleName: [],
    sex: false,
  })
    .then(response => {
      console.log(response.data);
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


// 获取图形验证码
export function apiGetCaptcha(uuid) {
  return `${BASE_URL}/login/captcha.jpg/${uuid}`; // 返回图形验证码的URL，用于<img>标签的src
}

// 发送邮箱验证码
export function apiGetEmailCode(email, captchaCode, uuid) {
  return axios.post(`${BASE_URL}/login/getCode`, {
    email,
    code: captchaCode,
    uuid
  })
    .then(response => response.data)
    .catch(error => {
      console.error('Error getting email code:', error);
      throw error;
    });
}

// 验证邮箱验证码
export function apiCheckEmailCode(email, code) {
  return axios.post(`${BASE_URL}/login/check?email=${encodeURIComponent(email)}&code=${code}`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error checking email code:', error);
      throw error;
    });
}

// 注册新用户
export function apiRegister(registerInfo) {
  const registerVO = {
    email: registerInfo.email,
    code: registerInfo.code,
    nickName: registerInfo.nickName,
    password: registerInfo.password,
    uuid: registerInfo.uuid
  }
  return axios.post(`${BASE_URL}/login/register`, registerVO)
    .then(response => response.data)
    .catch(error => {
      console.error('Error during registration:', error);
      throw error;
    });
}

