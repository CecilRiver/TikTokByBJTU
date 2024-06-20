<template>
  <div class="home-main">
      <img :src="getAvatarUrl(userInfo.avatar)" alt="User Avatar">
      <h1>@ {{userInfo.nickName }}</h1>
      <p class="description">{{ userInfo.description }}</p>
      <div class="socialize">
        <p>关注: {{ userInfo.follow }}</p>
        <p>粉丝: {{ userInfo.fans }}</p>
      </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { BASE_URL } from '@/apis/config.js';
import { apiGetUserInfo } from '@/apis/user/user.js';

const userInfo = ref({
  id: null,
  nickName: '',
  avatar: null,
  sex: false,
  description: '',
  follow: 0,
  fans: 0
});

const getAvatarUrl = (avatarId) => {
  if(avatarId == 0){
   return "https://b0.bdstatic.com/0df6c8c7f109aa7b67e7cb15e6f8d025.jpg@h_1280"
  }
  return `${BASE_URL}/file/${avatarId}`;
}

onMounted(async () => {
  try {
    const response = await apiGetUserInfo();
    if (response.state && response.data) {
      userInfo.value = response.data;
    } else {
      console.error('Failed to fetch user data:', response.message);
    }
  } catch (error) {
    console.error('Error fetching user information:', error);
  }
});
</script>

<style scoped>
.home-main{
  margin: 20px auto;
  text-align: center;
}
img {
  width: 100px;
  height: 100px;
  border-radius: 50%;
}
.description{
  font-style: italic;
}
.socialize{
  margin: 60px;
  display: flex;
  justify-content: space-between;
  font-size: 20px;
}
</style>
