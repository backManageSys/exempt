<template> 
        <div class="app-container documentation-container">
                <label class="titlestyle">{{"主页名：" + title }}</label>
                <el-form :label-position="labelPosition" class="demo-form-inline" style="margin-top:30px;">
                    <el-form-item label="修改主页名">
                        <el-input v-model="newTitle" style="width: 30%;"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateTitle">修改</el-button>
                    </el-form-item>
                </el-form>
        </div>
</template>

<script>
    import {titleList, titleUpdate} from '@/api/company'
    export default {
        data() {
            return {
                title: '',
                dialogFormVisible:false,
                newTitle:'',
                labelPosition: 'right',
            }

        },
        created(){
            this.getData();
        },
        methods: {
            getData() {
                this.getTitle();
            },
            getTitle() {
                titleList().then(response=>{
                    console.log(response,'sdll')
                     if(response.code!=200){
                        this.$message({
                            message: "获取失败",
                            type: 'warning'
                        });
                    }else{
                        if( response.data != null && response.data != '')
                            this.title = response.data[0].title;
                    }
                })
            },
            updateTitle(){
                titleUpdate(this.newTitle).then(response=>{
                    if(response.code!=200){
                        this.$message({
                            message: "修改失败",
                            type: 'warning'
                        });
                    }else{
                        
                        this.getTitle();
                    //    this.title = this.newTitle;
                       this.$message({
                            message: '修改成功',
                            type: 'success'
                        });
                    }
                })
            }

        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .documentation-container {
      margin: 50px;
      .document-btn {
        float: left;
        margin-left: 50px;
        display: block;
        cursor: pointer;
        background: black;
        color: white;
        height:60px;
        width: 100px;
        line-height: 60px;
        font-size: 20px;
        text-align: center;
      }
    };
    .titlestyle {
        margin-left: 100px;
        line-height: 60px;
        font-size: 20px;
    }
    </style>
