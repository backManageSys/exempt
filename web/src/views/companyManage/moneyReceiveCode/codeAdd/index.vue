<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-form :label-position="labelPosition" :model="codeAddParameters" class="demo-form-inline">
            <el-form-item label="时间">
                <el-input type = "number" v-model="codeAddParameters.duration" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="信息">
                <el-input v-model="codeAddParameters.info" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="号码">
                <el-input v-model="codeAddParameters.number" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="优先权">
                <el-input v-model="codeAddParameters.priority" type="number" placeholder=""></el-input>
            </el-form-item>
            <el-form-item label="码类型">
                    <el-select v-model="codeAddParameters.type" placeholder="">
                      <el-option label="转账通码" value="TPASS"></el-option>
                      <el-option label="转账固码" value="TSOLID"></el-option>
                      <el-option label="收款通码离线码" value="RPASSOFF"></el-option>
                      <el-option label="收款通码在线码" value="RPASSQR"></el-option>
                      <el-option label="收款固码(二开)" value="RSOLID"></el-option>
                    </el-select>
                </el-form-item>
            <el-form-item>
                <el-dropdown size="medium" split-button type="primary" @command="handleCommand">
                    {{ codeAddParameters.teamName }}
                    <el-dropdown-menu slot="dropdown">
                    <div v-for="item in teams" :key="item.id">
                            <el-dropdown-item :command='{id:item.id,teamName:item.teamName}' >{{item.teamName}}</el-dropdown-item>
                    </div>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="addcode">添加</el-button>
            </el-form-item>
        </el-form>
  </div>
</template>

<script>
import { codeAdd,codesGet,teamsGet } from '@/api/company'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                codeAddParameters: {
                    "duration": 5,
                    "info": "",
                    "number": "",
                    "priority": 1,
                    "team": "",
                    "type": "TSOLID",
                    "teamName":"选择队伍"
                },
                codes:{},
                currentPage:1,
                teamName:'选择队伍',
                teams:[{
                    'teamName':'teamName',
                    'addTime':'addTime',
                    'area':'area',
                    'id':'id',
                    'operator':'operator',
                    'status':'status',
                    'supervisor':'supervisor',
                    'verifyCode':'verifyCode'
                    }
                ],
            }
        },
        created(){
            this.getData();
        },
        methods: {
            handleCommand(command) {
                // this.$message('click on item ' + command.id);
                this.codeAddParameters.team = command.id;
                this.codeAddParameters.teamName = command.teamName;
                // console.log('click on item ' + command.teamName);
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            },
            getData(){
                // this.getcodes();
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
            getcodes(){
                codesGet().then(response=>{
                    console.log(response,'sdll')
                     if(response.data.infoCod){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       this.codes = response.data;
                    }
                })
            },
            addcode() {
                codeAdd(
                    this.codeAddParameters.duration,
                    this.codeAddParameters.info,
                    this.codeAddParameters.number,
                    this.codeAddParameters.priority,
                    this.codeAddParameters.team,
                    this.codeAddParameters.type
                    ).then(response=>{
                    if(response.data.infocode){
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
                    this.getcodes();
                }
            }
        }
    }
</script>

<style scoped>

</style>
