<template>
  <div class="app-container">
    <div>所有商户</div>
    <el-input v-model="searchStr" suffix-icon="el-icon-search" placeholder="请输入搜索内容"></el-input>
    <el-table
      :data="filterData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
      border>
      <!-- <el-table-column prop="user.username" label="用户名"  align="center"></el-table-column> -->
      <el-table-column prop="name" label="商户名" align="center"></el-table-column>
      <el-table-column prop="priority" label="等级" align="center"></el-table-column>
      <el-table-column prop="balance" label="余额" align="center"></el-table-column>
      <el-table-column prop="applyName" label="操作上级" align="center"></el-table-column>
      <el-table-column prop="addTimep" label="添加时间" align="center" min-width="70%"></el-table-column>
      <el-table-column prop="statusp" label="状态" align="center">
        <template slot-scope="{row}">
          <el-button type="success" size="small" v-if="row.status=='启用'">启用</el-button>
          <!-- <el-tag type="success" v-if="row.status=='审批通过'">{{ row.approvalTime }}</el-tag> -->
          <el-button type="info" size="small" v-else-if="row.status=='停用'">停用</el-button>
          <!-- <el-tag type="warning" v-if="row.status=='等待审批'">{{ row.addTime }}</el-tag> -->
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" align="center">
        <template scope="scope">
          <el-button size="small" @click="openDialog(scope.$index,scope.row)">查看或修改</el-button>
        </template>
      </el-table-column>
      <!-- <el-table-column prop="approvalTime" label="审批时间"  align="center"></el-table-column> -->


    </el-table>
    <div class="block">
      <span class="demonstration">调整每页显示条数</span>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pagesize"
        layout="sizes, prev, pager, next"
        :total="total">
      </el-pagination>
    </div>
    <el-dialog title="查看或修改商户信息" :visible.sync="dialogFormVisible">
      <el-form ref="form" :model="newRow.user" :rules="addRules" label-width="20%">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="newRow.user.username" disabled="disabled" placeholder="用户名" style="width:90%;"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="newRow.user.password" type="password" placeholder="密码" style="width:90%;"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="newRow.status" placeholder="启用" style="width:20%;">
            <el-option label="启用" value="启用"></el-option>
            <el-option label="停用" value="停用"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="等级">
          <el-select v-model="newRow.priority" placeholder="请选择" style="width:15%;">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="点位设置">
          <el-button type="primary" @click="dialogShow">查看或修改点位</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateSupplier('form')">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="查看或修改点位" :visible.sync="alipayRateDialogFormVisible">
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
        <el-button @click="alipayRateDialogFormVisible = false">关 闭</el-button>
        <el-button type="primary" @click="edit">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {merchantsGet, updateMerchant, getSelect, getPayType, getPayRateList, updatePayRateList} from '@/api/role'
  import {isvalidUsername, isvalidPassword} from '@/utils/validate'
  import store from '../../../../store'
  import {getTime} from '@/utils/index'

  export default {
    data() {
      const validateUsername = (rule, value, callback) => {
        if (!isvalidUsername(value)) {
          callback(new Error('请输入正确的用户名（只能由英文字母组成）'))
        } else {
          callback()
        }
      };
      const validatePass = (rule, value, callback) => {
        if (!isvalidPassword(value)) {
          callback(new Error('必须包含字母和数字且超过8位'))
        } else {
          callback()
        }
      };
      return {
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
        teams: [{
          "addTime": "2019-01-17T16:37:02.184Z",	//申请时间
          "alipay": 0,
          "applyId": 0,	//申请人id
          "approvalTime": "2019-01-17T16:37:02.184Z",	//审批时间
          "approverId": 0,	//审批人id
          "balance": 0,
          "id": 0,
          "name": "string",
          "priority": 0,	//用户星级
          "status": "启用",	//WAITING 等待审批/PASS 审批通过/ REJECT 审批不通过
          "user": {
            "avatarUrl": "string",
            "cards": [
              {
                "accountWithBank": "string",
                "bank": "string",
                "bin": "string",
                "cardNumber": "string",
                "id": 0,
                "name": "string",
                "status": "string"
              }
            ],
            "id": 0,
            "password": "string",
            "role": 0,
            "tableId": 0,
            "username": "string",
          },
          "verifyCode": "string",
          "wechat": 0
        }
        ],
        currentPage: 1,
        pagesize: 10,
        newRow: {
          "status": "",
          "codeType": "",
          "level": 0,
          "password": "",
          "user": {},
        },
        newRowIndex: 1,
        dialogFormVisible: false,
        alipayRateDialogFormVisible: false,
        searchStr: '', // 新增
        options: [
          {
            value: "1",
            label: "1"
          },
          {
            value: "2",
            label: "2"
          },
          {
            value: "3",
            label: "3"
          },
          {
            value: "4",
            label: "4"
          },
          {
            value: "5",
            label: "5"
          }
        ],
        addRules: {
          username: [{required: true, trigger: 'blur', validator: validateUsername}],
          password: [{required: true, trigger: 'blur', validator: validatePass}]
          // post: [{ required: true, trigger: 'blur', validator: validateEmpty }]
        },
      }
    },
    computed: {
      filterData() {
        return this.teams.filter((item) => {
          var reg = new RegExp(this.searchStr, 'i')
          return !this.searchStr || reg.test(item.name)
        })
      },
      total() {
        return this.teams.length
      }
    },

    created() {
      this.getData();
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
        this.thirdState = false;
        this.selectState = false;
        getPayRateList(this.newRow.user.id, this.value1).then(response => {
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.point = response.data.rate;
            this.status = response.data.status;
          }
        });
      },
      edit() {
        this.alipayRateDialogFormVisible = false;
        updatePayRateList(this.newRow.user.id, this.value1,this.point,this.status,).then(response => {
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.$message({
              message: response.data,
              type: 'success'
            });
          }
        });
      },
      dialogShow() {
        this.alipayRateDialogFormVisible = true;
        this.point = "";
        this.options1 = [];
        this.options2 = [];
        this.value = '';
        this.value1 = '';
        this.value2 = '';
        this.secondState = true;
        this.thirdState = true;
        this.selectState = true;
        this.status = '';
      },
      updateSupplier(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            updateMerchant(this.newRow.user.id,
              this.newRow.level,
              this.newRow.user.username,
              this.newRow.user.password,
              this.newRow.status,
              this.newRow.wechat,
              this.newRow.alipay_TPASS,
              this.newRow.alipay_TSOLID,
              this.newRow.alipay_RPASSOFF,
              this.newRow.alipay_RPASSQR,
              this.newRow.alipay_RSOLID,
              this.newRow.alipay_RedEnvelope,
            ).then(response => {
              this.dialogFormVisible = false;
              if (response.code != 200) {
                this.$message({
                  message: response.data.description,
                  type: 'warning'
                });
              } else {
                // this.teams[this.newRowIndex].priority = this.newRow.level;
                this.getData();
                this.$message({
                  message: '修改成功',
                  type: 'success'
                });
              }
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      openDialog(index, row) {
        this.dialogFormVisible = true;
        this.newRow = row;
        this.newRow.level = row.priority;
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
        merchantsGet().then(response => {
          console.log(response,'MerchantsGetResponse');
          if (response.data.infoCod) {
            this.$message({
              message: response.data.description,
              type: 'warning'
            });
          } else {
            this.teams = response.data;
            this.teams.forEach(el => {
              //    el.statusp =el.status=='WAITING'?'等待审批':'PASS'?'审批通过':'审批不通过';
              el.wechatp = el.wechat + '%';
              el.alipayp = el.alipay + '%';
              el.addTimep = getTime(el.addTime)
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
  };
</script>

<style scoped>
</style>
