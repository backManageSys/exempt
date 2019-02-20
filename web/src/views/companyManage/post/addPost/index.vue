<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span class="span">添加岗位</span>
            </div>
            <el-form :label-position="labelPosition" :model="postaddParameters" label-width="10%">
                <el-form-item label="岗位" style="width:80%;">
                    <el-input v-model="postaddParameters.post" placeholder="岗位" style="width: 50%;" ></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="postAdd">添加</el-button>
                </el-form-item>
            </el-form>
        </el-card>
  </div>
</template>

<script>
import { addPost } from '@/api/company'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                postaddParameters: {
                      //  "post": "post",
                }
            }
        },
        methods: {
            postAdd(){
                    addPost(this.postaddParameters.post,).then(response=>{
                        console.log(response,'sdll')
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
                            // response.data.forEach(el => {
                            //     el.permission = el.permission.join(',')
                            // });
                            // this.teams = response.data;
                        }
                    })
                },
            // addpost() {
            //     postAdd(
            //         this.postaddParameters.post,
            //         // this.postaddParameters.post,
            //         ).then(response=>{
            //         if(response.data.infoCode){
            //             this.$message({
            //                 message: response.data.description,
            //                 type: 'warning'
            //             });
            //         }else{
            //             this.$message({
            //                 message: '添加成功',
            //                 type: 'success'
            //             });
            //         }
            //     })
            // }
        }
    }
</script>
<style>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
  color: black;
  /* border: 1px solid black; */
  width: 310px;
  display: inline-block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 680px;
  height: 350px;
  margin: 80px auto;
}
</style>
