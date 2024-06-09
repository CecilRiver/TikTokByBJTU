import axios from 'axios';

const BASE_URL = 'http://your-api-url.com/api'; // 修改为你的 API 基础 URL

// 获取分类数据的 API 函数
export function getClassifications() {
  return axios.get(`${BASE_URL}/classifications`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error fetching classifications:', error);
      throw error;
    });
}
