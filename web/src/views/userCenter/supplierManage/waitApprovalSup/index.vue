<template>
        <div class="app-container">
        <div>待审批供码用户</div>
            <el-table
            :data="teams"
            height="450"
            border
            style="width: 100%">
            <!-- <el-table-column prop="alipayName" label="支付宝用户名"  align="center"></el-table-column>
            <el-table-column prop="alipayUserId" label="支付宝ID"  align="center"></el-table-column> -->

            <!-- <el-table-column label='设备'>
               <el-table-column prop="devices.id" label="设备序号"  align="center"></el-table-column>
               <el-table-column prop="devices.imei" label="设备信息"  align="center"></el-table-column>

            </el-table-column>    -->
            <!-- <el-table-column prop="id" label="id"  align="center"></el-table-column> -->
            <!-- <el-table-column prop="applicantId" label="登录ID"  align="center"></el-table-column> -->
            <el-table-column prop="status" label="状态"  align="center"></el-table-column>
            <el-table-column prop="time" label="时间"  align="center"></el-table-column>
            <el-table-column label="用户信息" align="center">
                <!-- <el-table-column prop="user.avatarUrl" label="avatarUrl"  align="center"></el-table-column> -->
                <!-- <el-table-column prop="user.id" label="id"  align="center"></el-table-column> -->
                <!-- <el-table-column prop="user.password" label="密码"  align="center"></el-table-column> -->
                <el-table-column prop="user.role" label="角色"  align="center"></el-table-column>
                <!-- <el-table-column prop="user.tableId" label="tableId"  align="center"></el-table-column> -->
                <el-table-column prop="user.username" label="用户名"  align="center"></el-table-column>
            </el-table-column>
            <el-table-column label="操作" width="280" align="center">
                    <template scope="scope">
                        <el-button size="small"
                                @click="approval(scope.$index,scope.row,1)">审批通过</el-button>
                        <el-button size="small"
                                @click="approval(scope.$index,scope.row,0)">审批不通过</el-button>
                    </template>
                </el-table-column>
        </el-table>
        <div class="block">
            <span class="demonstration">调整每页显示条数</span>
            <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-sizes="[100, 200, 300, 400]"
            :page-size="100"
            layout="sizes, prev, pager, next"
            :total=total>
            </el-pagination>
        </div>
      </div>
    </template>
    
    <script>
    import { waitApprovalSup, ApprovalSup } from '@/api/company'
    import store from '../../../../store'
    import {getTime} from '@/utils/index'
        export default {
            data() {
                return {
                    activeNames: ['1'],
                    labelPosition: 'right',
                    
                    teams:[{
                        "id": 2,
                        "user": {
                            "id": 36,
                            "username": "手动阀手动阀",
                            "password": "$2a$10$T3C8CzyJMdLg4clNLO/sIeSZGodzzzI.W/Oan6/tizWfiVvRHwoWq",
                            "role": "4",
                            "tableId": 2,
                            "cards": []
                        },
                        "applicantId": 1,
                        "time": "Jan 18, 2019 1:47:19 PM",
                        "approverId": 0,
                        "status": "启用",
                        "devices": [],
                        "priority": 0,
                        "codeType": "NONE"
                        }
                    ],
                    currentPage:1
                }
            },
            computed:{
                total(){
                    return this.teams.length
                }
            },
            created(){
                this.getData();
            },
            methods: {
                approval(index, row,state) {
                    console.log(row);
                    row.state = state;
                    ApprovalSup(
                            row.id,
                            row.priority,
                            row.user.password,
                            row.state,
                            row.user.username,
                            row.sid
                        ).then(response=> {
                        if(response.data.infoCode){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                          this.$message({
                           message: '审批成功',
                          type: 'success'
                          });
                        }
                       });
                       this.teams.splice(index,1)

                },
                handleSizeChange(val) {
                    console.log(`每页 ${val} 条`);
                  
                },
                handleCurrentChange(val) {
                    console.log(`当前页: ${val}`);
                },
                getData(){
                    this.getTeams();
                },
                getTeams(){
                    waitApprovalSup().then(response=>{
                        console.log(response,'sdll')
                         if(response.data.infoCod){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                           this.teams = response.data;
                           this.teams.forEach(el => {
                               el.sid = el.id;
                               el.time = getTime(el.time)
                           });
                        }
                    })
                },
                handleChange(val) {
                    console.log(val);
                      if(val==2)
                    {
                        this.getTeams();
                    }
                }
            }
        }
    </script>
    
    <style scoped>
    
    </style>
    