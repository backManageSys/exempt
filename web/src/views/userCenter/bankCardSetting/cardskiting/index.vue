<template>
  <div class="chart-container">
    <el-input
      v-model="searchStr"
      style="width:30vw;margin:20px 0 20px 0;"
      suffix-icon="el-icon-search"
      placeholder="请输入搜索内容"
    ></el-input>
    <el-table
      :data="filterData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
      height="500"
      border
      style="width: 100%"
    >

    <el-table-column prop="cardId" label="卡号" align="center"></el-table-column>
    <el-table-column prop="money" label="金额" align="center"></el-table-column>
    <el-table-column prop="type" label="提现类型" align="center">
      <template scope="scope">
          <div v-if="scope.row.type =='merchant' ">商户提现</div>
          <div v-if="scope.row.type =='agent' ">代理提现</div>
      </template>
    </el-table-column>
    <el-table-column prop="applicantId" label="申请人id" align="center"></el-table-column>
    <el-table-column prop="operateId" label="处理人id" align="center"></el-table-column>
    <el-table-column prop="applyTime" label="申请提现时间" align="center"></el-table-column>
    <el-table-column prop="operateTime" label="申请提现时间" align="center"></el-table-column> 
    <el-table-column prop="state" label="提现状态" align="center">
      <template slot-scope="{row}">
        <el-button type="primary" size="small" v-if="row.state=='WAITING'">等待处理</el-button>
        <el-button type="warning" size="small" v-else-if="row.state=='DEALING'">正在处理</el-button>
        <el-button type="success" size="small" v-else-if="row.state=='SUCCESS'">提现成功</el-button>
        <el-button type="danger" size="small" v-else-if="row.state=='FAILED'">提现失败</el-button>
        <el-tag type="warning" v-if="row.state=='SUCCESS'">{{ row.operateTime }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="memo" label="备注" align="center"></el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pagesize"
        layout="sizes, prev, pager, next"
        :total=total
      ></el-pagination>
    </div>
  </div>
</template>

<script>
// import Chart from '@/components/Charts/lineMarker'
import { withdrewHistory } from "@/api/role";
import { getTime } from "@/utils/index";
import store from '../../../../store';
export default {
  // name: "index",
  //   components: { Chart },
  data() {
    return {
      // activeNames: ["1"],
      // labelPosition: "right",
      // postaddParameters: {
      //   post: "post"
      // },
      teams: [
        {
          // cardId: "string",
          // id: "string",
          // money: 0,
          // type: 0
        }
      ],
      currentPage: 1,
      pagesize: 10,
      searchStr: ""
    };
  },
  computed: {
    filterData() {
      return this.teams.filter(item => {
        var reg = new RegExp(this.searchStr, "i");
        console.log(item.cardId);
        return (
          !this.searchStr ||
          reg.test(item.cardId) ||
          reg.test(item.money)
        );
      });
    },
    total(){
      return this.teams.length;
    }
  },
  created() {
    this.getData();
  },
  methods: {
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
      withdrewHistory().then(response => {
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
              el.applyTime = getTime(el.applyTime);
              el.operateTime = getTime(el.operateTime);    
            });
            if(store.getters.role != 1){
              var a = []
              this.teams.forEach(el => {
                if(el.applicantId == store.getters.uid) {
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


