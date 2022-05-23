package com.example.springboottest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class Employee_clientRepositoryTest {
    @Autowired
    private  Employee_clientRepository employee_clientRepository;
    @Test
    void findAll(){
        System.out.println(employee_clientRepository.findAll());
    }

}