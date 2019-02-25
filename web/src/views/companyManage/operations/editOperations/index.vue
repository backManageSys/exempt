<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="15%" :rules="addRules">
      <el-form-item label="选择一级通道">
        <el-select v-model="firstValue" placeholder="请选择" @change="firstPlatformChange" @visible-change="getSelect">
          <el-option
            v-for="item in options"
            :key="item.id"
            :label="item.codeCategory"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="修改一级通道名">
        <el-input v-model="form.firstPlatformName" style="width: 30%;" placeholder="一级通道名"
                  :disabled="firstState"></el-input>
      </el-form-item>
      <div style="text-align: center;margin-bottom:20px">
        <el-button type="primary" @click="editFirstPlatformName">修改</el-button>
      </div>

      <el-form-item label="添加二级通道">
        <el-select v-model="value" placeholder="请选择" @change="firstChange" @visible-change="getSelect">
          <el-option
            v-for="item in options"
            :key="item.id"
            :label="item.codeCategory"
            :value="item.codeCategory">
          </el-option>
        </el-select>

        <el-select v-model="form.secondPlatformName" :disabled="secondSelectState" placeholder="请选择"
                   @change="secondChange" @visible-change="getPayType">
          <el-option
            v-for="item in options1"
            :key="item.id"
            :label="item.codeType"
            :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="修改二级通道名">
        <el-input v-model="form.secondPlatform" style="width: 30%;" placeholder="二级通道名"
                  :disabled="secondState"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="status" placeholder="启用">
          <el-option label="启用" value="启用"></el-option>
          <el-option label="停用" value="停用"></el-option>
        </el-select>
      </el-form-item>
      <div style="text-align: center">
        <el-button type="primary" @click="editSecond">修改</el-button>
      </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {getSelect, addPlatform, addPayType, updatePayPlatform, getPayType, updatePayType} from '@/api/role'
  import Form from "../../../../components/form/index";
  import {isvalidUsername, isvalidPassword} from '@/utils/validate'

  export default {
    name: "index",
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
        form: {
          alipay: "",
          password: "",
          status: "",
          username: "",
          wechat: "",
          status: "停用",
          platformName: "",
          firstPlatformName: "",
          secondPlatformName: "",
          secondPlatform: ""
        },
        addRules: {
          username: [{required: true, trigger: 'blur', validator: validateUsername}],
          password: [{required: true, trigger: 'blur', validator: validatePass}]
          // post: [{ required: true, trigger: 'blur', validator: validateEmpty }]
        },
        firstState: true,
        secondState: true,
        secondSelectState: true,
        options: [{
          value: '微信',
          label: '微信'
        }, {
          value: '支付宝',
          label: '支付宝'
        }],
        options1: [],
        value: '',
        firstValue: "",
        status: "停用"
      }

    },

    components: {
      Form
    },
    methods: {
      firstPlatformChange() {
        this.firstState = false;
      },
      editSecond() {
        console.log(1111, this.form.secondPlatformName, this.status, this.form.secondPlatform);
        if (this.form.secondPlatform === "") {
          this.$message({
            message: "请填写二级通道名！",
            type: "warning"
          });
          return;
        }
        ;
        updatePayType(this.form.secondPlatformName.id, this.form.secondPlatformName.codeCategory, this.form.secondPlatform, this.status).then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            console.log(response);
            this.$message({
              message: response.data,
              type: "success"
            });
          }
        });

      },
      firstChange() {
        this.secondSelectState = false;
      },
      secondChange() {
        this.status = this.form.secondPlatformName.status;
        this.secondState = false;
      },
      getPayType() {
        getPayType(this.value).then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.options1 = response.data;
          }
        });
      },
      editFirstPlatformName() {
        if (this.form.firstPlatformName === "") {
          this.$message({
            message: "请填写一级通道名！",
            type: "warning"
          });
          return;
        }
        ;
        updatePayPlatform(this.firstValue, this.form.firstPlatformName).then(response => {
          if (response.code != 200) {
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
      addStair() {

        addPlatform(
          this.form.platformName
        ).then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.$message({
              message: "添加成功",
              type: "success"
            });
          }
        });


      },
      addSecond() {
        console.log(this.form.secondPlatformName, this.value);
        addPayType(this.value, this.form.secondPlatformName, this.status).then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.$message({
              message: "添加成功",
              type: "success"
            });
          }
        });

      },
      getSelect() {
        getSelect().then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.options = response.data;
          }
        });
      }
    }
  }

</script>

<style scoped>

</style>
