import request from '@/utils/request'
export function ordersGet() {
  return request({
    url: '/order/list',
    method: 'get'
  })
}
export function ordersUpdate(id,state,money,realPay,orderId,payTime,memo) {
  return request({
    url: '/order/list/update/'+id,
    method: 'post',
    data:{
      state,
      money,
      realPay,
      orderId,
      payTime,
      memo
    }
  })
}
