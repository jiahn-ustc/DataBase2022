package com.example.springboottest.entity;


import com.example.springboottest.TriggersId.Employee_clientTriggersId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass(Employee_clientTriggersId.class)
@NoArgsConstructor
@AllArgsConstructor
public class Employee_client {
    @Id
    private String client_id;
    @Id
    private String employee_id;
    private String type;
}
