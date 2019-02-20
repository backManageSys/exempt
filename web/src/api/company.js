import request from '@/utils/request'

export function GetAnnouncement(){
  return request({
    url: '/company/announcement/get',
    method: 'get',
  })
}
export function SetAnnouncement(announcement){
  return request({
    url: '/company/announcement/set',
    method: 'get',
    params: {
      announcement
    }
  })
}
export function codeAdd(duration,info,number,priority,team,type) {
  return request({
      url: '/company/code/add',
        method: 'post',
        data: {
            duration,
            info,
            number,
            priority,
            team,
            type
        }
  })
}
export function codeInfo(id) {
    return request({
        url: '/company/code/info/'+id,
        method: 'get'
    })
}
export function codeDelete(id,verifyCode) {
    return request({
        url: '/company/code/delete/'+id,
        method: 'get',
        params:{
          verifyCode
        }
    })
}
export function permissionAllocate(post,permissions) { 
    return request({
        url: '/company/permission/allocate',
        method:'post',
        data:{
            post,
            permissions
        }
    })
 }
export function checkSinglePermission(post) {
  return request({
    url: '/company/permission',
    method: 'get',
    params: {
      post
    }
  })
}
export function checkAllPermission() {
  return request({
    url: '/company/permissions',
    method: 'get',
    params: {
      
    }
  })
}
export function codesGet() {
  return request({
    url: '/company/codes',
    method: 'get'
  })
}
export function waitApprovalMer() {
  return request({
    url: '/company/approval/merchants',
    method: 'get',
    params: {
    }
  })
}
export function ApprovalMer(
  alipay,
  approverId,
  level,
  password,
  status,
  username,
  wechat,mid) {
  return request({
    url: '/company/approval/merchant/'+mid,
    method: 'put',
    data: {
      alipay,
      approverId,
      level,
      password,
      status,
      username,
      wechat
    }
  })
}
export function waitApprovalSup() {
  return request({
    url: '/company/approval/suppliers',
    method: 'get',
    params: {
    }
  })
}
export function ApprovalSup(
  id,
  level,
  password,
  state,
  username,
  sid) {
  return request({
    url: '/company/approval/supplier/'+sid,
    method: 'put',
    data: {
      id,// 0,		// 审核人的userid
      level,// 0,
      password,// "string",
      state,// 0,	// 1通过申请/0拒绝
      username// "string"
    }
  })
}
export function cardAdd(attribution,balance,bank,name,number,relation,status) {
  return request({
    url: '/company/card/add',
    method: 'post',
    data: {
      attribution,
      balance,
      bank,
      name,
      number,
      relation,
      status
    }
  })
}
export function cardsGet() {
  return request({
      url: '/company/cards',
        method: 'get',
  })
}
export function cardDelete(id) {
  console.log(id)
  return request({
      url: '/company/card/delete/'+id,
      method: 'get',
  })
}
export function teamVerifyCodeCheckByTeamName(teamName,verifyCode) {
  return request({
    url: '/company/team/verifybyteamname',
    method: 'get',
    params:{
      teamName,
      verifyCode
    }
  })
}

export function personalCardsGet() {
  return request({
      url: '/usr/cards',
      method: 'get',
  })
}
/**
 * 
 * @param {岗位} post 
 */
export function addPost(post) {
  return request({
    url: '/company/post/add',
    method: 'get',
    params: {
      post,
    }
  })
}
export function deletePost(id) {
  return request({
    url: '/company/post/delete/'+id,
    method: 'get'
  })
}
export function postGet() {
  return request({
    url: '/company/post/list',
    method: 'get',
    params: {
    }
  })
}


export function teamAdd(area, operator, status, supervisor, teamName, verifyCode) {
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
export function teamDelete(id, verifyCode) {
  return request({
    url: '/company/team/delete/'+id,
    method: 'get',
    params: {
      id,
      verifyCode
    }
  })
}
export function teamVerifyCodeCheck(id,verifyCode) {
  return request({
    url: '/company/team/verify/'+id,
    method: 'get',
    params:{
      id,
      verifyCode
    }
  })
}
export function teamUpdate(area, operator, status, supervisor, teamName, verifyCode,id) {
  return request({
    url: '/company/team/update/'+id,
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
export function supplierUpdate(codeType, level, password, id) {
  return request({
    url: '/supplier/update'+id,
    method: 'post',
    data: {
      codeType,
      level,
      password
    }
  })
}
export function titleUpdate(title) {
  return request({
    url: '/company/sys/update',
    method: 'post',
    data: {
      title,
    }
  })
 }
export function titleList(){
  return request({
    url: '/company/sys/list',
    method: 'get',
  })
}
export function GetRiskcontrol(){
  return request({
    url: '/company/riskcontrol/get',
    method: 'get',
  })
}
export function SetRiskcontrol(newtime){
  return request({
    url: '/company/riskcontrol/set',
    method: 'get',
    params: {
      newtime
    }
  })
}
