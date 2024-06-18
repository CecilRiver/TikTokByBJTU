import { defineStore } from 'pinia'
export default defineStore('user', {
    state: () => ({
        info: {},
        token: sessionStorage.getItem("token"),
        lookId: null
    }),
    actions: {
        setToken(newToken) {
            this.token = newToken;
            sessionStorage.setItem("token", newToken); // 同时更新 sessionStorage
        },
        clearToken() {
            this.token = null;
            sessionStorage.removeItem("token"); // 清除 sessionStorage 的 token
        },
        setUserInfo(userInfo) {
            this.info = userInfo; // 更新用户信息
        }
    }
})