package com.example.springboottest.controller;

import com.example.springboottest.entity.Employee;
import com.example.springboottest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeHandler {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Employee> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return employeeRepository.findAll(pageable);
    }
}
