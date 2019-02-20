<template>
  <div>
    <el-card class="box-card" align="center">
      <div slot="header" class="clearfix">
        <span>设置二维码失效时间</span>
      </div>
      <el-form>
         <el-form-item></el-form-item>
         <div class="text item">{{ '当前失效时间: ' + oldtime +"分钟"}}</div>
        <el-form-item></el-form-item>
        <el-form-item>
          <el-button type="success" @click="openDialog">更新</el-button>
        </el-form-item>
      </el-form>

    </el-card>
    <el-dialog title="更新二维码失效时间" :visible.sync="dialogFormVisible">
      <el-form :model="newRow">
        <el-form-item >
          <el-input v-model="newRow.newtime" placeholder="更新二维码失效时间"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
 import store from "../../../store";
import { SetRiskcontrol,GetRiskcontrol } from "@/api/company";
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
        oldtime: 5,
        dialogFormVisible: false,
      newRow: {
        newtime: 10
      }
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    openDialog() {
      this.dialogFormVisible = true;
    //   this.newRow.type = this.userInfo.role == 2 ? "agent" : "merchant";
    //   this.newRow.id = store.getters.uid;
      // console.log("opendialog");
      //this.newRow = JSON.parse(JSON.stringify(row));
    },
    fetchData() {
      GetRiskcontrol().then(response => {
        this.oldtime = response.data
      });
    },
    confirm() {
      // console.log("get money", this.newRow);
      SetRiskcontrol(
        this.newRow.newtime
      ).then(res => {
        if (res.code != 200) {
          this.$message({
            message: "更新失败",
            type: "warning"
          });
        } else {
          this.$message({
            message: "更新成功",
            type: "success"
          });
          this.oldtime = res.data;
        }
      });
      this.dialogFormVisible = false;
    }
  }
};
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
  width: 500px;
  height: 300px;
  margin: 80px auto;
}
</style>
