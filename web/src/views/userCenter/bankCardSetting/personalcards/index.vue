<template>
        <div class="app-container">
        <div>个人银行卡</div>
            <el-table
            :data="teams"
            height="250"
            border
            style="width: 100%">
            <el-table-column prop="cardNumber" label="编号" ></el-table-column>
            <el-table-column prop="accountWithBank" label="银行卡号" ></el-table-column>
            <el-table-column prop="bank" label="归属银行" ></el-table-column>
            <el-table-column prop="bin" label="开户行编号" ></el-table-column>
            <!-- <el-table-column prop="cardNumber" label="數字" width="180"></el-table-column> -->
            <el-table-column prop="name" label="姓名" ></el-table-column>
            <el-table-column prop="status" label="状态" ></el-table-column>
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
        import { cardAdd,cardsGetOne } from '@/api/personal'
        import store from '../../../../store'
        export default {
            data() {
                return {
                    activeNames: ['1'],
                    labelPosition: 'right',
                    teams:[{
                        // "accountWithBank": "string",
                        // "bank": "string",
                        // "bin": "string",
                        // "cardNumber": "string",
                        // "id": 0,
                        // "name": "string",
                        // "status": "string"
                        }
                    ],
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
            },
            methods: {
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
                    console.log('asdasdasd',store.getters.uid)
                    cardsGetOne(store.getters.uid).then(response=>{
                        console.log(response,'sdll')
                         if(response.code!=200){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                           this.teams = response.data;
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
    