<template>
  <div class="tiktok-container"  @wheel="handleScroll">
    <div class="video-container" @click="togglePlay">
      <video v-if="currentVideo.url" class="video-player" :src="currentVideo.url" controls>
        您的浏览器不支持 HTML 视频。
      </video>
      <div v-else class="video-placeholder">Loading...</div>
      <div class="video-details">
        <div class="video-title">{{ currentVideo.title }} <span>{{ currentVideo.duration }}</span></div>
        <div class="video-creator">@{{ currentVideo.creator }} · {{ formatDate(currentVideo.gmtCreated) }}</div>
        <div class="video-description">{{ currentVideo.description }}</div>
      </div>
    </div>
    <div class="video-info">
      <v-list dense>
        <v-list-item-group v-model="currentIndex">
          <v-list-item
            v-for="(video, index) in recommendedVideos"
            :key="video.id"
            @click="playVideo(index)"
            :class="{ active: currentIndex === index }"
          >
            <template #prepend>
              <v-avatar>
                <img :src="getCoverUrl(video.cover)" alt="Thumbnail">
              </v-avatar>
            </template>
            <v-list-item-content>
              <v-list-item-title>{{ video.title }}</v-list-item-title>
              <v-list-item-subtitle>{{ video.creator }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
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
import { apiGetRecommendedVideos } from '../../apis/video';
import dayjs from 'dayjs';

const recommendedVideos = ref([]);
const currentIndex = ref(0);
const currentVideo = ref({});

const handleScroll = (event) => {
  if (event.deltaY > 0) {
    nextVideo();
  } else if (event.deltaY < 0) {
    previousVideo();
  }
};

const nextVideo = () => {
  if (currentIndex.value < recommendedVideos.value.length - 1) {
    currentIndex.value += 1;
    playVideo(currentIndex.value);
  }
};

const previousVideo = () => {
  if (currentIndex.value > 0) {
    currentIndex.value -= 1;
    playVideo(currentIndex.value);
  }
};

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
  return `http://localhost:8882/tiktok/file/${coverId}`;
};

const playVideo = async (index) => {
  currentIndex.value = index;
  const selectedVideo = recommendedVideos.value[index];
  const videoStreamUrl = `http://localhost:8882/tiktok/file/${selectedVideo.link}`;
  currentVideo.value = { ...selectedVideo, url: videoStreamUrl };
};

const formatDate = (date) => {
  return dayjs(date).format('YYYY年MM月DD日');
};

onMounted(async () => {
  try {
    const response = await apiGetRecommendedVideos();
    if (response.state && response.data) {
      recommendedVideos.value = response.data.map(video => ({
        id: video.id,
        title: video.title,
        description: video.description,
        creator: video.user.nickName,
        thumbnail: video.cover,
        link: video.url, // this is the ID for fetching the actual video stream
        gmtCreated: video.gmtCreated,
        duration: video.duration
      }));
      await playVideo(0); // Play the first video by default
      console.log('推荐视频', recommendedVideos.value);
    } else {
      console.error('Failed to fetch videos:', response.message);
    }
  } catch (error) {
    console.error('Error fetching videos:', error);
  }
});
</script>

<style scoped>
.tiktok-container {
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

.video-info {
  flex: 1;
  overflow-y: auto;
  background: #1e1e1e;
  color: #fff;
  padding: 10px;
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

.active {
  background: #f0f0f0;
}

@media (max-width: 768px) {
  .tiktok-container {
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

  .video-info {
    display: none; /* 在小屏幕上隐藏视频列表 */
  }

  .side-actions {
    right: 10px;
    gap: 15px;
  }
}


</style>
