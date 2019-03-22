<!--提现历史-->
<template>
  <div class="chart-container">
    <el-input
      v-model="searchStr"
      style="width:30vw;margin:20px 0 20px 0;"
      suffix-icon="el-icon-search"
      placeholder="请输入搜索内容"
      @keyup.enter.prevent="getTeams"
    ></el-input>
    <el-table
      :data="teams"
      height="500"
      border
      style="width: 100%"
    >
      <el-table-column prop="number" label="编号" align="center"></el-table-column>
      <el-table-column prop="applicantUsername" label="申请人" align="center"></el-table-column>
      <el-table-column prop="applyTime" label="申请提现时间" align="center"></el-table-column>
      <el-table-column prop="card_out_balance" label="转出卡余额" align="center"></el-table-column>
      <el-table-column prop="money_out" label="发起提现金额" align="center"></el-table-column>
      <el-table-column prop="money_in" label="实际到卡金额" align="center"></el-table-column>
      <el-table-column prop="operateTime" label="提现处理时间" align="center"></el-table-column>
      <el-table-column prop="operateUsername" label="提现处理人" align="center"></el-table-column>
      <el-table-column prop="revokeTime" label="撤销时间" align="center"></el-table-column>
      <!--<el-table-column prop="type" label="提现类型" align="center">-->
      <!--<template scope="scope">-->
      <!--<div v-if="scope.row.type =='merchant' ">商户提现</div>-->
      <!--<div v-if="scope.row.type =='agent' ">代理提现</div>-->
      <!--</template>-->
      <!--</el-table-column>-->

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
      <el-table-column label="操作" align="center" min-width="100%">
        <template scope="scope">
          <el-button size="small" type="success" align="center" v-if="scope.row.state=='SUCCESS'"
                     @click="getWithdrew(scope.row.id,scope.row.memo)">撤销
          </el-button>
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
        :total=total
      ></el-pagination>
    </div>
    <el-dialog title="添加备注信息" :visible.sync="dialogFormVisible">
      <el-form :model="newRow" label-width="13%">
        <el-form-item label="备注：">
          <el-input v-model="newRow.memo" placeholder="备注" style="width:70%;"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {withdrewHistory, withdrewRevoke} from "@/api/role";
  import {getTime} from "@/utils/index";
  import store from '../../../../store';

  export default {
    data() {
      return {
        dialogFormVisible: false,
        teams: [
          {}
        ],
        currentPage: 1,//页数
        pagesize: 10,//每页显示数量
        searchStr: "",//搜索框的值
        id: '',
        newRow: {
          memo: ""
        },
      };
    },
    computed: {
      /*filterData() {
        return this.teams.filter(item => {
          var reg = new RegExp(this.searchStr, "i");
          console.log(item.cardId);
          return (
            !this.searchStr ||
            reg.test(item.cardId) ||
            reg.test(item.money)
          );
        });
      },*/
      total() {
        return this.teams.length;
      }
    },
    mounted () {
      // 获取搜索按钮，并添加点击事件
      var searchBtn = document.getElementsByClassName('el-input__icon')[0];
      searchBtn.addEventListener('click',()=> {
        this.getTeams();
      },false)
    },
    created() {
      this.getData();
    },
    methods: {
      handleSizeChange(val) {
        this.pagesize = val;
        this.getTeams();
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.getTeams();
      },
      getData() {
        this.getTeams();
      },
      getWithdrew(id, memo) {
        this.dialogFormVisible = true;
        this.id = id;
        this.memo = memo;
      },
      update() {
        if (this.newRow.memo === "") {
          this.$message({
            message: "请添加备注",
            type: 'warning'
          });
          return false;
        } else {
          this.dialogFormVisible = false;
          withdrewRevoke(this.id, this.newRow.memo).then(response => {
            if (response.code !== 200) {
              this.$message({
                message: response.data.description,
                type: 'warning'
              });
            } else {
              this.$message({
                message: response.data.description,
                type: 'success'
              });
              this.getData();
            }
          })
        }
      },
      getTeams() {
        withdrewHistory(this.searchStr,this.currentPage,this.pagesize).then(response => {
          console.log(response,"response");
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            if (response.data.content.length !== 0) {
              this.teams = response.data.content;
              this.teams.forEach(el => {
                el.applyTime = getTime(el.applyTime);
                el.operateTime = getTime(el.operateTime);
                el.revokeTime = getTime(el.revokeTime);
              });
            }
          }
        });
      }
    }
  };
</script>

<style scoped>

</style>


