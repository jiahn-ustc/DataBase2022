package com.example.springboottest.helpClass;

import lombok.Data;

@Data
public class Client_get_loanWithAmount {
    private String client_id;
    private String bank_name;
    private String loan_id;
    private String amount;
}
