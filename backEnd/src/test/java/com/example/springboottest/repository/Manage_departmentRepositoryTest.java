package com.example.springboottest.repository;


import com.example.springboottest.TriggersId.Manage_departmentTriggersId;
import com.example.springboottest.entity.Department;
import com.example.springboottest.entity.Employee;
import com.example.springboottest.entity.Manage_department;

import com.example.springboottest.helpClass.DetialManageDepartment;

import com.example.springboottest.helpClass.haveTotalElementsDepartment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Manage_departmentRepositoryTest {
    @Autowired
    private Manage_departmentRepository manage_departmentRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void findAll(){
        List<Manage_department> managedepartments = manage_departmentRepository.findAll();
        for(Manage_department managedepartment:managedepartments)
            System.out.println(managedepartment);
    }
    @Test
    void findDetialAll(){
        Pageable pageable = PageRequest.of(0,2);
        Page<Manage_department> managedepartmentsPage = manage_departmentRepository.findAll(pageable);
        List<Manage_department> managedepartments = managedepartmentsPage.getContent();

        haveTotalElementsDepartment H = new haveTotalElementsDepartment();
        H.totalElements=managedepartmentsPage.getTotalElements();
        H.detialManageDepartmentList = new ArrayList<>();

        for(int i=0;i<managedepartments.size();i++)
        {
            DetialManageDepartment detial = new DetialManageDepartment();
            Manage_department managedepartment = managedepartments.get(i);
            detial.setManage_department(managedepartment);
            Employee employee = employeeRepository.findById(managedepartment.getManager_id()).get();
            Department department = departmentRepository.findById(managedepartment.getDepartment_id()).get();
            detial.setEmployee(employee);
            detial.setDepartment(department);
            H.detialManageDepartmentList.add(detial);
        }
        System.out.println(H.totalElements);
    }

    @Test
    void findById(){
        Manage_departmentTriggersId managedepartmentTriggersId = new Manage_departmentTriggersId();
        managedepartmentTriggersId.setBank_name("中国银行");
        managedepartmentTriggersId.setDepartment_id("1901");

        Manage_department managedepartment= manage_departmentRepository.findById(managedepartmentTriggersId).get();
        System.out.println(managedepartment);
    }

}