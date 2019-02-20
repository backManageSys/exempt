import request from '@/utils/request'

export function teamAdd(area,operator,status,supervisor,teamName,verifyCode) {
  return request({
    url: '/company/team/add',
    method: 'post',
    data: {
      area,
      operator,
      status,
      supervisor,
      teamName,
      verifyCode
    }
  })
}
export function teamsGet() {
  return request({
    url: '/company/teams',
    method: 'get'
  })
}
