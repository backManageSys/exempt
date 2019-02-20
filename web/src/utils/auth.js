import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const Uid = '0'
const RolesKey = []
const RoleKey = 0

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getRoles() {
  return Cookies.get(RolesKey)
}

export function setRoles(Roles) {
  return Cookies.set(RolesKey, Roles)
}

export function removeRoles() {
  return Cookies.remove(RolesKey)
}
export function getRole() {
  return Cookies.get(RoleKey)
}

export function setRole(Role) {
  return Cookies.set(RoleKey, Role)
}

export function removeRole() {
  return Cookies.remove(RoleKey)
}

export function getUid() {
  return Cookies.get(Uid)
}

export function setUid(token) {
  return Cookies.set(Uid, token)
}

export function removeUid() {
  return Cookies.remove(Uid)
}

// export function getName() {
//   return Cookies.get(Name)
// }

// export function setName(Name) {
//   return Cookies.set(Name, token)
// }

// export function removeName() {
//   return Cookies.remove(Name)
// }