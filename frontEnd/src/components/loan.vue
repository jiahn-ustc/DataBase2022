<template>
  <div>
    <h1>贷款信息</h1>
    <el-table
        :data="tableData"
        border
        style="width: 100%"
    >
      <el-table-column
          fixed
          prop="loan_id"
          label="贷款号"
          width="120">
      </el-table-column>
      <el-table-column
          prop="bank_name"
          label="支行名"
          width="120">
      </el-table-column>
      <el-table-column
          prop="amount"
          label="总贷款额"
          width="120">
      </el-table-column>
      <el-table-column
          label="操作"
          width="100">
        <template slot-scope="scope">

          <el-button @click="deleteLoan(scope.row)" type="text" size="small">删除</el-button>
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




  </div>
</template>

<script>
export default {
  methods: {
    page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/loan/findAll/'+currentPage+'/'+_this.size+'').then(function (resp){
        _this.tableData = resp.data.content
        for(var i=0;i<_this.tableData.size;i++)
          _this.tableData[i].test = i;
        console.log(_this.tableData)
        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size
        //console.log(resp)
      })
    },
    deleteLoan(row){
      const _this = this
      axios.delete('http://localhost:8181/loan/deleteById/'+row.loan_id).then(function (resp){
        if(resp.data=="success")
        {
          _this.$alert(row.loan_id+" 删除成功",'消息',{
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


      })
    }
  },
  created() {
    const _this = this
    axios.get('http://localhost:8181/loan/findAll/1/'+_this.size+'').then(function (resp){
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
      tableData: []
    }
  }
}
</script>