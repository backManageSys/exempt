<template>
        <div class="app-container">
        <div style="width: 100%">所有权限查询</div>
            <el-table
            :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
            
            border
            style="width: 100%">
            <el-table-column prop="post" label="职位" width="80"></el-table-column>
            <el-table-column prop="permission" label="权限" ></el-table-column>
        </el-table>
        <div class="block">
            <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-sizes="[10, 2, 30, 40]"
            :page-size="pagesize"
            layout="sizes, prev, pager, next"
            :total=total>
            </el-pagination>
        </div>
      </div>
    </template>
    <script>
    import { checkAllPermission } from '@/api/company'
        export default {
            data() {
                return {
                    activeNames: ['1'],
                    labelPosition: 'right',
                    teams:[{
                        'post': 'post',
                        'permission':'permission'
                        }
                    ],
                    pagesize:10,
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
                },
                getTeams(){
                    checkAllPermission().then(response=>{
                        console.log(response,'sdll')
                         if(response.data.infoCod){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                            response.data.forEach(el => {
                                el.permission = el.permission.join(',')
                            });
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
    
    <style scoped>
    
    </style>
    