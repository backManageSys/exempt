<template>
        <div class="app-container">

          
            <el-form :model="formItem" ref="formItem" label-width="80px">
                <el-form-item  label="查询职位">
                    <el-select  style="width: 200px" v-model="formItem.sendValue">
                        <el-option
                        v-for="item in posts"
                        :key="item.id"
                        :label="item.post"
                        :value="item.post">
                        </el-option>
                    </el-select >
                    <el-button type="primary" @click="onSubmit()">查询</el-button>
                </el-form-item>
            </el-form>

            
            <el-table
            :data="teams"
            height="250"
            border
            style="width: 100%">
             <el-table-column
                prop="post"
                label="职位"
                width="180">
                </el-table-column>
                <el-table-column
                prop="permission"
                label="权限"
                    >
                </el-table-column>
            </el-table>

        </div>
</template>
    
    <script>
    import { checkSinglePermission,postGet } from '@/api/company'
    import Form from "../../../../components/form/index";

        export default {
            data() {
                return {
                    formItem:{
                        sendValue:'',
                        statelist:[
                            {
                                value:'0',
                                label:'admin'
                            },
                            {
                                value:'1',
                                label:'merchant'
                            },
                            {
                                value:'2',
                                label:'test'
                            }
                        ]
                    },
                    form: {
                        post:'admin'
                    },
                    activeNames: ['1'],
                    labelPosition: 'right',
                    teams: [{
                        // post:'post',
                        // permission:'permission'
                    }],
                    currentPage:1,
                    posts:[{}]
                }
            },
            components: {
                Form
            },
            created(){
                this.getData();
            },
            methods: {
                handleSizeChange(val) {
                  
                },
                handleCurrentChange(val) {
                },
                getData(){
                    // this.getTeams();
                    this.getPost();
                },
                getTeams(){
                    checkSinglePermission(this.form.post).then(response=>{
                         if(response.data.infoCod){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                           this.teams = response.data;
                        }
                    })
                },
                getPost(){
                 postGet().then(response=>{
                         if(response.code!=200){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                            this.posts = response.data;
                        }
                    })
            },
                handleChange(val) {
                      if(val==2)
                    {
                        this.getTeams();
                    }
                },
                onSubmit() {
                    //this.$refs[formName].validate((valid) => {
                        var valid =1
                        if (valid) {
                        // alert('submit!');
                        checkSinglePermission(this.formItem.sendValue).then(response => {
                            if(response.code!=200){
                                this.$message({
                                    message: response.data.description,
                                    type: 'warning'
                                });
                            }else{
                                this.$message({
                                    message: '查询成功',
                                    type: 'success'
                                });
                                // this.teams = {}
                                // response.data.forEach(el => {
                                //     el.permission = el.permission.join(',')
                                // });
                                var a = {}
                                a.permission = response.data.permission.join(',');
                                a.post = response.data.post;
                                this.teams = [a]
                            }
                            resolve()
                        }).catch(error => {
                            this.$message(error);
                        })
                        } else {
                        return false;
                        }
                    //});
                }
            }
        }
    </script>
    
    <style scoped>
    
    </style>
