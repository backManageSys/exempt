<template>
        <div class="app-container">
        <div>待审批商户</div>
            <el-table
            :data="teams"
            height="450"
            border
            style="width: 100%">
            <el-table-column prop="mid" label="商户id"  align="center"></el-table-column>
            <el-table-column prop="user.username" label="用户名"  align="center"></el-table-column>
            <el-table-column label="支付宝点位"  align="center">
                  <template scope="scope">
                        <el-button size="small" @click="alipayRate(scope.row)">查看</el-button>
                    </template>
            </el-table-column>
            <el-table-column prop="wechatp" label="微信点位"  align="center"></el-table-column>
            <!-- <el-table-column prop="approverId" label="审核人id"  align="center"></el-table-column> -->
            <el-table-column prop="priority" label="等级"  align="center"></el-table-column>
            <!-- <el-table-column prop="user.password" label="密码"  align="center"></el-table-column> -->
            <el-table-column prop="applyId" label="代理商id"  align="center"></el-table-column>
            <el-table-column prop="status" label="状态"  align="center"></el-table-column>
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
            :page-sizes="[10, 20, 30, 40]"
            :page-size="10"
            layout="sizes, prev, pager, next"
            :total=total>
            </el-pagination>
        </div>
         <el-dialog title="支付宝点位信息" :visible.sync="alipayRateDialogFormVisible">
            <el-form  :model="newRow"  label-width="30%">
                <el-form-item label="转账通码点位:">
                    <div>{{'&#12288;'+newRow.alipay_TPASS+"%"}}</div>
                </el-form-item>
                <el-form-item label="转账固码点位:">
                    <div>{{'&#12288;'+newRow.alipay_TSOLID+"%"}}</div>
                </el-form-item>
                <el-form-item label="收款通码离线码点位:">
                    <div>{{'&#12288;'+newRow.alipay_RPASSOFF+"%"}}</div>
                </el-form-item>
                <el-form-item label="收款通码在线码点位:">
                    <div>{{'&#12288;'+newRow.alipay_RPASSQR+"%"}}</div>
                </el-form-item>
                <el-form-item label="收款固码(二开)点位:">
                    <div>{{'&#12288;'+newRow.alipay_RSOLID+"%"}}</div>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="alipayRateDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="alipayRateDialogFormVisible = false">确 定</el-button>
            </div>
    </el-dialog>
      </div>
    </template>
    
    <script>
    import { waitApprovalMer, ApprovalMer } from '@/api/company'
    import store from '../../../../store'
        export default {
            data() {
                return {
                    activeNames: ['1'],
                    labelPosition: 'right',
                    err: '&#12288;',
                    teams:[{
                        alipay:0,
                        approverId:0,
                        level:0,
                        password:0,
                        status:0,
                        username:0,
                        wechat:0,
                        mid:0,
                        }
                    ],
                    newRow:{
                     
                    },
                    alipayRateDialogFormVisible :false,
                    currentPage:1
                }
            },
            computed:{
                total(){
                    return this.teams.length;
                }
            },
            created(){
                this.getData();
                // this.
            },
            methods: {
                alipayRate(row){
                    this.newRow = row ;
                    this.alipayRateDialogFormVisible = true;
                },
                approval(index, row,status) {
                    // console.log(row);
                    ApprovalMer(
                            row.alipay,
                            store.getters.uid,
                            row.priority,
                            row.user.password,
                            status,
                            row.user.username,
                            row.wechat,
                            row.mid).then(response=> {
                        if(response.data.infoCode == 200){
                            if(status == 1)
                                this.$message({
                                    message: "审批通过",
                                    type: 'success'
                                });
                           if(status == 0)
                                this.$message({
                                    message: "审批不通过",
                                    type: 'warning'
                                });
                        }else{
                          this.$message({
                           message: '审批失败',
                          type: 'error'
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
                    waitApprovalMer().then(response=>{
                        console.log(response,'sdll')
                         if(response.code!=200){
                            this.$message({
                                message: "获取待审批商户失败",
                                type: 'error'
                            });
                        }else{
                           this.teams = response.data;
                           this.teams.forEach(el => {
                               el.approverId = store.getters.uid;
                               el.mid = el.id;
                               el.alipayp = el.alipay+'%';
                               el.wechatp = el.wechat+'%';
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
    