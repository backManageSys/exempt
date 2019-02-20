import request from '@/utils/request'
export function login(username, password) {
  return request({
    url: '/account/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo(uid) {
  // console.log(uid)
  return request({
    url: '/user/info/'+uid,
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function deleteAccount(id) {
  return request({
    url: '/account/delete',
    method: 'get',
    params: { id }
  })
}
