import axios from 'axios';

const BASE_URL = 'http://your-api-url.com/api'; // 修改为你的 API 基础 URL

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
export function apiGetUserInfo(userId) {
  return axios.get(`${BASE_URL}/user/info/${userId}`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error getting user information:', error);
      throw error;
    });
}
