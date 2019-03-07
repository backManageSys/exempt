<template>
        <div class="app-container">
          <el-form ref="form" :model="form" label-width="10%" :rules="addRules">
      
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" style="width: 30%;"  placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" style="width: 30%;"  placeholder="密码"></el-input>
            </el-form-item>

<el-form-item label="点位设置">
            <el-select v-model="value" placeholder="请选择" @change = "firstChange">
                                               <el-option
                                                 v-for="item in options1"
                                                 :key="item.value"
                                                 :label="item.label"
                                                 :value="item.value">
                                               </el-option>
                                             </el-select>
                                             <el-select v-model="value1" placeholder="请选择" :disabled="secondState" @change = "secondChange">
                                                                          <el-option
                                                                            v-for="item in options2"
                                                                            :key="item.value"
                                                                            :label="item.label"
                                                                            :value="item.value">
                                                                          </el-option>
                                                                        </el-select>
                                             <el-input v-model="point" style="width: 15%;" type="number" placeholder="请输入点位" :disabled="thirdState"></el-input>%
</el-form-item>

            <!-- <el-form-item label="支付宝点位">
              <el-input v-model="form.alipay" style="width: 10%;" placeholder="支付宝点位"></el-input>%
            </el-form-item>
            <el-form-item label="微信点位">
              <el-input v-model="form.wechat" style="width: 10%;" placeholder="微信点位"></el-input>%
            </el-form-item>       -->
            <el-form-item label="状态" >
                    <el-select v-model="form.status">
                    <el-option label="启用" value="启用"></el-option>
                    <el-option label="停用" value="停用"></el-option>
                    </el-select>
            </el-form-item>
            <!-- <el-form-item label="验证码">
              <el-input v-model="form.code" style="width: 30%;"></el-input>
            </el-form-item> -->
            <!-- <el-form-item label="流量">
              <el-input v-model="form.flow" style="width: 30%;"></el-input>
            </el-form-item> -->
        
      
          <el-form-item>
          <el-button type="primary" @click="onSubmit('form')">添加</el-button>
        </el-form-item>
      </el-form>
        </div>
      </template>

<script>
    import {addAgent} from '@/api/role'
    import Form from "../../../../components/form/index";
    import { isvalidUsername,isvalidPassword } from '@/utils/validate'  
    export default {
        name: "index",
        data(){
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
                    status:"停用"
                },
                  addRules: {
                    username: [{ required: true, trigger: 'blur', validator: validateUsername }],
                    password: [{ required: true, trigger: 'blur', validator: validatePass }]
                    // post: [{ required: true, trigger: 'blur', validator: validateEmpty }]
                  } ,
                   options1: [{
                                            value: '微信',
                                            label: '微信'
                                          }, {
                                            value: '支付宝',
                                            label: '支付宝'
                                          }],
                                            options2: [{
                                                        value: 'vx',
                                                        label: '微信1'
                                                      }, {
                                                        value: 'zfb',
                                                        label: '支付宝1'
                                                      }],
                                           value: '',
                                           value1: '',
                                           value2: '',
                                           secondState:true,
                                           thirdState:true,
                                            point:"",
              }

        },

      components: {
        Form
      },
      methods:{
       firstChange(){
                  this.secondState = false;
                  },
                   secondChange(){
                      this.thirdState = false;
                      },
        onSubmit(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              // alert('submit!');
              addAgent(this.form.alipay,this.form.password,this.form.status,this.form.username,this.form.wechat).then(response => {
                if(response.data.infoCode){
                   this.$message({
                    message: response.data.description,
                    type: 'warning'
                  });
                }else{
                  this.$message({
                    message: '添加成功',
                    type: 'success'
                  });

                }
                // const data = response.data
                // setToken(data.token)
                // commit('SET_TOKEN', data.token)
                resolve()
              }).catch(error => {
                // reject(error)
                 this.$message(error);
              })
            } else {
              return false;
            }
          });
        },
      }
    }
    
</script>

<style scoped>

</style>
