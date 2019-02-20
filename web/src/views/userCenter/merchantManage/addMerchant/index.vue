<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="150px" :rules="addRules">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" style="width: 30%;" type="text"  placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" style="width: 30%;"  placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item label="等级">
        <!-- <el-input v-model="form.level" type="number" min="1"  style="width: 30%;"></el-input> -->
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
            <el-select v-model="form.status" placeholder="">
              <el-option v-model="isAdmin_lable" :value="isAdmin_value" ></el-option>
              <!-- <el-option label="停用" value="停用"></el-option> -->
            </el-select>
        </el-form-item>

      <el-form-item label="微信点位">
        <el-input v-model="form.wechat" style="width: 15%;"  placeholder="微信点位"></el-input>%
      </el-form-item>
      <el-form-item label="转账通码点位">
        <el-input v-model="form.TPASS" style="width: 15%;"  placeholder="转账通码点位"></el-input>%
      </el-form-item>
      <el-form-item label="转账固码点位">
        <el-input v-model="form.TSOLID" style="width: 15%;"  placeholder="转账固码点位"></el-input>%
      </el-form-item>
      <el-form-item label="收款通码离线码点位">
        <el-input v-model="form.RPASSOFF" style="width: 15%;"  placeholder="收款通码离线码点位"></el-input>%
      </el-form-item>
      <el-form-item label="收款通码在线码点位">
        <el-input v-model="form.RPASSQR" style="width: 15%;"  placeholder="收款通码在线码点位"></el-input>%
      </el-form-item>
      <el-form-item label="收款固码(二开)点位">
        <el-input v-model="form.RSOLID" style="width: 15%;"  placeholder="收款固码(二开)点位"></el-input>%
      </el-form-item>
      <!-- <el-form-item label="申请人id">
              <el-input v-model="form.applyId" style="width: 30%;"></el-input>
      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" @click="onSubmit('form')">添加</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { addMerchant } from "@/api/role";
import Form from "../../../../components/form/index";
import store from "../../../../store";
import { isvalidUsername,isvalidPassword } from '@/utils/validate';
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
        applyId: "",
        level: "",
        password: "",
        username: "",
        wechat: "",
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
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePass }]
        // post: [{ required: true, trigger: 'blur', validator: validateEmpty }]
      },
      isAdmin_lable :""
    };
  },

  components: {
    Form
  },
  created() {
    this.form.applyId = store.getters.uid;
    console.log("11111111111111");
    console.log(store.getters.role);
    console.log(store.getters.uid);
    if(store.getters.role == 1){
        console.log("2222")
        this.isAdmin_lable = '启用';
    }
    if(store.getters.role == 2)
        this.isAdmin_lable = '申请启用';
  },
  methods: {
    isAdmin_value(){
      if(store.getters.role == 1)
        return '启用';
      if(store.getters.role == 2)
        return '申请启用';
    },
    // isAdmin_lable(){
    //   if(store.getters.role == 1)
    //     return '启用';
    //   if(store.getters.role == 2)
    //     return '申请启用';
    // },
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (
            this.form.applyId.length == 0 ||
            this.form.level.length == 0 ||
            this.form.password.length == 0 ||
            this.form.username == 0 ||
            this.form.wechat.length == 0||
            this.form.TPASS.length == 0||
            this.form.TSOLID.length == 0||
            this.form.RPASSOFF.length == 0||
            this.form.RPASSQR.length == 0||
            this.form.RSOLID.length == 0
          ) {
            alert("请输入完整信息");
            return;
          }

          // alert('submit!');
          addMerchant(
            this.form.applyId,
            this.form.level,
            this.form.password,
            this.form.username,
            this.form.status,
            this.form.wechat,
            this.form.TPASS,
            this.form.TSOLID,
            this.form.RPASSOFF,
            this.form.RPASSQR,
            this.form.RSOLID
          )
            .then(response => {
              // console.log(response.data.infoCode)
              if (response.data.infoCode) {
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
