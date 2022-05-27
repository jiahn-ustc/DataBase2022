<template>
  <div>
    <h1>支行信息</h1>
    <el-table
        :data="tableData"
        border
        style="width: 100%"
        >
      <el-table-column
          fixed
          prop="bank_name"
          label="支行名"
          width="200">
      </el-table-column>
      <el-table-column
          prop="city"
          label="所在城市"
          width="120">
      </el-table-column>
      <el-table-column
          prop="amount"
          label="资产(单位：元)"
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
    <h1>业务统计</h1>
    <h2>储蓄账户</h2>
    <h3>按年统计</h3>
    <el-table
        :data="savingTableYear"
        border
        style="width: 100%"
    >
      <el-table-column
          fixed
          prop="bank_name"
          label="支行名"
          width="200">
      </el-table-column>
      <el-table-column
          prop="year"
          label="年份"
          width="120">
      </el-table-column>
      <el-table-column
          prop="amount"
          label="总金额"
          width="120">
      </el-table-column>
      <el-table-column
          prop="numClients"
          label="用户数"
          width="120">
      </el-table-column>

    </el-table>


    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="s_year_size"
        :total="s_year_total"
        @current-change="s_year_page"
        >
    </el-pagination>
    <h3>按季统计</h3>
    <el-table
        :data="savingTableSeason"
        border
        style="width: 100%"
    >
      <el-table-column
          fixed
          prop="bank_name"
          label="支行名"
          width="200">
      </el-table-column>
      <el-table-column
          prop="yearWithSeason"
          label="年份与季节"
          width="120">
      </el-table-column>
      <el-table-column
          prop="amount"
          label="总金额"
          width="120">
      </el-table-column>
      <el-table-column
          prop="numClients"
          label="用户数"
          width="120">
      </el-table-column>

    </el-table>


    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="s_season_size"
        :total="s_season_total"
        @current-change="s_season_page"
    >
    </el-pagination>
    <h3>按月统计</h3>
    <el-table
        :data="savingTableMonth"
        border
        style="width: 100%"
    >
      <el-table-column
          fixed
          prop="bank_name"
          label="支行名"
          width="200">
      </el-table-column>
      <el-table-column
          prop="yearWithMonth"
          label="年份与月份"
          width="120">
      </el-table-column>
      <el-table-column
          prop="amount"
          label="总金额"
          width="120">
      </el-table-column>
      <el-table-column
          prop="numClients"
          label="用户数"
          width="120">
      </el-table-column>

    </el-table>


    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="s_month_size"
        :total="s_month_total"
        @current-change="s_month_page"
    >
    </el-pagination>

    <h2>贷款</h2>
    <h3>按年统计</h3>
    <el-table
        :data="checkTableYear"
        border
        style="width: 100%"
    >
      <el-table-column
          fixed
          prop="bank_name"
          label="支行名"
          width="200">
      </el-table-column>
      <el-table-column
          prop="year"
          label="年份"
          width="120">
      </el-table-column>
      <el-table-column
          prop="amount"
          label="总金额"
          width="120">
      </el-table-column>
      <el-table-column
          prop="numClients"
          label="用户数"
          width="120">
      </el-table-column>

    </el-table>


    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="c_year_size"
        :total="c_year_total"
        @current-change="c_year_page"
    >
    </el-pagination>
    <h3>按季统计</h3>
    <el-table
        :data="checkTableSeason"
        border
        style="width: 100%"
    >
      <el-table-column
          fixed
          prop="bank_name"
          label="支行名"
          width="200">
      </el-table-column>
      <el-table-column
          prop="yearWithSeason"
          label="年份与季节"
          width="120">
      </el-table-column>
      <el-table-column
          prop="amount"
          label="总金额"
          width="120">
      </el-table-column>
      <el-table-column
          prop="numClients"
          label="用户数"
          width="120">
      </el-table-column>

    </el-table>


    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="c_season_size"
        :total="c_season_total"
        @current-change="c_season_page"
    >
    </el-pagination>
    <h3>按月统计</h3>
    <el-table
        :data="checkTableMonth"
        border
        style="width: 100%"
    >
      <el-table-column
          fixed
          prop="bank_name"
          label="支行名"
          width="200">
      </el-table-column>
      <el-table-column
          prop="yearWithMonth"
          label="年份与月份"
          width="120">
      </el-table-column>
      <el-table-column
          prop="amount"
          label="总金额"
          width="120">
      </el-table-column>
      <el-table-column
          prop="numClients"
          label="用户数"
          width="120">
      </el-table-column>

    </el-table>


    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="c_month_size"
        :total="c_month_total"
        @current-change="c_month_page"
    >
    </el-pagination>



  </div>
</template>

<script>
export default {
  methods: {
    page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/bank/findAll/'+currentPage+'/'+_this.size+'').then(function (resp){
        _this.tableData = resp.data.content

        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size
        //console.log(resp)
      })
    },
    s_year_page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/bank/findSavingYear/'+currentPage+'/'+_this.s_year_size+'').then(function (resp){
        _this.savingTableYear = resp.data.statisticsByYears


        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size
        //console.log(resp)
      })
    },
    s_season_page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/bank/findSavingSeason/'+currentPage+'/'+_this.s_season_size+'').then(function (resp){
        _this.savingTableSeason = resp.data.statisticsBySeasons


        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size
        //console.log(resp)
      })
    },
    s_month_page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/bank/findSavingMonth/'+currentPage+'/'+_this.s_month_size+'').then(function (resp){
        _this.savingTableMonth = resp.data.statisticsByMonths


        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size
        //console.log(resp)
      })
    },
    c_year_page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/bank/findLoanPayYear/'+currentPage+'/'+_this.c_year_size+'').then(function (resp){
        _this.checkTableYear = resp.data.statisticsByYears


        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size
        //console.log(resp)
      })
    },
    c_season_page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/bank/findLoanPaySeason/'+currentPage+'/'+_this.c_season_size+'').then(function (resp){
        _this.checkTableSeason = resp.data.statisticsBySeasons


        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size
        //console.log(resp)
      })
    },
    c_month_page(currentPage){
      const _this = this
      axios.get('http://localhost:8181/bank/findLoanPayMonth/'+currentPage+'/'+_this.c_month_size+'').then(function (resp){
        _this.checkTableMonth = resp.data.statisticsByMonths


        //_this.total = resp.data.totalElements
        //_this.size = resp.data.size
        //console.log(resp)
      })
    }
  },
  created() {
    const _this = this
    axios.get('http://localhost:8181/bank/findAll/1/'+_this.size+'').then(function (resp){
      _this.tableData = resp.data.content
      _this.total = resp.data.totalElements

    })
    axios.get('http://localhost:8181/bank/findSavingYear/1/'+_this.s_year_size+'').then(function (resp){

      _this.savingTableYear = resp.data.statisticsByYears
      _this.s_year_total = resp.data.totalElements

    })
    axios.get('http://localhost:8181/bank/findSavingSeason/1/'+_this.s_season_size+'').then(function (resp){

      _this.savingTableSeason = resp.data.statisticsBySeasons
      _this.s_season_total = resp.data.totalElements

    })
    axios.get('http://localhost:8181/bank/findSavingMonth/1/'+_this.s_month_size+'').then(function (resp){

      _this.savingTableMonth = resp.data.statisticsByMonths
      _this.s_month_total = resp.data.totalElements

    })
    axios.get('http://localhost:8181/bank/findLoanPayYear/1/'+_this.c_year_size+'').then(function (resp){

      _this.checkTableYear = resp.data.statisticsByYears
      _this.c_year_total = resp.data.totalElements

    })
    axios.get('http://localhost:8181/bank/findLoanPaySeason/1/'+_this.c_season_size+'').then(function (resp){

      _this.checkTableSeason = resp.data.statisticsBySeasons
      _this.c_season_total = resp.data.totalElements

    })
    axios.get('http://localhost:8181/bank/findLoanPayMonth/1/'+_this.c_month_size+'').then(function (resp){

      _this.checkTableMonth = resp.data.statisticsByMonths
      _this.c_month_total = resp.data.totalElements

    })
  },
  data() {
    return {
      size: 6,
      total: null,
      s_year_size: 6,
      s_year_total: null,
      s_season_size: 10,
      s_season_total: null,
      s_month_size: 20,
      s_month_total: null,
      c_year_size: 10,
      c_year_total: null,
      c_season_size: 10,
      c_season_total: null,
      c_month_size: 10,
      c_month_total: null,
      tableData: [],
      savingTableYear: [],
      savingTableSeason: [],
      savingTableMonth: [],
      checkTableYear: [],
      checkTableSeason: [],
      checkTableMonth: []
    }
  }
}
</script>