import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if false, the item will hidden in breadcrumb(default is true)
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    meta: { 'title': '面板', role: ['面板'] },
    children: [{
      path: 'dashboard',
      meta:{ 'title': '子面板', role: ['面板'] },
      component: () => import('@/views/dashboard/index')
    }]
  }
];

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})


export const asyncRouterMap = [
  
  {
    path: '/company',
    redirect: '/company/announcement',
    component: Layout,
    name: 'company1',
    meta: { title: '公司管理',role:['公司管理'], icon: 'example' },
    children: [
      {
        path: 'announcement',
        name: 'announcement',
        component: () => import('@/views/companyManage/announcement/index'),
        meta: { title: '公告管理',role:['公告管理'] }
      },
      // {
      //   path: 'moneyReceiveCode',
      //   name: 'moneyReceiveCode',
      //   component: () => import('@/views/companyManage/moneyReceiveCode/index'),
      //   meta: { title: '收款码新增',role:['收款码新增'] }
      // },
      {
        path: 'team',
        name: 'team',
        redirect: '/team',
        component: () => import('@/views/companyManage/team/index'),
        meta: { title: '团队管理',role:['团队管理'], breadcrumb: false },
        children:[
          {
            path: 'teams',
            name: 'teams',
            component: () => import('@/views/companyManage/team/teams/index'),
            meta: { title: '所有团队',role:['所有团队'] },
          },
          {
            path: 'teamAdd',
            name: 'teamAdd',
            component: () => import('@/views/companyManage/team/addTeam/index'),
            meta: { title: '添加团队',role:['添加团队'], breadcrumb: false },
            breadcrumb: false
          }
        ]
      },
      {
        path: 'adminManage',
        component: () => import('@/views/userCenter/adminManage/index'),
        name: 'adminManage',
        meta: { title: '管理员管理',role:['管理员管理'] },
        children: [
          {
            path: 'addAdmin',
            name: 'addAdmin',
            component: () => import('@/views/userCenter/adminManage/addAdmin/index'),
            meta: { title: '添加管理员',role:['添加管理员'] }
          },
          {
            path: 'admins',
            name: 'admins',
            component: () => import('@/views/userCenter/adminManage/admins/index'),
            meta: { title: '所有管理员',role:['所有管理员'] }
          }
        ]
      },
      {
        path: 'post',
        name: 'post',
        component: () => import('@/views/companyManage/post/index'),
        meta: { title: '岗位管理',role:['岗位管理'] },
        props: true,
        children: [
          {
            path: 'postAdd',
            name: 'permission',
            component: () => import('@/views/companyManage/post/addPost/index'),
            meta: { title: '岗位添加',role:['岗位添加'] }
          },
          {
            path: 'allpost',
            name: 'allpost',
            component: () => import('@/views/companyManage/post/allPost/index'),
            meta: { title: '所有岗位',role:['所有岗位'] }
          }
        ]
      },
      {
        path: 'privilege',
        name: 'privilege',
        component: () => import('@/views/companyManage/privilege/index'),
        meta: { title: '权限管理',role:['权限管理'] },
        children: [
          {
            path: 'permission',
            name: 'permission',
            component: () => import('@/views/companyManage/permission/allocate'),
            meta: { title: '分配权限',role:['分配权限'] }
          },
          {
            path: 'singlePermission',
            name: 'singlePermission',
            component: () => import('@/views/companyManage/permission/singlePermission/index'),
            meta: { title: '某个职位权限查询',role:['某个职位权限查询'] }
          },
          {
            path: 'allPermission',
            name: 'allPermission',
            component: () => import('@/views/companyManage/permission/allPermission/index'),
            meta: { title: '所有权限查询',role:['所有权限查询'] }
          }
        ]
      },
      {
        path: 'bankCard',
        name: 'bankCard',
        component: () => import('@/views/companyManage/bankCard/index'),
        meta: { title: '银行卡管理',role:['银行卡管理'] },
        children: [
          {
            path: 'cardAdd',
            name: 'cardAdd',
            component: () => import('@/views/companyManage/bankCard/cardAdd/index'),
            meta: { title: '公司添加银行卡', role: ['公司添加银行卡'] },
            breadcrumb: false
          },
          {
            path: 'cards',
            name: 'cards',
            component: () => import('@/views/companyManage/bankCard/cards/index'),
            meta: { title: '公司所有银行卡', role: ['公司所有银行卡'] },
          },
         
          {
            path: 'personalCards',
            name: 'personalCards',
            component: () => import('@/views/companyManage/bankCard/personalCards/index'),
            meta: { title: '所有个人银行卡', role: ['所有个人银行卡'] },
            breadcrumb: false
          }
        ]
      },
      {
        path: 'riskControl',
        name: 'riskControl',
        component: () => import('@/views/companyManage/riskControl/index'),
        meta: { title: '风控管理',role:['风控管理'] }
      },
      {
        path: 'settings',
        name: 'settings',
        component: () => import('@/views/companyManage/settings/index'),
        meta: { title: '系统设置',role:['系统设置'] }
      }
    ]
  },

  {
    path: '/user',
    redirect: '/user/agency',
    component: Layout,
    name: 'user',
    meta: {
      title: '用户中心',role:['用户中心'],
      icon: 'team'
    },
    children: [
      {
        path: 'userInfo',
        name: 'userInfo',
        component: () => import('@/views/userCenter/index'),
        meta: { title: '用户信息', role: ['用户信息'] }
       },
      {
        path: 'agency',
        component: () => import('@/views/userCenter/agency/index'),
        name: 'agency',
        meta: { title: '代理管理',role:['代理管理'] },
        children: [
          
          {
            path: 'addAgency',
            name: 'addAgency',
            component: () => import('@/views/userCenter/agency/addAgency/index'),
            meta: { title: '添加代理',role:['添加代理'] }
          },
          {
            path: 'agencys',
            name: 'agencys',
            component: () => import('@/views/userCenter/agency/agencys/index'),
            meta: { title: '所有代理',role:['所有代理'] }
          },
          {
            path: 'peragency',
            name: 'peragency',
            component: () => import('@/views/userCenter/agency/peragency/index'),
            meta: { title: '我是代理',role:['我是代理'] }
          }
        ]
      },
      {
        path: 'supplierManage',
        component: () => import('@/views/userCenter/supplierManage/index'),
        name: 'supplierManage',
        meta: { title: '供码用户管理',role:['供码用户管理'] },
        children: [
          {
            path: 'addsupplier',
            name: 'addsupplier',  
            component: () => import('@/views/userCenter/supplierManage/addSupplier/index'),
            meta: { title: '添加供码用户',role:['添加供码用户'] }
          },
          // {
          //   path: 'waitApprovalSup',
          //   name: 'waitApprovalSup',  
          //   component: () => import('@/views/userCenter/supplierManage/waitApprovalSup/index'),
          //   meta: { title: '审批供码用户',role:['审批供码用户'] }
          // },
          {
            path: 'suppliers',
            name: 'suppliers',
            component: () => import('@/views/userCenter/supplierManage/Suppliers/index'),
            meta: { title: '所有供码用户',role:['所有供码用户'] }
          },
          // {
          //   path: 'persupplier',
          //   name: 'persupplier',
          //   component: () => import('@/views/userCenter/supplierManage/Suppliers/mySupplier'),
          //   meta: { title: '我的供码用户', role: ['供码用户'] }
          // },
          {
            path: 'persupplier',
            name: 'persupplier',
            component: () => import('@/views/userCenter/supplierManage/Suppliers/perSupplier'),
            meta: { title: '我是供码用户', role: ['我是供码用户'] }
          }    
        ]
      },
      {
        path: 'merchantManage',
        component: () => import('@/views/userCenter/merchantManage/index'),
        name: 'merchantManage',
        meta: { title: '商户管理',role:['商户管理'] },
        children: [
          {
            path: 'addmerchant',
            name: 'addmerchant',  
            component: () => import('@/views/userCenter/merchantManage/addMerchant/index'),
            meta: { title: '添加商户',role:['添加商户'] }
          },
          {
            path: 'waitApprovalMer',
            name: 'waitApprovalMer',  
            component: () => import('@/views/userCenter/merchantManage/waitApprovalMer/index'),
            meta: { title: '审批商户',role:['审批商户'] }
          },
          {
            path: 'merchants',
            name: 'merchants',
            component: () => import('@/views/userCenter/merchantManage/merchants/index'),
            meta: { title: '所有商户',role:['所有商户'] }
          },
          {
            path: 'mymerchants',
            name: 'mymerchants',
            component: () => import('@/views/userCenter/merchantManage/myMerchants/index'),
            meta: { title: '我的商户',role:['我的商户'] }
          },
          {
            path: 'permerchant',
            name: 'permerchant',
            component: () => import('@/views/userCenter/merchantManage/permerchant/index'),
            meta: { title: '我是商户',role:['我是商户'] }
          } 
        ]
      },
      {
        path: 'bankCardSetting',
        component: () => import('@/views/userCenter/bankCardSetting/index'),
        name: 'bankCardSetting',
        meta: { title: '银行卡设置',role:['银行卡设置'] },
        children: [
          {
            path: 'cardAdd',
            name: 'cardAdd',
            component: () => import('@/views/userCenter/bankCardSetting/addcard/index'),
            meta: { title: '个人添加银行卡',role:['个人添加银行卡'], breadcrumb: false },
            breadcrumb: false
          },
          {
            path: 'cardsPersonal',
            name: 'cardsPersonal',
            component: () => import('@/views/userCenter/bankCardSetting/personalcards/index'),
            meta: { title: '个人银行卡',role:['个人银行卡'] },
          },
           {
            path: 'cardskiting',
            name: 'cardskiting',
            component: () => import('@/views/userCenter/bankCardSetting/cardskiting/index'),
            meta: { title: '提现历史',role:['提现历史'] },
           }    
        ]
      },
      {
        path: 'qrcodePersonal',
        name: 'qrcodePersonal',
        component: () => import('@/views/userCenter/qrCode/index'),
        meta: { title: '获取二维码', role: ['用户中心'] },
      },
      // {
      //   path: 'test',
      //   name: 'test',
      //   component: () => import('@/views/test.htm'),
      //   meta: { title: 'test' }
      // }
    ]
  },

  {
    path: '/finance',
    component: Layout,
    redirect: '/finance/receiveCodeList',
    name: 'finance',
    meta: {
      title: '财务管理',role:['财务管理'],
      icon: 'nested'
    },
    children: [
      {
        path: 'withdrewWaiting',
        name: 'withdrewWaiting',
        component: () => import('@/views/financeManage/agencyWithdrew/index'),
        meta: { title: '抢单', role: ['抢单'] },
      },
      {
        path: 'mylist',
        name: 'mylist',
        component: () => import('@/views/financeManage/agencyWithdrew/mylist'),
        meta: { title: '处理单子', role: ['处理单子'] },
      },
      {
        path: 'cardskiting',
        name: 'cardskiting',
        component: () => import('@/views/userCenter/bankCardSetting/cardskiting/index'),
        meta: { title: '提现历史',role:['提现历史'] },
      },    
      {
        path: 'c2p',
        name: 'c2p',
        component: () => import('@/views/change/C2Pcard/index'),
        meta: { title: '公司转个人', role: ['处理单子'] },
      },
      {
        path: 'p2c',
        name: 'p2c',
        component: () => import('@/views/change/P2Ccard/index'),
        meta: { title: '个人转公司', role: ['处理单子'] },
      },
      // {
      //   path: 'qrcode',
      //   name: 'qrcode',
      //   component: () => import('@/views/change/qrcode/index'),
      //   meta: { title: '内部码账变', role: ['处理单子'] },
      // },
      // {
      //   path: 'receiveCodeList',
      //   component: () => import('@/views/financeManage/receiveCodeList/index'),
      //   redirect: '/receiveCodeList/codes',
      //   name: 'receiveCodeList',
      //   meta: { title: '收款码列表',role:['收款码列表'] },
      //   children: [
      //     {
      //       path: 'codes',
      //       name: 'codes',
      //       component: () => import('@/views/companyManage/moneyReceiveCode/codes/index'),
      //       meta: { title: '所有收款码',role:['所有收款码'] },
      //     },
      //     {
      //       path: 'codeAdd',
      //       name: 'codeAdd',
      //       component: () => import('@/views/companyManage/moneyReceiveCode/codeAdd/index'),
      //       meta: { title: '添加收款码',role:['添加收款码']},
      //     }
      //   ]
      // },
      // {
      //   path: 'bankCardList',
      //   component: () => import('@/views/companyManage/bankCard/cards/index'),
      //   name: 'bankCardList',
      //   meta: { title: '银行卡列表',role:['银行卡列表'] }
      // },

    ]
  },

  {
    path: '/report',
    redirect: '/report/receiveCodeReport',
    component: Layout,
    name: 'report',
    meta: {
      title: '报表统计',role:['报表统计'],
      icon: 'report'
    },
    children: [
      {
        path: 'receiveCodeReport',
        component: () => import('@/views/report/receiveCodeReport/index'),
        // component: () => import('@/views/report/merchantsReport/merchants/index'),
        name: 'receiveCodeReport',
        meta: { title: '收款码报表',role:['收款码报表'] }
      },
      {
        path: 'merchantsReport',
        component: () => import('@/views/report/merchantsReport/merchants/index'),
        name: 'merchantsReport',
        meta: { title: '商户报表',role:['商户报表'] }
      },
      {
        path: 'localTeamReport',
        component: () => import('@/views/report/localTeamReport/index'),
        // component: () => import('@/views/report/merchantsReport/merchants/index'),
        name: 'localTeamReport',
        meta: { title: '地方团队报表',role:['地方团队报表'] }
      },
      {
        path: 'fundReport',
        // component: () => import('@/views/report/fundReport/index'),
        component: () => import('@/views/report/fundReport/index'),
        name: 'fundReport',
        meta: { title: '资金报表',role:['资金报表'] }
      },
      {
        path: 'agencyReport',
        // component: () => import('@/views/report/agencyReport/index'),
        component: () => import('@/views/report/agencyReport/index'),
        name: 'agencyReport',
        meta: { title: '代理报表',role:['代理报表'] }
      }
    ]
  },

  {
    path: '/order',
    redirect: '/order/orderDetails',
    component: Layout,
    name: 'order',
    meta: {
      title: '订单管理',role:['订单管理'],
      icon: 'order'
    },
    children: [
      {
        path: 'orderDetails',
        component: () => import('@/views/order/orderDetails/index'),
        name: 'orderDetails',
        meta: { title: '订单明细',role:['订单明细'] }
      },
      // {
      //   path: 'withdrawOrder',
      //   component: () => import('@/views/order/withdrawOrder/index'),
      //   name: 'withdrawOrder',
      //   meta: { title: '商户提现订单',role:['商户提现订单'] }
      // },
      {
        path: 'codeChangeOrder',
        component: () => import('@/views/order/codeChangeOrder/index'),
        name: 'codeChangeOrder',
        meta: { title: '内部码帐变订单',role:['内部码帐变订单'] }
      },
      {
        path: 'cardChangeOrder',
        component: () => import('@/views/order/cardChangeOrder/index'),
        name: 'cardChangeOrder',
        meta: { title: '内部卡帐变订单',role:['内部卡帐变订单'] }
      },
    ]
  },
  { path: '*', redirect: '/404',meta:{'title':'404',role:['404']}, hidden: true }
]

