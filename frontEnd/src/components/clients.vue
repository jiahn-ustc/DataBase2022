<template>
  <div>
    <h1>客户信息</h1>
  <el-table
      :data="tableData"
      border
      style="width: 100%">
    <el-table-column
        fixed
        prop="client_id"
        label="身份证号"
        width="200">
    </el-table-column>
    <el-table-column
        prop="name"
        label="姓名"
        width="120">
    </el-table-column>
    <el-table-column
        prop="tel"
        label="电话"
        width="120">
    </el-table-column>
    <el-table-column
        prop="address"
        label="住址"
        width="150">
    </el-table-column>
    <el-table-column
        prop="contact_name"
        label="联系人姓名"
        width="120">
    </el-table-column>
    <el-table-column
        prop="contact_tel"
        label="联系人电话"
        width="120">
    </el-table-column>
    <el-table-column
        prop="contact_email"
        label="联系人邮箱"
        width="200">
    </el-table-column>
    <el-table-column
        prop="contact"
        label="与联系人的关系"
        width="120">
    </el-table-column>

    <el-table-column
        label="操作"
        width="100">
      <template slot-scope="scope">
        <el-button @click="edit(scope.row)" type="text" size="small">修改</el-button>
        <el-button @click="deleteClient(scope.row)" type="text" size="small">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="size"
        :total="total"
        @current-change="page">
    </el-pagination>
    <h1>添加客户</h1>
    <el-form style="width: 30%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
      <el-form-item label="身份证号" prop="client_id">
        <el-input v-model="ruleForm.client_id"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="tel">
        <el-input v-model="ruleForm.tel"></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="ruleForm.address"></el-input>
      </el-form-item>
      <el-form-item label="联系人姓名">
        <el-input v-model="ruleForm.contact_name"></el-input>
      </el-form-item>
      <el-form-item label="联系人电话">
        <el-input v-model="ruleForm.contact_tel"></el-input>
      </el-form-item>
      <el-form-item label="联系人邮箱">
        <el-input v-model="ruleForm.contact_email"></el-input>
      </el-form-item>
      <el-form-item label="客户与联系人的关系">
        <el-input v-model="ruleForm.contact"></el-input>
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>

    <h1>查询客户</h1>
    <el-form style="width: 30%" :model="searchRuleForm" :rules="rules" ref="searchRuleForm" label-width="150px" class="demo-ruleForm">
      <el-form-item label="身份证号" prop="client_id">
        <el-input v-model="searchRuleForm.client_id"></el-input>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="searchRuleForm.name" readonly></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="searchRuleForm.tel" readonly></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="searchRuleForm.address" readonly></el-input>
      </el-form-item>
      <el-form-item label="联系人姓名">
        <el-input v-model="searchRuleForm.contact_name" readonly></el-input>
      </el-form-item>
      <el-form-item label="联系人电话">
        <el-input v-model="searchRuleForm.contact_tel" readonly></el-input>
      </el-form-item>
      <el-form-item label="联系人邮箱">
        <el-input v-model="searchRuleForm.contact_email" readonly></el-input>
      </el-form-item>
      <el-form-item label="客户与联系人的关系">
        <el-input v-model="searchRuleForm.contact" readonly></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="search">立即查询</el-button>
        <el-button @click="resetForm('searchRuleForm')">重置</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
export default {
  methods: {
    search(){
      const _this = this
      if(_this.searchRuleForm.client_id==='')
      {
        _this.$alert("身份证号不能为空",'消息',{
          confirmButtonText: '确定'
        })
      }
      else{
        axios.get('http://localhost:8181/client/findById/'+_this.searchRuleForm.client_id).then(function (resp){

          _this.searchRuleForm = resp.data;
          console.log(_this.searchRuleForm);
          if(_this.searchRuleForm.name==null)
          {
            _this.$alert("数据库中没有身份证号为"+_this.searchRuleForm.client_id+"的客户，查询失败！！",'消息',{
              confirmButtonText: '确定'

            })
          }
          else
          {
            _this.$alert(_this.searchRuleForm.client_id+" 查询成功",'消息',{
              confirmButtonText: '确定'

            })
          }

        }).catch(function (error) {
          if (error.response) {
            _this.$alert("数据库中没有身份证号为"+_this.searchRuleForm.client_id+"的客户，查询失败！！",'消息',{
              confirmButtonText: '确定'
            })
            _this.searchRuleForm.address='';
            _this.searchRuleForm.name='';
            _this.searchRuleForm.tel='';
            // The request was made and the server responded with a status code
            // that falls out of the range of 2xx
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            alert(222)
            // The request was made but no response was received
            // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
            // http.ClientRequest in node.js
            console.log(error.request);
          } else {
            alert(333)
            // Something happened in setting up the request that triggered an Error
            console.log('Error', error.message);
          }
          console.log(error.config);

        });
      }
    },
    deleteClient(row){

      const _this = this
      axios.delete('http://localhost:8181/client/deleteById/'+row.client_id).then(function (resp){
        if(resp.data=="success")
        {
          _this.$alert(row.name+" 删除成功",'消息',{
            confirmButtonText: '确定',
            callback: action => {
              window.location.reload()
            }
          })
        }
        else {
          _this.$alert("删除失败,原因为："+resp.data,'消息',{
            confirmButtonText: '确定'
          })
        }


      }).catch(function (error) {
        if (error.response) {
          _this.$alert("数据库中没有身份证号为"+_this.searchRuleForm.client_id+"的客户,删除失败！！",'消息',{
            confirmButtonText: '确定'
          })
          // The request was made and the server responded with a status code
          // that falls out of the range of 2xx
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          alert(222)
          // The request was made but no response was received
          // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
          // http.ClientRequest in node.js
          console.log(error.request);
        } else {
          alert(333)
          // Something happened in setting up the request that triggered an Error
          console.log('Error', error.message);
        }
        console.log(error.config);

      });
    },
    edit(row) {

      this.$router.push({
        path: '/updateClient',
        query: {
          client_id: row.client_id
        }
      })
    },
    page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/client/findAll/'+currentPage+'/'+_this.size+'').then(function (resp){
        _this.tableData = resp.data.content
        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size
        console.log(resp)
      })
    },
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post('http://localhost:8181/client/save',this.ruleForm).then(function (resp){
            console.log(resp);
            //alert(resp.data);
            if(resp.data=='success')
            {
              _this.$alert("客户"+_this.ruleForm.name+" 添加成功",'消息',{
                confirmButtonText: '确定',
                callback: action => {
                  window.location.reload()
                }

              })
            }
            else{
              _this.$alert("客户"+_this.ruleForm.name+" 添加失败,原因为："+resp.data,'消息',{
                confirmButtonText: '确定'

              })
            }

          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  created() {
    const _this = this
    axios.get('http://localhost:8181/client/findAll/1/'+_this.size+'').then(function (resp){
      _this.tableData = resp.data.content
      _this.total = resp.data.totalElements
      //_this.size = resp.data.size
      console.log(resp)
    })
  },
  data() {
    return {
      size: 6,
      total: null,
      tableData: [{
        client_id: 1,
        name: '',
        tel: 123,
        address: '上海',
        contact_name: '',
        contact_tel: '',
        contact_email: '',
        contact: '',

      }, {
        client_id: 2,
        tel: 456,
        address: '上海'
      }, {
        client_id: 3,
        tel: 789,
        address: '上海'
      }, {
        client_id: 4,
        tel: 1011,
        address: '上海'
      }],
      ruleForm: {
        client_id: '',
        name: '',
        tel: '',
        address: '',
        contact_name: '',
        contact_tel: '',
        contact_email: '',
        contact: ''
      },
      rules: {
        client_id: [
          { required: true, message: '请输入客户身份证号', trigger: 'blur' },
        ],
        name:[
          { required: true, message: '请输入客户姓名', trigger: 'blur' },
        ],
        tel: [
          { required: true, message: '请输入客户电话', trigger: 'blur' }
        ],
        address: [
          {  required: true, message: '请输入客户地址', trigger: 'blur' }
        ]
      },
      searchRuleForm: {
        client_id: '',
        name: '',
        tel: '',
        address: '',
        contact_name: '',
        contact_tel: '',
        contact_email: '',
        contact: ''
      }

    }
  }
}
</script>