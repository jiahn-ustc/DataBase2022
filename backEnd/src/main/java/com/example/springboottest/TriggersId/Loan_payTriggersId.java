package com.example.springboottest.TriggersId;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Loan_payTriggersId implements Serializable {

    String loan_id;
    String pay_code;
}
