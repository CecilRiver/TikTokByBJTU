<template>
  <v-list dense>
    <v-list-item
      v-for="video in videos"
      :key="video.id"
      class="video-list-item"
      @click="selectVideo(video)"
    >
      <v-list-item-avatar>
        <v-img :src="getCoverUrl(video.cover)" />
      </v-list-item-avatar>

      <v-list-item-content>
        <v-list-item-title>{{ video.title }}</v-list-item-title>
        <v-list-item-subtitle>
          {{ video.user.nickName }} · {{ formatDate(video.gmtCreated) }}
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
  </v-list>
</template>

<script setup>
import { ref } from 'vue';
import dayjs from 'dayjs';

// Props to accept the videos array
const props = defineProps({
  videos: Array
});

const selectVideo = (video) => {
  // Logic to handle video selection, such as navigating to a video player page
  console.log('Selected video:', video);
};

const getCoverUrl = (coverId) => {
  return `http://localhost:8882/tiktok/file/${coverId}`;
};

const formatDate = (date) => {
  return dayjs(date).format('YYYY年MM月DD日');
};
</script>

<style scoped>
.video-list-item {
  cursor: pointer;
}
</style>
