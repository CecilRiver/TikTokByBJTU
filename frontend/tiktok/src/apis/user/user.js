import axios from 'axios';
import { BASE_URL } from '../config';



function getUserId() {
  const userJson = sessionStorage.getItem('user');
  if (!userJson) {
    throw new Error('User data not found in sessionStorage');
  }
  const user = JSON.parse(userJson);
  const userId = user?.id;
  if (!userId) {
    throw new Error('Invalid user data');
  }
  return userId
}

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
