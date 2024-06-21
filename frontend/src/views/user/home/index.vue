<template>
  <div class="home-main">
      <img :src="getAvatarUrl(userInfo.avatar)" alt="User Avatar" @click="handleAvatarChange">
      <h1>@ {{userInfo.nickName }}</h1>
      <p class="description">{{ userInfo.description }}</p>
      <div class="socialize">
        <p @click="goToFriends('follows')">关注: {{ userInfo.follow }}</p>
        <p @click="goToFriends('fans')">粉丝: {{ userInfo.fans }}</p>
      </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { BASE_URL } from '@/apis/config.js';
import { apiGetUserInfo, apiGetAvatarToken, apiUpdateUserInfo } from '../../../apis/user/user.js';
import { apiSaveFile } from '../../../apis/file.js';
import { useRouter } from 'vue-router';

const router = useRouter();

function goToFriends(tab) {
  router.push({ name: 'FriendsList', params: { tab } });
}


const userInfo = ref({
  id: null,
  nickName: '',
  avatar: null,
  sex: false,
  description: '',
  follow: 0,
  fans: 0,
  defaultFavoritesId: 0,
});

// 获取七牛云上传token
const getAvatarToken = async () => {
  try {
    const response = await apiGetAvatarToken();
    //console.log(response);
    if (response.state && response.data) {
      return response.data;
    } else {
      console.error('Failed to get upload token:', response.message);
      return null;
    }
  } catch (error) {
    console.error('Error fetching upload token:', error);
    return null;
  }
};

// 更新用户头像信息
const updateUserInfo = async (newAvatarId) => {
  const updatedInfo = {
  defaultFavoritesId: userInfo.value.defaultFavoritesId,
  description: userInfo.value.description,
  nickName: userInfo.value.nickName,
  sex: userInfo.value.sex,
  avatar: newAvatarId };
  console.log(updatedInfo);
  try {
    const response = await apiUpdateUserInfo(updatedInfo);
    console.log(response);
    if (response.state) {
      userInfo.value.avatar = newAvatarId;
      alert('头像更新成功');
    } else {
      console.error('Failed to update user avatar:', response.message);
    }
  } catch (error) {
    console.error('Error updating user avatar:', error);
  }
};

// 处理头像点击事件，选择和上传头像
const handleAvatarChange = async () => {
  const token = await getAvatarToken();
  if (!token) return;
  
  const fileInput = document.createElement('input');
  fileInput.type = 'file';
  fileInput.accept = 'image/*';
  fileInput.onchange = async e => {
    const file = e.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append('file', file);
    formData.append('token', token);

    try {
      const uploadResponse = await fetch('https://up-z1.qiniup.com', {
        method: 'POST',
        body: formData
      }).then(res => res.json());
  //    console.log(uploadResponse);
      if (uploadResponse.key) {
        try {
          const saveResponse = await apiSaveFile(uploadResponse.key);
          const avatarId = saveResponse.data; // 确保这里的返回值是正确的
          console.log(avatarId);
          await updateUserInfo(avatarId);
        } catch (error) {
          console.error('保存头像信息失败:', error);
        }
      } else {
        console.error('Upload failed:', uploadResponse.message);
      }
    } catch (error) {
      console.error('Error uploading file:', error);
    }
  };
  fileInput.click();
};

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
