<template>
  <div>
    <el-card v-if="userInfo.role==1" class="box-card">
      <div slot="header" class="clearfix">
        <span
          class="span"
        >个人信息</span>
      </div>
      <div class="text item">{{ '用 户 名: ' + userInfo.username }}</div>
      <div class="text item">{{ '所属团队: ' + info.team }}</div>
      <div class="text item">{{ '添加时间: ' + gettime(info.addTime) }}</div>
      <div class="text item">{{ '状 &#x3000; 态: ' + info.status }}</div>
      <div class="text item">{{ '岗 &#x3000; 位: ' + info.post }}</div>
      <div class="text item">{{ '角 &#x3000; 色: ' + userInfo.role_ch }}</div>
    </el-card>
    <el-card v-if="userInfo.role==4" class="box-card">
      <div slot="header" class="clearfix">
        <span>个人信息</span>
      </div>
      <div class="text item">{{ '用户名: ' + userInfo.username }}</div>
      <div class="text item">{{ '添加时间: ' + gettime(info.time) }}</div>
      <!-- <div class="text item">{{ '审批时间: ' + gettime(info.approvalTime) }}</div> -->
      <div class="text item">{{ '状 &#x3000;态: ' + info.status }}</div>
      <div class="text item">{{ '角 &#x3000;色: ' + userInfo.role_ch }}</div>
      <div class="text item">{{ '等 &#x3000;级: ' + info.priority }}</div>
      <div class="text item" v-if="info.codeType == 'TPASS' ">码类型: 转账通码</div>
      <div class="text item" v-if="info.codeType == 'TSOLID' ">码类型: 转账固码</div>
      <div class="text item" v-if="info.codeType == 'RPASSOFF' ">码类型: 离线收款通码</div>
      <div class="text item" v-if="info.codeType == 'RPASSQR' ">码类型: 在线收款通码</div>
      <div class="text item" v-if="info.codeType == 'RSOLID' ">码类型:' 收款固码</div>
    </el-card>
    <el-card v-if="userInfo.role==2" class="box-card">
      <div slot="header" class="clearfix">
        <span>个人信息</span>
        <!-- <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button> -->
      </div>
      <div class="text item">{{ '用户名: ' + userInfo.username }}</div>
      <div class="text item">{{ '状 &#x3000;态: ' + info.status }}</div>
      <div class="text item">{{ '角 &#x3000;色: ' + userInfo.role_ch }}</div>
      <div class="text item">{{ '支付宝点位: ' + info.alipay + '%'}}</div>
      <div class="text item">{{ '微信点位: ' + info.wechat + '%'}}</div>
      <el-form>
        <el-form-item>{{ '余额:' + info.balance }}</el-form-item>
        <el-form-item>
          <el-button type="primary" @click="openDialog">提现</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card v-if="userInfo.role==3" class="box-card">
      <div slot="header" class="clearfix">
        <span>个人信息</span>
      </div>
      <div class="text item">{{ '用户名: ' + userInfo.username }}</div>
      <div class="text item">{{ '添加时间: ' + gettime(info.addTime) }}</div>
      <div class="text item">{{ '状 &#x3000;态: ' + info.status }}</div>
      <div class="text item">{{ '角 &#x3000;色: ' + userInfo.role_ch }}</div>
      <div class="text item">{{ '等&#x3000;级: ' + info.priority }}</div>
      <div class="text item">{{ '支付宝点位: '}}
        <el-button size="small" @click="alipayRate()">查看</el-button>
      </div>
      <div class="text item">{{ '微信点位: ' + info.wechat + '%' }}</div>
      <el-form>
        <el-form-item>{{ '余额:' + info.balance }}</el-form-item>
        <el-form-item>
          <el-button type="primary" @click="openDialog">提现</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-dialog title="支付宝点位信息" :visible.sync="alipayRateDialogFormVisible">
            <el-form  :model="info"  label-width="30%">
                <el-form-item label="转账通码点位:">
                    <div>{{'&#12288;'+info.alipay_TPASS+"%"}}</div>
                </el-form-item>
                <el-form-item label="转账固码点位:">
                    <div>{{'&#12288;'+info.alipay_TSOLID+"%"}}</div>
                </el-form-item>
                <el-form-item label="收款通码离线码点位:">
                    <div>{{'&#12288;'+info.alipay_RPASSOFF+"%"}}</div>
                </el-form-item>
                <el-form-item label="收款通码在线码点位:">
                    <div>{{'&#12288;'+info.alipay_RPASSQR+"%"}}</div>
                </el-form-item>
                <el-form-item label="收款固码(二开)点位:">
                    <div>{{'&#12288;'+info.alipay_RSOLID+"%"}}</div>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="alipayRateDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="alipayRateDialogFormVisible = false">确 定</el-button>
            </div>
        </el-dialog>
    <!-- <el-table :data="list" style="width: 100%;padding-top: 15px;">
      <el-table-column label="卡号" min-width="200" prop="cardNumber"></el-table-column>
      <el-table-column label="姓名" min-width="200" prop="name"></el-table-column>
      <el-table-column label="银行" min-width="200" prop="bank"></el-table-column>
      <el-table-column label="开户行" min-width="200" prop="accountWithBank"></el-table-column>
      <el-table-column label="银行编号" min-width="200" prop="bin"></el-table-column>
    <el-table-column label="状态" min-width="200" prop="status"></el-table-column>-->
    <!-- </el-table-column>
            <template slot-scope="scope">
                ¥{{ scope.row.price | toThousandFilter }}
            </template>
            </el-table-column>
            <el-table-column label="Status" width="100" align="center">
            <template slot-scope="scope">
                <el-tag :type="scope.row.status | statusFilter"> {{ scope.row.status }}</el-tag>
            </template>
    </el-table-column>-->
    <!-- </el-table> -->
    <el-dialog title="申请提现" :visible.sync="dialogFormVisible">
      <el-form :model="newRow">
        <el-form-item label="申请金额">
          <el-input v-model="newRow.money" placeholder="申请金额"></el-input>
        </el-form-item>
        <el-form-item label="提现卡号">
          <el-select v-model="newRow.cardId" placeholder="提现卡号">
            <el-option
              v-for="card in list"
              :key="card.id"
              :label="card.accountWithBank"
              :value="card.accountWithBank"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="getMoney">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getInfo } from "@/api/login";
import store from "../../store";
import { withdrew } from "@/api/transaction";
import { getTime } from "@/utils/index";
// getInfo(uid)
export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        success: "success",
        pending: "danger"
      };
      return statusMap[status];
    },
    orderNoFilter(str) {
      return str.substring(0, 30);
    }
  },
  data() {
    return {
      list: null,
      info: {},
      userInfo: {},
      newRowIndex: 1,
      dialogFormVisible: false,
      alipayRateDialogFormVisible:false,
      newRow: {
        cardId: "",
        id: 0,
        money: 0,
        type: "string"
      }
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    openDialog() {
      this.dialogFormVisible = true;
      this.newRow.type = this.userInfo.role == 2 ? "agent" : "merchant";
      this.newRow.id = store.getters.uid;
      // console.log("opendialog");
      //this.newRow = JSON.parse(JSON.stringify(row));
    },
    alipayRate(){
      this.alipayRateDialogFormVisible = true;
    },
    fetchData() {
      getInfo(store.getters.uid).then(response => {
        //   getInfo(33).then(response => {
        if (response.data.info.userInfo) {
          this.userInfo = response.data.info.userInfo;
        } else {
          this.userInfo = response.data.info.user;
        }
        this.list = this.userInfo.cards;
        this.userInfo.role_ch = this.userInfo.role == 1 ? "管理员" : this.userInfo.role == 2 ? "代理" : this.userInfo.role == 3 ? "商户" : "供码用户";
        this.info = response.data.info;
       // this.info.status = this.info.status == "已通过" || this.info.status == "WORKING" ? "未审批" : "已通过";
        // console.log(this.userInfo, "klkll");
      });
    },
    gettime(date) {
      return getTime(date);
    },
    getMoney() {
      // console.log("get money", this.newRow);
      withdrew(
        this.newRow.cardId,
        this.newRow.id,
        // 33,
        this.newRow.money,
        this.newRow.type
      ).then(res => {
        if (res.code != 200) {
          this.$message({
            message: res.data.description,
            type: "warning"
          });
        } else {
          this.$message({
            message: "提交提现申请成功",
            type: "success"
          });
        }
      });
      this.dialogFormVisible = false;
    }
  }
};
</script>
<style>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
  color: black;
  /* border: 1px solid black; */
  width: 310px;
  display: inline-block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 680px;
  height: 350px;
  margin: 80px auto;
}
</style>
