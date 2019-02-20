const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  roles: state => state.user.roles,
  role: state => state.user.role,
  uid: state => state.user.uid,
  permission_routers: state => state.permission.routers,
  addRouters: state => state.permission.addRouters,
}
export default getters
