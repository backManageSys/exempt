<template>
        <div class="app-container">
             <el-input v-model="searchStr" suffix-icon="el-icon-search" placeholder="请输入搜索内容"></el-input>
            <el-table
            :data="filterData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
            border
            style="width: 100%">
            <el-table-column prop="orderNumber" label="订单编号"  align="center" min-width="110%"></el-table-column>
            <el-table-column prop="codeType" label="供码模式"  align="center" min-width="100%">
             <template slot-scope="scope">
                    <el-tag  v-if="scope.row.codeType=='TPASS'" >转账通码</el-tag>
                    <el-tag  v-else-if="scope.row.codeType=='TSOLID'" >转账固码</el-tag>
                    <el-tag  v-else-if="scope.row.codeType=='RPASSOFF'" >收款通码离线码</el-tag>
                    <el-tag  v-else-if="scope.row.codeType=='RPASSQR'" >收款通码在线码</el-tag>
                    <el-tag  v-else-if="scope.row.codeType=='RSOLID'" >收款固码(二开)</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="money" label="订单金额"  align="center"></el-table-column>
            <el-table-column prop="paymoney" label="成交金额"  align="center"></el-table-column>
            <el-table-column prop="rechargeId" label="充值方编号"  align="center"></el-table-column>
            <el-table-column prop="nickname" label="支付宝昵称"  align="center"></el-table-column>
            <!-- <el-table-column prop="code" label="收款码"  align="center"></el-table-column> -->
            <el-table-column prop="time" label="订单时间"  align="center"></el-table-column>
            <el-table-column prop="payTime" label="支付时间"  align="center"></el-table-column>
            <el-table-column prop="orderState" label="状态"  align="center">
                 <!-- <template slot-scope="{row}">
                    <el-button type="success" size="small" v-if="row.orderState=='PAID'">已支付</el-button>
                    <el-button type="info" size="small" v-else-if="row.orderState=='WAITING_FOR_PAYING'">未付款</el-button>
                    <el-button type="warning" size="small" v-else-if="row.orderState=='EXPIRED'">已失效</el-button>
                    <el-tag type="warning" v-if="row.orderState=='PAID'">{{ row.payTime_format }}</el-tag>
                </template> -->
            </el-table-column>
            <!-- <el-table-column prop="payTime" label="到账时间"  align="center"></el-table-column> -->
            <!-- <el-table-column prop="ip" label="ip"  align="center"></el-table-column> -->
            <!-- <el-table-column prop="uid" label="uid"  align="center"></el-table-column> -->
            <el-table-column prop="merchantName" label="商户"  align="center"></el-table-column>
            <el-table-column label="操作" fixed="right" align="center" v-if="judge">
              <template scope="scope">
                <el-button size="small" @click="openDialog(scope.$index,scope.row)" v-if="editable(scope.$index,scope.row)">补单</el-button>
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
            :total=total>
            </el-pagination>
         </div>
             <el-dialog title="补单" :visible.sync="dialogFormVisible" @close='closeDialog' >
            <el-form :model="newRow">
                <el-form-item label="支付宝订单编号">
                    <el-input v-model="newRow.alipayOrderId"  placeholder="订单金额"></el-input>
                </el-form-item>
                <el-form-item label="订单状态">
                    <el-select v-model="newRow.orderState" placeholder="">
                    <el-option label="等待付款" value="WAITING_FOR_PAYING"></el-option>
                    <el-option label="已失效" value="EXPIRED"></el-option>
                    <el-option label="已支付" value="PAID"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="订单金额">
                    <el-input v-model="newRow.money"  placeholder="订单金额"></el-input>
                </el-form-item>
                <el-form-item label="实付金额">
                    <el-input v-model="newRow.paymoney"  placeholder="实付金额"></el-input>
                </el-form-item>
                <el-form-item label="支付时间">
                    <el-date-picker v-model="newRow.TT" type="datetime"    placeholder="支付时间" ></el-date-picker>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="newRow.memo"  placeholder="备注"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false;">取 消</el-button>
                <el-button type="primary" @click="confirm">确 定</el-button>
            </div>
    </el-dialog>
      </div>
    </template>

    <script>
import { ordersGet,ordersUpdate } from "@/api/order";
import { getTime,getDateTimeFormat } from "@/utils/index";
import store from '../../../store';
export default {
  data() {
    return {
      teams: [
        {
         // codeType:'TPASS'
          // id: 1,
          // number: "dfadfas",
          // state: "WAITING_FOR_PAYING",
          // payCode: "sadfsadf",
          // ip: "10.30.256.106",
          // rechargeId: "12",
          // money: 1,
          // payMoney: 0,
          // uid: 1,
          // imei: "12222222",
          // time: "",
          // merchantName:'',
          // merchantId:26,
          // supplierId:28,
          // agentId:0
          
        }
      ],
      newRow:{
        TT:""
      },
      dialogFormVisible: false,
      currentPage: 1,
      pagesize: 10,
      searchStr: "" ,// 新增
    };
  },
    computed: {
    filterData() {
      return this.teams.filter(item => {
        if(this.searchStr == '未支付')
          this.searchStr = 'WAITING_FOR_PAYING';
        var reg = new RegExp(this.searchStr, "i");
        // console.log(item.money);
        // console.log("1212dasdasd");
        // console.log(item.orderState);
        return !this.searchStr || reg.test(item.money) || reg.test(item.merchantName) 
      });
    },
    total(){
      return this.teams.length;
    }
    // editable(index,row){
    //   // if(row.orderState == 'WAITING_FOR_PAYING')
    //   //   return true;
    //   // else
    //   //   return false;
    // }
  },
  created() {
    this.getData();
    console.log("weweweq")
    console.log(store.getters.uid)
    console.log(store.getters.role)
  },
  methods: {
    judge(){
      console.log("1asdasdsadadadadadasd2214142fdsfsdgsdgsdG");
      console.log(store.getters.role);
      if(store.getters.role == 1)
          return true;
      else
          return false;
    },
    editable(index,row){ 
       if(row.orderState == '等待付款'|| row.orderState == '已失效'|| row.orderState == 'WAITING_FOR_PAYING' || row.orderState == 'EXPIRED')
          return true;
       else
          return false;
    },
    openDialog(index, row) {
        this.dialogFormVisible = true;
        // console.log(row)
        this.newRow = row;
       // this.newRow.payTime = getTime(row.payTime);
        if(row.orderState == '等待付款')
            this.newRow.orderState = 'WAITING_FOR_PAYING';
        if(row.orderState == '已付款')
            this.newRow.orderState = 'PAID';
        if(row.orderState == '已失效')
            this.newRow.orderState = 'EXPIRED';
    },
    closeDialog() {  
       this.dialogFormVisible = false;
       this.getTeams();
    },
    dateChange(val){
      console.log(val);
      this.newRow.payTime = val; 
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
    confirm(){
      ordersUpdate(this.newRow.orderId,this.newRow.orderState,this.newRow.money,
      this.newRow.paymoney,this.newRow.alipayOrderId,Date.parse(this.newRow.TT)/1000,this.newRow.memo).then(response =>{
        if(response.code != 200){
          this.$message({
            message: response.data,
            type: "warning"
          });
        }else{
          this.$message({
            message: "补单成功",
            type: "success"
          });
          this.dialogFormVisible = false;
          //this.newRow.payTime = getTime(this.newRow.payTime)
          this.getTeams();
        }
      })
    },  
    getTeams() {
      ordersGet().then(response => {
        console.log(response, "sdll");
        if (response.code != 200) {
          this.$message({
            message: response.data.description,
            type: "warning"
          });
        } else {
          if (response.data.length != 0) {
            this.teams = response.data; 
            this.teams.forEach(el => {
              el.time=getTime(el.time);
              if(el.payTime != null)
                el.payTime = getTime(el.payTime);
              // else
              //   el.payTime = ""
              if(el.orderState == 'WAITING_FOR_PAYING')
                  el.orderState = '等待付款';
              if(el.orderState == 'PAID')
                  el.orderState = '已付款';
              if(el.orderState == 'EXPIRED')
                  el.orderState = '已失效';
           //   console.log(el.payTime)
           //   console.log(el.payTime_format)
            });
            console.log(store.getters.role)
            console.log("2222223333332")
            console.log(store.getters.uid)
            console.log("2222222")
           // console.log(store.getters.token)
           if(store.getters.role == 2 )
            {
                var a = []
                this.teams.forEach(el =>{
                    if(el.agentId==store.getters.uid) {
                        a.push(el)
                    }
                })
                this.teams = a;
            }
            if(store.getters.role == 3)
            {
                var a = []
                this.teams.forEach(el =>{
                    if(el.merchantId==store.getters.uid) {
                        a.push(el)
                    }
                })
                this.teams = a;
            }
            if(store.getters.role == 4)
            {
              var a = []
                this.teams.forEach(el =>{

                    // console.log(el.supplierId)
                    // console.log(store.getters.uid)
                    if(el.supplierId==store.getters.uid) {
                        a.push(el)
                    }
                })
                this.teams = a;
            }
          }
        }
      });
    }
  }
};
</script>

    <style scoped>
</style>
