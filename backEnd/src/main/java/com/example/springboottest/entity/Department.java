package com.example.springboottest.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Department {
    @Id
    private String department_id;
    private String name;
    private String type;
}
