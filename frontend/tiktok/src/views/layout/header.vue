<template>
  <v-app-bar floating name="app-bar" :elevation="0" color="#252632">
    <v-app-bar-nav-icon icon="mdi-menu" @click="clickEvent(1, 1)"></v-app-bar-nav-icon>
    <v-app-bar-title>TikTok</v-app-bar-title>
    <v-text-field class="e700-hide search-bar" hide-details :density="'comfortable'" prepend-inner-icon="mdi-magnify" single-line
      clearable @focus="showSearch = true" placeholder="搜索视频" v-model="searchKey" @keyup.enter="search">
    </v-text-field>
    <v-spacer />
    <v-toolbar-items variant="plain" class="pr-2">
      <v-btn class="d700-hide search-button" @click="search" :variant="'text'"><v-icon>mdi-magnify</v-icon>搜索</v-btn>
      <auth />
    </v-toolbar-items>
  </v-app-bar>
  <v-card v-if="showSearch" class="search-results" @click:outside="showSearch = false">
    <v-card-text>
      <h2 class="text-h6 mb-2" v-if="searchHistory.length > 0">
        搜索历史
      </h2>
      <v-chip-group v-model="searchKey" column @update:model-value="search">
        <v-chip filter :value="item" variant="outlined" v-for="item in searchHistory" :key="item">
          {{ item }}
        </v-chip>
      </v-chip-group>
    </v-card-text>
    <v-divider />
    <v-card-text class="pt-0">
      <hotList elevation="0" />
    </v-card-text>
  </v-card>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { apiGetUserSearchHistory } from '../../apis/user/user';
import hotList from '../../components/hotList.vue';
import auth from '../auth/index.vue';

const { clickEvent } = defineProps({
  clickEvent: {
    type: Function,
    default: (type, data) => { }
  }
});

const searchKey = ref("");
const showSearch = ref(false);
const router = useRouter();
const searchHistory = ref([]);

const openSearch = () => {
  showSearch.value = !showSearch.value;
};

onMounted(() => {
  apiGetUserSearchHistory().then(({ data }) => {
    searchHistory.value = data.data;
  }).catch(error => {
    console.error("Failed to fetch search history:", error);
  });
});

const search = () => {
  showSearch.value = false;
  if (!searchKey.value || searchKey.value.length == 0) {
    router.push({
      path: "/",
    });
    return;
  }
  router.push({
    path: "/video/search/" + searchKey.value,
  });
  apiGetUserSearchHistory().then(({ data }) => {
    searchHistory.value = data.data;
  }).catch(error => {
    console.error("Failed to fetch search history:", error);
  });
  searchKey.value = "";
};
</script>

<style scoped>
::v-deep(.v-field__outline) {
  --v-field-border-width: 0px;
}

.v-app-bar {
  background-color: #252632;
  color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.v-app-bar-title {
  font-weight: bold;
  font-size: 24px;
}

.search-bar {
  max-width: 400px;
  margin: 0 auto;
  background-color: #3b3d4a;
  border-radius: 20px;
}

.search-button {
  color: white;
}

.search-results {
  background-color: #3b3d4a;
  border-radius: 10px;
  max-width: 400px;
  margin: 20px auto;
  padding: 20px;
}

.v-chip-group {
  display: flex;
  flex-wrap: wrap;
}

.v-chip {
  margin: 4px;
}

@media only screen and (min-width: 700px) {
  .d700-hide {
    display: none;
  }
}

@media only screen and (max-width: 700px) {
  .e700-hide {
    display: none;
  }
}
</style>
