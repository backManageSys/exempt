<template>
<div class="app-container">
    <!-- <el-tag>商户号/代理号</el-tag>
    <el-tag>下发类型(商户还是代理)</el-tag>
    <el-tag>提现金额</el-tag>
    <el-tag>账动前金额</el-tag>
    <el-tag>状态</el-tag>
    <el-tag>申请提现时间</el-tag>
    <el-tag>操作时间</el-tag>
    <el-tag>操作人(抢单人</el-tag>
    <el-tag>哪个财务)</el-tag>
    <el-tag>审核人(财务)</el-tag>
    <el-tag>备注(可以修改)</el-tag> -->

            <el-table
            :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
            :height="tableHeight"
            border
            style="width: 100%">
            <el-table-column prop="id" label="id" align="center"></el-table-column>
            <el-table-column prop="cardId" label="银行卡号" align="center"></el-table-column>
            <el-table-column prop="applicantId" label="申请人id" align="center"></el-table-column>
            <el-table-column prop="type" label="类型" align="center">
                <template slot-scope="{row}">
                    <el-tag  v-if="row.type=='merchant'" >商户提现订单</el-tag>
                    <el-tag  v-else-if="row.type=='agent'" >代理提现订单</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="money" label="提现金额" align="center"></el-table-column>
            <el-table-column prop="balance" label="账变前金额" align="center"></el-table-column>
            <el-table-column prop="state" label="状态" align="center">
                <template slot-scope="{row}">
                    <el-tag type="warning" size="small" v-if="row.state=='WAITING'">等待处理</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="applyTime" label="申请时间" align="center"></el-table-column>
            <!-- <el-table-column prop="operateId" label="抢单人id" align="center"></el-table-column> -->
            <!-- <el-table-column prop="cardId" label="卡号" align="center"></el-table-column> -->
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button size="small" type="success"
                            @click.native.prevent="getWithdrew(scope.row.id,scope.$index,teams)">抢单
                    </el-button>
                </template>
            </el-table-column>
    
        </el-table>
          <div class="block" >
            <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="pagesize"
            layout="sizes, prev, pager, next"
            :total=total>
            </el-pagination>
        </div>
</div>

</template>
<script>
  import Table from "../../../components/table/index"
  import {withdrewsWaiting,withdrewGet} from '@/api/transaction'
  import store from '../../../store'
  import {getTime} from '@/utils/index'
     export default {
            data() {
                return {
                    activeNames: ['1'],
                    labelPosition: 'right',
                    teams:[{
                        "id": 1,
                        "cardNumber": "string",
                        "name": "string",
                        "bank": "string",
                        "accountWithBank": "string",
                        "bin": "string",
                        "status": "string",
                        "user": {
                            "id": 2,
                            "username": "string",
                            "role": 3,
                            "tableId": 1,
                            "cards": []
                        }
                        }
                    ],
                    currentPage:1,
                    pagesize:10
                }
            },
            mounted:function(){
                this.tableHeight = window.innerHeight -  110;
                //window.innerHeight:浏览器的可用高度
                //this.$refs.table.$el.offsetTop：表格距离浏览器的高度
                //后面的50：根据需求空出的高度，自行调整
            },
            computed:{
                total(){
                    return this.teams.length;
                }
            },
            created(){
                this.getData();
            },
            methods: {
                getWithdrew(id,index,teams){
                    withdrewGet(id, store.getters.uid).then(response=>{
                        if(response.code!=200){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                            this.$message({
                            message: '抢单成功',
                            type: 'success'
                        });
                         this.getData();
                         
                        }
                    })
                    // teams.splice(index,1);
                    // console.log("11111111122222222222");
                    // this.teams = teams ;
                },
                handleSizeChange(val) {
                    console.log(`每页 ${val} 条`);
                    this.pagesize=val;
                  
                },
                handleCurrentChange(val) {
                    console.log(`当前页: ${val}`);
                    this.currentPage=val;
                },
                getData(){
                    this.getTeams();
                },
                getTeams(){
                    withdrewsWaiting().then(response=>{
                        console.log(response,'sdll')
                         if(response.code!=200){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                           this.teams = response.data;
                           this.teams.forEach(el => {
                               el.applyTime = getTime(el.applyTime)
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
