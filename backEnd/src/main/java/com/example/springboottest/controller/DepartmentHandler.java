package com.example.springboottest.controller;

import com.example.springboottest.entity.Department;
import com.example.springboottest.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentHandler {
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Department> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return departmentRepository.findAll(pageable);
    }

    @GetMapping("/findById/{department_id}")
    public Department findById(@PathVariable("department_id") String department_id){
        if(departmentRepository.findById(department_id).isPresent())
        {
            return departmentRepository.findById(department_id).get();
        }
        else{
            Department department = new Department();
            department.setDepartment_id(department_id);
            return department;
        }
    }
}
