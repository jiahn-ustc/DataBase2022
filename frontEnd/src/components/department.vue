<template>
  <div>
    <h1>部门信息</h1>
    <el-table
        :data="tableData"
        border
        style="width: 100%"
    >
      <el-table-column
          fixed
          prop="department_id"
          label="部门号"
          width="200">
      </el-table-column>
      <el-table-column
          prop="name"
          label="部门名称"
          width="120">
      </el-table-column>
      <el-table-column
          prop="type"
          label="部门类型"
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

    <h1>各支行部门管理信息</h1>
    <el-table
        :data="manageTableData"
        border
        style="width: 100%"
    >
      <el-table-column
          fixed
          prop="manage_department.bank_name"
          label="支行名"
          width="200">
      </el-table-column>
      <el-table-column
          prop="department.name"
          label="部门名称"
          width="120">
      </el-table-column>
      <el-table-column
          prop="employee.employee_id"
          label="部门经理身份证号"
          width="200">
      </el-table-column>
      <el-table-column
          prop="employee.name"
          label="部门经理姓名"
          width="120">
      </el-table-column>

    </el-table>

    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="manage_size"
        :total="manage_total"
        @current-change="manage_page">
    </el-pagination>



  </div>
</template>

<script>
export default {
  methods: {
    page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/department/findAll/'+currentPage+'/'+_this.size+'').then(function (resp){
        _this.tableData = resp.data.content
        /*for(var i=0;i<_this.tableData.size;i++)
          _this.tableData[i].test = i;*/
        console.log(_this.tableData)
        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size
        //console.log(resp)
      })
    },
    manage_page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/manageDepartment/findDetialAll/'+currentPage+'/'+_this.manage_size+'').then(function (resp){
       // _this.manage_total = resp.data.totalElements;
        _this.manageTableData = resp.data.detialManageDepartmentList

        /*for(var i=0;i<_this.tableData.size;i++)
          _this.tableData[i].test = i;*/
        //console.log(_this.manageTableData)
        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size
        //console.log(resp)
      })
    }
  },
  created() {
    const _this = this
    axios.get('http://localhost:8181/department/findAll/1/'+_this.size+'').then(function (resp){
      _this.tableData = resp.data.content
      _this.total = resp.data.totalElements

    })
    axios.get('http://localhost:8181/manageDepartment/findDetialAll/1/'+_this.manage_size+'').then(function (resp){
      //console.log(resp)
      //alert(resp.data.totalElements)
      _this.manage_total = resp.data.totalElements;
      _this.manageTableData = resp.data.detialManageDepartmentList
      console.log(_this.manageTableData)


      /*for(var i=0;i<_this.manageTableData.length;i++)
      {
        _this.temp_id = resp.data.content[i].department_id
        axios.get('http://localhost:8181/department/findById/'+_this.temp_id).then(function (resp1){

          _this.manageTableData[i].department_name = resp1.data.name;
          console.log(_this.manageTableData[i])
        })
      }*/


    })





  },
  data() {
    return {
      size: 2,
      total: null,
      temp_id: '',
      manage_size: 4,
      manage_total: null,
      tableData: [],
      manageTableData: [
      ]
    }
  }
}
</script>