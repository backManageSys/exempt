     <!-- <vue-recaptcha sitekey="6LdRB4sUAAAAAHGqNXwexwX7PpXiof_Lz0YHwQLS">
    <button>Click me</button>
  </vue-recaptcha> -->

<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <h3 class="title">{{ title }}</h3>
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input v-model="loginForm.username" name="username" type="text" auto-complete="on" placeholder="用户名" />
      </el-form-item>
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :type="pwdType"
          v-model="loginForm.password"
          name="password"
          auto-complete="on"
          placeholder="密码"
          @keyup.enter.native="handleLogin" />
        <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye" />
        </span>
      </el-form-item>
      <el-form-item>
        <el-button :loading="loading" type="primary" style="width:100%;" @click.native.prevent="handleLogin">
          登陆
        </el-button>
      </el-form-item>
      <div class="tips">
        <span style="margin-right:20px;">请输入账号密码</span>
      </div>
    </el-form>
  </div>
</template>
        <!-- <vue-recaptcha
          ref="recaptcha"
          @verify="onVerify"
          @expired="onExpired"
          :sitekey="sitekey">
        </vue-recaptcha> -->
        <!-- <button @click="resetRecaptcha"> Reset ReCAPTCHA </button> -->
<script>
  grecaptcha.ready(function() {
      grecaptcha.execute('6LdRB4sUAAAAAHGqNXwexwX7PpXiof_Lz0YHwQLS', {action: 'homepage'});
  });
  </script>
<script>
import { isvalidUsername,isvalidPassword } from '@/utils/validate'
import { login } from '@/api/login'
import store from '../../store'
import {titleList, titleUpdate} from '@/api/company'
import {codeVerify,codeVerify1} from '@/api/googleverify'
// import {remotejs} from '@/components/remotejs'
 import VueRecaptcha from 'vue-recaptcha';
//  import {*} from '@/api/vue-recaptcha'
export default {
  name: 'Login',
  components: { VueRecaptcha },
  data() {
    const validateUsername = (rule, value, callback) => {
      console.log(value)
      if (!isvalidUsername(value)) {
        callback(new Error('请输入正确的用户名'))
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
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePass }]
      },
      loading: false,
      pwdType: 'password',
      redirect: undefined,
      title:"管理系统",
      sitekey:"6LdRB4sUAAAAAHGqNXwexwX7PpXiof_Lz0YHwQLS",
      secretkey:'6LdRB4sUAAAAAOzE_J2uhZ4LDhbxkIUFqfx1N1gB'
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created(){
    titleList().then(res => {
      if(res.code!=200){
        this.title = '管理系统'
      }else{
        this.title = res.data[0].title;
      }
    })

  },
  methods: {
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    handleLogin() {
    //  console.log("dfdfsffd1")
      this.$refs.loginForm.validate(valid => {//如果表单为false，不会进入validate函数内部，而是直接返回。
        this.$router.push({ path: this.redirect || '/' })
        console.log("23123123")
        console.log(valid)
        if (valid) {
         // console.log("dfdfsffd4")
          this.loading = true ;
         // console.log("dfdfsffd5")
          this.$store.dispatch('Login', this.loginForm).then(() =>{
         //   console.log("dfdfsffd6")
            this.loading = false
          //  console.log("dfdfsffd7")
          //  if(response.code == 200){
         //   console.log("dfdfsffd8")
            this.$router.push({ path:  this.redirect ||'/' })
        //    console.log("asdASASD9")
        //    }
          }).catch(() => {
            this.loading = false
            // this.$message({
            //         message: "123",
            //         type: 'warning'
            //     });
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
     onSubmit(token) {
    alert('thanks ' + document.getElementById('field').value);
    },
     onSubmit: function () {
      this.$refs.invisibleRecaptcha.execute()
    },
    onVerify: function (response) {
      console.log('Verify: ' + response,'key',this.secretkey)

      codeVerify(response).then(res=>{
        console.log(res)
      })
      console.log('qweaxcsd')
    },
    onExpired: function () {
      console.log('Expired')
    },
    resetRecaptcha () {
      this.$refs.recaptcha.reset() // Direct call reset method
    },
 
   validate(event) {
    console.log("1212s");
    event.preventDefault();
    console.log("12121");
    if (!document.getElementById('field').value) {
      console.log("1212w");
      alert("You must add text to the required field");
    } else {
      console.log("1212d");
      grecaptcha.execute();
      console.log("document.getElementById('field').value");
    }
  },
 
   onload() {
    var element = document.getElementById('submit');
    element.onclick = validate;
  }

  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
$bg:#2d3a4b;
$light_gray:#eee;

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;
    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      &:-webkit-autofill {
        -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: #fff !important;
      }
    }
  }
  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}

</style>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;
.login-container {
  position: fixed;
  height: 100%;
  width: 100%;
  background-color: $bg;
  .login-form {
    position: absolute;
    left: 0;
    right: 0;
    width: 520px;
    max-width: 100%;
    padding: 35px 35px 15px 35px;
    margin: 120px auto;
  }
  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;
    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }
  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }
  .title {
    font-size: 26px;
    font-weight: 400;
    color: $light_gray;
    margin: 0px auto 40px auto;
    text-align: center;
    font-weight: bold;
  }
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
