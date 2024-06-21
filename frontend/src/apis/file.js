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

export function apiSaveFile(fileKey) {
  return axios.post(`${BASE_URL}/file?fileKey=${fileKey}`, {})
    .then(response => {
      if (response.data && response.data.state) {
        return response.data; // 返回成功保存的文件信息
      } else {
        console.error('Failed to save file info:', response.data.message);
        throw new Error('Failed to save file info: ' + response.data.message);
      }
    })
    .catch(error => {
      console.error('Error in apiSaveFile:', error);
      throw error; // 抛出错误以便调用者处理
    });
}
