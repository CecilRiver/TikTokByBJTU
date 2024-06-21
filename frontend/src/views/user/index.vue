<template>
  <div>
    <v-container>
      <v-row>
        <v-col cols="12">
          <v-slide-group
            v-model="activeTab"
            show-arrows
          >
            <v-slide-item v-for="(tab, index) in tabs" :key="index" :value="index">
              <router-link :to="tab.to" class="v-tab">
                {{ tab.title }}
              </router-link>
            </v-slide-item>
          </v-slide-group>
        </v-col>
        <v-col cols="12">
          <router-view></router-view>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  name: 'User',
  data() {
    return {
      activeTab: 0,
      tabs: [
        { title: '主页', to: '/user/home' },
        { title: '分类', to: '/user/classify' },
        { title: '我的视频', to: '/user/video' },
        { title: '收藏', to: '/user/favorites' },
        { title: '喜欢', to: '/user/like' },
        { title: '历史', to: '/user/history' },
      ],
    };
  },
  watch: {
    $route(to) {
      this.activeTab = this.tabs.findIndex(tab => tab.to === to.path);
    },
  },
  created() {
    this.activeTab = this.tabs.findIndex(tab => tab.to === this.$route.path);
  },
};
</script>

<style scoped>
.v-tab {
  padding: 10px 20px;
  text-decoration: none;
  color: white;
}
.v-slide-group__content {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
