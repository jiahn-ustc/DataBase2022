<template>
  <div>
    <h1>客户办理贷款信息</h1>
    <el-table
        :data="tableData"
        border
        style="width: 100%"
    >
      <el-table-column
          fixed
          prop="client_id"
          label="客户身份证号"
          width="200">
      </el-table-column>
      <el-table-column
          prop="bank_name"
          label="支行名"
          width="120">
      </el-table-column>
      <el-table-column
          prop="loan_id"
          label="贷款号"
          width="120">
      </el-table-column>

    </el-table>

    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="size"
        :total="total"
        @current-change="page">
    </el-pagination>
    <h1>办理贷款</h1>
    <el-form style="width: 30%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">

      <el-form-item label="客户身份证号" prop="client_id">
        <el-input v-model="ruleForm.client_id"></el-input>
      </el-form-item>
      <el-form-item label="贷款支行" prop="bank_name">
        <el-input v-model="ruleForm.bank_name"></el-input>
      </el-form-item>
      <el-form-item label="贷款号" prop="loan_id">
        <el-input v-model="ruleForm.loan_id"></el-input>
      </el-form-item>
      <el-form-item label="贷款总金额" prop="amount">
        <el-input v-model="ruleForm.amount"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">立即办理</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>

    <h1>贷款查询</h1>
    <el-form style="width: 30%" :model="searchRuleForm" :rules="rules" ref="searchRuleForm" label-width="150px" class="demo-ruleForm">
      <el-form-item label="贷款号" prop="loan_id">
        <el-input v-model="searchRuleForm.loan_id"></el-input>
      </el-form-item>
      <el-form-item label="贷款总金额">
        <el-input v-model="searchRuleForm.amount" readonly></el-input>
      </el-form-item>
      <el-form-item label="已发放金额">
        <el-input v-model="searchRuleForm.amount_provided" readonly></el-input>
      </el-form-item>
      <el-form-item label="发放状态">
        <el-input v-model="searchRuleForm.loan_status" readonly></el-input>
      </el-form-item>
      <el-form-item label="发放支行">
        <el-input v-model="searchRuleForm.bank_name" readonly></el-input>
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
      if(_this.searchRuleForm.loan_id==='')
      {
        _this.$alert("贷款号不能为空",'消息',{
          confirmButtonText: '确定'
        })
      }
      else{
        axios.get('http://localhost:8181/clientGetLoan/findById/'+_this.searchRuleForm.loan_id).then(function (resp){

          _this.searchRuleForm = resp.data;
          console.log(_this.searchRuleForm);
          if(_this.searchRuleForm.bank_name==null)
          {
            _this.$alert("数据库中没有贷款号为"+_this.searchRuleForm.loan_id+"的贷款，查询失败！！",'消息',{
              confirmButtonText: '确定'

            })
          }
          else
          {
            _this.$alert(_this.searchRuleForm.loan_id+" 查询成功",'消息',{
              confirmButtonText: '确定'

            })
          }

        });
      }
    },
    page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/clientGetLoan/findAll/'+currentPage+'/'+_this.size+'').then(function (resp){
        _this.tableData = resp.data.content

        console.log(_this.tableData)

      })
    },
    submitForm(formName) {
      console.log(this.ruleForm);
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post('http://localhost:8181/clientGetLoan/save',this.ruleForm).then(function (resp){
            console.log(resp);
            //alert(resp.data);
            if(resp.data=='success')
            {
              _this.$alert("账户"+_this.ruleForm.loan_id+" 开户成功",'消息',{
                confirmButtonText: '确定',
                callback: action => {
                  window.location.reload()
                }

              })
            }
            else{
              _this.$alert("客户"+_this.ruleForm.loan_id+" 添加失败,原因为："+resp.data,'消息',{
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
    axios.get('http://localhost:8181/clientGetLoan/findAll/1/'+_this.size+'').then(function (resp){
      _this.tableData = resp.data.content
      _this.total = resp.data.totalElements
      //_this.size = resp.data.size
      /*for(var i=0;i<_this.tableData.length;i++)
        _this.tableData[i].test = i;*/
      console.log(_this.tableData)
    })
  },
  data() {
    return {
      size: 6,
      total: null,
      tableData: [],
      ruleForm: {


        client_id: '',
        bank_name: '',
        loan_id: '',
        amount: ''

      },
      rules: {
        client_id: [
          { required: true, message: '请输入客户身份证号', trigger: 'blur' },
        ],
        loan_id: [
          { required: true, message: '请输入贷款号', trigger: 'blur' },
        ],
        bank_name:[
          { required: true, message: '请输入开户银行名', trigger: 'blur' },
        ],
        amount:[
          { required: true, message: '请输入贷款总金额', trigger: 'blur' },
        ]
      },
      searchRuleForm: {
        loan_id: '',
        amount: '',
        amount_provided: '',
        loan_status: '',
        bank_name: ''
      }
    }
  }
}
</script>