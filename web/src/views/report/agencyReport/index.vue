<template>
  <div class="chart-container">
    <!-- <div>商户报表</div> -->
    <!-- <chart height="100%" width="100%"/> -->
    <el-input
      v-model="searchStr"
      style="width:30vw;margin:20px 20px 20px 20px;"
      suffix-icon="el-icon-search"
      placeholder="请输入搜索内容"
    ></el-input>
    <el-date-picker v-model="startDate" type="date" @change="startDateChange" placeholder="起始日期"
                    style="margin:20px 20px 20px 20px;"></el-date-picker>
    <el-date-picker v-model="endDate" type="date" @change="endDateChange" placeholder="截止日期"
                    style="margin:20px 20px 20px 20px;"></el-date-picker>
    <el-button type="primary" @click="dateSearch">查询</el-button>
    <el-select v-model="platform" placeholder="请选择" style="width: 12.5%" @change="selectChang"
               @visible-change="getSelect">
      <el-option
        v-for="item in options1"
        :key="item.id"
        :label="item.codeCategory"
        :value="item.codeCategory">
      </el-option>
    </el-select>
    <el-table
      :data="filterData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
      height="600"
      border
      style="width: 100%">
      <el-table-column prop="number" label="编号" align="center"></el-table-column>
      <!-- <el-table-column prop="username" label="用户名"  align="center"></el-table-column> -->
      <el-table-column prop="agentName" label="代理名" align="center"></el-table-column>
      <el-table-column prop="withdrewed" label="已提现金额" align="center"></el-table-column>
      <el-table-column prop="withdrewing" label="正在提现金额" align="center"></el-table-column>
      <el-table-column prop="balance" label="余额" align="center"></el-table-column>
      <el-table-column prop="profitList" label="代理商的各渠道利润" align="center">
        <template slot-scope="scope">
          <el-tag :type="device.type?'success':'info'" v-for="device in scope.row.profitList1" :key="device.type">{{
            device.type }} : {{ device.money }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="depositList" label="各渠道流量统计" align="center">
        <template slot-scope="scope">
          <el-tag :type="device.type?'success':'info'" v-for="device in scope.row.depositList1" :key="device.type">{{
            device.type }} : {{ device.money }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="date" label="日期" align="center"></el-table-column>
      <!-- <el-table-column prop="platformAnalyseList" label="platformAnalyseList"  align="center"></el-table-column> -->
      <!-- <el-table-column prop="successOrders" label="成功订单数"  align="center"></el-table-column> -->
      <!-- <el-table-column prop="totalOrders" label="总订单数"  align="center"></el-table-column> -->
      <!-- <el-table-column prop="totalOrders" label="订单成功率"  align="center">
          <template slot-scope="scope">
              <el-tag type="'success'" v-if="scope.row.totalOrders!=0">{{ scope.row.successOrders / scope.row.totalOrders * 100}}%</el-tag>
              <el-tag type="'success'" v-else>0%</el-tag>
          </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名"  align="center"></el-table-column>
      <el-table-column prop="withdrewed" label="已提现"  align="center"></el-table-column>
      <el-table-column prop="withdrewing" label="提现中"  align="center"></el-table-column> -->

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
  </div>
</template>

<script>
  import Chart from '@/components/Charts/lineMarker'
  import {agencyReport, getPermerchantReport} from '@/api/report'
  import {getTime, getTimeFormat} from "@/utils/index";
  import store from '../../../store';
  import {getSelect} from '@/api/role'

  export default {
    name: 'LineChart',
    components: {Chart},
    data() {
      return {
        platform: "全部",
        options1: [],
        activeNames: ['1'],
        labelPosition: 'right',
        postaddParameters: {
          "post": "post",
        },
        teams: [{
          code: "string",
          merchantId: 0,
          merchantName: "string",
          money: 0,
          orderId: 0,
          orderNumber: "string",
          orderState: "WAITING_FOR_PAYING",
          payTime: "2019-01-18T05:34:14.271Z",
          paymoney: 0,
          rechargeId: "string",
          time: "2019-01-18T05:34:14.271Z",
          balance: '',
          alipay: '',
          username: ''
        }],
        currentPage: 1,
        pagesize: 10,
        searchStr: '',
        startDate: getTimeFormat(new Date()),
        endDate: getTimeFormat(new Date())
      }
    },
    computed: {
      filterData() {
        return this.teams.filter(item => {
          var reg = new RegExp(this.searchStr, "i");
          return !this.searchStr || reg.test(item.username) || reg.test(item.balance);
        });
      },
      total() {
        return this.teams.length;
      }
    },
    created() {
      this.getData();
    },
    methods: {
      selectChang() {
        if (this.platform === "全部") {
          this.teams.map((item) => {
            item.depositList1 = item.depositList;
            item.profitList1 = item.profitList;
          })
        } else {
          this.teams.map((item) => {
            const list = [];
            item.profitList.map((item1) => {
              if (item1.type === this.platform) {
                list.push(item1);
              }
            });
            item.profitList1 = list;
            const list1 = [];
            item.depositList.map((item1) => {
              if (item1.type === this.platform) {
                list1.push(item1);
              }
            });
            item.depositList1 = list1;
          });
        }
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
            const all = {
              codeCategory: "全部",
              id: 0
            };
            this.options1.unshift(all);
          }
        });
      },
      startDateChange(val) {
        this.startDate = val;
      },
      endDateChange(val) {
        this.endDate = val;
      },
      dateSearch() {
        agencyReport(getTimeFormat(this.startDate), getTimeFormat(this.endDate)).then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: 'warning'
            });
          } else {
            if (response.data.length != 0)
              this.teams = response.data;
            this.teams.forEach(el => {
              //  el.orderState = (el.orderState=='WAITING_FOR_PAYING')?'等待支付':'PAID'?'已支付':'失效';
              // el.date = getTime(el.date);
            });
          }
        })
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
        agencyReport(getTimeFormat(new Date()), getTimeFormat(new Date())).then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: 'warning'
            });
          } else {
            if (response.data.length != 0)
              this.teams = response.data;
            this.teams.map((item) => {
              item.depositList1 = item.depositList;
              item.profitList1 = item.profitList;
            });
            var a = [];
            if (store.getters.role == 2) {
              this.teams.forEach(el => {
                if (store.getters.name == el.agentName)
                  a.push(el);
              });
              this.teams = a;
            }
            // this.teams.alipay = this.teams.alipay+'%'
            // this.teams.alipay = this.teams.alipay+'%'
          }
        })
      },
    }
  }
</script>

<style scoped>
  .chart-container {
    position: relative;
    width: 100%;
    height: calc(100vh - 84px);
  }
</style>

