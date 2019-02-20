import request from '@/utils/request'

export function withdrew(cardId,
  id,
  money,
  type) {
  return request({
    url: '/withdrew ',
    method: 'post',
    data:{
        cardId,//: 0,
        id,//: 0,
        money,//: 0,
        type//: "string"
    }
  })
}
export function withdrewDeal(
    id, memo,
    operatorId,
    state) {
  return request({
    url: '/withdrew/deal/'+id,
    method: 'post',
    data:{
        memo,//: "string",		// 备注
        operatorId,//: 0,		// 处理人的userid
        state,//: "string"		// SUCCESS成功/FAILED失败
    }
  })
}
export function withdrewGet(id, operatorId) {
  return request({
    url: '/withdrew/get/'+id,
    method: 'get',
    params:{
        operatorId,//: 0,		// 处理人的userid
    }
  })
}
export function withdrewList(uid) {
  return request({
    url: '/withdrew/list/'+uid,
    method: 'get'
  })
}
export function withdrewsWaiting() {
  return request({
    url: '/withdrews/waiting',
    method: 'get'
  })
}