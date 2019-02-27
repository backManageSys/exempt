<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="150px" :rules="addRules">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" style="width: 30%;" type="text" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" style="width: 30%;" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item label="等级">
        <el-select v-model="form.level" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="form.status" placeholder="启用">
          <el-option label="启用" value="启用"></el-option>
          <el-option label="停用" value="停用"></el-option>
        </el-select>
      </el-form-item>


      <el-form-item label="点位设置:" v-for="(optionData, index) in selectData">
        <el-select v-model="optionData.value" placeholder="请选择" @change="firstChange(index)"
                   @visible-change="getSelect(index)">
          <el-option
            v-for="item in optionData.options1"
            :key="item.id"
            :label="item.codeCategory"
            :value="item.codeCategory">
          </el-option>
        </el-select>
        <el-select v-model="optionData.value1" placeholder="请选择" :disabled="selectData[index].secondState"
                   @change="secondChange(index)"
                   @visible-change="getPayType(index)">
          <el-option
            v-for="item in optionData.options2"
            :key="item.id"
            :label="item.codeType"
            :value="item.id"
            :disabled="item.disabled">
          </el-option>
        </el-select>
        <el-input v-model="selectData[index].point" style="width: 15%;" type="number" placeholder="请输入点位"
                  :disabled="selectData[index].thirdState"></el-input>
        %
        <el-select v-model="selectData[index].status" placeholder="启用" style="width: 12.5%"
                   :disabled="selectData[index].selectState">
          <el-option label="启用" value="启用"></el-option>
          <el-option label="停用" value="停用"></el-option>
        </el-select>
        <i class="el-icon-circle-plus" style="font-size: 22px;line-height: 2;color:#66b1ff;cursor: pointer;"
           @click="addSelect()"></i>
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="onSubmit('form')">添加</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import {addMerchant, getSelect, getPayType} from "@/api/role";
  import Form from "../../../../components/form/index";
  import store from "../../../../store";
  import {isvalidUsername, isvalidPassword} from '@/utils/validate';

  export default {
    name: "index",
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
        disableData: [],
        selectData: [
          {
            point: 0,
            options1: [],
            options2: [],
            value: '',
            secondState: true,
            thirdState: true,
            value1: '',
            status: "停用",
            selectState: true,
          }
        ],
        form: {
          alipay: "",
          applyId: "",
          level: "",
          password: "",
          username: "",
          wechat: "",
          status: "启用"
        },
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
        isAdmin_lable: ""
      };
    },

    components: {
      Form
    },
    created() {
      this.form.applyId = store.getters.uid;
      if (store.getters.role === 1) {
        this.isAdmin_lable = '启用';
      }
      if (store.getters.role === 2)
        this.isAdmin_lable = '申请启用';
    },
    methods: {
      isAdmin_value() {
        if (store.getters.role === 1)
          return '启用';
        if (store.getters.role === 2)
          return '申请启用';
      },
      getPayType(index) {
        this.disableData = [];
        this.selectData.map((item) => {
          this.disableData.push(item.value1);
        });
        getPayType(this.selectData[index].value).then(response => {
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            response.data.map((item) => {
              item.disabled = false;
              this.disableData.map((item1) => {
                if (item.id === item1) {
                  item.disabled = true;
                }
              });

            });
            this.selectData[index].options2 = response.data;
          }
        });
      },
      getSelect(index) {
        getSelect().then(response => {
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.selectData[index].options1 = response.data;
          }
        });
      },
      onCancel() {
        console.log(this.value, this.value1, this.point);
      },
      firstChange(index) {
        this.selectData[index].secondState = false;
        this.selectData[index].options2 = [];
        this.selectData[index].point = 0;
        this.selectData[index].value1 = "";
        this.selectData[index].thirdState = true;
        this.selectData[index].selectState = true;
        this.selectData[index].status = "停用";
      },
      secondChange(index) {
        this.selectData[index].thirdState = false;
        this.selectData[index].selectState = false;
      },
      addSelect() {
        const obj = {
          point: 0,
          options1: [],
          options2: [],
          value: '',
          secondState: true,
          thirdState: true,
          selectState: true,
          value1: '',
          status: "停用"
        };
        this.selectData.push(obj);
      },
      onSubmit(formName) {
        const list = [];
        this.selectData.map((item) => {
          const obj = {
            payType_id: item.value1,
            rate: item.point,
            status: item.status
          };
          list.push(obj);
        });
        this.$refs[formName].validate(valid => {
          if (valid) {
            addMerchant(
              this.form.applyId,
              this.form.level,
              this.form.password,
              this.form.username,
              this.form.status,
              list
            ).then(response => {
              if (response.code !== 200) {
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
              // const data = response.data
              // setToken(data.token)
              // commit('SET_TOKEN', data.token)
              resolve();
            })
              .catch(error => {
                // reject(error)
                this.$message(error);
              });
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      }
    }
  };
</script>

<style scoped>
</style>
