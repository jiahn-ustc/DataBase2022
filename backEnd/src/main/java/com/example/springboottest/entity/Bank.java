package com.example.springboottest.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Bank {
    @Id
    private String bank_name;
    private String city;
    private String amount;
}
