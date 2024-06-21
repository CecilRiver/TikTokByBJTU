<template>
  <div class="friends-list">
    <v-app-bar  color="black" dark>
      <v-btn icon @click="goBack">
      <v-icon>mdi-arrow-left</v-icon>
    </v-btn>
      <v-toolbar-title class="bar-title">我的好友</v-toolbar-title>
    </v-app-bar>
    <v-tabs v-model="activeTab" background-color="primary" dark>
      <v-tab @click="fetchFollows">关注</v-tab>
      <v-tab @click="fetchFans">粉丝</v-tab>
    </v-tabs>
    <v-list>
      <v-list-item-group>
        <v-list-item v-for="friend in friends" :key="friend.id">
          <div class="item">
            <v-list-item-avatar >
            <img :src="getAvatarUrl(friend.avatar)" />
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title class="title" v-text="friend.nickName"></v-list-item-title>
            <v-list-item-subtitle class="subtitle" v-text="friend.description"></v-list-item-subtitle>
          </v-list-item-content>
          </div>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import axios from 'axios';
import { useRoute,useRouter } from 'vue-router';
import {BASE_URL} from "@/apis/config.js"

const route = useRoute();
const router = useRouter();
const activeTab = ref(0);
const friends = ref([]);

function goBack() {
  router.back();
}

const fetchFollows = () => {
  const userId = sessionStorage.getItem("userId"); 
  const basePage = 1;
  axios.get(`${BASE_URL}/customer/follows?userId=${userId}&basePage=${basePage}`)
    .then(response => {
      console.log("follows:",response.data);
      friends.value = response.data.data.records;
    })
    .catch(error => console.error('Error fetching follows:', error));
};

const fetchFans = () => {
  const userId = sessionStorage.getItem("userId"); 
  const basePage = 1;
  axios.get(`${BASE_URL}/customer/fans?userId=${userId}&basePage=${basePage}`)
    .then(response => {
      console.log("fans:",response.data);
      friends.value = response.data.data.records;
    })
    .catch(error => console.error('Error fetching fans:', error));
};

const getAvatarUrl = (avatarId) => {
  return avatarId ? `${BASE_URL}/file/${avatarId}` : "https://b0.bdstatic.com/0df6c8c7f109aa7b67e7cb15e6f8d025.jpg@h_1280";
};

watch(() => route.params.tab, (newTab) => {
  activeTab.value = newTab === 'follows' ? 0 : 1;
  if (newTab === 'follows') {
    fetchFollows();
  } else {
    fetchFans();
  }
}, { immediate: true });
</script>

<style scoped>
.friends-list, v-app-bar {
  text-align: center;
}

v-app-bar, v-tabs {
  color: white;
}

.bar-title {
  text-align: left;
}

.item{
  display: flex;
  align-items: center;
}
img{
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-left: 10px;
}
.title{
  font-size: 20px;
}
.subtitle{
  font-style: italic;
  margin-top: 5px;
  margin-left: 25px;
}
</style>
