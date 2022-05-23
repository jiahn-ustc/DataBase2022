package com.example.springboottest.controller;

import com.example.springboottest.entity.Account;
import com.example.springboottest.entity.Check_account;

import com.example.springboottest.entity.Saving_account;
import com.example.springboottest.repository.AccountRepository;
import com.example.springboottest.repository.BankRepository;
import com.example.springboottest.repository.Check_accountRepository;
import com.example.springboottest.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkAccount")
public class Check_accountHandler {

    @Autowired
    private Check_accountRepository check_accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankRepository bankRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Check_account> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size)
    {
        Pageable pageable = PageRequest.of(page-1,size);
        return check_accountRepository.findAll(pageable);
    }

    @PostMapping("/save")
    public String save(@RequestBody Check_account check_account){

        if(!clientRepository.findById(check_account.getClient_id()).isPresent())
        {
            return "数据库中不存在该客户，请先创建该客户";
        }
        if(accountRepository.findById(check_account.getAccount_id()).isPresent())
        {
            return "该账户号已被占用，请更换账户号";
        }
        if(!bankRepository.findById(check_account.getBank_name()).isPresent())
        {
            return "没有这个支行，请检查支行名是否正确";
        }


        if(check_account.getBalance().length()==0)
            check_account.setBalance("0");
        if(check_account.getOverdraft().length()==0)
            check_account.setOverdraft("0");


        Account account = new Account();
        account.setAccount_id(check_account.getAccount_id());
        account.setAccount_type("储蓄账户");
        Account account1 = accountRepository.save(account);
        Check_account check_account1 = check_accountRepository.save(check_account);
        if(check_account1 != null ){

            return "success";
        }
        else
            return "error";


    }
    @GetMapping("/findById/{account_id}")
    public Check_account findById(@PathVariable("account_id") String account_id){
        if(check_accountRepository.findById(account_id).isPresent())
        {
            return check_accountRepository.findById(account_id).get();
        }
        else {
            Check_account check_account = new Check_account();
            check_account.setAccount_id(account_id);
            return check_account;
        }
    }

    @PostMapping("/update")
    public String update(@RequestBody Check_account check_account){
        if(!clientRepository.findById(check_account.getClient_id()).isPresent())
        {
            return "数据库中不存在该客户，请先创建该客户";
        }
        if(!bankRepository.findById(check_account.getBank_name()).isPresent())
        {
            return "没有这个支行，请检查支行名是否正确";
        }

        if(check_account.getBalance().length()==0)
            check_account.setBalance("0");
        if(check_account.getOverdraft().length()==0)
            check_account.setOverdraft("0");
        Check_account check_account1= check_accountRepository.save(check_account);
        if(check_account1 != null)
        {
            return "success";
        }
        else
            return "error";

    }
    @DeleteMapping("/deleteById/{account_id}")
    public String deleteById(@PathVariable("account_id") String account_id){
        check_accountRepository.deleteById(account_id);
        accountRepository.deleteById(account_id);
        return "success";
    }
}
