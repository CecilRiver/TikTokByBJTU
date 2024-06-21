# TikTokByBJTU 短视频平台

API 设计与分析课程大作业：一个仿抖音的短视频平台，包含 Spring Boot 后端和 Vue 3 前端。

## 功能

- 用户注册、登录、退出，基于 JWT 的鉴权
- 推荐视频：按点赞数推荐，已观看的视频不再推荐
- 视频上下滑动切换、点赞
- 视频发布、我的视频分页查看、删除（权限控制）
- 关注 / 粉丝列表、收藏夹
- 视频、图片、文本内容审核（七牛云）
- 请求日志与接口耗时监控
- Swagger 接口文档

## 技术栈

- 后端：Java 8 / Spring Boot 2.7 / MyBatis-Plus / MySQL / Redis / JWT / Swagger / p6spy
- 前端：Vue 3 / Vite / Vue Router / Pinia / Vuetify / video.js
- 对象存储：七牛云

## 目录结构

```
backend/tiktok/   Spring Boot 后端
frontend/tiktok/  Vue 3 前端
```

## 本地运行

### 后端

环境要求：JDK 8、Maven、MySQL、Redis。

1. 创建数据库 `tiktok` 并导入表结构。
2. 配置环境变量：

| 变量 | 说明 |
|---|---|
| `DB_URL` | JDBC 地址，默认 `jdbc:mysql://localhost:3306/tiktok` |
| `DB_USERNAME` | 数据库用户名，默认 `tiktok` |
| `DB_PASSWORD` | 数据库密码 |
| `REDIS_HOST` / `REDIS_PORT` / `REDIS_PASSWORD` | Redis 连接 |
| `MAIL_USERNAME` / `MAIL_PASSWORD` | 邮件服务账号与授权码 |
| `QINIU_ACCESS_KEY` / `QINIU_SECRET_KEY` / `QINIU_BUCKET` | 七牛云配置 |
| `SERVER_PORT` | 服务端口，默认 `8882` |

3. 启动后端：

```bash
cd backend/tiktok
./mvnw spring-boot:run
```

Windows 下使用 `mvnw.cmd spring-boot:run`。

### 前端

环境要求：Node.js。

```bash
cd frontend/tiktok
npm install
npm run dev
```

开发服务器默认运行在 `http://localhost:5378`。接口地址配置在 `src/apis/config.js`，默认为 `http://localhost:8882/tiktok`。

## 说明

- 仓库不包含数据库导出文件，运行前需自行建库建表。
- Swagger 默认开启，可通过 `swagger.enable` 控制。
- 邮件和七牛云相关功能需要配置对应环境变量后才能使用。
