<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-form :label-position="labelPosition" :model="formaddParameters" class="demo-form-inline">
            <el-form-item label="个人卡号">
             <el-select  style="width: 200px" v-model="formaddParameters.cardNumber_in">
                 <el-option
                    v-for="item in cardNumber_ins"
                    :key="item.id"
                    :label="item.cardNumber"
                    :value="item.cardNumber">
                    </el-option>
            </el-select >
            </el-form-item>
            <el-form-item label="loginid">
                <el-input v-model="formaddParameters.loginid" placeholder="1.00" style="width: 30%;"></el-input>
            </el-form-item>
             <el-form-item label="金额">
                <el-input v-model="formaddParameters.money" placeholder="1.00" type="number" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="change">添加</el-button>
            </el-form-item>
        </el-form>
        


  </div>
</template>

<script>
import { permissionAllocate,cardsGet } from '@/api/company'
import { getTreePermissions } from '@/api/permissions'
import {getIds} from '@/utils/treeids'
import store from '../../../store'
import { cardsGetOne } from '@/api/personal'
import { qrcode } from '@/api/change'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                formaddParameters: {
                        cardNumber_in: "string",
                        cardNumber_out: "string",
                        money: 0,
                        operateId: 0,
                        loginid:0
                },
                currentPage:1,
                treepermissions:[],
                props: {
                    children: 'children',
                    label: 'title'
                },
                posts:[{}],
                cardNumber_ins:[{}],
                cardNumber_outs:[{}]
            }
        },
        created(){
            this.formaddParameters.operateId = store.getters.uid;
            // console.log(this.treepermissions)
            this.getData();
            
        },
        methods: {
            getPerCards(){
                cardsGetOne(store.getters.uid).then(response=>{
                    if(response.code!=200){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                            this.cardNumber_outs = response.data;
                            console.log(this.cardNumber_outs,'this.cardNumber_outs')
                        }
                })

            },
            getCompanyCards(){
                cardsGet().then(response=>{
                        console.log(response,'response')
                         if(response.code!=200){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                            this.cardNumber_ins = response.data.companyCardList;
                            console.log(' this.cardNumber_ins', this.cardNumber_ins)
                        }
                    })
            },
            getData(){
                this.getPerCards();
                this.getCompanyCards();
            },
            change() {
                qrcode(
                    this.formaddParameters.cardNumber_in,
                    this.formaddParameters.cardNumber_out,
                    this.formaddParameters.money,
                    this.formaddParameters.operateId,
                    ).then(response=>{
                    if(response.data.infoCode){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                        this.$message({
                            message: '添加成功',
                            type: 'success'
                        });
                    }
                })
            },
            getCheckedKeys(){
                this.formaddParameters.permission = this.$refs.tree.getCheckedKeys();
                this.formaddParameters.permission = this.formaddParameters.permission.concat(this.$refs.tree.getHalfCheckedKeys());
                // console.log(this.formaddParameters.permission,this.formaddParameters.permission1 )
            },
            handleChange(val) {
                console.log(val);
                  if(val==2)
                {
                    this.getpermissions();
                }
            }
        }
    }
</script>

<style scoped>

</style>
