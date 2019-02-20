'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // BASE_API: '"https://easy-mock.com/mock/5950a2419adc231f356a6636/vue-admin"',
  //BASE_API: '"http://10.107.30.218:8080"',
  // BASE_API: '"http://172.31.198.55:8080"',
  // BASE_API: '"https://junrongcenter.com:8080"',
  // BASE_API: '"http://ijehj4.natappfree.cc"'
  BASE_API: '"http://47.102.146.253:1024"'

})
