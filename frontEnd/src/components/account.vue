<template>
  <div>
    <h1>所有账户信息</h1>
    <el-table
        :data="tableData"
        border
        style="width: 100%"
    >
      <el-table-column
          fixed
          prop="account_id"
          label="账户号"
          width="200">
      </el-table-column>
      <el-table-column
          prop="account_type"
          label="账户类型"
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




  </div>
</template>

<script>
export default {
  methods: {
    page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/account/findAll/'+currentPage+'/'+_this.size+'').then(function (resp){
        _this.tableData = resp.data.content
        for(var i=0;i<_this.tableData.size;i++)
          _this.tableData[i].test = i;
        console.log(_this.tableData)
        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size
        //console.log(resp)
      })
    }
  },
  created() {
    const _this = this
    axios.get('http://localhost:8181/account/findAll/1/'+_this.size+'').then(function (resp){
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
      size: 8,
      total: null,
      tableData: []
    }
  }
}
</script>