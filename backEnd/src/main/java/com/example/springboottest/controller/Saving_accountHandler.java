package com.example.springboottest.controller;

import com.example.springboottest.entity.Account;
import com.example.springboottest.entity.Saving_account;
import com.example.springboottest.repository.AccountRepository;
import com.example.springboottest.repository.BankRepository;
import com.example.springboottest.repository.ClientRepository;
import com.example.springboottest.repository.Saving_accountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savingAccount")
public class Saving_accountHandler {

    @Autowired
    private Saving_accountRepository saving_accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankRepository bankRepository;

    public static boolean isFloatNumber(String str){
        String reg = "^-?[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<Saving_account> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size)
    {
        Pageable pageable = PageRequest.of(page-1,size);
        return saving_accountRepository.findAll(pageable);
    }

    @PostMapping("/save")
    public String save(@RequestBody Saving_account saving_account){
        //System.out.println(saving_account);
        if(!clientRepository.findById(saving_account.getClient_id()).isPresent())
        {
            return "数据库中不存在该客户，请先创建该客户";
        }
        if(accountRepository.findById(saving_account.getAccount_id()).isPresent())
        {
            return "该账户号已被占用，请更换账户号";
        }
        if(!bankRepository.findById(saving_account.getBank_name()).isPresent())
        {
            return "没有这个支行，请检查支行名是否正确";
        }
        String client_id = saving_account.getClient_id();
        String bank_name = saving_account.getBank_name();
        List<Saving_account> saving_accounts = saving_accountRepository.findAll();
        for(Saving_account saving_account1:saving_accounts)
        {
            if(saving_account1.getClient_id().equals(client_id) && saving_account1.getBank_name().equals(bank_name))
                return "一个客户最多只能在同一个支行拥有一个储蓄账户";
        }

        if(saving_account.getCurrency_type().length()==0)
            saving_account.setCurrency_type("人民币");
        if(saving_account.getBalance().length()==0)
            saving_account.setBalance("0");
        if(saving_account.getRate().length()==0)
            saving_account.setRate("0.01");
        if(saving_account.getBalance().length()>0)
        {
            if(!isFloatNumber(saving_account.getBalance()))
                return "余额必须为浮点型数据";
            else {
                float balance = Float.parseFloat(saving_account.getBalance());
                if(balance<0)
                    return "余额不允许为负数";
            }
        }


        Account account = new Account();
        account.setAccount_id(saving_account.getAccount_id());
        account.setAccount_type("储蓄账户");
        Account account1 = accountRepository.save(account);
        Saving_account saving_account1 = saving_accountRepository.save(saving_account);
        if(saving_account1 != null ){

            return "success";
        }
        else
            return "error";


    }
    @GetMapping("/findById/{account_id}")
    public Saving_account findById(@PathVariable("account_id") String account_id){
        if(saving_accountRepository.findById(account_id).isPresent())
        {
            return saving_accountRepository.findById(account_id).get();
        }
        else {
            Saving_account saving_account = new Saving_account();
            saving_account.setAccount_id(account_id);
            return saving_account;
        }
    }

    @PostMapping("/update")
    public String update(@RequestBody Saving_account saving_account){
        if(!clientRepository.findById(saving_account.getClient_id()).isPresent())
        {
            return "数据库中不存在该客户，请先创建该客户";
        }
        if(!bankRepository.findById(saving_account.getBank_name()).isPresent())
        {
            return "没有这个支行，请检查支行名是否正确";
        }
        if(saving_account.getCurrency_type().length()==0)
            saving_account.setCurrency_type("人民币");
        if(saving_account.getBalance().length()==0)
            saving_account.setBalance("0");
        if(saving_account.getRate().length()==0)
            saving_account.setRate("0");
        if(saving_account.getBalance().length()>0)
        {
            if(!isFloatNumber(saving_account.getBalance()))
                return "余额必须为浮点型数据";
            else {
                float balance = Float.parseFloat(saving_account.getBalance());
                if(balance<0)
                    return "余额不允许为负数";
            }
        }
        Saving_account saving_account1= saving_accountRepository.save(saving_account);
        if(saving_account1 != null)
        {
            return "success";
        }
        else
            return "error";

    }
    @DeleteMapping("/deleteById/{account_id}")
    public String deleteById(@PathVariable("account_id") String account_id){
        saving_accountRepository.deleteById(account_id);
        accountRepository.deleteById(account_id);
        return "success";
    }
}
