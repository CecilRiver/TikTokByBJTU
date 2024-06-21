import axios from 'axios';

import { BASE_URL } from '../config';

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
