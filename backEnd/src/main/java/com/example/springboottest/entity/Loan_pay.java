package com.example.springboottest.entity;

import com.example.springboottest.TriggersId.Loan_payTriggersId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Data
@IdClass(Loan_payTriggersId.class)
@NoArgsConstructor
@AllArgsConstructor
public class Loan_pay {

    private String bank_name;
    @Id
    private String loan_id;
    @Id
    private String pay_code;

    private String pay_date;
    private String pay_amount;
}
