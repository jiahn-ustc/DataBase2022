package com.example.springboottest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Check_accountRepositoryTest {
    @Autowired
    private Check_accountRepository check_accountRepository;

    @Test
    void findAll(){
        System.out.println(check_accountRepository.findAll());
    }

}