package com.example.springboottest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Client_get_loanRepositoryTest {

    @Autowired
    private Client_get_loanRepository client_get_loanRepository;

    @Test
    void findAll(){
        System.out.println(client_get_loanRepository.findAll());
    }

}