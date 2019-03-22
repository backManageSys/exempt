<template>
  <!-- <div>团队管理</div> -->
  <div class="app-container">
    <el-form :label-position="labelPosition" :model="formaddParameters" class="demo-form-inline"
             style="margin-top:30px">
      <el-form-item label="选择类型" label-width="10%">
        <el-select style="width: 200px" v-model="accountChangeValue" placeholder="请选择" @change="accountChange1" >
          <el-option
            v-for="item in accountChange"
            :key=""
            :label="item.cardNumber"
            :value="item.cardNumber">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="出款账号" label-width="10%">
        <el-select style="width: 200px" v-model="formaddParameters.cardNumber_in" placeholder="出款账号">
          <el-option
            v-for="item in cardNumber_ins"
            :key=""
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="入款账号" label-width="10%">
        <el-select style="width: 200px" v-model="formaddParameters.cardNumber_out" placeholder="入款账号">
          <el-option
            v-for="item in cardNumber_outs"
            :key=""
            :label="item"
            :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="金额" label-width="10%">
        <el-input v-model="formaddParameters.money" placeholder="金额" type="number" style="width: 200px"></el-input>
      </el-form-item>
      <el-form-item label-width="10%">
        <el-button type="primary" @click="change">转账</el-button>
      </el-form-item>
    </el-form>


  </div>
</template>

<script>
  import {permissionAllocate, cardsGet,getLoginId,getCompanyCard,internalaccountchangeAdd} from '@/api/company'
  import {getCompanyCard1,companyCards,getCompanyCard2} from '@/api/role'
  import {getTreePermissions} from '@/api/permissions'
  import {getIds} from '@/utils/treeids'
  import store from '../../../store'
  import {cardsGetOne} from '@/api/personal'
  import {C2Pcard} from '@/api/change'

  export default {
    data() {
      return {
        accountChangeValue:"",
        accountChange: [{
          cardNumber: "入款"
        }, {
          cardNumber: "储备"
        }],
        activeNames: ['1'],
        labelPosition: 'right',
        formaddParameters: {
          cardNumber_in: "",
          cardNumber_out: "",
          money: "",
          operateId: ""
        },
        currentPage: 1,
        treepermissions: [],
        props: {
          children: 'children',
          label: 'title'
        },
        posts: [{}],
        cardNumber_ins: [{}],
        cardNumber_outs: [{}]
      }
    },
    created() {
      this.formaddParameters.operateId = store.getters.uid;
      this.getData();

    },
    methods: {
      accountChange1(){
        this.formaddParameters.cardNumber_in = "";
        this.formaddParameters.cardNumber_out = "";
        if(this.accountChangeValue === "入款"){
          getLoginId(store.getters.uid).then(response => {
            if (response.code !== 200) {
              this.$message({
                message: response.data.description,
                type: 'warning'
              });
            } else {
              this.cardNumber_ins = response.data;
            }
          });
        getCompanyCard1(store.getters.uid).then(response => {
            if (response.code !== 200) {
              this.$message({
                message: response.data.description,
                type: 'warning'
              });
            } else {
              this.cardNumber_outs = response.data;
            }
          })
        }else{


          getCompanyCard2(store.getters.uid).then(response => {
            console.log(response,'response__cardNumber_ins')
            if (response.code !== 200) {
              this.$message({
                message: response.data.description,
                type: 'warning'
              });
            } else {
              this.cardNumber_ins = response.data;
            }
          });
          companyCards().then(response => {
            console.log(response,'cardNumber_outs')
            if (response.code !== 200) {
              this.$message({
                message: response.data.description,
                type: 'warning'
              });
            } else {
              this.cardNumber_outs = response.data;
            }
          })

        }
      },
      getPerCards() {
        cardsGetOne(store.getters.uid).then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: 'warning'
            });
          } else {
            this.cardNumber_ins = response.data;
          }
        })

      },
      getCompanyCards() {
        cardsGet().then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: 'warning'
            });
          } else {
            this.cardNumber_outs = response.data.companyCardList;
          }
        })
      },
      getData() {
        // this.getPerCards();
        // this.getCompanyCards();
      },
      change() {
        if(this.accountChangeValue === "入款"){
          internalaccountchangeAdd(
            this.formaddParameters.cardNumber_out,
            this.formaddParameters.cardNumber_in,
            this.formaddParameters.money,
            store.getters.uid,
            this.accountChangeValue
          ).then(response => {
            if (response.data.infoCode !== 200) {
              this.$message({
                message: response.data.description,
                type: 'warning'
              });
            } else {
              this.$message({
                message: response.data.description,
                type: 'success'
              });
            }
          })
        } else{
          internalaccountchangeAdd(
            this.formaddParameters.cardNumber_in,
            this.formaddParameters.cardNumber_out,
            this.formaddParameters.money,
            store.getters.uid,
            this.accountChangeValue
          ).then(response => {
            if (response.data.infoCode) {
              this.$message({
                message: response.data.description,
                type: 'warning'
              });
            } else {
              this.$message({
                message: '添加成功',
                type: 'success'
              });
            }
          })
        }

      },
      getCheckedKeys() {
        this.formaddParameters.permission = this.$refs.tree.getCheckedKeys();
        this.formaddParameters.permission = this.formaddParameters.permission.concat(this.$refs.tree.getHalfCheckedKeys());
      },
      handleChange(val) {
        if (val == 2) {
          this.getpermissions();
        }
      }
    }
  }
</script>

<style>
  .el-input>input::-webkit-outer-spin-button,
  .el-input>input::-webkit-inner-spin-button {
    -webkit-appearance: none;
  }

  .el-input>input[type="number"] {
    -moz-appearance: textfield;
  }
</style>

