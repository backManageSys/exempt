import { asyncRouterMap, constantRouterMap } from '@/router'

/**
 * 通过meta.role判断是否与当前用户权限匹配
 * @param roles
 * @param route
 */
// function hasPermission(roles, route) {
//   if (route.meta && route.meta.roles) {
//     return roles.some(role => route.meta.roles.includes(role))
//   } else {
//     return true;
//   }
// }
function hasPermission(roles, permissionRoles) {
  // console.log('setpermission',permissionRoles,roles, roles.some(role => permissionRoles.indexOf(role) >= 0))
  if (roles.indexOf('admin') >= 0) return true // admin permission passed directly
  if (!permissionRoles) return true
  return roles.some(role => permissionRoles.indexOf(role) >= 0)
  // if (roles.some(role => permissionRoles.indexOf(role) >= 0))
  // {
  //   return true;
  // }else{

  // }
}
/**
 * 递归过滤异步路由表，返回符合用户角色权限的路由表
 * @param routes asyncRouterMap
 * @param roles
 */
function filterAsyncRouter(routes, roles) {
  const res = []
  // console.log(routes)
  // console.log(roles)
  routes.forEach(route => {
    const tmp = { ...route }
    // console.log(tmp)
    var flag = hasPermission(roles, tmp.meta.role)
    // console.log(flag,tmp)
    if (flag) {
      // console.log(tmp.meta.role)
      if (tmp.children) {
        tmp.children = filterAsyncRouter(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  // console.log('res',res)
  return res
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    }
  },
  actions: {
    GenerateRoutes({ commit }, data) {
      return new Promise(resolve => {
        const { roles } = data
        let accessedRouters
        if (roles.includes('admin')) {
          accessedRouters = asyncRouterMap
        } else {
          accessedRouters = filterAsyncRouter(asyncRouterMap, roles)
        }
        // console.log(accessedRouters,'accessedRouters')
        commit('SET_ROUTERS', accessedRouters)
        resolve()
      })
    },
    CleanRoutes({ commit }){
      commit('SET_ROUTERS',[])
    }
  }
}

export default permission
