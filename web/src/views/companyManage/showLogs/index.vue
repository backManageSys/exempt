<!--日志明细-->
<template>
  <div class="source">
    <div>
      <el-input class="inpSearch"
        placeholder="请输入内容"
        v-model="condition" @keyup.enter="getList">
        <el-button @click="getList" class="searchButton" slot="append" icon="el-icon-search"></el-button>
      </el-input>
      <div class="el-table el-table--fit el-table--border el-table--enable-row-hover el-table--enable-row-transition"
           style="width: 100%;">
        <div class="el-table__header-wrapper">
          <table cellspacing="0" cellpadding="0" border="0" class="el-table__header">
            <thead class="has-gutter">
            <tr class="">
              <th colspan="1" rowspan="1" class="el-table_3_column_7     is-leaf">
                <div class="cell">id</div>
              </th>
              <th colspan="1" rowspan="1" class="el-table_3_column_8     is-leaf">
                <div class="cell">请求来源</div>
              </th>
              <th colspan="1" rowspan="1" class="el-table_3_column_9     is-leaf">
                <div class="cell">类型</div>
              </th>
              <th colspan="3" rowspan="1" class="el-table_3_column_9     is-leaf">
                <div class="cell">描述</div>
              </th>
              <th colspan="3" rowspan="1" class="el-table_3_column_9     is-leaf">
                <div class="cell">时间</div>
              </th>
              <th colspan="1" rowspan="1" class="el-table_3_column_9     is-leaf">
                <div class="cell">异常码</div>
              </th>
              <th colspan="1" rowspan="1" class="el-table_3_column_9     is-leaf">
                <div class="cell">操作方法</div>
              </th>
              <th colspan="2" rowspan="1" class="el-table_3_column_9     is-leaf">
                <div class="cell">参数</div>
              </th>
              <th colspan="2" rowspan="1" class="el-table_3_column_9     is-leaf">
                <div class="cell">用户名</div>
              </th>
            </tr>
            </thead>
          </table>
        </div>
        <div class="el-table__body-wrapper is-scrolling-none">
          <table cellspacing="0" cellpadding="0" border="0" class="el-table__body">
            <tbody>
            <tr class="el-table__row" v-for="item in data">
              <td colspan="1" rowspan="1" class="el-table_3_column_7     is-leaf">
                <div class="cell">{{item.id}}</div>
              </td>
              <td colspan="1" rowspan="1" class="el-table_3_column_8     is-leaf">
                <div class="cell">{{item.requestip}}</div>
              </td>
              <td colspan="1" rowspan="1" class="el-table_3_column_9     is-leaf">
                <div class="cell">{{item.type}}</div>
              </td>
              <td colspan="3" rowspan="1" class="el-table_3_column_9     is-leaf">
                <div class="cell">{{item.description}}</div>
              </td>
              <td colspan="3" rowspan="1" class="el-table_3_column_9     is-leaf">
                <div class="cell">{{item.actiondate}}</div>
              </td>
              <td colspan="1" rowspan="1" class="el-table_3_column_9     is-leaf">
                <div class="cell">{{item.exceptioncode}}</div>
              </td>
              <td colspan="1" rowspan="1" class="el-table_3_column_9     is-leaf">
                <div class="cell">{{item.actionmethod}}</div>
              </td>
              <td colspan="2" rowspan="1" class="el-table_3_column_9     is-leaf">
                <div class="cell">{{item.params}}</div>
              </td>
              <td colspan="2" rowspan="1" class="el-table_3_column_9     is-leaf">
                <div class="cell">{{item.username}}</div>
              </td>
            </tr>
            </tbody>
          </table>
          <!--分页-->
          <div class="fenY">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :page-sizes="[10, 20, 30, 40]"
              :page-size="pagesize"
              layout="sizes, prev, pager, next"
              :total="total">
            </el-pagination>

          </div>
        </div>
        <div class="el-table__column-resize-proxy" style="display: none;"></div>
      </div>
    </div>
  </div>
</template>
<script>
  import {getLimit} from "@/api/table.js"
  import { getTime } from '@/utils/index'
  export default {
    data: function() {
      return {
        total : 10,//数据总数
        condition : '',//搜索条件
        currentPage:2,//第几页
        pagesize:10,//每页显示条数
        options : [
          {
            value : '10',
            label : '10page',
          },
          {
            value : '20',
            label : '20page',
          },{
            value : '30',
            label : '30page',
          },
          {
            value : '40',
            label : '40page',
          },

        ],
        data: [
          {
            actiondate: "Feb 23, 2019 7:48:15 PM",
            actionmethod: "AddPayType",
            description: "管理员添加支付类型:成功",
            exceptioncode: 0,
            id: 21,
            params: "{codeCategory : 微信, codeType : 收款码, status : 停用 }",
            requestip: "49.92.166.106",//请求来源
            type: "正常",
            username: "0",
          }
        ]
      }
    },
    methods: {
      handleSizeChange(val) {//选择每页多少条
        this.pagesize = val;
        this.getList();
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) { // 切换页数，当前第几页
        this.currentPage = val;
        this.getList();
        console.log(`当前页: ${val}`);
      },
      getList () {
        let size = this.pagesize;
        let page = this.currentPage;
        let cond = this.condition;
        getLimit(size,page,cond).then((data)=>{
          console.log(data,'ShowLogsData');
          this.total = data.data.count;
          this.data = data.data.systemLogs;
          this.data.forEach(value => {
            value.actiondate=getTime(value.actiondate);
          })
        }).catch((err)=>{
          console.log(err);
        })
      }
    },
    computed: {

    },
    created(){
      this.getList();
    },
    mounted(){
      var a = document.getElementsByClassName('el-pager')[0];
      // a.style.width = 320 + 'px';
    },
}
</script>
<style scoped>
  .el-table__header-wrapper > table > thead > tr > th > div {
    text-align: center;
    font-weight: bold;
    font-size: 17px;
  }

  .el-table__body-wrapper,
  .el-table__header-wrapper {
    width: 1290px;
  }
  .el-table__body-wrapper  {
    padding-bottom: 200px;
    border-bottom: none;
  }
  .app-main >.el-table__body-wrapper,
  .el-table__header-wrapper > table,
  .el-table__body-wrapper > table {
    width: 100%;
  }
  .el-table--border::after, .el-table--group::after, .el-table::before {
    background-color: #fff!important;
  }
/*下拉菜单*/
  .fenY  {
    display: flex;
  }
  .fenY > div {
    width: 100px;
  }
  /*搜索按钮*/
  .inpSearch  {
    width: 500px;
  }
  .searchButton:active  {
    background-color: #409eff;
    color:#fff;
  }
</style>
