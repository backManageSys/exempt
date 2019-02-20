<template>
  <div class="app-container">
    <div>我是供码用户</div>
    <el-input v-model="searchStr" suffix-icon="el-icon-search" placeholder="请输入搜索内容"></el-input>
    <el-table
      :data="filterData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
      border>
      <el-table-column prop="user.username" label="用户名" align="center"></el-table-column>
      <el-table-column prop="priority" label="等级" align="center"></el-table-column>
      <el-table-column prop="devices_team" label="设备状态" align="center" min-width="150%" >
        <template slot-scope="scope">
          <el-tag
            :type="device.online?'success':'info'"
            v-for="device in scope.row.devices"
            :key="device.device_team"
          >{{ device.device_team +"&#12288;&#12288;"}}
          <el-button type="success" size="mini" @click="turnOn(scope.row,device)">启用</el-button>
          <el-button type="warning" size="mini" @click="turnDown(scope.row,device)">停用</el-button>
          <div v-html="text"></div>
          </el-tag>
          
        </template>
      </el-table-column>
      <el-table-column prop="status" label="账户状态" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" align="center">
        <template scope="scope">
          <el-button size="small" @click="openDialog(scope.$index,scope.row)" v-if="seen(scope.$index,scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pagesize"
        layout="sizes, prev, pager, next"
        :total="total"
      ></el-pagination>
    </div>
    <el-dialog title="修改供码用户信息" :visible.sync="dialogFormVisible">
            <el-form ref="form" :model="newRow.user" :rules="addRules" label-width="13%">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="newRow.user.username" type="text" placeholder="用户名" style="width:90%;"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="newRow.user.password" type="password" placeholder="密码" style="width:90%;"></el-input>
                </el-form-item>
                <el-form-item label="码类型">
                    <el-select v-model="newRow.codeType" placeholder=""  style="width:30%;">
                    <el-option label="转账通码" value="TPASS"></el-option>
                    <el-option label="转账固码" value="TSOLID"></el-option>
                    <el-option label="收款通码离线码" value="RPASSOFF"></el-option>
                    <el-option label="收款通码在线码" value="RPASSQR"></el-option>
                    <el-option label="收款固码(二开)" value="RSOLID"></el-option>
                    </el-select>
                </el-form-item>
                 <!-- <el-form-item label="状态">
                    <el-select v-model="newRow.status" placeholder="启用">
                    <el-option label="启用" value="启用"></el-option>
                    <el-option label="停用" value="停用"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="level">
                    <el-input v-model="newRow.level" type="number" min="1" placeholder="level"></el-input>
                </el-form-item> -->
                
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateSupplier">确 定</el-button>
            </div>
    </el-dialog>
  </div>
</template>

<script>
import {suppliersGet, supplierUpdate , deviceUpdate } from "@/api/role";
import { isvalidUsername,isvalidPassword } from '@/utils/validate' 
import store from '../../../../store'
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
        teams: [
          {
            priority: "",
            devices: [
              {
                imei:"123",
                online:""
              }
            ],
            status: '',
            user: {
              username: ''
            },
            devices_team: " ",
            username: ''
          }
        ],
        newRow: {
          codeType: "RPASSOFF",
          level: 0,
          password: "",
          user: {},
          priority: 0
        },
        currentPage: 1,
        pagesize: 10,
        newRowIndex: 1,
        dialogFormVisible: false,
        searchStr: "", // 新增
        addRules: {
          username: [{ required: true, trigger: 'blur', validator: validateUsername }],
          password: [{ required: true, trigger: 'blur', validator: validatePass }]
          // post: [{ required: true, trigger: 'blur', validator: validateEmpty }]
        },
        text:"<br/>"
      };
    },
    computed: {
      filterData() {
        return this.teams.filter(item => {
          var reg = new RegExp(this.searchStr, "i");
          console.log(item.user.username);
          return !this.searchStr || reg.test(item.user.username);
        });
      },
      total(){
          return this.teams.length
      }
    },
    created() {
      this.getData();
    },
    methods: {
      turnOn(row,device){
        console.log(row.user.username)
        console.log(device.imei)
        deviceUpdate(row.id,device.imei,"启用").then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            // this.teams[this.newRowIndex].priority = this.newRow.level;
            // this.dialogFormVisible = false;
            this.$message({
              message: "设备启用成功",
              type: "success"
            });
             this.getTeams();
          }
        });
      },
      turnDown(row,device){
        console.log(row.user.username)
        console.log(device.imei)
        deviceUpdate(row.id,device.imei,"停用").then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            // this.teams[this.newRowIndex].priority = this.newRow.level;
            // this.dialogFormVisible = false;
            this.$message({
              message: "设备停用成功",
              type: "success"
            });
              this.getTeams()
          }
        });
      },
      seen(index,row){
        if(row.user.username == null || row.user.username == '')
          return false;
        else
          return true;
      },
      updateSupplier(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
        supplierUpdate(
          this.newRow.codeType,
          this.newRow.level,
          this.newRow.user.username,
          this.newRow.user.password,
          this.newRow.status,
          this.newRow.id
        ).then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.teams[this.newRowIndex].priority = this.newRow.level;
            this.dialogFormVisible = false;
            this.$message({
              message: "修改成功",
              type: "success"
            });
          }
        });
        } else {
              console.log('error submit!!');
              return false;
            }
          });
      },
      openDialog(index, row) {
        this.dialogFormVisible = true;
        // console.log(row)
        this.newRow = row;
        // if(row.codeType==None){
        //     this.newRow.codeType = 'TSOLID'
        // }else{
        //      this.newRow.codeType = row.codeType;
        // }
        this.newRow.level = row.priority;
        // this.newRow.password = row.user.password;
        //this.newRow = JSON.parse(JSON.stringify(row));
        this.newRowIndex = index;
        console.log(this.newRow);
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.pagesize = val;
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.currentPage = val;
      },
      getData() {
        this.getTeams();
      },
      getTeams() {
         suppliersGet().then(response=>{  
                    console.log(response,'sdll',store.getters.uid)
                     if(response.code!=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       var teams = response.data;
                        var a =[];
                        teams.forEach(el => {
                            //el.devices = [{imei:"123",online:1},{imei:"456",online:0}]
                            el.devices.forEach(de=>{
                                console.log(de.imei)
                                de.device_team = de.imei +'\xa0\xa0\xa0\xa0'+ (de.online?'在线':'离线')+'\xa0\xa0\xa0\xa0'+'已'+de.status;
                            })
                            console.log(el.user.id,store.getters.uid)
                            if(el.user.id == store.getters.uid){
                                a = el
                            }
                        })
                        this.teams = [a]
                        console.log(this.teams)
                    }
                })
      }
    }
  };
</script>

<style scoped>
</style>
