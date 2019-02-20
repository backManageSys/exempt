<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-form :label-position="labelPosition" :model="teamAddParameters" class="demo-form-inline" label-width="100px">
            <el-form-item label="区域">
                <el-input v-model="teamAddParameters.area" placeholder="区域" style="width: 30%;"></el-input>
            </el-form-item>
             <el-form-item label="状态">
                    <el-select v-model="teamAddParameters.status" placeholder="启用">
                    <el-option label="启用" value="启用"></el-option>
                    <el-option label="停用" value="停用"></el-option>
                    </el-select>
                </el-form-item>
            <el-form-item label="团队名">
                <el-input v-model="teamAddParameters.teamName" placeholder="团队名" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="验证码">
                <el-input v-model="teamAddParameters.verifyCode" placeholder="验证码" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="addteam">添加</el-button>
            </el-form-item>
        </el-form>
  </div>
</template>

<script>
import { teamAdd,teamsGet } from '@/api/team'
import store from '../../../../store'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                teamAddParameters: {
                        "area": "",
                        "operator": "",
                        "status": "",
                        "supervisor": "",
                        "teamName": "",
                        "verifyCode": ""
                },
                teams:{},
                currentPage:1
            }
        },
        created(){
            this.teamAddParameters.supervisor = store.getters.uid;
            this.teamAddParameters.operator = store.getters.uid;
        },
        methods: {
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
                teamAdd(this.teamAddParameters.area,this.teamAddParameters.operator,this.teamAddParameters.status,this.teamAddParameters.supervisor,this.teamAddParameters.teamName,this.teamAddParameters.verifyCode).then(response=>{
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
