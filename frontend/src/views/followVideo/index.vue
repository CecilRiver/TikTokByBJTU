<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-typography variant="h4">推荐视频</v-typography>
      </v-col>
      <VideoList :videos="recommendedVideos" />
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import VideoList from '@/components/video/list.vue';

const recommendedVideos = ref([]);

onMounted(async () => {
    try {
        const response = await axios.get('http://localhost:8882/tiktok/index/pushVideos');
        if (response.data.state && response.data.data) {
            recommendedVideos.value = response.data.data.map(video => ({
                id: video.id,
                title: video.title,
                creator: video.user.nickName,
                thumbnail: video.cover,
                link: video.url, 
                description: video.description
            }));
        } else {
            console.error('Failed to fetch videos:', response.data.message);
        }
    } catch (error) {
        console.error('Error fetching videos:', error);
    }
});
</script>

<style scoped>
</style>
