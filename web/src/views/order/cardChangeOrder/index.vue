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
      style="width: 100%">
      <el-table-column prop="cardNumber_in" label="转入卡卡号" align="center"></el-table-column>
      <el-table-column prop="cardNumber_out" label="转出卡卡号" align="center"></el-table-column>
      <el-table-column prop="money_out" label="发起转帐金额" align="center"></el-table-column>
      <el-table-column prop="money_in" label="实际到账金额" align="center"></el-table-column>
      <el-table-column prop="state" label="状态" align="center">
        <template scope="scope">
            <el-button type="success" size="small" v-if="scope.row.state=='SUCCESS'">已到账</el-button>
            <el-button type="info" size="small" v-else-if="scope.row.state=='WAITING'">未到账</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="operateTimep" label="操作时间" align="center" min-width="100%"></el-table-column>
      <el-table-column prop="operateUsername" label="操作人" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" align="center" v-if="judge">
        <template scope="scope">
          <el-button size="small" @click="openDialog(scope.$index,scope.row)" v-if="editable(scope.$index,scope.row)">修改订单</el-button>
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
     <el-dialog title="修改订单" :visible.sync="dialogFormVisible">
            <el-form :model="newRow">
                <el-form-item label="实付金额">
                    <el-input v-model="newRow.money_in"  placeholder="实付金额"></el-input>
                </el-form-item>
                <el-form-item label="订单状态">
                    <el-select v-model="newRow.state" placeholder="">
                      <el-option label="未到账" value="WAITING"></el-option>
                      <el-option label="已到账" value="SUCCESS"></el-option>
                    </el-select>
                </el-form-item>   
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="confirm">确 定</el-button>
            </div>
    </el-dialog>
  </div>
</template>

<script>
// import Chart from '@/components/Charts/lineMarker'
import { Pcard,Ccard } from "@/api/role";
import { getTime } from "@/utils/index";
import store from '../../../store';
export default {
  name: "index",
  //   components: { Chart },
  data() {
    return {
      activeNames: ["1"],
      labelPosition: "right",
      postaddParameters: {
        post: "post"
      },
      teams: [
        {
          cardNumber_in: "",
          cardNumber_out: "",
          money: 0,
          operateId: 0
        }
      ],
      newRow:{
        
      },
      currentPage: 1,
      pagesize: 10,
      searchStr: "",
      dialogFormVisible:false
    };
  },
  computed: {
    filterData() {
      return this.teams.filter(item => {
        var reg = new RegExp(this.searchStr, "i");
        console.log(item.cardNumber_in);
        return (
          !this.searchStr ||
          reg.test(item.cardNumber_in) ||
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
    confirm(){
      
    },
    judge(){
      if(store.getters.role == 1)
          return true;
      else
          return false;
    },
    editable(index,row){ 
       if(row.state == 'WAITING')
          return true;
       else
          return false;
    },
    openDialog(index, row) {
        this.dialogFormVisible = true;
        // console.log(row)
        this.newRow = row;
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
      Pcard().then(response => {
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
              el.operateTimep = getTime(el.operateTime);
            });

          }
        }
      });
    }
  }
};
</script>

<style scoped>
/* .chart-container{
  position: relative;
  width: 100%;
  height: calc(100vh - 84px);
} */
</style>


