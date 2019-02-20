import request from '@/utils/request'
export function merchantsReport(startDate,endDate) {
  return request({
    url: '/report/merchant',
    method: 'get',
    params:{
      startDate,
      endDate
    }
  })
}

export function getPermerchantReport(uid) {
  // console.log(uid)
  return request({
      url: 'report/merchant/'+uid,
    method: 'get'
  })
}

export function fundingReport(startDate,endDate){
  
  return request({
    url: '/report/funding',
    method: 'get',
    params:{
      startDate,
      endDate
    }
  })
}
export function agencyReport(startDate,endDate){
  
  return request({
    url: '/report/agent',
    method: 'get',
    params:{
      startDate,
      endDate
    }
  })
}
export function receiveCodeReport(startDate,endDate){
  
  return request({
    url: '/report/receiptCode',
    method: 'get',
    params:{
      startDate,
      endDate
    }
  })
}
export function supplierReport(startDate,endDate){
  
  return request({
    url: '/report/supplier',
    method: 'get',
    params:{
      startDate,
      endDate
    }
  })
}
