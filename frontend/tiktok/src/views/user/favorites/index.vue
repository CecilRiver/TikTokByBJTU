<template>
  <div>
    <v-container>
      <!-- 触发收藏夹编辑对话框的按钮 -->
      <v-btn @click="showEditDialog">
        创建/编辑收藏夹
      </v-btn>

      <!-- 收藏视频到收藏夹的对话框 -->
      <v-dialog v-model="dialog" scrollable :width="300">
        <template v-slot:activator="{ props }">
          <v-btn v-bind="props">选择收藏夹</v-btn>
        </template>
        <v-card>
          <v-card-title>选择收藏夹</v-card-title>
          <v-divider></v-divider>
          <v-card-text style="height: 300px;">
            <v-radio-group v-model="dialogm1" column>
              <v-radio :label="item.name" :value="item.id" v-for="item in favoriteItems" :key="item.id"></v-radio>
            </v-radio-group>
          </v-card-text>
          <v-divider></v-divider>
          <v-card-actions>
            <v-btn color="red" text @click="dialog = false">取消</v-btn>
            <v-spacer></v-spacer>
            <!-- 放置编辑组件 -->
            <FavoriteEdit :close-event="getFavorites">
              <template #default="{ props }">
                <v-btn :variant="'text'" v-bind="props">创建收藏夹</v-btn>
              </template>
            </FavoriteEdit>
            <v-btn color="primary" text @click="save">收藏</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { apiFavoriteVideo, apiGetFavorites } from '@/apis/user/favorites';
import FavoriteEdit from './FavoriteEdit.vue';

const dialog = ref(false);
const dialogm1 = ref(0);
const favoriteItems = ref([]);

const getFavorites = async () => {
  const response = await apiGetFavorites();
  if (response.data && response.data.state) {
    favoriteItems.value = response.data.data;
    if (favoriteItems.value.length > 0) {
      dialogm1.value = favoriteItems.value[0].id;
    }
  } else {
    dialog.value = false;
  }
};

const save = async () => {
  if (dialogm1.value > 0) {
    const response = await apiFavoriteVideo(dialogm1.value, props.videoId);
    if (response.data && response.data.state) {
      dialog.value = false;
    }
  }
};

const showEditDialog = () => {
  // 激活创建/编辑收藏夹对话框
  dialog.value = true;
};
</script>
