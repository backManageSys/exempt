import { asyncRouterMap, constantRouterMap } from '@/router'
import { removeRole,removeUid,removeUname } from '@/utils/auth'

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
function hasPermission(permissionRoles,roles) {
  if (roles.indexOf('admin') >= 0 ) return true // 第二次 判断后台传来的权限树是否有admin这个总管理标记，有的话直接传递的管理权限
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
  const res = [];
  routes.forEach(route => {
    const tmp = { ...route };
    var flag = hasPermission( tmp.meta.role,roles);
    if (flag) {
      if (tmp.children) {
        tmp.children = filterAsyncRouter(tmp.children, roles)
      }
      res.push(tmp)
    }
  });
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
        if (roles.includes('admin')) { // 判断后台传来的权限树是否有admin这个总管理标记。如果有则给其所有权限
          accessedRouters = asyncRouterMap
        } else { // 如果没有。则过滤出——符合当前账号的权限
          accessedRouters = filterAsyncRouter(asyncRouterMap, roles)
        }
        commit('SET_ROUTERS', accessedRouters)  // 调用store中方法。将过滤后的菜单权限发送给state中addRouters
        resolve()
      })
    },
    CleanRoutes({ commit }){
      removeRole();//退出删除cookie的role
      removeUid();//退出删除cookie的uid
      removeUname();//退出删除cookie的un ——uname
      commit('SET_ROUTERS',[])
    }
  }
}

export default permission
