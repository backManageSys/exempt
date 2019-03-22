<template>
  <div class="app-container">
    <div>待审批商户</div>
    <el-table
      :data="teams"
      height="450"
      border
      style="width: 100%">
      <el-table-column prop="mid" label="商户id" align="center"></el-table-column>
      <el-table-column prop="user.username" label="用户名" align="center"></el-table-column>
      <el-table-column label="查看点位" align="center">
        <template scope="scope">
          <el-button size="small" @click="alipayRate(scope.row)">查看</el-button>
        </template>
      </el-table-column>
      <!-- <el-table-column prop="approverId" label="审核人id"  align="center"></el-table-column> -->
      <el-table-column prop="priority" label="等级" align="center"></el-table-column>
      <!-- <el-table-column prop="user.password" label="密码"  align="center"></el-table-column> -->
      <el-table-column prop="applyName" label="代理商" align="center"></el-table-column>
      <el-table-column prop="status" label="状态" align="center"></el-table-column>
      <el-table-column label="操作" width="280" align="center">
        <template scope="scope">
          <el-button size="small"
                     @click="approval(scope.$index,scope.row,1)">审批通过
          </el-button>
          <el-button size="small"
                     @click="approval(scope.$index,scope.row,0)">审批不通过
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <span class="demonstration">调整每页显示条数</span>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="10"
        layout="sizes, prev, pager, next"
        :total=total>
      </el-pagination>
    </div>
    <el-dialog title="点位信息" :visible.sync="alipayRateDialogFormVisible">
      <el-select v-model="value" placeholder="请选择" @change="firstChange" @visible-change="getSelect" style="width: 20%">
        <el-option
          v-for="item in options1"
          :key="item.id"
          :label="item.codeCategory"
          :value="item.codeCategory">
        </el-option>
      </el-select>
      <el-select v-model="value1" placeholder="请选择" :disabled="secondState" @change="secondChange"
                 @visible-change="getPayType" style="width: 30%">
        <el-option
          v-for="item in options2"
          :key="item.id"
          :label="item.codeType"
          :value="item.id">
        </el-option>
      </el-select>
      <el-input v-model="point" style="width: 15%;" type="number" placeholder="请输入点位" :disabled="thirdState"></el-input>
      %
      <el-select v-model="status" placeholder="启用" style="width: 14%" :disabled="selectState">
        <el-option label="启用" value="启用"></el-option>
        <el-option label="停用" value="停用"></el-option>
      </el-select>
      <div slot="footer" class="dialog-footer">
        <el-button @click="alipayRateDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="alipayRateDialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {waitApprovalMer, ApprovalMer} from '@/api/company';
  import {getSelect, getPayType, getPayRateList} from '@/api/role'
  import store from '../../../../store'

  export default {
    data() {
      return {
        activeNames: ['1'],
        labelPosition: 'right',
        err: '&#12288;',
        teams: [{
          alipay: 0,
          approverId: 0,
          level: 0,
          password: 0,
          status: 0,
          username: 0,
          wechat: 0,
          mid: 0,
        }
        ],
        newRow: {},
        alipayRateDialogFormVisible: false,
        currentPage: 1,
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
      }
    },
    computed: {
      total() {
        return this.teams.length;
      }
    },
    created() {
      this.getData();
      // this.
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
        this.secondState = false;
      },
      secondChange() {
        getPayRateList(this.newRow.user.id, this.value1).then(response => {
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            if (response.data === undefined) {
              this.point = 0;
            } else {
              this.point = response.data.rate;
            }
            if (response.data === undefined) {
              this.status = "停用";
            } else {
              this.status = response.data.status;
            }
          }
        });
      },
      alipayRate(row) {
        this.newRow = row;
        this.alipayRateDialogFormVisible = true;
      },
      approval(index, row, status) {
        ApprovalMer(
          row.alipay,
          store.getters.uid,
          row.priority,
          row.user.password,
          status,
          row.user.username,
          row.wechat,
          row.mid).then(response => {
          if (response.data.infoCode == 200) {
            if (status == 1)
              this.$message({
                message: "审批通过",
                type: 'success'
              });
            if (status == 0)
              this.$message({
                message: "审批不通过",
                type: 'warning'
              });
          } else {
            this.$message({
              message: '审批失败',
              type: 'error'
            });
          }
        });
        this.teams.splice(index, 1)

      },
      handleSizeChange(val) {

      },
      handleCurrentChange(val) {
      },
      getData() {
        this.getTeams();
      },
      getTeams() {
        waitApprovalMer().then(response => {
          console.log(response,'waitApprovalMerResponse')
          if (response.code != 200) {
            this.$message({
              message: "获取待审批商户失败",
              type: 'error'
            });
          } else {
            this.teams = response.data;
            this.teams.forEach(el => {
              el.approverId = store.getters.uid;
              el.mid = el.id;
              el.alipayp = el.alipay + '%';
              el.wechatp = el.wechat + '%';
            });
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
