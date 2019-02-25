<template>
  <div class="app-container">
    <div>所有代理商</div>
    <el-input v-model="searchStr" suffix-icon="el-icon-search" placeholder="请输入搜索内容"></el-input>
    <el-table
      :data="filterData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
      height="450"
      border
      style="width: 100%">
      <!-- <el-table-column prop="userInfo.username" label="用户名" align="center"></el-table-column> -->

      <el-table-column prop="name" label="代理名" align="center"></el-table-column>
      <!--<el-table-column prop="alipayp" label="支付宝点位"  align="center"></el-table-column>
      <el-table-column prop="wechatp" label="微信点位"  align="center"></el-table-column>  -->
      <el-table-column prop="dailyFlow" label="当日流量" align="center"></el-table-column>
      <el-table-column prop="dailyCommission" label="当日佣金" align="center"></el-table-column>
      <el-table-column prop="status" label="状态" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" align="center">
        <template scope="scope">
          <el-button size="small"
                     @click="openDialog(scope.$index,scope.row)">修改
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
        :total="total"
      ></el-pagination>
    </div>
    <el-dialog title="修改代理信息" :visible.sync="dialogFormVisible">
      <el-form ref="form" :model="newRow.user" :rules="addRules" label-width="20%">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="newRow.user.username" placeholder="请输入用户名" style="width:90%;"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="newRow.user.password" type="password" placeholder="请输入密码" style="width:90%;"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="newRow.status" placeholder="请输入状态" style="width:20%;">
            <el-option label="启用" value="启用"></el-option>
            <el-option label="停用" value="停用"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="点位设置">
          <el-button type="primary" @click="dialogShow">查看或修改点位</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateSupplier('form')">修 改</el-button>
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
  import {agentsGet, updateAgent,getSelect, getPayType, getPayRateList, updatePayRateList} from '@/api/role'
  import {isvalidUsername, isvalidPassword} from '@/utils/validate'

  export default {
    data() {
      const validateUsername = (rule, value, callback) => {
        if (!isvalidUsername(value)) {
          callback(new Error('请输入正确的用户名（只能由英文字母组成）'))
        } else {
          callback()
        }
      }
      const validatePass = (rule, value, callback) => {
        if (!isvalidPassword(value)) {
          callback(new Error('必须包含字母和数字且超过8位'))
        } else {
          callback()
        }
      }
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
        alipayRateDialogFormVisible: false,
        teams: [{
          // alipay:""
          // id: 1,
          // agentName: "a1",
          // status: "string",
          // percent: 0.3,
          // balance: 0,
          // userInfo: {
          //     id: 29,
          //     username: "a1",
          //     password: "$2a$10$dJ/s9iiGBGzQS/7XbSYUMODsSzblY0X3Zu7GKdjvKrKuHoaCc5Q6a",
          //     role: 2,
          //     tableId: 1,
          //     cards: []
          // }
        }
        ],
        currentPage: 1,
        pagesize: 10,
        dialogFormVisible: false,
        searchStr: '', // 新增
        newRow: {
          codeType: "",
          level: 0,
          // password: "",
          user: {
            username: "",
            password: ""
          },
          status: "启用",
        },
        addRules: {
          username: [{required: true, trigger: 'blur', validator: validateUsername}],
          password: [{required: true, trigger: 'blur', validator: validatePass}]
          // post: [{ required: true, trigger: 'blur', validator: validateEmpty }]
        }
      }
    },
    computed: {
      filterData() {
        return this.teams.filter(item => {
          var reg = new RegExp(this.searchStr, "i");
          return !this.searchStr || reg.test(item.userInfo.username);
        });
      },
      total() {
        return this.teams.length
      }
    },
    created() {
      this.getData();
    },
    methods: {
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
        console.log(111);
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
            if( response.data === undefined){
              this.point = "";
              this.status = "停用";
            }else{
              this.point = response.data.rate;
              this.status = response.data.status;
            }
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
      updateSupplier(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            updateAgent(this.newRow.uid,
              this.newRow.alipay,
              this.newRow.user.username,
              this.newRow.user.password,
              this.newRow.status,
              this.newRow.wechat,).then(response => {
              if (response.code != 200) {
                this.$message({
                  message: response.data.response.description,
                  type: 'warning'
                });
              } else {
                // this.teams[this.newRowIndex].priority = this.newRow.level;
                this.dialogFormVisible = false;
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
        agentsGet().then(response => {
          console.log(response, 'sdll')
          if (response.data.infoCod) {
            this.$message({
              message: response.data.description,
              type: 'warning'
            });
          } else {
            this.teams = response.data;
            this.teams.forEach(el => {
              el.alipayp = el.alipay + '%';
              el.wechatp = el.wechat + '%';
            });

          }
        })
      },
      handleChange(val) {
        console.log(val);
        if (val == 2) {
          this.getTeams();
        }
      },
      openDialog(index, row) {
        this.dialogFormVisible = true;
        //this.newRow = JSON.parse(JSON.stringify(row));
        this.newRow = row;
        this.newRow.user = row.userInfo;
        this.newRow.level = row.priority;

      },
    }
  };
</script>

<style scoped>
</style>
