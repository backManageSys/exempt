<!--内部卡账变订单-->
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
      style="width: 100%">
      <el-table-column prop="cardBalanceIn" label="转入卡余额" align="center"></el-table-column>
      <el-table-column prop="cardBalanceOut" label="转出卡余额" align="center"></el-table-column>
      <el-table-column prop="cardNumber_in" label="转入卡卡号" align="center"></el-table-column>
      <el-table-column prop="cardNumber_out" label="转出卡卡号" align="center"></el-table-column>
      <el-table-column prop="operateTime" label="首次操作时间" align="center"></el-table-column>
      <el-table-column prop="finalOperateTime" label="最终操作时间" align="center"></el-table-column>
      <el-table-column prop="money_out" label="转出金额" align="center"></el-table-column>
      <el-table-column prop="money_in" label="转入金额" align="center"></el-table-column>
      <el-table-column prop="operateUsername" label="操作人用户名" align="center"></el-table-column>
      <el-table-column prop="reason" label="撤销原因" align="center"></el-table-column>
      <el-table-column prop="type" label="类型" align="center"></el-table-column>
      <el-table-column prop="state" label="订单状态" align="center">
        <template scope="scope">
            <el-button type="success" size="small" v-if="scope.row.state=='SUCCESS'">已到账</el-button>
            <el-button type="info" size="small" v-else-if="scope.row.state=='WAITING'">未到账</el-button>
            <el-button type="danger" size="small" v-else-if="scope.row.state=='FAILED'">失败</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" align="center" v-if="judge" min-width="150%">
        <template scope="scope">
          <!--<el-button size="small" @click="openDialog(scope.$index,scope.row)" v-if="editable(scope.$index,scope.row)">修改订单</el-button>-->
          <el-button size="small" @click="revocation(scope.$index,scope.row)">未到账</el-button>
          <el-button size="small" @click="openDialog(scope.$index,scope.row)">已到账</el-button>
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
                <el-form-item label="请输入备注">
                    <el-input v-model="reason"  placeholder="输入备注"></el-input>
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
import { ShowCardOrder,Ccard,internalaccountchangeUpdate ,internalaccountchangeRevoke} from "@/api/role";
import { getTime } from "@/utils/index";
import store from '../../../store';
export default {
  name: "index",
  //   components: { Chart },
  data() {
    return {
      reason:'',
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
      currentPage: 1,//当前页
      pagesize: 10,//每页显示数量
      searchStr: "",//搜索框的值
      dialogFormVisible:false
    };
  },
  computed: {
    /*filterData() {
      return this.teams.filter(item => {
        var reg = new RegExp(this.searchStr, "i");
        console.log(item.cardNumber_in);
        return (
          !this.searchStr ||
          reg.test(item.cardNumber_in) ||
          reg.test(item.money)
        );
      });
    },*/
    total(){
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
    confirm(){
        if(this.reason !== ""){
          internalaccountchangeRevoke(this.newRow.id,this.reason).then(response => {
            if (response.code !== 200) {
              this.$message({
                message: response.data.description,
                type: "warning"
              });
            } else {
              this.$message({
                message: response.data,
                type: "success"
              });
            }
          });
        }else{
          this.$message({
            message: "请输入备注",
            type: "warning"
          });
        }
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
        // this.dialogFormVisible = true;
        this.newRow = row;
      internalaccountchangeUpdate(store.getters.uid,row.id).then(response => {
        if (response.code !== 200) {
          this.$message({
            message: response.data.description,
            type: "warning"
          });
        } else {
          this.$message({
            message: response.data,
            type: "success"
          });
        }
      });
    },
    revocation(index, row){
      this.newRow = row;
      this.$confirm('是否撤销订单?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.dialogFormVisible = true;
      }).catch(() => {
        console.log(2)
      });
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pagesize = val;
      this.getTeams();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.getTeams();
    },
    getData() {
      this.getTeams();
    },
   getTeams() {
     ShowCardOrder(this.searchStr,this.pagesize,this.currentPage).then(response => {
        if (response.code != 200) {
          this.$message({
            message: response.data.description,
            type: "warning"
          });
        } else {
          if (response.data.length !== 0) {
            console.log(response,'response');
            this.teams = response.data;
            this.teams.forEach(el => {
              // 判断 是否有值。如果有  则继续判断是否包含NaN  如果没有。则正常执行，否则赋值 —— 空
              el.operateTimep = el.operateTimep && (el.operateTimep.indexOf("NaN") == -1) ? getTime(el.operateTime) : "空";
              el.finalOperateTime = el.finalOperateTime && (el.finalOperateTime.indexOf("NaN") == -1) ? getTime(el.finalOperateTime) : "空";
              el.operateTime = el.operateTime && (el.operateTime.indexOf("NaN") == -1) ? getTime(el.operateTime) : "空" ;
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


