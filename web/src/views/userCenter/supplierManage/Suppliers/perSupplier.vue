<template>
  <div class="app-container">
    <div>我的供码设备</div>
    <el-input v-model="searchStr" suffix-icon="el-icon-search" placeholder="请输入搜索内容"></el-input>
    <el-table
      :data = "equipment"
      border>
      <el-table-column prop="imei" label="设备号" align="center"></el-table-column>
      <el-table-column prop="online" label="是否在线" align="center"  :formatter="formatState" ></el-table-column>
      <el-table-column prop="status" label="是否启用" align="center"></el-table-column>
      <el-table-column prop="loginId" label="支付宝账号" align="center"></el-table-column>
      <el-table-column prop="nickName" label="支付宝昵称" align="center"></el-table-column>
      <el-table-column prop="alipayBalance" label="余额" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" align="center" min-width="250%">
        <template scope="scope">
          <el-button type="success" size="mini" @click="turnOn(scope.row)">启用</el-button>
          <el-button type="warning" size="mini" @click="turnDown(scope.row)">停用</el-button>
          <el-button size="small" @click="openDialog(scope.row)">查看</el-button>
          <el-button size="small" @click="showDialog(scope.row)">添加</el-button>
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
    <el-dialog :title="equipmentTitle" :visible.sync="dialogFormVisible">
      <el-table
        :data = "zfb"
        border>
        <el-table-column prop="loginId" label="支付宝账号" align="center"></el-table-column>
        <el-table-column prop="name" label="支付宝昵称" align="center"></el-table-column>
        <el-table-column prop="wealth" label="余额" align="center"></el-table-column>
      </el-table>
      <div class="block">
        <el-pagination
          @size-change="handleSizeChange1"
          @current-change="handleCurrentChange1"
          :current-page.sync="currentPage1"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="pagesize1"
          layout="sizes, prev, pager, next"
          :total="total1"
        ></el-pagination>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateSupplier">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="添加银行卡" :visible.sync="dialogFormVisible1">
      <el-form ref="form"  label-width="20%">
      <el-form-item label="银行卡号">
        <el-input v-model="accountOfBank" type="number" onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"></el-input>
      </el-form-item>
      <el-form-item label="卡上余额">
        <el-input v-model="cardWealth" type="number" onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"></el-input>
      </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="addPersonalCard">增 加</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {suppliersGet, supplierUpdate , deviceUpdate,getSelect, getPayType,getDevice,getAlipayByDevice ,addPersonalCard} from "@/api/role";
import { isvalidUsername,isvalidPassword } from '@/utils/validate'
import store from '../../../../store'
  export default {
    data() {
      return {
        cardWealth:"",
        accountOfBank: '',
        loginId:"",
        zfb:[],
        equipment:[],
        point: "",
        options1: [],
        options2: [],
        value: '',
        value1: '',
        value2: '',
        secondState: true,
        thirdState: true,
        selectState: true,
        status: '',
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
        currentPage1: 1,
        pagesize: 10,
        pagesize1: 10,
        newRowIndex: 1,
        dialogFormVisible: false,
        dialogFormVisible1: false,
        searchStr: "", // 新增
        text:"<br/>",
        equipmentTitle:''
      };
    },
    computed: {
      total(){
          return this.teams.length
      },
      total1(){
        return this.zfb.length
      }
    },
    created() {
      this.getData();
    },
    methods: {
      getPayType() {
        getPayType(this.value).then(response => {
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.options2 = response.data;
          }
        });
      },
      getSelect() {
        getSelect().then(response => {
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.options1 = response.data;
          }
        });
      },
      firstChange() {
        this.value1 = "";
      },
      turnOn(row){
        deviceUpdate(row.id,row.imei,"启用").then(response => {
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.$message({
              message: "设备启用成功",
              type: "success"
            });
             this.getTeams();
          }
        });
      },
      turnDown(row){
        deviceUpdate(row.id,row.imei,"停用").then(response => {
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.$message({
              message: "设备停用成功",
              type: "success"
            });
              this.getTeams()
          }
        });
      },
      updateSupplier(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
        supplierUpdate(
          this.value1,
          this.newRow.level,
          this.newRow.user.password,
          this.newRow.status,
          store.getters.uid,
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
            this.getData();
          }
        });
        } else {
              console.log('error submit!!');
              return false;
            }
          });
      },
      formatState(row, column, cellValue) {
        if (cellValue === 1){
          return '在线';
        }else if (cellValue === 0){
          return '离线';
        }
      },
      openDialog(row) {
        this.dialogFormVisible = true;
        console.log(row);
        this.equipmentTitle =  row.imei;
        getAlipayByDevice(row.imei).then(response => {
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            console.log(response.data);
            this.zfb = response.data;
          }
        });
      },
      showDialog(row) {
        this.dialogFormVisible1 = true;
        this.loginId = row.loginId;
      },
      handleSizeChange(val) {
        this.pagesize = val;
      },
      handleSizeChange1(val) {
        this.pagesize1 = val;
      },
      handleCurrentChange(val) {
        this.currentPage = val;
      },
      handleCurrentChange1(val) {
        this.currentPage1 = val;
      },
      getData() {
        // this.getTeams();
        this.getDevice();
      },
      addPersonalCard(){
        addPersonalCard(this.loginId,this.cardWealth,this.accountOfBank).then(response => {
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.$message({
              message: response.data,
              type: "warning"
            });
            this.dialogFormVisible1 = false;
          }
        });
      },
      getDevice(){
        getDevice(store.getters.uid).then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            console.log(12323112,response.data);
           this.equipment = response.data;
          }
        });
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
                       this.equipment = response.data.devices;
                       console.log(2123213,response.data,this.equipment);
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

<style>
  .el-input>input::-webkit-outer-spin-button,
  .el-input>input::-webkit-inner-spin-button {
    -webkit-appearance: none;
  }

  .el-input>input[type="number"] {
    -moz-appearance: textfield;
  }
</style>

