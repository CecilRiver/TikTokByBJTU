import axios from 'axios';
import { BASE_URL } from '../config';

// 获取用户的搜索历史
export function apiGetUserSearchHistory() {
  return axios.get(`${BASE_URL}/user/searchHistory`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error fetching user search history:', error);
      throw error;
    });
}

// 获取用户信息
export function apiGetUserInfo() {
  const userId = sessionStorage.getItem('userId');
  if (!userId) {
    throw new Error('Invalid user data');
  }
  console.log(`${BASE_URL}/customer/getInfo/${userId}`);
  return axios.get(`${BASE_URL}/customer/getInfo/${userId}`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error getting user information:', error);
      throw error;
    });
}

// 获取上传头像的Token
export function apiGetAvatarToken() {
  return axios.get(`${BASE_URL}/customer/avatar/token`)
    .then(response => response.data)
    .catch(error => {
      console.error('获取头像上传token失败:', error);
      throw error;
    });
}

// 更新用户信息
export function apiUpdateUserInfo(userInfo) {
  return axios.put(`${BASE_URL}/customer`, userInfo)
    .then(response => response.data)
    .catch(error => {
      console.error('更新用户信息失败:', error);
      throw error;
    });
}