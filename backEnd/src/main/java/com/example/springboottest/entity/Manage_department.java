package com.example.springboottest.entity;

import com.example.springboottest.TriggersId.Manage_departmentTriggersId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Data
@IdClass(Manage_departmentTriggersId.class)
@NoArgsConstructor
@AllArgsConstructor
public class Manage_department {
    @Id
    private String bank_name;
    @Id
    private String department_id;

    private String manager_id;

}
