<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-form :label-position="labelPosition" :model="cardAddParameters" class="demo-form-inline" label-width="5%">
            <el-form-item label="姓名">
                <el-input v-model="cardAddParameters.name" placeholder="姓名" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="银行">
                <el-input v-model="cardAddParameters.bank" placeholder="银行" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="卡号">
                <el-input type="number" v-model="cardAddParameters.number" placeholder="卡号" style="width: 30%;"></el-input>
            </el-form-item>   
            <el-form-item label="余额">
                <el-input type="number" v-model="cardAddParameters.balance" placeholder="余额" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="归属">
                 <el-dropdown size="medium" split-button type="primary" @command="handleCommandTeam">
                    {{ cardAddParameters.teamName }}
                    <el-dropdown-menu slot="dropdown">
                    <div v-for="item in teams" :key="item.id">
                        <el-dropdown-item :command='{id:item.id,teamName:item.teamName}' >{{item.teamName}}</el-dropdown-item>
                    </div>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-form-item>
            <el-form-item label="关联">
                <el-input v-model="cardAddParameters.relation" placeholder="关联" style="width: 30%;"></el-input>
            </el-form-item>
             <el-form-item label="状态">
                    <el-select v-model="cardAddParameters.status" placeholder="启用">
                    <el-option label="启用" value="启用"></el-option>
                    <el-option label="停用" value="停用"></el-option>
                    </el-select>
                </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="addcard">添加</el-button>
            </el-form-item>
        </el-form>
  </div>
</template>

<script>
import { cardAdd } from '@/api/company'
import {teamsGet} from '@/api/company'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                cardAddParameters: {
                        "attribution": "",
                        "balance": "",
                        "bank": "",
                        "name": "",
                        "number": "",
                        "relation": "",
                        "status": "",
                        teamName:"选择团队"
                },
                cards:{},
                currentPage:1,
                teams:[{}]
            }
        },
        created(){
        this.getTeams();
      },
        methods: {
             handleCommandTeam(command) {
              // this.$message('click on item ' + command.id);
             
              this.cardAddParameters.teamName = command.teamName;
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
                       this.teams = response.data;
                    }
                })
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            },
            getData(){
                this.getcards();
            },
            addcard() {
                cardAdd(
                this.cardAddParameters.teamName,
                this.cardAddParameters.balance,
                this.cardAddParameters.bank,
                this.cardAddParameters.name,
                this.cardAddParameters.number,
                this.cardAddParameters.relation,
                this.cardAddParameters.status
                ).then(response=>{
                    if(response.code!=200){
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
                    this.getcards();
                }
            }
        }
    }
</script>

<style scoped>

</style>
