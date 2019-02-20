<template>
    <div class="app-container">
    <div>所有个人银行卡</div>
    <div></div>
        <el-table
        :data="cards"
        max-height="700"
        border
        >
        <el-table-column prop="cardNumber" label="编号" align="center"></el-table-column>
        <el-table-column prop="user.username" label="用户名" align="center"></el-table-column>
        <el-table-column prop="user.role" label="角色" align="center">
            <template slot-scope="scope">
                <div  v-if="scope.row.user.role == 2">代理商</div>
                <div  v-else-if="scope.row.user.role == 3">商户</div>
                <div  v-else-if="scope.row.user.role == 4">供码用户</div>
            </template>
        </el-table-column>
        <el-table-column prop="name" label="姓名" align="center"></el-table-column>
        <el-table-column prop="bank" label="银行" align="center"></el-table-column>
        <el-table-column prop="accountWithBank" label="卡号" align="center" min-width="150%"></el-table-column>
        <el-table-column prop="cardBalance" label="余额" align="center"></el-table-column>
        <el-table-column prop="bin" label="开户行编号" align="center" ></el-table-column>
        <el-table-column prop="status" label="状态" align="center"></el-table-column>
        <el-table-column prop="addTime" label="添加时间" align="center"></el-table-column>
        <!-- <el-table-column label="操作" fixed="right" align="center" >
            <template scope="scope">
                <el-button size="small"
                        @click="operationDel(scope.$index,scope.row)">删除</el-button>
            </template>
        </el-table-column> -->

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
import {  personalCardsGet } from '@/api/company'
import { getTime,getTimeFormat } from "@/utils/index";
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                cards:[{
                    // 'attribution':'attribution',
                    // 'balance':'balance',
                    // 'bank':'bank',
                    // 'cardNumber':'cardNumber',
                    // 'id':'id',
                    // 'name':'name',
                    // 'relation':'relation',
                    // 'status':'status',
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
                 personalCardsGet().then(response=>{
                    console.log(response,'sdll')
                     if(response.data.infoCod){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       this.cards = response.data;
                       this.cards.forEach(el => {
                           el.addTime = getTime(el.addTime);
                       })
                    }
                })
            }
            // handleChange(val) {
            //     console.log(val);
            //       if(val==2)
            //     {
            //         this.getcards();
            //     }
            // }
        }
    }
</script>

<style scoped>

</style>
