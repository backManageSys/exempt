import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken, getRoles, getRole } from '@/utils/auth'
import { getUid, setUid, setRole,setUname} from '../../utils/auth';
import { get } from 'https';
import { Store } from 'vuex';

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    role:getRole(),
    uid: getUid()
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_ROLE: (state, role) => {
      state.role = role
    },
    SET_UID: (state, uid) => {
      state.uid = uid
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim();
      setUname(username);
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(response => {
          const data = response.data
          setToken(data.token);
          setUid(data.uid);
          setRole(data.role);

          // window.sessionStorage.setItem('userId',data.uid);// 登陆时记录uid到session
          commit('SET_ROLE', data.role)
          commit('SET_TOKEN', data.token)
          commit('SET_UID', data.uid)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo(state.uid).then(response => {
          const data = response.data
          var roles = ['404', '面板','用户信息','用户中心']
          if (data.permissions && data.permissions.length > 0) { // 验证返回的roles是否是一个非空数组
            roles = roles.concat(data.permissions)
            commit('SET_ROLES', roles)

            commit('SET_NAME', data.info.user.username)
          } else {
            reject('未获取到角色')
          }
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        removeToken()
        resolve()
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
