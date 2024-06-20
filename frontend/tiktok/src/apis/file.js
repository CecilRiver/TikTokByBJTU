import axios from 'axios';

import { BASE_URL } from './config';

// 获取文件的 URL
export function apiFileGet(fileId) {
  return axios.get(`${BASE_URL}/file/${fileId}`)
    .then(response)
    .catch(error => {
      console.error('Error fetching file URL:', error);
      throw error;
    });
}
