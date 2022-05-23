package com.example.springboottest.repository;

import com.example.springboottest.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void findAll(){
        List<Employee> employees = employeeRepository.findAll();
        for(Employee employee:employees)
            System.out.println(employee);
    }

}