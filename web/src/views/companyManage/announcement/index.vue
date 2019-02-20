<template>
  <div>
    <el-card class="box-card" align="center">
      <div slot="header" class="clearfix">
        <span>发布公告</span>
      </div>
      <el-form>
         <el-form-item></el-form-item>
        <el-input 
        v-model="announcement" 
        rows="10" 
        type="textarea" 
        style="font-size:20px;font-family:'Microsoft YaHei'">
        </el-input>
        <el-form-item></el-form-item>
        <el-form-item>
          <el-button type="success" @click="confirm">确认发布</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
 import store from "../../../store";
import { SetAnnouncement,GetAnnouncement } from "@/api/company";
// getInfo(uid)
export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        success: "success",
        pending: "danger"
      };
      return statusMap[status];
    },
    orderNoFilter(str) {
      return str.substring(0, 30);
    }
  },
  data() {
    return {
        announcement:""
    //     oldtime: 5,
    //     dialogFormVisible: false,
    //   newRow: {
    //     newtime: 10
    //  }
    };
  },
//   created() {
//     this.fetchData();
//   },
  methods: {
    confirm() {
      // console.log("get money", this.newRow);
      SetAnnouncement(
        this.announcement
      ).then(res => {
        if (res.code != 200) {
          this.$message({
            message: "更新公告失败",
            type: "warning"
          });
        } else {
          this.$message({
            message: "更新公告成功",
            type: "success"
          });
        }
      });
    }
  }
};
</script>
<style scoped>
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
  width: 800px;
  height: 500px;
  margin: 80px auto;
}

/* .textarea >>> .el-textarea__inner{
 font-family:"Microsoft" !important;
 font-size:20px !important;
 } */

</style>
