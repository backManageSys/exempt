<template>
  <div class="dashboard-container">
    <el-card class="box-card" align="center">
      <div slot="header" class="clearfix">
        <span>最新公告</span>
      </div>
      <div class="dashboard-card-content" >
        <div>{{ announcement}}</div>
      </div>
    </el-card>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import{GetAnnouncement}from '@/api/company'
export default {
  name: 'Dashboard',
  data(){ 
    return{
       announcement:"暂无公告"
    };
  },
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  },
  created(){
     GetAnnouncement().then(response=>{
        console.log(response,'response')
          if(response.code!=200){
            this.$message({
                message: "暂无公告",
                type: 'warning'
            });
        }else{
            this.announcement = response.data;
        }
    })
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: -10px;
    display: flex;
  }
  &-card {
    width: 50%;
    height: 200px;
    margin-right: 20px;
    &-content {
      text-align:left;
      font-size: 20px;
      margin-top: 10px;
      margin-left: 30px;
    }
  }
}
.box-card {
  width: 800px;
  height: 500px;
  margin: 80px auto;
}
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
</style>
