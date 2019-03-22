import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '../store'
import { getToken,getUname } from '@/utils/auth'
// axios.defaults.withCredentials = true
// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 10000, // 请求超时时间
  // withCredentials : true
})
//
// request拦截器
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      config.headers['X-Token'] = getToken() +','+getUname()// 让每个请求携带自定义token 请根据实际情况自行修改
    }
    return config;
  },
  error => {
    // Do something with request error
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {
    /**
     * code为非20000是抛错 可结合自己业务进行修改
     */
    // const res = response.data
    if (response.data.code !== 200) {
      if(response.data.code == 1011){
        Message({
          message: response.data.data.description,
          type: 'error',
          duration: 3 * 1000
        })
      }
      if(response.data.code == 1010){
        Message({
          message: "该账户已停用",
          type: 'error',
          duration: 3 * 1000
        })
      }
      // Message({
      //   message: res.message,
      //   type: 'error',
      //   duration: 5 * 1000
      // })

      // // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
      // if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
      //   MessageBox.confirm(
      //     '你已被登出，可以取消继续留在该页面，或者重新登录',
      //     '确定登出',
      //     {
      //       confirmButtonText: '重新登录',
      //       cancelButtonText: '取消',
      //       type: 'warning'
      //     }
      //   ).then(() => {
      //     store.dispatch('FedLogOut').then(() => {
      //       location.reload() // 为了重新实例化vue-router对象 避免bug
      //     })
      //   })
      // }
      // return Promise.reject('error')
      return response.data
    } else {
      return response.data
    }
  },
  error => {
    if(error == 'Error: Request failed with status code 401'){
      Message({
        message: "用户名或密码错误",
        type: 'error',
        duration: 3 * 1000
      })
    }
    return Promise.reject(error)
  }
)

export default service

