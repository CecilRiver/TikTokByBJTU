<template>
    <div>
      <v-card class="mx-auto" color="#36393f" max-width="650" theme="dark" variant="flat">
        <v-sheet color="#202225">
          <v-card-item>
            <v-card-title>
              <v-icon icon="mdi-account" start></v-icon>
              注册账号
            </v-card-title>
          </v-card-item>
        </v-sheet>
  
        <v-card class="ma-4" color="#2f3136" rounded="lg" variant="flat">
          <v-card-item>
            <v-card-title class="text-body-2 d-flex align-center">
              <v-icon color="#949cf7" icon="mdi-step-forward" start></v-icon>
              <span class="text-medium-emphasis font-weight-bold">{{ stepData[step].title }}</span>
              <v-spacer></v-spacer>
              <v-chip class="ms-2 text-medium-emphasis" color="grey-darken-4" size="small" variant="flat">{{ step }}</v-chip>
            </v-card-title>
          </v-card-item>
  
          <v-divider></v-divider>
  
          <v-window v-model="step">
            <!-- 步骤 1: 验证邮箱 -->
            <v-window-item :value="1">
              <v-form :disabled="isLoading">
                <v-card-text>
                  <v-text-field v-model="registerInfo.email" label="邮箱" placeholder="请输入邮箱"></v-text-field>
                  <v-row no-gutters>
                    <v-col cols="12">
                      <v-img :src="captchaImg" @click="getCaptchaImg" aspect-ratio="1.75"></v-img>
                      <v-text-field v-model="registerInfo.captchaCode" label="图形验证码" hide-details placeholder="请输入图形验证码"></v-text-field>
                    </v-col>
                  </v-row>
                  <v-btn color="blue" block @click="getEmailCode">获取邮箱验证码</v-btn>
                </v-card-text>
                <v-otp-input v-model="registerInfo.code" placeholder="0"></v-otp-input>
              </v-form>
            </v-window-item>
  
            <!-- 步骤 2: 设置密码 -->
            <v-window-item :value="2">
              <v-card-text>
                <v-text-field v-model="registerInfo.nickName" label="请输入昵称" type="text"></v-text-field>
                <v-text-field v-model="registerInfo.password" label="请输入密码" type="password"></v-text-field>
                <v-text-field v-model="registerInfo.confirmPassword" label="请确认密码" type="password"></v-text-field>
                <span class="text-caption text-grey-darken-1">tips: 请为您的账号设置一个密码</span>
              </v-card-text>
            </v-window-item>
  
            <!-- 步骤 3: 注册成功 -->
            <v-window-item :value="3">
              <div class="pa-4 text-center" style="color: greenyellow;">
                <v-icon :size="60">mdi-check-circle</v-icon>
                <h3 class="text-h6 font-weight-light mb-2">欢迎来到豆音</h3>
                <span class="text-caption text-grey">Thanks for signing up!</span>
              </div>
            </v-window-item>
          </v-window>
  
          <v-card-actions>
            <v-btn v-if="step > 1" variant="text" @click="step--" :loading="isLoading">返回</v-btn>
            <v-spacer></v-spacer>
            <v-btn v-if="step < 3" color="blue" @click="stepData[step].next" :loading="isLoading">下一步</v-btn>
          </v-card-actions>
        </v-card>
      </v-card>
    </div>
  </template>
  
  <script setup>
  import { reactive, ref } from 'vue';
  import { apiRegister, apiGetCaptcha, apiCheckEmailCode, apiGetEmailCode } from '../../apis/user/auth'; 
  import { VOtpInput } from 'vuetify/labs/VOtpInput';
  import {guid} from '../../utils/buildUtil';
  
  const step = ref(1);
  const isLoading = ref(false);
  const captchaImg = ref('');
  const registerInfo = reactive({
    email: "",
    captchaCode: "",
    code: "",
    nickName: "",
    password: "",
    confirmPassword: "",
    uuid: guid()  
  });
  

  getCaptchaImg();
  
  function getCaptchaImg() {
    captchaImg.value = apiGetCaptcha(registerInfo.uuid);
  }
  
  function getEmailCode() {
    isLoading.value = true;
    apiGetEmailCode(registerInfo.email, registerInfo.captchaCode, registerInfo.uuid)
      .then(response => {
        console.log(response);
        isLoading.value = false;
        alert(response.message);  // Show backend returned message
      })
      .catch(error => {
        isLoading.value = false;
        console.error('Error getting email code:', error);
      });
  }
  
  function verifyEmailCode() {
    isLoading.value = true;
    apiCheckEmailCode(registerInfo.email, parseInt(registerInfo.code))
      .then(response => {
        console.log(response);
        isLoading.value = false;
        if (response.message == '验证成功') {
          step.value++;  // Move to next step
        } else {
          alert(response.message);  // Show error message
        }
      })
      .catch(error => {
        isLoading.value = false;
        console.error('Error verifying email code:', error);
      });
  }
  
  function register() {
    isLoading.value = true;
    if (registerInfo.password !== registerInfo.confirmPassword) {
      alert('Passwords do not match');
      isLoading.value = false;
      return;
    }
    apiRegister(registerInfo)
      .then(response => {
        isLoading.value = false;
        if (response.success) {
          step.value++;  // Move to success page
        } else {
          alert(response.message);  // Show error message
        }
      })
      .catch(error => {
        isLoading.value = false;
        console.error('Error registering:', error);
      });
  }
  
  const stepData = ref({
    1: {
      title: "验证邮箱",
      next: verifyEmailCode
    },
    2: {
      title: "设置密码",
      next: register
    },
    3: {
      title: "注册成功"
    }
  });
  </script>
  