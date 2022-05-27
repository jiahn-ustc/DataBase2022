<template>
  <el-form style="width: 30%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
  <el-form-item label="身份证号" >
    <el-input v-model="ruleForm.client_id" readOnly></el-input>
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
    <el-form-item label="联系人姓名" >
      <el-input v-model="ruleForm.contact_name"></el-input>
    </el-form-item>
    <el-form-item label="联系人电话" >
      <el-input v-model="ruleForm.contact_tel"></el-input>
    </el-form-item>
    <el-form-item label="联系人邮箱" >
      <el-input v-model="ruleForm.contact_tel"></el-input>
    </el-form-item>
    <el-form-item label="客户与联系人的关系" >
      <el-input v-model="ruleForm.contact"></el-input>
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
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post('http://localhost:8181/client/update',this.ruleForm).then(function (resp){
            //console.log(resp);
            //alert(resp.data);
            if(resp.data=='success')
            {
              _this.$alert(_this.ruleForm.name+" 修改成功",'消息',{
                confirmButtonText: '确定',
                callback: action => {
                  _this.$router.push('/clients');
                  window.location.reload()

                }
              })
            }
            else{
              _this.$alert(_this.ruleForm.name+" 修改失败,"+resp.data,'消息',{
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
    }
  },
  created() {
    //alert(this.$route.query.client_id)
    const _this = this
    axios.get('http://localhost:8181/client/findById/'+this.$route.query.client_id).then(function (resp){
      _this.ruleForm = resp.data;
    })
  },
  data() {
    return {

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
        tel: [
          { required: true, message: '请输入客户电话', trigger: 'blur' }
        ],
        address: [
          {  required: true, message: '请输入客户地址', trigger: 'blur' }
        ]
      }
    }
  }
}
</script>