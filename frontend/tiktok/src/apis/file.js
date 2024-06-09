import axios from 'axios';

const BASE_URL = 'http://your-api-url.com/api'; // 修改为你的 API 基础 URL

// 获取文件的 URL
export function apiFileGet(fileId) {
  return axios.get(`${BASE_URL}/files/${fileId}`)
    .then(response => response.data.url)
    .catch(error => {
      console.error('Error fetching file URL:', error);
      throw error;
    });
}
