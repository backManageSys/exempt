export function getTreePermissions(){
    return  [{
    title: '公司管理', icon: 'example' ,
    children: [
        {
            title: '公告管理',
        },
        {
            title: '团队管理' ,
            children: [
                {
                    title: '所有团队' ,
                },
                {
                    title: '添加团队' ,
                }
            ]
        },
        {
            title: '管理员管理' ,
            children: [
                {
                    title: '添加管理员',
                },
                {
                    title: '所有管理员',
                }
            ]
        },
        {
            title: '岗位管理' ,
            props: true,
            children: [
                {
                    title: '岗位添加',
                },
                {
                    title: '所有岗位',
                }
            ]
        },
        {
            title: '权限管理' ,
            children: [
                {
                    title: '分配权限',
                },
                {
                    title: '某个职位权限查询',
                },
                {
                    title: '所有权限查询',
                }
            ]
        },
        {
            title: '银行卡管理' ,
            children: [
                {
                    title: '公司所有银行卡' ,
                },
                {
                    title: '公司添加银行卡' ,
                },
                {
                    title: '所有个人银行卡' ,
                },
            ]
        },
        {
            title: '风控管理',
        },
        {
            title: '系统设置',
        }
    ]
},
{

    title: '用户中心',
    children: [
        {
            title: '用户信息',
        },
        {
            title: '代理管理' ,
            children: [
                {
                    title: '添加代理',
                },
                {
                    title: '所有代理',
                },
                {
                    title: '我是代理',
                }
            ]
        },
        {
            title: '供码用户管理' ,
            children: [
                {
                    title: '添加供码用户',
                },
                // {
                //     title: '审批供码用户',
                // },
                {
                    title: '所有供码用户',
                },
                {
                    title: '我是供码用户',
                }
            ]
        },
        {
            title: '商户管理' ,
            children: [
                {
                    title: '添加商户',
                },
                {
                    title: '审批商户',
                },
                {
                    title: '所有商户',
                },
                {
                    title: '我是商户',
                },
                {
                    title: '我的商户',
                }
            ]
        },
        {
            title: '银行卡设置' ,
            children: [
                {
                    title: '个人所有银行卡' ,
                },
                {
                    title: '个人添加银行卡' ,
                },
                {
                    title: '个人银行卡' ,
                },{
                    title:'提现历史'
                }
            ]
        },
    ]
},
{

    title: '财务管理',
    children: [
                {
                    title: '抢单',
                },
                {
                    title: '处理单子',
                },
                {
                    title:'提现历史'
                }
            ]
        
        
    
},
{
    title: '报表统计',
    children: [
        {
            title: '收款码报表',
        },
        {
            title: '商户报表',
        },
        {
            title: '地方团队报表',
        },
        {
            title: '资金报表',
        },
        {
            title: '代理报表',
        }
    ]
},
{
    title: '订单管理',
    children: [
        {
            title: '订单明细',
        },
        // {
        //     title: '商户提现订单',
        // },
        {
            title: '内部码帐变订单',
        },
        {
            title: '内部卡帐变订单',
        },
    ]
}];
}