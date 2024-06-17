import axios from 'axios';

const BASE_URL = 'http://your-api-url.com/api'; // 修改为你的 API 基础 URL

// 获取用户收藏夹列表
export function apiGetFavorites() {
  return axios.get(`${BASE_URL}/user/favorites`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error fetching favorites:', error);
      throw error;
    });
}

// 将视频添加到收藏夹
export function apiFavoriteVideo(favoriteId, videoId) {
  return axios.post(`${BASE_URL}/user/favorites/add`, { favoriteId, videoId })
    .then(response => response.data)
    .catch(error => {
      console.error('Error adding video to favorite:', error);
      throw error;
    });
}

export function apiRemoveFavorite(favoriteId, videoId) {
  return axios.delete(`${BASE_URL}/user/favorites/remove`, { data: { favoriteId, videoId } })
    .then(response => response.data)
    .catch(error => {
      console.error('Error removing video from favorite:', error);
      throw error;
    });
}

// 保存视频到收藏夹
export function apiSaveFavorite(favoriteId, videoId) {
  return axios.post(`${BASE_URL}/user/favorites/save`, { favoriteId, videoId })
    .then(response => response.data)
    .catch(error => {
      console.error('Error saving video to favorite:', error);
      throw error;
    });
}
