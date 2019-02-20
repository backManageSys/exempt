import request from '@/utils/request'

export function addAdmin(code, operator, password, post, status, team, username) {
  return request({
    url: '/admin/add',
    method: 'post',
    data: {
      code,
      operator,
      password,
      post,
      status,
      team,
      username
    }
  })
}
export function addAgent(alipay,
  password,
  status,
  username,
  wechat) {
  return request({
    url: '/agent/add',
    method: 'post',
    data: {
      alipay,
      password,
      status,
      username,
      wechat
    }
  })
}
export function addMerchant(
  applyId,
  level,
  password,
  username,
  status,
  wechat,
   alipay_TPASS,
   alipay_TSOLID,
   alipay_RPASSOFF,
   alipay_RPASSQR,
   alipay_RSOLID
  ) {
  return request({
    url: '/merchant/add',
    method: 'post',
    data: {
      applyId,
      level,
      password,
      username,
      status,
      wechat,
      alipay_TPASS,
      alipay_TSOLID,
      alipay_RPASSOFF,
      alipay_RPASSQR,
      alipay_RSOLID
    }
  })
}
export function addSupplier(id, level, password, username) {
  return request({
    url: '/supplier/add',
    method: 'post',
    data: {
      id,
      level,
      password,
      username
    }
  })
}
export function deleteAdmin(aid) {
  return request({
    url: '/admin/delete',
    method: 'get',
    params: {
      aid
    }
  })
}
export function deleteAgent(aid) {
  return request({
    url: '/agent/delete',
    method: 'get',
    params: {
      aid
    }
  })
}
export function deleteMerchant(aid) {
  return request({
    url: '/merchant/delete',
    method: 'get',
    params: {
      aid
    }
  })
}
export function updateMerchant(uid,
  level,
  name,
  password,
  status,
  wechat,
  alipay_TPASS,
  alipay_TSOLID,
  alipay_RPASSOFF,
  alipay_RPASSQR,
  alipay_RSOLID
  ) {
  return request({
    url: '/merchant/update/' + uid,
    method: 'put',
    data: {
      level,
      name,
      password,
      status,
      wechat,
      alipay_TPASS,
      alipay_TSOLID,
      alipay_RPASSOFF,
      alipay_RPASSQR,
      alipay_RSOLID
    }
  })
}
export function updateAgent(uid, alipay,
  name,
  password,
  status,
  wechat) {
  return request({
    url: '/agent/update/' + uid,
    method: 'put',
    data: {
      alipay,
      name,
      password,
      status,
      wechat
    }
  })
}
export function updateStaff(uid,
  name,
  password,
  status,
  team,
  post) {
  return request({
    url: '/staff/update/' + uid,
    method: 'put',
    data: {
      name,
      password,
      status,
      team,
      post
    }
  })
}
export function deleteSupplier(sid) {
  return request({
    url: '/supplier/delete',
    method: 'get',
    params: { sid }
  })
}
export function adminsGet() {
  return request({
    url: '/admins',
    method: 'get'
  })
}
export function agentsGet() {
  return request({
    url: '/agents',
    method: 'get'
  })
}
export function merchantsGet() {
  return request({
    url: '/merchants',
    method: 'get'
  })
}
export function merchantsMy(id) {
  return request({
    url: '/myMerchants/' + id,
    method: 'get'
  })
}
export function suppliersGet() {
  return request({
    url: '/suppliers',
    method: 'get'
  })
}
export function supplierUpdate(codeType, level,name, password,status, uid) {
  return request({
    url: '/supplier/update/' + uid,
    method: 'put',
    data: {
      codeType,
      level,
      name,
      password,
      status
    }
  })
}
export function deviceUpdate(id,imei,status) {
  return request({
    url: '/app/device',
    method: 'get',
    params: {
      id,
      imei,
      status
    }
  })
}
export function deviceInfo(id,){

}

export function qrcode() {
  return request({
    url: '/internalaccountchange/qrcode',
    method: 'post'
    // data: {
    //   cardNumber, loginId, money, operateId
    // }
  })
}
export function Pcard(cardNumber_in, cardNumber_out, money, operateId) {
  return request({
    url: '/internalaccountchange/C2Pcard',
    method: 'post',
    data: {
      cardNumber_in, cardNumber_out, money, operateId
    }
  })
}
export function Ccard(cardNumber_in, cardNumber_out, money, operateId) {
  return request({
    url: '/internalaccountchange/P2Ccard ',
    method: 'post',
    data: {
      cardNumber_in, cardNumber_out, money, operateId
    }
  })
}
export function withdrew(cardId,id,money,type) {
  return request({
    url: '/withdrew',
    method: 'post',
    data: {
      cardId,
      id,
      money,
      type
    }
  })
}
export function withdrewHistory() {
  return request({
    url: '/withdrew/history',
    method: 'get'
    // data: {
    //   cardId,
    //   id,
    //   money,
    //   type
    // }
  })
}
