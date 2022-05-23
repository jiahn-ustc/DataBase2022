package com.example.springboottest.helpClass;

import com.example.springboottest.entity.Department;
import com.example.springboottest.entity.Employee;
import com.example.springboottest.entity.Manage_department;
import lombok.Data;

@Data
public class DetialManageDepartment {
   private Manage_department manage_department;
   private Employee employee;
   private Department department;
}
