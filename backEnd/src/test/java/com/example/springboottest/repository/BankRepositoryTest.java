package com.example.springboottest.repository;

import com.example.springboottest.entity.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankRepositoryTest {
    @Autowired
    private BankRepository bankRepository;

    @Test
    void findAll(){
        List<Bank> banks = bankRepository.findAll();
        for(Bank bank:banks)
            System.out.println(bank);
    }

}