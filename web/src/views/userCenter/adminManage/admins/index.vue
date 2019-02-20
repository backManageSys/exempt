<template>
        <div class="app-container">
        <div>所有管理员</div>
            <el-table
            :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
            height="450"
            border
            style="width: 100%">
            <el-table-column prop="user.username" label="用户名"  align="center"></el-table-column>
            <!-- <el-table-column prop="staffName" label="职工名"  align="center"></el-table-column> -->
            <el-table-column prop="team" label="团队"  align="center"></el-table-column>
            <el-table-column prop="post" label="职务"  align="center"></el-table-column>
            <el-table-column prop="operator" label="操作上级id"  align="center"></el-table-column>
            <el-table-column prop="addTimep" label="添加时间"  align="center" min-width="100%"></el-table-column>
            <el-table-column prop="status" label="状态"  align="center"></el-table-column>
            <el-table-column label="操作" fixed="right" align="center" >
            <template scope="scope" >
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
            :total=total>
            </el-pagination>
        </div>
        <el-dialog title="修改管理员信息" :visible.sync="dialogFormVisible" >
            <el-form ref="form" :model="newRow.user" :rules="addRules" label-width="13%">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="newRow.user.username" placeholder="请输入用户名" style="width:90%;"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="newRow.user.password" type="password" placeholder="请输入密码" style="width:90%;"></el-input>
                </el-form-item>
                <el-form-item label="团队名称">
                    <el-dropdown size="medium" split-button type="primary" @command="handleCommandTeam">
                    {{ newRow.team }}
                    <el-dropdown-menu slot="dropdown">
                    <div v-for="item in teams1" :key="item.id">
                        <el-dropdown-item :command='{id:item.id,teamName:item.teamName}' >{{item.teamName}}</el-dropdown-item>
                    </div>
                    </el-dropdown-menu>
                </el-dropdown>
                </el-form-item>
                <el-form-item label="岗位">
                    <el-dropdown size="medium" split-button type="primary" @command="handleCommandPost" >
                    {{ newRow.post }}
                    <el-dropdown-menu slot="dropdown">
                    <div v-for="item in posts" :key="item.id">
                        <el-dropdown-item :command='{id:item.id,post:item.post}' >{{item.post}}</el-dropdown-item>
                    </div>
                    </el-dropdown-menu>
                </el-dropdown>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="newRow.status" placeholder="请输入状态" style="width:20%;">
                    <el-option label="启用" value="启用"></el-option>
                    <el-option label="停用" value="停用"></el-option>
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
    import { adminsGet,updateStaff } from '@/api/role'
    import { isvalidUsername,isvalidPassword } from '@/utils/validate'  
    import {teamsGet,postGet} from '@/api/company'
    import {getTime} from '@/utils/index'
    import store from '../../../../store'
        export default {
                data() {
                    const validateUsername = (rule, value, callback) => {
                    console.log(rule)
                    console.log(value)
                    console.log(callback)
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
                    teams:[{
                        // 'userInfo':{},
                        // 'staffName': 'staffName',
                        // 'team':'team',
                        // 'post': 'post',
                        // 'status': 'status'
                        }
                    ],
                    posts:[{
                      'id': 0,
                      'post': 'post'
                      //'permission':'permission'
                      }
                    ],
                    teams1:[{
                    // 'teamName':'teamName',
                    // 'addTime':'addTime',
                    // 'area':'area',
                    // 'id':'id',
                    // 'operator':'operator',
                    // 'status':'status',
                    // 'supervisor':'supervisor',
                    // 'verifyCode':'verifyCode'
                    }],
                    currentPage:1,
                    pagesize:10,
                    dialogFormVisible: false,
                    newRow: {
                        user: {
                            username:"",
                            password:""
                        },
                        status:"启用",
                    },
                     addRules: {
                  username: [{ required: true, trigger: 'blur', validator: validateUsername }],
                  password: [{ required: true, trigger: 'blur', validator: validatePass }]
                  // post: [{ required: true, trigger: 'blur', validator: validateEmpty }]
                }
                }
            },
            computed:{
                total(){
                    return this.teams.length;
                }
            },
            created(){
                this.getData();
            },
            methods: {
                    openDialog(index,row) {
                        console.log("121")
                    this.dialogFormVisible=true;
                    //this.newRow = JSON.parse(JSON.stringify(row));
                    console.log("122")
                    this.newRow = row;
                     console.log(row)
                    this.newRow.user = row.user;
                    console.log( row.user)
                    },
                    updateSupplier(formName) {
                    this.$refs[formName].validate((valid) => {
                    if (valid) {
                        updateStaff(this.newRow.user.id,
                        this.newRow.user.username,
                        this.newRow.user.password,
                        this.newRow.status,
                        this.newRow.team,
                        this.newRow.post).then(response=> {
                            if(response.code!=200){
                                this.$message({
                                    message: response.data.response.description,
                                    type: 'warning'
                                });
                            }else{
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
                handleCommandTeam(command) {
                    // this.$message('click on item ' + command.id);
                    this.newRow.team = command.teamName;
                    // console.log('click on item ' + command.teamName);
                },
                handleCommandPost(command) {
                    // this.$message('click on item ' + command.id);
                    this.newRow.post = command.post;
                    // this.form.teamName = command.teamName;
                    // console.log('click on item ' + command.teamName);
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
                        this.teams1 = response.data;
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
                handleSizeChange(val) {
                    console.log(`每页 ${val} 条`);
                    this.pagesize=val;
                },
                handleCurrentChange(val) {
                    console.log(`当前页: ${val}`);
                    this.currentPage=val;
                },
                getData(){
                    this.getTeams();
                    this.getPosts();
                    this.getStaff();
                },
                getStaff(){
                    adminsGet().then(response=>{
                        console.log(response,'sdll')
                         if(response.data.infoCod){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                           this.teams = response.data;
                            this.teams.forEach(el => {
                                el.addTimep = getTime(el.addTime)
                            });
                        }
                    })
                },
                
            }
        }
    </script>
    
    <style scoped>
    
    </style>
    