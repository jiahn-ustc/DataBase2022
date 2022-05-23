package com.example.springboottest.controller;

import com.example.springboottest.entity.Department;
import com.example.springboottest.entity.Employee;
import com.example.springboottest.helpClass.DetialManageDepartment;
import com.example.springboottest.helpClass.haveTotalElementsDepartment;
import com.example.springboottest.entity.Manage_department;
import com.example.springboottest.repository.DepartmentRepository;
import com.example.springboottest.repository.EmployeeRepository;
import com.example.springboottest.repository.Manage_departmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/manageDepartment")
public class Manage_departmentHandler {

    @Autowired
    private Manage_departmentRepository Manage_departmentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/findAll/{page}/{size}")
    public Page<Manage_department> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return Manage_departmentRepository.findAll(pageable);
    }

    @GetMapping("/findDetialAll/{page}/{size}")
    public haveTotalElementsDepartment findDetialAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Manage_department> Manage_departmentsPage = Manage_departmentRepository.findAll(pageable);
        List<Manage_department> Manage_departments = Manage_departmentsPage.getContent();
        haveTotalElementsDepartment H = new haveTotalElementsDepartment();
        H.totalElements=Manage_departmentsPage.getTotalElements();
        H.detialManageDepartmentList = new ArrayList<>();

        for(int i=0;i<Manage_departments.size();i++)
        {
            DetialManageDepartment detialManageDepartment = new DetialManageDepartment();
            Manage_department Manage_department = Manage_departments.get(i);
            detialManageDepartment.setManage_department(Manage_department);
            Employee employee = employeeRepository.findById(Manage_department.getManager_id()).get();
            Department department = departmentRepository.findById(Manage_department.getDepartment_id()).get();
            detialManageDepartment.setEmployee(employee);
            detialManageDepartment.setDepartment(department);
            H.detialManageDepartmentList.add(detialManageDepartment);
        }
        return H;

    }
}
