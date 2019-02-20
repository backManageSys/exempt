<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-form :label-position="labelPosition" :model="permissionaddParameters" class="demo-form-inline">
            <el-form-item label="职位">
             <el-select  style="width: 200px" v-model="permissionaddParameters.post" @change="getTeams">
                 <el-option
                    v-for="item in posts"
                    :key="item.id"
                    :label="item.post"
                    node-key="id"
                    :value="item.post">
                    </el-option>
            </el-select >
            </el-form-item>
             <el-table
            :data="teams"
            border
            style="width: 100%">
             <el-table-column
                prop="post"
                label="职位"
                width="180">
                </el-table-column>
                <el-table-column
                prop="permission"
                label="权限"
                    >
                </el-table-column>
            </el-table>
            <el-form-item label="权限">
            </el-form-item>
                <el-tree
                    :props="props"
                    :data="treepermissions"
                    show-checkbox
                    node-key="title"
                    ref="tree"
                    :default-expanded-keys="expanded"

                   >
                </el-tree>
            <el-form-item>
                <el-button type="primary" @click="addpermission">修改</el-button>
            </el-form-item>
        </el-form>
        


  </div>
</template>

<script>
import { permissionAllocate,postGet,checkSinglePermission } from '@/api/company'
import { getTreePermissions } from '@/api/permissions'
import {getIds} from '@/utils/treeids'

    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                permissionaddParameters: {
                        "post": "商户",
                        "permission": "permission",
                },
                currentPage:1,
                treepermissions:[],
                props: {
                    children: 'children',
                    label: 'title'
                },
                posts:[{}],
                teams:[{
                        // "post": "职位",
                        // "permission": "权限",
                }],
                expanded:[],
                heads:[]
            }
        },
        created(){
            this.treepermissions = getTreePermissions();
            var a =[]
            this.treepermissions.forEach(el1 => {
                     if(el1.children){
                        //  a.push(el1.title)
                         el1.children.forEach(el2=>{
                             if(el2.children){
                                 el2.children.forEach(el3=>{
                                     if(el3.children){

                                     }else{
                                         a.push(el3.title)
                                     }
                                 })
                                 }else{
                                 a.push(el2.title)
                             }
                         })

                     }else{
                         a.push(el1.title)
                     }
                 });
            console.log(a)
            this.heads = a
            console.log(this.heads)
            this.getPost();
        },
        methods: {
            intersect(a,b){
                let set1 = new Set(a),set2 = new Set(b);
                return [...new Set([...set1].filter( x => set2.has(x)))];
            },
            setCheckedNodes(nodes) {
                 console.log(nodes)
                 this.expanded = nodes;
                 var a = []
                 var no = this.intersect(nodes,this.heads)
                no.forEach(element => {
                    var c = {}
                    c.title = element;
                    a.push(c)
                });
                 console.log(a)
                this.$refs.tree.setCheckedNodes(a);
            },
            getTeams(){
                    checkSinglePermission(this.permissionaddParameters.post).then(response => {
                            // console.log(response.data.infoCode)
                            if(response.code!=200){
                                this.$message({
                                    message: response.data.description,
                                    type: 'warning'
                                });
                            }else{
                                this.$message({
                                    message: '查询成功',
                                    type: 'success'
                                });
                                // this.teams = {}
                                // response.data.forEach(el => {
                                //     el.permission = el.permission.join(',')
                                // });
                                var a = {}
                                a.permission = response.data.permission.join(',');
                                a.post = response.data.post;
                                this.teams = [a]
                                this.setCheckedNodes(response.data.permission);
                                console.log(this.teams,'ppp;')
                            }
                            resolve()
                        }).catch(error => {
                            this.$message(error);
                        })
                },
            getPost(){
                 postGet().then(response=>{
                        console.log(response,'response')
                         if(response.code!=200){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                            this.posts = response.data;
                        }
                    })
            },
            addpermission() {
                this.getCheckedKeys();
                permissionAllocate(
                    this.permissionaddParameters.post,
                    this.permissionaddParameters.permission,
                    ).then(response=>{
                    if(response.data.infoCode){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                        this.$message({
                            message: '添加成功',
                            type: 'success'
                        });
                    }
                })
            },
            getCheckedKeys(){
                this.permissionaddParameters.permission = this.$refs.tree.getCheckedKeys();
                this.permissionaddParameters.permission = this.permissionaddParameters.permission.concat(this.$refs.tree.getHalfCheckedKeys());
                // console.log(this.permissionaddParameters.permission,this.permissionaddParameters.permission1 )
            },
            handleChange(val) {
                console.log(val);
                  if(val==2)
                {
                    this.getpermissions();
                }
            }
        }
    }
</script>

<style scoped>

</style>
