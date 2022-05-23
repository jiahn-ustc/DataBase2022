package com.example.springboottest.repository;

import com.example.springboottest.entity.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepartmentRepositoryTest {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void findAll(){
        List<Department> departments = departmentRepository.findAll();
        for(Department department:departments)
            System.out.println(department);
    }

}