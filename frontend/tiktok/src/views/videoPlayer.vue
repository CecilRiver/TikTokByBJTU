<template>
  <div class="video-player-container">
    <div class="video-container" @click="togglePlay">
      <video v-if="video.url" class="video-player" :src="video.url" controls>
        您的浏览器不支持 HTML 视频。
      </video>
      <div v-else class="video-placeholder">Loading...</div>
      <div class="video-details">
        <div class="video-title">{{ video.title }} <span>{{ video.duration }}</span></div>
        <div class="video-creator">@{{ video.creator }} · {{ formatDate(video.gmtCreated) }}</div>
        <div class="video-description">{{ video.description }}</div>
      </div>
    </div>
    <div class="side-actions">
      <v-btn icon>
        <v-icon>mdi-heart</v-icon>
      </v-btn>
      <v-btn icon>
        <v-icon>mdi-comment</v-icon>
      </v-btn>
      <v-btn icon>
        <v-icon>mdi-share</v-icon>
      </v-btn>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import dayjs from 'dayjs';

const video = ref({});
const route = useRoute();

const togglePlay = () => {
  const videoElement = document.querySelector('.video-player');
  if (videoElement) {
    if (videoElement.paused) {
      videoElement.play();
    } else {
      videoElement.pause();
    }
  }
};

const getCoverUrl = (coverId) => {
  return `http://localhost:8882/tiktok/covers/${coverId}.jpg`;
};

const fetchVideo = async (videoId) => {
  try {
    const response = await axios.get(`http://localhost:8882/tiktok/index/video/${videoId}`);
    if (response.data.state && response.data.data) {
      const videoData = response.data.data;
      const videoStreamUrl = `http://localhost:8882/tiktok/file/${videoData.url}`;
      video.value = { ...videoData, url: videoStreamUrl };
    } else {
      console.error('Failed to fetch video:', response.data.message);
    }
  } catch (error) {
    console.error('Error fetching video:', error);
  }
};

const formatDate = (date) => {
  return dayjs(date).format('YYYY年MM月DD日');
};

onMounted(() => {
  const videoId = route.params.videoId;
  fetchVideo(videoId);
});
</script>

<style scoped>
.video-player-container {
  display: flex;
  justify-content: space-between;
  height: 100vh;
  overflow: hidden;
  position: relative;
  background: #000;
}

.video-container {
  flex: 2;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  background: black;
}

.video-player {
  max-height: 100%;
  width: 100%; /* 修改为 100% 以适应父容器的宽度 */
  border-radius: 10px;
}

.video-placeholder {
  color: white;
  font-size: 20px;
}

.video-details {
  position: absolute;
  left: 20px;
  bottom: 60px;
  color: white;
  text-shadow: 0px 0px 10px rgba(0, 0, 0, 0.7);
  background: rgba(0, 0, 0, 0.5);
  padding: 10px;
  border-radius: 5px;
  max-width: 80%; /* 让信息框不要太宽 */
}

.video-title {
  font-size: 18px;
  font-weight: bold;
}

.video-creator {
  font-size: 14px;
  margin-top: 5px;
}

.video-description {
  font-size: 12px;
  margin-top: 5px;
}

.side-actions {
  position: absolute;
  right: 20px;
  top: 50%;
  transform: translateY(-50%);
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.side-actions v-btn {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
}

@media (max-width: 768px) {
  .video-player-container {
    flex-direction: column;
  }

  .video-container {
    width: 100%;
  }

  .video-player {
    width: 100%;
  }

  .video-details {
    bottom: 80px; /* 增加与视频进度条的距离 */
    left: 10px; /* 缩小左右间距 */
    right: 10px; /* 使其居中对齐 */
  }

  .side-actions {
    right: 10px;
    gap: 15px;
  }
}
</style>
