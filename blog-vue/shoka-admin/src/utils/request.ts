import useStore from "@/store";
import axios, { AxiosError, AxiosResponse, InternalAxiosRequestConfig } from "axios";
import { ElMessage, ElNotification } from "element-plus";
import { messageConfirm } from "./modal";
import { getToken, token_prefix } from "./token";

// 是否显示重新登录
export let isRelogin = { show: false };

const requests = axios.create({
  baseURL: "/api",
  timeout: 10000,
  // 请求头
  headers: {
    "Content-Type": "application/json;charset=UTF-8",
  },
});

// 请求拦截器
requests.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 请求带token
    if (getToken()) {
      config.headers["Authorization"] = token_prefix + getToken();
    }
    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  }
);

// 配置响应拦截器
requests.interceptors.response.use(
  (response: AxiosResponse) => {
    const data = response.data
    if (response.config.responseType === 'blob') {
      if (response.data.type === 'application/json') {
        response.data = new Promise(resolve => {
          const reader = new FileReader()
          reader.onload = function () {
            resolve(JSON.parse(this.result) || [] || '')
          }
          reader.readAsText(data)
        })
      } else {
        const link = document.createElement('a')
        const blob = new Blob([data])
        link.href = window.URL.createObjectURL(blob)
        const content = response.headers['content-disposition']
        const index = content.indexOf('filename=')
        const fileName = content.substring(index + 9)
        link.download = decodeURIComponent(fileName)
        link.click()
        window.URL.revokeObjectURL(link.href)
        response.data = { code: 20000 }
      }
    }

    switch (response.data.code) {
      case 400:
        ElNotification({
          title: "失败",
          message: response.data.msg,
          type: "error",
        });
        break;
      case 402:
        const { user } = useStore();
        if (!isRelogin.show) {
          isRelogin.show = true;
          messageConfirm("登录状态已过期，您可以继续留在该页面，或者重新登录")
            .then(() => {
              isRelogin.show = false;
              user.LogOut().then(() => {
                location.href = "/login";
              });
            })
            .catch(() => {
              isRelogin.show = false;
            });
        }
        break;
      case 500:
        ElNotification({
          title: "失败",
          message: response.data.msg,
          type: "error",
        });
        break;
    }
    return response;
  },
  (error: AxiosError) => {
    let { message } = error;
    if (message == "Network Error") {
      message = "后端接口连接异常";
    } else if (message.includes("timeout")) {
      message = "系统接口请求超时";
    } else if (message.includes("Request failed with status code")) {
      message = "系统接口" + message.substring(message.length - 3) + "异常";
    }
    ElMessage({
      message: message,
      type: "error",
      duration: 5 * 1000,
    });
    return Promise.reject(error);
  }
);

// 对外暴露
export default requests;
