import axios from 'axios';

const BASE_URL = 'http://your-api-url.com/api'; // 修改为你的 API 基础 URL

// 用户认证函数，type表示登录操作类型，info包含登录信息
export function apiAuth(type, info) {
  return axios.post(`${BASE_URL}/auth/login`, {
    type: type,
    email: info.email,
    password: info.password
  })
    .then(response => response.data)
    .catch(error => {
      console.error('Error during authentication:', error);
      throw error;
    });
}
