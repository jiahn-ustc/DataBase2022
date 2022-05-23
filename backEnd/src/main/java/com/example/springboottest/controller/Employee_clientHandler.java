package com.example.springboottest.controller;

import com.example.springboottest.entity.Employee_client;
import com.example.springboottest.repository.Employee_clientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employeeClient")
public class Employee_clientHandler {
    @Autowired
    private Employee_clientRepository employee_clientRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Employee_client> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return employee_clientRepository.findAll(pageable);
    }
}
