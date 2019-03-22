import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/table/list',
    method: 'get',
    params
  })
}

// 获取日志明细——操作记录
export function getLimit(size,page,condition) {
  return request({
    url: '/systemLog/getLimit?page='+page+"&size="+size+"&condition="+condition,
    method: 'get'
    // data : {
    //   page : page,
    //   size : size,
    //   condition: condition
    // }
  })
}

