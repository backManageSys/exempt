<template>
  <div>
    <!-- <div>获取QrCode</div> -->
    <el-form ref="form" :model="formparameters" label-width="100px" style="margin-top:20px">
      <el-form-item label="金额">
        <el-input v-model="formparameters.money" style="width: 30%;"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="formparameters.memo" style="width: 30%;"></el-input>
      </el-form-item>
      <el-form-item label="ip地址">
        <el-input v-model="formparameters.ip" style="width: 30%;"></el-input>
      </el-form-item>
      <el-form-item label="充值方编号">
        <el-input v-model="formparameters.id" style="width: 30%;"></el-input>
      </el-form-item>
      <el-form-item label="商户名">
        <el-input v-model="formparameters.merchantName" style="width: 30%;"></el-input>
      </el-form-item>
      <el-form-item label="码类型">
        <el-select v-model="value" placeholder="请选择" @change="firstChange" @visible-change="getSelect" style="width: 20%">
          <el-option
            v-for="item in options1"
            :key="item.id"
            :label="item.codeCategory"
            :value="item.codeCategory">
          </el-option>
        </el-select>
        <el-select v-model="value1" placeholder="请选择" :disabled="secondState" @change="secondChange"
                   @visible-change="getPayType" style="width: 30%">
          <el-option
            v-for="item in options2"
            :key="item.id"
            :label="item.codeType"
            :value="item.id">
          </el-option>
        </el-select>
        </el-select>
        <!--<el-button @click="getRate" type="success">查看费率</el-button>-->
      </el-form-item>
      <el-form-item label="当前费率" v-if="a()">
        <el-tag style="font-size:15px">{{formparameters.rate+'%'}}</el-tag>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getCode" :disabled="isdisabled">获取二维码</el-button>
        <!-- <el-button>取消</el-button> -->
      </el-form-item>
      <el-form-item>
        <img :src="img_src" height="200" width="200"/>
      </el-form-item>
    </el-form>
    <el-form>

    </el-form>
  </div>
</template>

<script>
  import store from '../../../store'
  import {qrCodeGet, redirect, rateGet} from '@/api/personal'
  import {getSelect, getPayType} from '@/api/role'
  import config from '../../../../config'

  export default {
    data() {
      return {
        options1: [],
        options2: [],
        value: '',
        value1: '',
        value2: '',
        secondState: true,
        thirdState: true,
        selectState: true,
        status: '',
        qrCode: '',
        formparameters: {
          // id:'123',
          // ip:'192.168.1.1',
          // memo:'1234123',
          // merchantId:'9',
          // money: '0.01',
          // sign:'1234132',
          // time:'123123',
          // type:'alipay',
          rate: ''
        },
        urlBase: "http://qr.liantu.com/api.php?text=",
        showqrcodeurl: 'alipays://platformapi/startapp?appId=20000123%26actionType=scan%26biz_data={"s": "money","u":"2088022126490523","a":"0.1","m":"11547555613680009"}',
        img_src: '',
        isdisabled: false
      }
    },
    created() {
      // this.formparameters.merchantId = store.getters.uid
      this.formparameters.merchantId = this.formparameters.merchantId
      this.formparameters.time = Date.parse(new Date()) / 1000;
      // console.log(this.$route.path,location.href,this.config)
      this.BASE_API = process.env.BASE_API;
      this.img_src = this.urlBase + this.BASE_API;
    },
    methods: {
      a() {

        if (this.formparameters.rate == '')
          return false;
        else
          return true;
      },
      getPayType() {
        getPayType(this.value).then(response => {
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.options2 = response.data;
          }
        });
      },
      getSelect() {
        getSelect().then(response => {
          if (response.code !== 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.options1 = response.data;
          }
        });
      },
      firstChange() {
        console.log(111);
        this.secondState = false;
      },
      secondChange() {
        this.thirdState = false;
        this.selectState = false;
      },
      getRate() {
        rateGet(this.formparameters.merchantName).then(res => {
          if (res.code == 200) {
            if (this.formparameters.codeType == 'TPASS')
              this.formparameters.rate = res.data.alipay_TPASS;
            if (this.formparameters.codeType == 'TSOLID')
              this.formparameters.rate = res.data.alipay_TSOLID;
            if (this.formparameters.codeType == 'RPASSOFF')
              this.formparameters.rate = res.data.alipay_RPASSOFF;
            if (this.formparameters.codeType == 'RPASSQR')
              this.formparameters.rate = res.data.alipay_RPASSQR;
            if (this.formparameters.codeType == 'RSOLID')
              this.formparameters.rate = res.data.alipay_RSOLID;
            if (this.formparameters.codeType == 'RedEnvelope')
              this.formparameters.rate = res.data.alipay_RedEnvelope;
          }
        })
      },
      getCode() {

        this.isdisabled = true;
        this.formparameters.time = Date.parse(new Date()) / 1000;
        qrCodeGet(this.formparameters.id, this.formparameters.ip, this.formparameters.memo, this.formparameters.merchantName,
          this.formparameters.money, this.formparameters.sign, this.formparameters.time, this.value1).then(res => {
          // console.log(res)
          if (res.code != 200) {
            this.$message({
              message: res.data.reason,
              type: 'warning'
            });
          } else {

            // location.href = "../../a.htm";
            this.img_src = this.urlBase + this.BASE_API + res.data.url + "?orderId=" + res.data.orderId;
            var ispc = this.IsPC();
            if (ispc) {
              //window.open("static/test.htm?img_src="+this.img_src);
              window.location = "static/test.htm?img_src=" + this.img_src;
            } else {
              window.location = "static/test_phone.htm?img_src=" + this.img_src;
            }

            // console.log(this.img_src)
          }
          this.isdisabled = false;
        })
      },
      IsPC() {
        var userAgentInfo = navigator.userAgent;
        var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");
        var flag = true;
        for (var v = 0; v < Agents.length; v++)
          if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
          }
        return flag;
      }
    }
  }
</script>

<style scoped>

</style>
