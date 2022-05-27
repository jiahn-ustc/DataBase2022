<template>
  <div>
    <h1>员工信息</h1>
    <el-table
        :data="tableData"
        border
        style="width: 100%">
      <el-table-column
          fixed
          prop="employee_id"
          label="身份证号"
          width="200">
      </el-table-column>
      <el-table-column
          prop="name"
          label="姓名"
          width="120">
      </el-table-column>
      <el-table-column
          prop="bank_name"
          label="所属支行"
          width="120">
      </el-table-column>
      <el-table-column
          prop="tel"
          label="电话"
          width="150">
      </el-table-column>
      <el-table-column
          prop="address"
          label="住址"
          width="150">
      </el-table-column>



    </el-table>
    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="size"
        :total="total"
        @current-change="page">
    </el-pagination>




  </div>
</template>

<script>
export default {
  methods: {
    page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/employee/findAll/'+currentPage+'/'+_this.size+'').then(function (resp){
        _this.tableData = resp.data.content
        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size

        console.log(resp)
      })
    }
  },
  created() {
    const _this = this
    axios.get('http://localhost:8181/employee/findAll/1/'+_this.size+'').then(function (resp){
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
      tableData: []
    }
  }
}
</script>