<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-form :label-position="labelPosition" :model="permissionaddParameters" class="demo-form-inline">
            <el-form-item label="post">
                <el-input v-model="permissionaddParameters.post" placeholder="post"></el-input>
            </el-form-item>
            <el-form-item label="permission">
                <el-input v-model="permissionaddParameters.permission" placeholder="permission"></el-input>
            </el-form-item>
        </el-form>
  </div>
</template>

<script>
import { permissionAllocate } from '@/api/company'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                permissionaddParameters: {
                        "post": "post",
                        "permission": "permission",
                },
                currentPage:1
            }
        },
        methods: {

            getData(){
                this.getTeams();
            },
            getTeams(){
                teamsGet().then(response=>{
                    console.log(response,'sdll')
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
            addteam() {
                permissionadd(
                    this.permissionaddParameters.post,
                    this.permissionaddParameters.permission,
                    ).then(response=>{
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
