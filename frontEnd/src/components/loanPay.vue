<template>
  <div>
    <h1>银行发放贷款信息</h1>
    <el-table
        :data="tableData"
        border
        style="width: 100%"
    >
      <el-table-column
          fixed
          prop="bank_name"
          label="支行名"
          width="120">
      </el-table-column>
      <el-table-column
          prop="loan_id"
          label="贷款号"
          width="120">
      </el-table-column>
      <el-table-column
          prop="pay_code"
          label="支付码"
          width="120">
      </el-table-column>
      <el-table-column
          prop="pay_date"
          label="支付日期"
          width="120">
      </el-table-column>
      <el-table-column
          prop="pay_amount"
          label="支付金额"
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
    <h1>发放贷款</h1>
    <el-form style="width: 30%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">

      <el-form-item label="贷款发放银行" prop="bank_name">
        <el-input v-model="ruleForm.bank_name"></el-input>
      </el-form-item>
      <el-form-item label="贷款号" prop="loan_id">
        <el-input v-model="ruleForm.loan_id"></el-input>
      </el-form-item>
      <el-form-item label="付款码" prop="pay_code">
        <el-input v-model="ruleForm.pay_code"></el-input>
      </el-form-item>

      <el-form-item label="发放金额" prop="pay_amount">
        <el-input v-model="ruleForm.pay_amount"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">立即开户</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>



  </div>
</template>

<script>
export default {
  methods: {
    page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/loanPay/findAll/'+currentPage+'/'+_this.size+'').then(function (resp){
        _this.tableData = resp.data.content

        console.log(_this.tableData)
        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size
        //console.log(resp)
      })
    },
    submitForm(formName) {
      this.ruleForm.pay_date=this.getCurrentTime();

      console.log(this.ruleForm);
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post('http://localhost:8181/loanPay/save',this.ruleForm).then(function (resp){
            console.log(resp);
            //alert(resp.data);
            if(resp.data=='success')
            {
              _this.$alert("贷款"+_this.ruleForm.loan_id+" 发放成功",'消息',{
                confirmButtonText: '确定',
                callback: action => {
                  window.location.reload()
                }

              })
            }
            else{
              _this.$alert("贷款"+_this.ruleForm.loan_id+" 发放失败,原因为："+resp.data,'消息',{
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
    axios.get('http://localhost:8181/loanPay/findAll/1/'+_this.size+'').then(function (resp){
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
        bank_name: '',
        loan_id: '',
        pay_code: '',
        pay_date: '',
        pay_amount: ''
      },
      rules: {
        bank_name: [
          { required: true, message: '请输入贷款发放的银行名', trigger: 'blur' },
        ],
        loan_id: [
          { required: true, message: '请输入发放的贷款号', trigger: 'blur' },
        ],
        pay_code:[
          { required: true, message: '请输入付款码', trigger: 'blur' },
        ],
        pay_amount:[
          { required: true, message: '请输入发放的贷款金额', trigger: 'blur' },
        ]
      }
    }
  }
}
</script>