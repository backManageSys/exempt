<template>
    <div class="app-container">
        <el-table
        :data="codes"
        height="250"
        border
        style="width: 100%">
        <el-table-column prop="accountInfo" label="账户信息" align="center"></el-table-column>
        <el-table-column prop="accountNumber" label="账户号码" align="center" ></el-table-column>
        <el-table-column prop="duration" label="时间" align="center"></el-table-column>
        <el-table-column prop="priorityLevel" label="优先等级" align="center"></el-table-column>
        <el-table-column prop="teamName" label="团队名" align="center"></el-table-column>
        <el-table-column prop="type" label="类型" align="center"></el-table-column>
        <el-table-column label="操作" fixed="right" >
            <template scope="scope">
                <el-button size="small"
                    @click="operationDel(scope.$index,scope.row)">删除</el-button>
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
import { codesGet,codeDelete } from '@/api/company'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                codes:[{
                    'accountInfo':'accountInfo',
                    'accountNumber':'accountNumber',
                    'duration':'duration',
                    'id':'id',
                    'priorityLevel':'priorityLevel',
                    'teamName':'teamName',
                    'type':'type',
                    }
                ],
                currentPage:1
            }
        },
        computed:{
            total(){
                return this.codes.length;
            }
        },
        created(){
            this.getData();
        },
        methods: {
              operationDel(index, row){
                var verifyCode = '';
                this.$prompt('请输入团队验证码', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    // inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
                    // inputErrorMessage: '邮箱格式不正确'
                }).then(({ value }) => {
                    // this.$message({
                    //     type: 'success',
                    //     message: '你的邮箱是: ' + value
                    // });
                    verifyCode = value;
                    operation(index, row,verifyCode);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });       
                })
            },
            operation(index,row,verifyCode){
                codeDelete(row.id,verifyCode).then(response=>{
                    console.log(response,'sdll')
                     if(response.code!=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       this.codes = response.data;
                    
                    }
                })
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
              
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            },
            getData(){
                this.getcodes();
            },
            getcodes(){
                codesGet().then(response=>{
                    console.log(response,'sdll')
                     if(response.code!=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       this.codes = response.data;
                    }
                })
            },
            handleChange(val) {
                console.log(val);
                  if(val==2)
                {
                    this.getcodes();
                }
            }
        }
    }
</script>

<style scoped>

</style>
