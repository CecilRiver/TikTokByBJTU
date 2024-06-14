import axios from 'axios';

const BASE_URL = 'http://your-api-url.com/api'; // 修改为你的 API 基础 URL

// 通过分类获取视频列表
export function apiVideoByClassfiy(classifyId, page, limit) {
  return axios.get(`${BASE_URL}/videos/classify/${classifyId}`, {
    params: { page, limit }
  })
    .then(response => response.data)
    .catch(error => {
      console.error('Error fetching videos by classify:', error);
      throw error;
    });
}

// 搜索视频
export function apiSearchVideo(keyword, page, limit) {
  return axios.get(`${BASE_URL}/videos/search`, {
    params: { keyword, page, limit }
  })
    .then(response => response.data)
    .catch(error => {
      console.error('Error searching videos:', error);
      throw error;
    });
}

// 根据视频ID获取视频详细信息
export function apiGetVideoById(videoId) {
  return axios.get(`${BASE_URL}/videos/${videoId}`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error fetching video by ID:', error);
      throw error;
    });
}
