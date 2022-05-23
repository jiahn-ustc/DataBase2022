<template>
  <div>
    <h1>支票账户信息</h1>
    <el-table
        :data="tableData"
        border
        style="width: 100%">
      <el-table-column
          fixed
          prop="account_id"
          label=账户号
          width="120">
      </el-table-column>
      <el-table-column
          prop="bank_name"
          label="支行名"
          width="120">
      </el-table-column>
      <el-table-column
          prop="client_id"
          label="客户身份证号"
          width="180">
      </el-table-column>
      <el-table-column
          prop="balance"
          label="余额"
          width="150">
      </el-table-column>
      <el-table-column
          prop="start_time"
          label="开户时间"
          width="200">
      </el-table-column>
      <el-table-column
          prop="overdraft"
          label="透支额"
          width="120">
      </el-table-column>

      <el-table-column
          prop="last_time"
          label="上次访问时间"
          width="200">
      </el-table-column>


      <el-table-column
          label="操作"
          width="100">
        <template slot-scope="scope">
          <el-button @click="edit(scope.row)" type="text" size="small">修改账户</el-button>
          <el-button @click="deleteAccount(scope.row)" type="text" size="small">销户</el-button>
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
    <h1>客户开通支票账户</h1>
    <el-form style="width: 30%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">

      <el-form-item label="账户号" prop="account_id">
        <el-input v-model="ruleForm.account_id"></el-input>
      </el-form-item>
      <el-form-item label="开户银行名" prop="bank_name">
        <el-input v-model="ruleForm.bank_name"></el-input>
      </el-form-item>
      <el-form-item label="客户身份证号" prop="client_id">
        <el-input v-model="ruleForm.client_id"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">立即开户</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>

    <h1>查询支票账户</h1>
    <el-form style="width: 30%" :model="searchRuleForm" :rules="rules" ref="searchRuleForm" label-width="150px" class="demo-ruleForm">
      <el-form-item label="支票账户号" prop="account_id">
        <el-input v-model="searchRuleForm.account_id"></el-input>
      </el-form-item>
      <el-form-item label="所属支行名">
        <el-input v-model="searchRuleForm.bank_name" readonly></el-input>
      </el-form-item>
      <el-form-item label="客户身份证号">
        <el-input v-model="searchRuleForm.client_id" readonly></el-input>
      </el-form-item>
      <el-form-item label="余额">
        <el-input v-model="searchRuleForm.balance" readonly></el-input>
      </el-form-item>
      <el-form-item label="开户时间">
        <el-input v-model="searchRuleForm.start_time" readonly></el-input>
      </el-form-item>
      <el-form-item label="透支额">
        <el-input v-model="searchRuleForm.overdraft" readonly></el-input>
      </el-form-item>

      <el-form-item label="上次访问时间">
        <el-input v-model="searchRuleForm.last_time" readonly></el-input>
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
      if(_this.searchRuleForm.account_id==='')
      {
        _this.$alert("账户号不能为空",'消息',{
          confirmButtonText: '确定'
        })
      }
      else{
        axios.get('http://localhost:8181/checkAccount/findById/'+_this.searchRuleForm.account_id).then(function (resp){

          _this.searchRuleForm = resp.data;
          console.log(_this.searchRuleForm);
          if(_this.searchRuleForm.bank_name==null)
          {
            _this.$alert("数据库中没有账户号为"+_this.searchRuleForm.account_id+"的支票账户，查询失败！！",'消息',{
              confirmButtonText: '确定'

            })
          }
          else
          {
            _this.$alert(_this.searchRuleForm.account_id+" 查询成功",'消息',{
              confirmButtonText: '确定'

            })
          }

        }).catch(function (error) {
          if (error.response) {
            _this.$alert("数据库中没有账户号为"+_this.searchRuleForm.account_id+"的客户，查询失败！！",'消息',{
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
    deleteAccount(row){

      const _this = this
      axios.delete('http://localhost:8181/checkAccount/deleteById/'+row.account_id).then(function (resp){
        _this.$alert(row.account_id+" 删除成功",'消息',{
          confirmButtonText: '确定',
          callback: action => {
            window.location.reload()
          }
        })
      }).catch(function (error) {
        if (error.response) {
          _this.$alert("数据库中没有身份证号为"+_this.searchRuleForm.account_id+"的客户,删除失败！！",'消息',{
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
        path: '/updateCheckAccount',
        query: {
          account_id: row.account_id
        }
      })
    },
    page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/checkAccount/findAll/'+currentPage+'/'+_this.size+'').then(function (resp){
        _this.tableData = resp.data.content
        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size
        console.log(resp)
      })
    },
    submitForm(formName) {
      this.ruleForm.start_time=this.getCurrentTime();
      this.ruleForm.last_time=this.ruleForm.start_time;
      console.log(this.ruleForm);
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post('http://localhost:8181/checkAccount/save',this.ruleForm).then(function (resp){
            console.log(resp);
            //alert(resp.data);
            if(resp.data=='success')
            {
              _this.$alert("账户"+_this.ruleForm.account_id+" 开户成功",'消息',{
                confirmButtonText: '确定',
                callback: action => {
                  window.location.reload()
                }

              })
            }
            else{
              _this.$alert("客户"+_this.ruleForm.account_id+" 添加失败,原因为："+resp.data,'消息',{
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
    },
    getCurrentTime(){
      const _this = this;
      let yy = new Date().getFullYear();
      let mm = new Date().getMonth()+1;
      let dd = new Date().getDate();
      let hh = new Date().getHours();
      let mf = new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes();
      let ss = new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds();
      return yy+'/'+mm+'/'+dd+' '+hh+':'+mf+':'+ss;
    }
  },
  created() {
    const _this = this
    axios.get('http://localhost:8181/checkAccount/findAll/1/'+_this.size+'').then(function (resp){
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
      tableData: [],
      ruleForm: {
        account_id: '',
        bank_name: '',
        client_id: '',
        balance: '',
        start_time: '',
        overdraft: '',
        last_time: ''
      },
      rules: {
        client_id: [
          { required: true, message: '请输入客户身份证号', trigger: 'blur' },
        ],
        account_id: [
          { required: true, message: '请输入账户号', trigger: 'blur' },
        ],
        bank_name:[
          { required: true, message: '请输入开户银行名', trigger: 'blur' },
        ]
      },
      searchRuleForm: {
        account_id: '',
        bank_name: '',
        client_id: '',
        balance: '',
        start_time: '',
        overdraft: '',
        last_time: ''
      }

    }
  }
}
</script>