package com.example.springboottest.repository;

import com.example.springboottest.entity.Account;
import com.example.springboottest.entity.Saving_account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Saving_accountRepositoryTest {
    @Autowired
    private Saving_accountRepository saving_accountRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void findAll(){
        List<Saving_account> saving_accounts = saving_accountRepository.findAll();
        for(Saving_account saving_account:saving_accounts)
            System.out.println(saving_account);
    }

    @Test
    void AccountFindAll(){
        List<Account> accounts = accountRepository.findAll();
        System.out.println(accounts);
    }

    @Test
    void time(){
        String str = "2019-07-28 22:39:13";
        String subStr = str.substring(0,4);
        Integer year = Integer.valueOf(subStr);
        if(year==2019)
            System.out.println(year);
    }

    @Test
    void Set(){
        Set<String> hs = new HashSet<>();
        hs.add("123");
        hs.add("456");
        hs.add("123");
        System.out.println("hs.size=");
        System.out.println(hs.size());
    }


}