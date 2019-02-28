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
    <el-table :data="filterData.slice((currentPage-1)*pagesize,currentPage*pagesize)" height="500" border
              style="width: 100%">
      <el-table-column prop="number" label="编码" align="center"></el-table-column>
      <el-table-column prop="merchantName" label="商户名" align="center"></el-table-column>
      <el-table-column prop="deposit" label="抽成前存款" align="center"></el-table-column>
      <el-table-column prop="availiableDeposit" label="抽成后存款" align="center"></el-table-column>
      <el-table-column prop="withdrewed" label="已提现金额" align="center"></el-table-column>
      <el-table-column prop="withdrewing" label="正在提现金额" align="center"></el-table-column>
      <el-table-column prop="agentProfit" label="代理收益" align="center"></el-table-column>
      <el-table-column prop="companyProfit" label="公司收益" align="center"></el-table-column>
      <el-table-column prop="balance" label="余额" align="center"></el-table-column>
      <el-table-column prop="date" label="日期" align="center" min-width="150%"></el-table-column>
      <el-table-column prop="totalOrders" label="订单成功率" align="center" min-width="150%">
        <template slot-scope="scope">
          <el-tag :type="device.type?'success':'info'" v-for="device in scope.row.successOrdersRateList1"
                  :key="device.type">
            {{ device.type }} : {{ device.successOrdersRate }}%
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="platformAnalyseList" label="渠道分析" align="center" min-width="150%">
        <template slot-scope="scope">
          <el-tag :type="device.type?'success':'info'" v-for="device in scope.row.platformAnalyseList1"
                  :key="device.type">{{ device.type }} : {{ device.money }}
          </el-tag>
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
  </div>
</template>

<script>
  import Chart from '@/components/Charts/lineMarker';
  import {merchantsReport, getPermerchantReport} from '@/api/report';
  import {getTime, getTimeFormat} from "@/utils/index";
  import store from '../../../../store';
  import {getSelect} from '@/api/role'

  export default {
    name: 'LineChart',
    components: {Chart},
    data() {
      return {
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
          balance: ''
        }],
        currentPage: 1,
        pagesize: 10,
        searchStr: '',
        startDate: getTimeFormat(new Date()),
        endDate: getTimeFormat(new Date()),
        platform: "全部",
        options1: [],
      }
    },
    computed: {
      filterData() {
        return this.teams.filter(item => {
          var reg = new RegExp(this.searchStr, "i");
          console.log(item.merchantName);
          return !this.searchStr || reg.test(item.merchantName) || reg.test(item.balance);
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
            item.successOrdersRateList1 = item.successOrdersRateList;
            item.platformAnalyseList1 = item.platformAnalyseList;
          })
        } else {
          this.teams.map((item) => {
            const list = [];
            const list1 = [];
            item.successOrdersRateList.map((item1) => {
              if (item1.type === this.platform) {
                list.push(item1);
              }
            });
            item.successOrdersRateList1 = list;
            item.platformAnalyseList.map((item1) => {
              if (item1.type === this.platform) {
                list1.push(item1);
              }
            });
            item.platformAnalyseList1 = list1;
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
        merchantsReport(getTimeFormat(this.startDate), getTimeFormat(this.endDate)).then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: 'warning'
            });
          } else {
            if (response.data.length != 0)
              this.teams = response.data;
            // this.teams.forEach(el => {
            //  el.orderState = (el.orderState=='WAITING_FOR_PAYING')?'等待支付':'PAID'?'已支付':'失效';
            // el.date = getTime(el.date);
            // });
          }
        })
      },
      handleSizeChange(val) {
        this.pagesize = val;
      },
      handleCurrentChange(val) {
        this.currentPage = val;
      },
      getData() {
        this.getTeams();
      },
      getTeams() {
        merchantsReport(getTimeFormat(new Date()), getTimeFormat(new Date())).then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: 'warning'
            });
          } else {
            if (response.data.length != 0)
              this.teams = response.data;
            this.teams.map((item) => {
              item.successOrdersRateList1 = item.successOrdersRateList;
              item.platformAnalyseList1 = item.platformAnalyseList;
            });
            var a = [];
            if (store.getters.role == 3) {
              this.teams.forEach(el => {
                if (store.getters.name == el.merchantName)
                  a.push(el);
              });
              this.teams = a;
            }
            if (store.getters.role == 2) {
              this.teams.forEach(el => {
                if (store.getters.uid == el.agentId)
                  a.push(el);
              });
              this.teams = a;
            }
          }
        })
      }
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

