import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import clients from '../components/clients'
import bank from '../components/bank'
import checkAccount from "../components/checkAccount"
import clientEmployee from "../components/clientEmployee"
import clientGetLoan from "../components/clientGetLoan"
import clientVisitAccount from "@/components/clientVisitAccount";
import department from "@/components/department";
import employees from "@/components/employees";
import loan from "@/components/loan";
import loanPay from "@/components/loanPay";
import savingAccount from "@/components/savingAccount";
import  App from '../App'
import index from '../views/index'
import updateClient from "@/components/updateClient";
import account from "@/components/account";
import updateSavingAccount from "@/components/updateSavingAccount";
import updateCheckAccount from "@/components/updateCheckAccount";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '客户管理',
    component: index,
    show: true,
    children:[
      {
        path: '/clients',
        name: '客户信息',
        component: clients,

      },
      {
        path: '/clientEmployee',
        name: '客户员工对接',
        component: clientEmployee
      }

    ]
  },
  {
    path: '/updateClient',
    name: '修改客户',
    show: false,
    component: updateClient
  },
  {
    path: '/updateSavingAccount',
    name: '修改储蓄账户',
    show: false,
    component: updateSavingAccount
  },
  {
    path: '/updateCheckAccount',
    name: '修改储蓄账户',
    show: false,
    component: updateCheckAccount
  },
  {
    path: '/Account',
    name: '账户',
    component: index,
    show: true,
    children:[
      {
        path: '/savingAccount',
        name: '储蓄账户',
        component: savingAccount
      },
      {
        path: '/checkAccount',
        name: '支票账户',
        component: checkAccount
      },
      {
        path: '/allAccounts',
        name: '所有账户信息',
        component: account
      }
    ]
  },
  {
    path: '/Loan',
    name: '贷款',
    component: index,
    show: true,
    children:[
      {
        path: '/clientGetLoan',
        name: '客户办理贷款',
        component: clientGetLoan
      },
      {
        path: '/loan',
        name: '贷款信息',
        component: loan
      },
      {
        path: '/loamPay',
        name: '贷款发放',
        component: loanPay
      }
    ]
  },
  {
    path: '/Bank',
    name: '支行',
    component: index,
    show: true,
    children:[
      {
        path:'/employees',
        name:'员工信息',
        component: employees
      },
      {
        path:'/department',
        name:'部门信息',
        component: department
      },
      {
        path:'/bank',
        name:'支行信息',
        component: bank
      }
    ]
  }



]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
