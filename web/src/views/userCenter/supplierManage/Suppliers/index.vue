<template>
  <div class="app-container">
    <div>所有供码用户</div>
    <el-input v-model="searchStr" suffix-icon="el-icon-search" placeholder="请输入搜索内容"></el-input>
    <el-table
      :data="filterData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
      height="450"
      border
      style="width: 100%">
      <el-table-column prop="user.username" label="用户名" align="center"></el-table-column>
      <el-table-column prop="priority" label="等级" align="center"></el-table-column>
      <el-table-column prop="devices_team" label="设备" align="center">
        <template slot-scope="scope">
          <el-tag
            :type="device.online?'success':'info'"
            v-for="device in scope.row.devices"
            :key="device.device_team"
          >{{ device.device_team }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="applicant" label="操作上级" align="center"></el-table-column>
      <el-table-column prop="timep" label="添加时间" align="center" min-width="120%"></el-table-column>
      <el-table-column prop="status" label="账号状态" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" align="center">
        <template scope="scope">
          <el-button size="small" @click="openDialog(scope.$index,scope.row)">修改</el-button>
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
    <el-dialog title="修改供码用户信息" :visible.sync="dialogFormVisible">
      <el-form ref="form" :model="newRow.user" :rules="addRules" label-width="13%">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="newRow.user.username" disabled="disabled" type="text" placeholder="用户名" style="width:90%;"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="newRow.user.password" type="password" placeholder="密码" style="width:90%;"></el-input>
        </el-form-item>
        <el-form-item label="码类型">

            <el-select v-model="value" placeholder="请选择" @change="firstChange" @visible-change="getSelect" style="width:30%">
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

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateSupplier('form')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {suppliersGet, supplierUpdate,getSelect, getPayType} from "@/api/role";
  import {isvalidUsername, isvalidPassword} from '@/utils/validate'
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
        teams: [
          {
            priority: 0,
            devices: [],
            status: "启用",
            user: {
              username: ''
            },
            devices_team: " ",
            username: ''
          }
        ],
        newRow: {

          codeType: "RPASSOFF",
          level: 0,
          username: "",
          password: "",
          user: {},
          priority: 0,
          status: "启用",
        },
        addRules: {
          username: [{required: true, trigger: 'blur', validator: validateUsername}],
          password: [{required: true, trigger: 'blur', validator: validatePass}]
          // post: [{ required: true, trigger: 'blur', validator: validateEmpty }]
        },
        currentPage: 1,
        pagesize: 10,
        newRowIndex: 1,
        dialogFormVisible: false,
        searchStr: "",// 新增
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
      };
    },
    computed: {
      filterData() {
        return this.teams.filter(item => {
          var reg = new RegExp(this.searchStr, "i");
          return !this.searchStr || reg.test(item.user.username);
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
        this.value1 = "";
      },
      updateSupplier(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            supplierUpdate(
              this.value1,
              this.newRow.priority,
              this.newRow.user.username,
              this.newRow.user.password,
              this.newRow.status,
              this.newRow.user.id
            ).then(response => {
              if (response.code != 200) {
                this.$message({
                  message: response.data.description,
                  type: "warning"
                });
              } else {
                // this.teams[this.newRowIndex].priority = this.newRow.level;
                this.dialogFormVisible = false;
                this.$message({
                  message: "修改成功",
                  type: "success"
                });
                this.getData();
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
        this.value =   this.newRow.codeCategory;
        this.value1 =   this.newRow.payTypeId;
        this.getSelect();
        this.getPayType();
        // if(row.codeType==None){
        //     this.newRow.codeType = 'TSOLID'
        // }else{
        //      this.newRow.codeType = row.codeType;
        // }
        this.newRow.user = row.user;
        this.newRow.level = row.priority;
        // this.newRow.password = row.user.password;
        //this.newRow = JSON.parse(JSON.stringify(row));
        this.newRowIndex = index;
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
        suppliersGet().then(response => {
          console.log(response,'SuppliersResponse');
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.teams = response.data;

            this.teams.forEach(el => {
              el.devices.forEach(de => {
                de.device_team = de.imei + " " + (de.online ? "在线" : "离线");
              });
              el.timep = getTime(el.time)
            });
          }
        });
      }
    }
  };
</script>

<style scoped>
</style>
