<template>
  <div>
    <h1>User Dashboard</h1>
    <nav>
      <ul>
        <li><router-link to="/user/home">Home</router-link></li>
        <li><router-link to="/user/classify">Classify</router-link></li>
        <li><router-link to="/user/video">My Videos</router-link></li>
        <li><router-link to="/user/favorites">Favorites</router-link></li>
        <li><router-link to="/user/like">Likes</router-link></li>
        <li><router-link to="/user/history">History</router-link></li>
      </ul>
    </nav>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'User'
}
</script>

<style>
/* 可以根据你的设计风格添加样式 */
nav ul {
  list-style: none;
  padding: 0;
}
nav ul li {
  display: inline;
  margin-right: 10px;
}
</style>
