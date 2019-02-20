<template>
    <div class="app-container">
    <div>团队管理</div>
        <el-table
        :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        height="500"
        ref="table"
        border
       >
        <el-table-column prop="id" label="序号" align="center"></el-table-column>
        <el-table-column prop="teamName" label="团队名" align="center"></el-table-column>
        <el-table-column prop="area" label="区域" align="center"></el-table-column>
        <!-- <el-table-column prop="operator" label="操作者" ></el-table-column> -->
        <el-table-column prop="status" label="状态" align="center"></el-table-column>
        <el-table-column prop="supervisor" label="主管id" align="center"></el-table-column>
        <el-table-column prop="addTime" label="创建时间" min-width="150%" align="center"></el-table-column>
        <!-- <el-table-column prop="verifyCode" label="verifyCode" width="180"></el-table-column> -->
        <el-table-column label="操作" fixed="right" width="280" align="center">
            <template scope="scope">
                <el-button size="small"
                        @click="openDialog(scope.$index,scope.row)">修改</el-button>
                        <el-button size="small"
                        @click="operationDel(scope.$index,scope.row)">删除</el-button>
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

    <el-dialog title="修改团队信息" :visible.sync="dialogFormVisible">
        <el-form :model="newRow" label-width="10%">
            <el-form-item label="团队名">
                <el-input v-model="newRow.teamName" placeholder="团队名" style="width: 80%;"></el-input>
            </el-form-item>
            <el-form-item label="区域">
                <el-input v-model="newRow.area" placeholder="区域" style="width: 80%;"></el-input>
            </el-form-item>
            <!-- <el-form-item label="new_operator">
                <el-input v-model="newRow.operator" placeholder="操作者"></el-input>
            </el-form-item> -->
             <!-- <p class="err-msg" v-html="err"></p> -->
            <el-form-item label="状态">
                <el-select v-model="newRow.status" placeholder="启用">
                <el-option label="启用" value="启用"></el-option>
                <el-option label="停用" value="停用"></el-option>
                </el-select>
            </el-form-item>
            
            <el-form-item label="验证码">
                <el-input v-model="newRow.verifyCode" placeholder="验证码" style="width: 80%;"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="updateTeam">确 定</el-button>
        </div>
    </el-dialog>

  </div>
  
</template>

<script>
import { teamAdd,teamsGet,teamDelete,teamVerifyCodeCheck,teamUpdate, } from '@/api/company'
import { getTime,getTimeFormat } from "@/utils/index";
    export default {
        data() {
            return {
                tableHeight: 290,
                activeNames: ['1'],
                labelPosition: 'right',
                teamAddParameters: {
                        "area": "area",
                        "operator": "operator",
                        "status": "status",
                        "supervisor": "supervisor",
                        "teamName": "teamName",
                        "verifyCode": "verifyCode"
                },
                teams:[{
                    // 'teamName':'teamName',
                    // 'addTime':'addTime',
                    // 'area':'area',
                    // 'id':'id',
                    // 'operator':'operator',
                    // 'status':'status',
                    // 'supervisor':'supervisor',
                    // 'verifyCode':'verifyCode'
                    }
                ],
                currentPage:1,
                pagesize:10,
                dialogFormVisible: false,
                newRowIndex:1,
                newRow: {
                    area: '',
                    operator: '',
                    status: '',
                    supervisor: '',
                    teamName: '',
                    verifyCode: ''
                    },
                formLabelWidth: '120px'
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
            operationDel(index, row){
                var verifyCode = '';
                this.$prompt('请输入团队验证码', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    
                }).then(({ value }) => {
                    verifyCode = value;
                    this.operation(index, row,verifyCode);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });       
                })
            },
            operation(index,row,verifyCode){
                teamDelete(row.id,verifyCode).then(response=>{
                    console.log(response,'sdll')
                     if(response.code!=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                        this.teams.splice(index,1)

                    }
                })
            },
            openDialog(index,row){
                var   verifyCode   = '';
                var flag = false;
                this.$prompt('请输入团队验证码', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                    }).then(({ value }) => {
                        verifyCode = value;
                        this.checkVerify(index,row,verifyCode)
                    }).catch(() => {
                    /*this.$message({
                        type: 'info',
                        message: '操作成功'
                    });*/
                });
            },
            checkVerify(index, row,verifyCode) {
                console.log(row);
                var flag = false;
                teamVerifyCodeCheck(row.id,verifyCode).then(response=> {
                    if(response.code!=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                        flag=true;
                        this.newRow = JSON.parse(JSON.stringify(row));;
                        this.newRowIndex = index;
                        this.dialogFormVisible = true;
                    }
                });
                return flag;
            },
            updateTeam(){
                teamUpdate(this.newRow.area,this.newRow.operator,this.newRow.status,this.newRow.supervisor,this.newRow.teamName,this.newRow.verifyCode,this.newRow.id).then(response=> {
                    if(response.code!=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                        this.teams[this.newRowIndex] = this.newRow;
                        this.dialogFormVisible = false;
                         this.$message({
                            message: '修改成功',
                            type: 'success'
                        });
                    }
                });
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.pagesize = val;
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
                     if(response.code != 200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       this.teams = response.data;
                       this.teams.forEach(el => {
                             el.addTime=getTime(el.addTime);
                       });
                    }
                });
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
