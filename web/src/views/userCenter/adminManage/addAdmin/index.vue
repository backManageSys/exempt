<template>
        <div class="app-container">
          <el-form ref="form" :model="form" label-width="80px" :rules="addRules">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" style="width: 30%;" placeholder="用户名" ></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" style="width: 30%;" placeholder="密码" ></el-input>
            </el-form-item>
            <el-form-item label="岗位" prop="post">
                <el-dropdown size="medium" split-button type="primary" @command="handleCommandPost" >
                    {{ form.post }}
                    <el-dropdown-menu slot="dropdown">
                    <div v-for="item in posts" :key="item.id">
                        <el-dropdown-item :command='{id:item.id,post:item.post}' >{{item.post}}</el-dropdown-item>
                    </div>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-form-item>
             <el-form-item label="状态">
                    <el-select v-model="form.status" placeholder="启用">
                    <el-option label="启用" value="启用"></el-option>
                    <el-option label="停用" value="停用"></el-option>
                    </el-select>
              </el-form-item>
            <el-form-item label="团队">
                <el-dropdown size="medium" split-button type="primary" @command="handleCommandTeam">
                    {{ form.teamName }}
                    <el-dropdown-menu slot="dropdown">
                    <div v-for="item in teams" :key="item.id">
                        <el-dropdown-item :command='{id:item.id,teamName:item.teamName}' >{{item.teamName}}</el-dropdown-item>
                    </div>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-form-item>
          <el-form-item>
          <el-button type="primary" @click="onSubmit('form')">添加</el-button>
        </el-form-item>
      </el-form>
        </div>
      </template>

<script>
    import { isvalidUsername,isvalidPassword } from '@/utils/validate'  
    import {addAdmin} from '@/api/role'
    import Form from "../../../../components/form/index";
    import {teamsGet,postGet} from '@/api/company'
    import store from '../../../../store'
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
        // const validateEmpty = (rule, value, callback) => {
        //   if (value == null || value == '') {
        //     callback(new Error('不能为空'))
        //   } else {
        //     callback()
        //   }
        // }
        return {
                  form: {
                    // code: '1',
                    // operator: '1',
                     password: '',
                     post: '选择岗位',
                    // status: '启用',
                    // team:'队伍',
                     username: '',
                     teamName:'选择团队',
                },
                posts:[{
                      'id': 0,
                      'post': 'post'
                      //'permission':'permission'
                      }
                  ],
                teams:[{
                    // 'teamName':'teamName',
                    // 'addTime':'addTime',
                    // 'area':'area',
                    // 'id':'id',
                    // 'operator':'operator',
                    // 'status':'status',
                    // 'supervisor':'supervisor',
                    // 'verifyCode':'verifyCode'
                    }],
                addRules: {
                  username: [{ required: true, trigger: 'blur', validator: validateUsername }],
                  password: [{ required: true, trigger: 'blur', validator: validatePass }]
                  // post: [{ required: true, trigger: 'blur', validator: validateEmpty }]
                }
              }

        },

      components: {
        Form
      },
      created(){
        this.getData();
        this.form.operator = store.getters.uid;
      },
      methods:{
         handleCommandTeam(command) {
              // this.$message('click on item ' + command.id);
              this.form.team = command.id;
              this.form.teamName = command.teamName;
              // console.log('click on item ' + command.teamName);
          },
         handleCommandPost(command) {
              // this.$message('click on item ' + command.id);
              this.form.post = command.post;
              // this.form.teamName = command.teamName;
              // console.log('click on item ' + command.teamName);
          },
          getData(){
              // this.getcodes();
              this.getTeams();
              this.getPosts();
          },
          getTeams(){
                teamsGet().then(response=>{
                    console.log(response,'11111111111111')
                     if(response.code !=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       this.teams = response.data;
                    }
                })
            },
            getPosts(){
               postGet().then(response=>{
                    console.log(response,'222222222222222222')
                    // console.log("11111111");
                      if(response.code !=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                       //  console.log("11111111aaaaa");
                    }else{
                      // console.log("11111111bbbbb");
                        this.posts = response.data;
                        var a=[];
                        this.posts.forEach(el =>{
                            if(el.post != '商户' && el.post != '供码用户' && el.post != '代理商')
                                //  console.log(el.);
                                //  console.log("11111111vvvv");
                                a.push(el);
                        });
                        this.posts = a;
                    }
                })
            },
        onSubmit(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              // alert('submit!');
              addAdmin(this.form.code,this.form.operator,this.form.password,this.form.post,this.form.status,this.form.team,this.form.username).then(response => {
                // console.log(response.data.infoCode)
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
              console.log('error submit!!');
              return false;
            }
          });
        },
      }
    }
    
</script>

<style scoped>

</style>
