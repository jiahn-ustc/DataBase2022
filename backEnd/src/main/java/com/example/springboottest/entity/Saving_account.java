package com.example.springboottest.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Saving_account {
    @Id
    private String account_id;
    private String bank_name;
    private String client_id;
    private String balance;
    private String start_time;
    private String rate;
    private String currency_type;
    private String last_time;
}
