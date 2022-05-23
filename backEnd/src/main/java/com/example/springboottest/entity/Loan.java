package com.example.springboottest.entity;


import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Entity
public class Loan {
    @Id
    private String loan_id;

    private String bank_name;

    private String amount;
}
