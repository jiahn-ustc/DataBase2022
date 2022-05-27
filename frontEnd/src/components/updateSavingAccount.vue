<template>
  <el-form style="width: 30%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
    <el-form-item label="账户号" >
      <el-input v-model="ruleForm.account_id" readOnly></el-input>
    </el-form-item>
    <el-form-item label="支行名" prop="bank_name">
      <el-input v-model="ruleForm.bank_name"></el-input>
    </el-form-item>
    <el-form-item label="客户身份证号" prop="client_id">
      <el-input v-model="ruleForm.client_id"></el-input>
    </el-form-item>
    <el-form-item label="余额">
      <el-input v-model="ruleForm.balance"></el-input>
    </el-form-item>
    <el-form-item label="开户时间" >
      <el-input v-model="ruleForm.start_time" readOnly></el-input>
    </el-form-item>
    <el-form-item label="利率" >
      <el-input v-model="ruleForm.rate"></el-input>
    </el-form-item>
    <el-form-item label="货币类型" >
      <el-input v-model="ruleForm.currency_type"></el-input>
    </el-form-item>
    <el-form-item label="上次访问时间" >
      <el-input v-model="ruleForm.last_time" readOnly></el-input>
    </el-form-item>



    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">立即修改</el-button>
      <el-button @click="resetForm('ruleForm')">置空</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  methods: {
    submitForm(formName) {
      this.ruleForm.last_time=this.getCurrentTime();
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post('http://localhost:8181/savingAccount/update',this.ruleForm).then(function (resp){
            //console.log(resp);
            //alert(resp.data);
            if(resp.data=='success')
            {
              _this.$alert(_this.ruleForm.account_id+" 修改成功",'消息',{
                confirmButtonText: '确定',
                callback: action => {
                  _this.$router.push('/savingAccount');
                  window.location.reload()

                }
              })
            }
            else{
              _this.$alert(_this.ruleForm.account_id+" 修改失败,"+resp.data,'消息',{
                confirmButtonText: '确定',
                callback: action => {
                  window.location.reload()
                }
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
    //alert(this.$route.query.client_id)
    const _this = this
    axios.get('http://localhost:8181/savingAccount/findById/'+this.$route.query.account_id).then(function (resp){
      _this.ruleForm = resp.data;
    })
  },
  data() {
    return {
      ruleForm: {
        account_id: '',
        bank_name: '',
        client_id: '',
        balance: '',
        start_time: '',
        rate: '',
        currency_type: '',
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
      }
    }
  }
}
</script>