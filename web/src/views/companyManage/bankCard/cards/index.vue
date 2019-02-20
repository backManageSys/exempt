<template>
    <div class="app-container">
    <div>公司银行卡管理</div>
    <div></div>
        <el-table
        :data="cards"
        max-height="700"
        border
        >
        <el-table-column prop="id" label="序号" align="center"></el-table-column>
        <el-table-column prop="name" label="姓名" align="center"></el-table-column>
         <el-table-column prop="bank" label="银行" align="center"></el-table-column>
         <el-table-column prop="cardNumber" label="卡号" align="center" min-width="150%"></el-table-column>
          <el-table-column prop="balance" label="余额" align="center"></el-table-column>
        <el-table-column prop="attribution" label="归属" align="center" ></el-table-column>
        <el-table-column prop="relation" label="关联" align="center" min-width="150%"></el-table-column>
        <el-table-column prop="status" label="状态" align="center"></el-table-column>
        <el-table-column label="操作" fixed="right" align="center" >
            <template scope="scope">
                <el-button size="small"
                        @click="operationDel(scope.$index,scope.row)">删除</el-button>
            </template>
        </el-table-column>

    </el-table>
    <div class="block" v-if="cards.length>10">
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
import { cardsGet,cardDelete,teamVerifyCodeCheckByTeamName } from '@/api/company'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                cards:[{
                    'attribution':'attribution',
                    'balance':'balance',
                    'bank':'bank',
                    'cardNumber':'cardNumber',
                    'id':'id',
                    'name':'name',
                    'relation':'relation',
                    'status':'status',
                    }
                ],
                currentPage:1
            }
        },
        computed:{
            total(){
                return this.cards.length;
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
                    }).then(({ value }) => {
                        console.log("asdadsadAfAfasfafafaf");
                        verifyCode = value;
                        this.operation(index,row,verifyCode);
                    }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });
                });
            },
            operation(index, row,verifyCode) {
                console.log(row);
                var flag = false;
                teamVerifyCodeCheckByTeamName(row.attribution,verifyCode).then(response=> {
                    if(response.code!=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                        // flag=true;
                        // this.newRow = JSON.parse(JSON.stringify(row));;
                        // this.newRowIndex = index;
                        // this.dialogFormVisible = true;
                        console.log(row);
                        cardDelete(row.id).then(response=> {
                            if(response.code!=200){
                                this.$message({
                                    message: response.data.description,
                                    type: 'warning'
                                });
                            }else{
                                this.$message({
                                message: '删除成功',
                                type: 'success'
                                });
                                this.cards.splice(index,1);
                            }
                        });
                    }
                });
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
              
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            },
            getData(){
                this.getcards();
            },
            getcards(){
                cardsGet().then(response=>{
                    console.log(response,'sdll')
                     if(response.data.infoCod){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       this.cards = response.data.companyCardList;
                    }
                })
            },
            handleChange(val) {
                console.log(val);
                  if(val==2)
                {
                    this.getcards();
                }
            }
        }
    }
</script>

<style scoped>

</style>
