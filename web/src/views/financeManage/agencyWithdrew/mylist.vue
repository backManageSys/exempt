<template>
  <div>
    <!-- <el-tag>商户号/代理号</el-tag>
    <el-tag>下发类型(商户还是代理)</el-tag>
    <el-tag>提现金额</el-tag>
    <el-tag>账动前金额</el-tag>
    <el-tag>状态</el-tag>
    <el-tag>申请提现时间</el-tag>
    <el-tag>操作时间</el-tag>
    <el-tag>操作人(抢单人</el-tag>
    <el-tag>哪个财务)</el-tag>
    <el-tag>审核人(财务)</el-tag>
    <el-tag>备注(可以修改)</el-tag> -->

    <el-table
      :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
      :height="tableHeight"
      border
      style="width: 100%">
      <el-table-column prop="id" label="id" align="center"></el-table-column>
      <el-table-column prop="number" label="订单编号" align="center"></el-table-column>
      <el-table-column prop="type" label="类型" align="center">
        <template slot-scope="{row}">
          <el-tag v-if="row.type=='merchant'">商户提现订单</el-tag>
          <el-tag v-else-if="row.type=='agent'">代理提现订单</el-tag>
        </template>
      </el-table-column>
      <!--<el-table-column prop="cardId" label="银行卡号" align="center"></el-table-column>-->
      <el-table-column label="银行卡号" align="center" min-width="150%">
        <template scope="scope">
          <el-select v-model="value1" placeholder="请选择"    @visible-change="getCompanyCard">
            <el-option
              v-for="item in optionData"
              :key="item.cardNumber"
              :label="item.cardNumber"
              :value="item.cardNumber">
            </el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column prop="money_out" label="发起提现金额" align="center"></el-table-column>
      <el-table-column prop="balance" label="账变前金额" align="center"></el-table-column>
      <el-table-column prop="applicantId" label="申请人id" align="center"></el-table-column>
      <el-table-column prop="applyTime" label="申请时间" align="center"></el-table-column>
      <el-table-column prop="state" label="状态" align="center">
        <template scope="scope">
          <el-tag v-if="scope.row.state=='DEALING'" type="warning">等待处理</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="memo" label="备注" align="center" min-width="100%">
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="150%">
        <template scope="scope">
          <el-button size="small" type="success" align="center"
                     @click="getWithdrew(scope.row.id,scope.row.memo,'SUCCESS')">通过
          </el-button>
          <el-button size="small" type="danger" align="center"
                     @click="getWithdrew(scope.row.id,scope.row.memo,'FAILED')">拒绝
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
        :total=total>
      </el-pagination>
    </div>
    <el-dialog title="添加备注信息" :visible.sync="dialogFormVisible" :before-close="cancel">
      <el-form :model="newRow" label-width="13%">
        <el-form-item label="备注：">
          <el-input v-model="newRow.memo" placeholder="备注" style="width:70%;"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </div>
    </el-dialog>
  </div>

</template>
<script>
  import Table from "../../../components/table/index"
  import {withdrewDeal, withdrewList} from '@/api/transaction'
  import {getTime} from '@/utils/index'
  import store from '../../../store'
  import { getCompanyCard } from "@/api/role";

  export default {
    data() {
      return {
        value1:'',
        optionData:[],
        activeNames: ['1'],
        labelPosition: 'right',
        teams: [{
          //  memo:"",
          "id": 1,
          "cardNumber": "string",
          "name": "string",
          "bank": "string",
          "accountWithBank": "string",
          "bin": "string",
          "status": "string",
          "user": {
            "id": 2,
            "username": "string",
            "role": 3,
            "tableId": 1,
            "cards": []
          }
        }
        ],
        newRow: {
          memo: ""
        },
        // memop:"",
        currentPage: 1,
        pagesize: 10,
        dialogFormVisible: false,
        id: '',
        memo: '',
        state: '',
        tableHeight:'100'
      }
    },
    mounted: function () {
      this.tableHeight = window.innerHeight - 90;
      //window.innerHeight:浏览器的可用高度
      //this.$refs.table.$el.offsetTop：表格距离浏览器的高度
      //后面的50：根据需求空出的高度，自行调整
    },
    computed: {
      total() {
        return this.teams.length;
      }
    },
    created() {
      this.getTeams();
    },
    methods: {
      getCompanyCard(){
        getCompanyCard(store.getters.uid).then(response => {
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: 'warning'
            });
          } else {
            this.optionData = response.data;
          }
        })
      },
      addMemo(row) {
        this.dialogFormVisible = true;
        this.newRow = row;
      },
      update() {
        if (this.state === "FAILED" && this.newRow.memo === "") {
          this.$message({
            message: "请添加备注",
            type: 'warning'
          });
          return false;
        } else {
          this.dialogFormVisible = false;
          withdrewDeal(this.value1,this.id, this.newRow.memo, store.getters.uid, this.state).then(response => {
            if (response.code != 200) {
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
      cancel() {
        this.dialogFormVisible = false;
        this.newRow.memo = "";
      },
      getWithdrew(id, memo, state) {
        this.dialogFormVisible = true;
        this.id = id;
        this.memo = memo;
        this.state = state;

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
        withdrewList(store.getters.uid).then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: 'warning'
            });
          } else {
            var a = []
            this.teams = response.data;
            this.teams.forEach(el => {
              el.applyTime = getTime(el.applyTime)
              if (el.state == 'DEALING')
                a.push(el)
            });
            this.teams = a;
          }
        })
      },
      handleChange(val) {
        if (val == 2) {
          this.getTeams();

        }
      }
    }
  }
</script>

<style scoped>

</style>
