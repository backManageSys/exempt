<template>
  <div>
    <div class="updatePswWrap">
      <!--修改密码按钮-->
      <p><button @click.prevent="formShow" v-show="updateBtnSn">修改密码</button></p>
      <!--表单-->
      <div class="updatePswBg" v-show="formVis" @click="formShow">
        <form class="updatePswCont" @click.prevent.stop>
          <div class="pswInp">
            <label for="psw">新密码:</label>
            <!--新密码输入框-->
            <input id="psw" type="password" v-model="newPsw" placeholder="" @focus="" @blur="onBlur" required />
            <p v-show="markVis">密码必须8位以上且包含数字和字母</p>
          </div>
          <!--两个按钮的整体父级-->
          <div class="btnWrap">
            <button type="button" @click.prevent="cancel">取消</button>
            <button type="button" @click.prevent="submitPsw">提交修改</button>
          </div>
        </form>
      </div>
    </div>
    <el-card v-if="userInfo.role==1" class="box-card">
      <div slot="header" class="clearfix">
        <span class="span">个人信息</span>
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
      <div class="text item">{{ '修改或查看: '}}
        <el-button size="small" @click="equipment()">查看</el-button>
      </div>
      <!--<div class="text item" v-if="info.codeType == 'TPASS' ">码类型: 转账通码</div>-->
      <!--<div class="text item" v-if="info.codeType == 'TSOLID' ">码类型: 转账固码</div>-->
      <!--<div class="text item" v-if="info.codeType == 'RPASSOFF' ">码类型: 离线收款通码</div>-->
      <!--<div class="text item" v-if="info.codeType == 'RPASSQR' ">码类型: 在线收款通码</div>-->
      <!--<div class="text item" v-if="info.codeType == 'RSOLID' ">码类型:' 收款固码</div>-->
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
      <div class="text item">{{ '查看点位: '}}
        <el-button size="small" @click="alipayRate()">查看</el-button>
      </div>
    </el-card>
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
    <el-dialog title="修改供码用户信息" :visible.sync="dialogFormVisiblEequipment">
      <el-form ref="form" :model="newRow.user" label-width="13%">
        <el-form-item label="码类型">
          <el-select v-model="value" placeholder="请选择" @change="firstChange" @visible-change="getSelect"
                     style="width:30%">
            <el-option
              v-for="item in options1"
              :key="item.id"
              :label="item.codeCategory"
              :value="item.codeCategory">
            </el-option>
          </el-select>
          <el-select v-model="value1" placeholder="请选择"
                     @visible-change="getPayType" style="width: 35%">
            <el-option
              v-for="item in options2"
              :key="item.id"
              :label="item.codeType"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisiblEequipment = false">取 消</el-button>
        <el-button type="primary" @click="updateSupplier">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {getInfo} from "@/api/login";
  import store from "../../store";
  import {withdrew} from "@/api/transaction";
  import {getTime} from "@/utils/index";
  import {getSelect, getPayType, supplierUpdate} from '@/api/role'
  import {isvalidPassword} from '@/utils/validate'
  import {updatePsw} from "@/api/role"; // 修改密码
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
        updateBtnSn : true,
        formVis : false,//控制修改密码的表单显隐
        markVis : false,// 控制提示语显隐
        newPsw : '######################################',
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
        list: null,
        info: {},
        userInfo: {},
        newRowIndex: 1,
        dialogFormVisible: false,
        alipayRateDialogFormVisible: false,
        dialogFormVisiblEequipment: false,
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
      equipment() {
        this.dialogFormVisiblEequipment = true;
        this.getSelect();
        this.getPayType();
      },
      updateSupplier(formName) {
        supplierUpdate(
          this.value1,
          this.info.priority,
          this.info.user.password,
          this.info.status,
          store.getters.uid,
          store.getters.uid
        ).then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.teams[this.newRowIndex].priority = this.newRow.level;
            this.dialogFormVisible = false;
            this.$message({
              message: "修改成功",
              type: "success"
            });
            this.getData();
          }
        });
      },
      firstChange() {
        this.secondState = false;
        this.options2 = [];
        this.point = "";
        this.status = "";
        this.value1 = "";
      },
      secondChange() {
        getPayRateList(store.getters.uid, this.value1).then(response => {
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
      openDialog() {
        this.dialogFormVisible = true;
        this.newRow.type = this.userInfo.role == 2 ? "agent" : "merchant";
        this.newRow.id = store.getters.uid;
        //this.newRow = JSON.parse(JSON.stringify(row));
      },
      alipayRate() {
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
          this.value = response.data.info.codeCategory;
          this.value1 = response.data.info.payTypeId;
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
      },
      //点击弹出修改密码的表单
      formShow(){
        this.formVis = !this.formVis;
      },
      //取消修改密码
      cancel(){
        this.formVis = !this.formVis;
      },
      // 提交新密码
      submitPsw(){
        if (isvalidPassword(this.newPsw)){
          this.markVis = false;// 隐藏密码错误提示语
          updatePsw(this.newPsw,this.$store.state.user.uid).then((data)=>{
            if (data.code == 200) {
              this.formVis = false;
              this.$message({
                    message: '修改密码成功',
                    type: 'success'
                  })
            } else {
              this.$message({
                    message: data.data.description,
                    type: 'warning'
                  });
            }
          }).catch((err)=>{
            console.log(err)
          });
        }else {
          this.markVis = true;//显示密码错误提示语
        }
      },
      // 密码框失去焦点时触发
      onBlur(){
        if (isvalidPassword(this.newPsw)){
          this.markVis = false;// 隐藏密码错误提示语
        }else {
          this.markVis = true;//显示密码错误提示语
        }
      },
    }
  };
</script>
<style scoped>
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
    margin: 0 auto 80px;
  }
  /*修改密码——整体父级盒子*/
  .updatePswWrap  {
    width: 680px;
    margin: 60px auto 30px;
    color: #606266;
    -webkit-box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
    box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
    padding: 8px 0;
  }
  .updatePswWrap *  {
    background-color: transparent;
    box-sizing: border-box;
    border: none;
    color: #606266;
  }
  .updatePswWrap button  {
    border: 1px solid #dcdfe6;
    cursor: pointer;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    padding: 12px 20px;
    margin: 0 8px;
    outline: none;
  }
  .updatePswWrap button:active  {
    border-color: #3a8ee6;
    color:#3a8ee6;
  }
  .updatePswWrap button:hover  {
    background-color: #ecf5ff;
    color:#3a8ee6;
  }
  /*修改密码的按钮的外层*/
  .updatePswWrap>p  {
    width: 100%;
    padding-left: 1px;
  }
  /*表单背景*/
  .updatePswBg  {
    transition: 300ms;
    display: flex;
    justify-content: space-around;
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    overflow: auto;
    margin: 0;
    z-index: 100;
    background-color: rgba(60,60,60,0.7);
  }
  /*表单*/
  .updatePswCont   {
    margin: auto;
    overflow: auto;
    width: 600px;
    padding: 60px;
    background-color: #fff;
    -webkit-border-radius: 6px;
    -moz-border-radius: 6px;
    border-radius: 6px;
  }
  /*密码输入框外层*/
  .pswInp  {
    width: 100%;
    position: relative;
    margin-bottom: 20px;
  }
  /*提示语*/
  .pswInp>p  {
    position: absolute;
    right: 0px;
    top: -4px;
    color: #ff6600;
    font-size: 15px;
  }
  /*输入框*/
  #psw  {
    width: 170px;
    height:40px;
    font-size: 14px;
    padding: 0 15px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    outline: none;
  }
  #psw:focus  {
    border-color:#3a8ee6;
  }
  /*label*/
  .pswInp>label  {
    line-height: 40px;
  }
</style>
