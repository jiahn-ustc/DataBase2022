package com.example.springboottest.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Employee {
    @Id
    private String employee_id;
    private String name;
    private String bank_name;
    private String tel;
    private String address;
}
