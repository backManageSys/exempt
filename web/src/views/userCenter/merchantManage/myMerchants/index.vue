<template>
    <div class="app-container">
    <div>我的商户</div>
     <el-input v-model="searchStr" suffix-icon="el-icon-search" placeholder="请输入搜索内容"></el-input>
        <el-table
        :data="filterData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        border
        style="width: 100%">
        <!-- <el-table-column prop="user.username" label="用户名"  align="center"></el-table-column> -->
        <el-table-column prop="name" label="商户名"  align="center"></el-table-column>
        <el-table-column prop="balance" label="余额"  align="center"></el-table-column>
        <el-table-column prop="applyId" label="代理商id"  align="center"></el-table-column>
        <el-table-column prop="addTimep" label="申请时间"  align="center"></el-table-column>
        <el-table-column prop="statusp" label="状态"  align="center">
             <template slot-scope="{row}">
            <el-button type="success" size="small" v-if="row.status=='启用'">启用</el-button>
            <!-- <el-tag type="success" v-if="row.status=='审批通过'">{{ row.approvalTime }}</el-tag> -->
            <el-button type="info" size="small" v-else-if="row.status=='停用'">停用</el-button>
             <!-- <el-tag type="warning" v-if="row.status=='等待审批'">{{ row.addTime }}</el-tag> -->
            </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" align="center" >
            <template scope="scope" >
                <el-button size="small" 
                        @click="openDialog(scope.$index,scope.row)">查看或修改</el-button>
            </template>
        </el-table-column>
        <!-- <el-table-column prop="approvalTime" label="审批时间"  align="center"></el-table-column> -->
        

    </el-table>
    <div class="block">
        <span class="demonstration">调整每页显示条数</span>
        <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pagesize"
        layout="sizes, prev, pager, next"
        :total="total">
        </el-pagination>
    </div>
        <el-dialog title="查看或修改商户信息" :visible.sync="dialogFormVisible">
            <el-form ref="form" :model="newRow.user" :rules="addRules" label-width="13%">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="newRow.user.username" placeholder="用户名" style="width:90%;"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="newRow.user.password" type="password" placeholder="密码" style="width:90%;"></el-input>
                </el-form-item>
                <el-form-item label="状态" >
                    <el-select v-model="newRow.user.status" placeholder="启用" style="width:20%;">
                    <el-option label="启用" value="启用"></el-option>
                    <el-option label="停用" value="停用"></el-option>
                    </el-select>
                </el-form-item> 
                <el-form-item label="等级">
                    <el-select v-model="newRow.priority" placeholder="请选择" style="width:10%;">
                        <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="支付宝点位">
                    <el-button type="primary" @click="alipayRateDialogFormVisible = true">查看或修改支付宝点位</el-button>  
                </el-form-item>
                <el-form-item label="微信点位">
                    <el-input v-model="newRow.wechat" placeholder="微信点位" style="width:10%;"></el-input>%
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateSupplier('form')">确 定</el-button>
            </div>
    </el-dialog>
      <el-dialog title="支付宝点位信息" :visible.sync="alipayRateDialogFormVisible">
            <el-form  :model="newRow"  label-width="30%">
                <el-form-item label="转账通码点位">
                    <el-input v-model="newRow.alipay_TPASS" style="width: 30%;"  placeholder="转账通码点位"></el-input>%
                </el-form-item>
                <el-form-item label="转账固码点位">
                    <el-input v-model="newRow.alipay_TSOLID" style="width: 30%;"  placeholder="转账固码点位"></el-input>%
                </el-form-item>
                <el-form-item label="收款通码离线码点位">
                    <el-input v-model="newRow.alipay_RPASSOFF" style="width: 30%;"  placeholder="收款通码离线码点位"></el-input>%
                </el-form-item>
                <el-form-item label="收款通码在线码点位">
                    <el-input v-model="newRow.alipay_RPASSQR" style="width: 30%;"  placeholder="收款通码在线码点位"></el-input>%
                </el-form-item>
                <el-form-item label="收款固码(二开)点位">
                    <el-input v-model="newRow.alipay_RSOLID" style="width: 30%;"  placeholder="收款固码(二开)点位"></el-input>%
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
import { merchantsMy,updateMerchant } from '@/api/role'
import { isvalidUsername,isvalidPassword } from '@/utils/validate'  
import store from '../../../../store'
import {getTime} from '@/utils/index'
    export default {
        data() {
             const validateUsername = (rule, value, callback) => {
            console.log(rule)
            console.log(value)
            console.log(callback)
            if (!isvalidUsername(value)) {
                callback(new Error('请输入正确的用户名（只能由英文字母组成）'))
            } else {
                callback()
            }
            }
            const validatePass = (rule, value, callback) => {
            if (!isvalidPassword(value)) {
                callback(new Error('必须包含字母和数字且超过8位'))
            } else {
                callback()
            }
            }
            return {
                teams:[{
                    "addTime": "2019-01-17T16:37:02.184Z",	//申请时间
                    "alipay": 0,	
                    "applyId": 0,	//申请人id
                    "approvalTime": "2019-01-17T16:37:02.184Z",	//审批时间
                    "approverId": 0,	//审批人id
                    "balance": 0,
                    "id": 0,
                    "name": "string",
                    "priority": 0,	//用户星级
                    "status": "启用",	//WAITING 等待审批/PASS 审批通过/ REJECT 审批不通过
                    "user": {
                        "avatarUrl": "string",
                        "cards": [
                        {
                            "accountWithBank": "string",
                            "bank": "string",
                            "bin": "string",
                            "cardNumber": "string",
                            "id": 0,
                            "name": "string",
                            "status": "string"
                        }
                        ],
                        "id": 0,
                        "password": "string",
                        "role": 0,
                        "tableId": 0,
                        "username": "string"
                    },
                    "verifyCode": "string",
                    "wechat": 0
                    }
                ],
                currentPage:1,
                pagesize:10,
                newRow: {
                    "codeType": "",
                    "level": 0,
                    "password": "",
                    "user": {}
                    },
                newRowIndex:1,
                dialogFormVisible: false,
                alipayRateDialogFormVisible:false,
                searchStr: '', // 新增
                 addRules: {
                username: [{ required: true, trigger: 'blur', validator: validateUsername }],
                password: [{ required: true, trigger: 'blur', validator: validatePass }]
                // post: [{ required: true, trigger: 'blur', validator: validateEmpty }]
              },
            }
        },
        computed: {
            filterData() {
                return this.teams.filter((item) => {
                    var reg = new RegExp(this.searchStr, 'i')
                    console.log(item.name)
                    return !this.searchStr || reg.test(item.name)
                })
            },
            total(){
                return  this.teams.length
            }
        },

        created(){
            this.getData();
        },
        methods: {
            
           updateSupplier(formName) {
                this.$refs[formName].validate((valid) => {
                 if (valid) {
                updateMerchant(this.newRow.user.id,
                this.newRow.level,
                this.newRow.user.username,
                this.newRow.user.password,
                this.newRow.user.status,
                this.newRow.wechat,
                this.newRow.alipay_TPASS,
                this.newRow.alipay_TSOLID,
                this.newRow.alipay_RPASSOFF,
                this.newRow.alipay_RPASSQR,
                this.newRow.alipay_RSOLID).then(response=> {
                    if(response.code!=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                        // this.teams[this.newRowIndex].priority = this.newRow.level;
                        this.dialogFormVisible = false;
                        this.getData();
                         this.$message({
                            message: '修改成功',
                            type: 'success'
                        });
                    }
                });
                } else {
                    console.log('error submit!!');
                    return false;
                    }
                });
            },
             openDialog(index,row) {
                this.dialogFormVisible=true;
                //this.newRow = JSON.parse(JSON.stringify(row));
                this.newRow = row;
                this.newRow.level = row.priority;

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
                merchantsMy(store.getters.uid).then(response=>{
                    console.log(response,'sdll')
                     if(response.data.infoCod){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       this.teams = response.data;
                       this.teams.forEach(el => {
                        //    el.statusp =el.status=='WAITING'?'等待审批':'PASS'?'审批通过':'审批不通过';
                           el.wechatp = el.wechat+'%';
                           el.alipayp = el.alipay+'%';
                           el.addTimep = getTime(el.addTime)
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
