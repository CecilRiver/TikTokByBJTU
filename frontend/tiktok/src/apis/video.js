import axios from 'axios';

import { BASE_URL } from './config';

function getUserId() {
  const userJson = sessionStorage.getItem('user');
  if (!userJson) {
    throw new Error('User data not found in sessionStorage');
  }
  const user = JSON.parse(userJson);
  const userId = user?.id;
  if (!userId) {
    throw new Error('Invalid user data');
  }
  return userId
}

// 获取推荐视频列表
export function apiGetRecommendedVideos() {
  return axios.get(`${BASE_URL}/index/pushVideos`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error fetching recommended videos:', error);
      throw error;
    });
}

// 获取视频流
export function apiGetVideoStream(videoId) {
  return axios.get(`${BASE_URL}/file/${videoId}`, { responseType: 'blob' })
    .then(response => {
      const url = URL.createObjectURL(response.data);
      return url;
    })
    .catch(error => {
      console.error('Error fetching video stream:', error);
      throw error;
    });
}

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

// 获取热门视频排名
export function apiVideoHotRank() {
  return axios.get(`${BASE_URL}/index/video/hot/rank`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error fetching hot video ranks:', error);
      throw error;
    });
}

// 添加历史记录
export function apiAddHistory(videoId) {
  return axios.post(`${BASE_URL}/videos/history`, { videoId })
    .then(response => response.data)
    .catch(error => {
      console.error('Error adding video to history:', error);
      throw error;
    });
}

// 获取相似视频
export function apiGetVideoBySimilar(videoId) {
  return axios.get(`${BASE_URL}/videos/similar/${videoId}`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error fetching similar videos:', error);
      throw error;
    });
}

// 初始化关注视频流
export function apiInitFollowFeed() {
  const userId = getUserId();
  return axios.get(`${BASE_URL}/videos/followFeed/${userId}`)
    .then(response => response.data)
    .catch(error => {
      console.error('Error initializing follow feed:', error);
      throw error;
    });
}

// 设置用户视频模型
export function apiSetUserVideoModel(userId, modelId) {
  return axios.post(`${BASE_URL}/videos/userModel`, { userId, modelId })
    .then(response => response.data)
    .catch(error => {
      console.error('Error setting user video model:', error);
      throw error;
    });
}

// 分享视频
export function apiShareVideo(videoId) {
  return axios.post(`${BASE_URL}/videos/share`, { videoId })
    .then(response => response.data)
    .catch(error => {
      console.error('Error sharing video:', error);
      throw error;
    });
}

// 视频点赞
export function apiStarVideo(videoId) {
  return axios.post(`${BASE_URL}/videos/star`, { videoId })
    .then(response => response.data)
    .catch(error => {
      console.error('Error starring video:', error);
      throw error;
    });
}
