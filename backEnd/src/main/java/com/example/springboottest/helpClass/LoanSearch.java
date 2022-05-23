package com.example.springboottest.helpClass;

import lombok.Data;

@Data
public class LoanSearch {
    private String loan_id;
    private String amount;
    private String amount_provided;
    private String loan_status;
    private String bank_name;
    private String return_data;
}
