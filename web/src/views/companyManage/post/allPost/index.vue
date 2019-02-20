<template>
        <div class="app-container">
        <div>所有岗位</div>
            <el-table
            :data="teams"
            max-height="700"

            >
            <el-table-column type="expand">
                <template slot-scope="props">
                    <el-form label-position="left" label="权限" inline class="demo-table-expand">
                        <el-form-item  v-for="(item, index) in props.row.permission" :key="index">
                            <span>{{ item }}</span>
                        </el-form-item>
                    </el-form>
                </template>
            </el-table-column>
            <el-table-column prop="post" label="职位" width="180"></el-table-column>
            <el-table-column label="操作" width="180">
                    <template scope="scope">
                        <el-button size="small"
                                @click="del(scope.$index,scope.row)">删除</el-button>
                        <!-- <el-button size="small"
                                @click="edit(scope.$index,scope.row)">修改</el-button> -->
                    </template>
            </el-table-column>
            <!-- <el-table-column prop="permission" label="permission" width="180"></el-table-column> -->
        </el-table>
        <div class="block" v-if="teams.length>10">
            <span class="demonstration">调整每页显示条数</span>
            <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-sizes="[100, 200, 300, 400]"
            :page-size="100"
            layout="sizes, prev, pager, next"
            :total=total>
            </el-pagination>
        </div>
      </div>
    </template>
    <script>
    import { checkAllPermission,deletePost,postGet } from '@/api/company'
        export default {
            data() {
                return {
                    labelPosition: 'right',
                    teams:[{
                        // 'id': 0,
                        // 'post': 'post',
                         'permission':'permission'
                        }
                    ],
                    currentPage:1
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
                del(index, row) {
                    console.log(row);
                    deletePost(row.id).then(response=> {
                        if(response.data.infoCode){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                          this.$message({
                           message: '删除成功',
                          type: 'success'
                          });
                        }
                       });
                       this.teams.splice(index,1)

                },
                handleSizeChange(val) {
                    console.log(`每页 ${val} 条`);
                },
                handleCurrentChange(val) {
                    console.log(`当前页: ${val}`);
                },
                getData(){
                    this.getTeams();
                },
                getTeams(){
                    /*checkAllPermission().then(response=>{
                        console.log(response,'response')
                         if(response.data.infoCod){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                            this.teams = response.data;
                        }
                    })*/
                    postGet().then(response=>{
                        console.log(response,'response')
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
                handleChange(val) {
                    console.log(val);
                      if(val==2)
                    {
                        this.getTeams();
                    }
                }
            }
        }
    </script>