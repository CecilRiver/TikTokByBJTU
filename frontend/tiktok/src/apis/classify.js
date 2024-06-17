import axios from 'axios';

import { BASE_URL } from './config';

// 获取分类数据的 API 函数
export function getClassifications() {
  return axios.get(`${BASE_URL}/classifications`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error fetching classifications:', error);
      throw error;
    });
}

// 获取用户相关的分类
export function apiGetClassifyByUser(userId) {
  return axios.get(`${BASE_URL}/classifies/user/${userId}`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error fetching classifies by user:', error);
      throw error;
    });
}

// 获取所有分类
export function apiClassifyGetAll() {
  return axios.get(`${BASE_URL}/classifies`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error getting all classifications:', error);
      throw error;
    });
}