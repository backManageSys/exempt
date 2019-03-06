<template>
        <div class="app-container">
          <el-form ref="form" :model="form" label-width="15%" :rules="addRules">
                      <el-form-item label="添加一级通道">
                                   <el-input v-model="form.platformName" style="width: 30%;"  placeholder="一级通道名"></el-input>
                                 </el-form-item>
                              <div style="text-align: center;margin-bottom:20px">
                                        <el-button type="primary" @click="addStair">添加</el-button>
                                        </div>

<el-form-item label="添加二级通道">
<el-select v-model="value" placeholder="请选择" @change = "firstChange" @visible-change="getSelect">
                                                        <el-option
                                                          v-for="item in options"
                                                          :key="item.id"
                                                          :label="item.codeCategory"
                                                          :value="item.codeCategory">
                                                        </el-option>
                                                      </el-select>

                                   <el-input v-model="form.secondPlatformName" style="width: 30%;"  placeholder="二级通道名" :disabled = "secondState"></el-input>
                                 </el-form-item>
                                 <el-form-item label="状态" >
                                                     <el-select v-model="status" placeholder="启用">
                                                     <el-option label="启用" value="启用"></el-option>
                                                     <el-option label="停用" value="停用"></el-option>
                                                     </el-select>
                                             </el-form-item>
               <div style="text-align: center">
          <el-button type="primary" @click="addSecond">添加</el-button>
          </div>
        </el-form-item>
      </el-form>
        </div>
      </template>

<script>
    import {getSelect,addPlatform,addPayType} from '@/api/role'
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
                    status:"停用",
                    platformName:"",
                    secondPlatformName:""
                },
                  addRules: {
                    username: [{ required: true, trigger: 'blur', validator: validateUsername }],
                    password: [{ required: true, trigger: 'blur', validator: validatePass }]
                    // post: [{ required: true, trigger: 'blur', validator: validateEmpty }]
                  } ,
                  secondState:true,
                   options: [{
                                            value: '微信',
                                            label: '微信'
                                          }, {
                                            value: '支付宝',
                                            label: '支付宝'
                                          }],
                                           value: '',
                                           status:"停用"
              }

        },

      components: {
        Form
      },
      methods:{
      firstChange(){
                            this.secondState = false;
                            },
       addStair(){

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
                   addSecond(){
                      addPayType(this.value,this.form.secondPlatformName,this.status).then(response => {
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
                      getSelect(){
                            getSelect().then(response => {
                                                       if (response.code != 200) {
                                                         this.$message({
                                                           message: response.data.description,
                                                           type: "warning"
                                                         });
                                                       } else {
                                                       this.options =  response.data;
                                                       }
                                                     });
                      }
      }
    }
    
</script>

<style scoped>

</style>
