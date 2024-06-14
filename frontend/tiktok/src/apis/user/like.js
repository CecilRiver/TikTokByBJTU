import axios from 'axios';

const BASE_URL = 'http://your-api-url.com/api'; // 修改为你的 API 基础 URL

// 用户关注操作
export function apiFollows(userId) {
  return axios.post(`${BASE_URL}/user/follows`, { userId })
    .then(response => response.data)
    .catch(error => {
      console.error('Error during follow operation:', error);
      throw error;
    });
}

// 初始化关注列表
export function apiInitFollowFeed() {
  return axios.get(`${BASE_URL}/user/follows/init`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error initializing follow feed:', error);
      throw error;
    });
}
