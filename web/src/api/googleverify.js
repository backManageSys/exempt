import request from '@/utils/request'

export function codeVerify(rp) {
  return request({
      url: '/google/api',
        method: 'get',
        params: {
            rp
        }
  })
}

export function codeVerify1() {
    var rp = 'pppp'
    return request({
        url: '/google/api',
        method: 'get',
        params:{
            rp
        }
    })
}
