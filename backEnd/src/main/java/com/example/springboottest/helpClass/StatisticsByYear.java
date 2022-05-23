package com.example.springboottest.helpClass;

import lombok.Data;

import java.util.Set;

@Data
public class StatisticsByYear {
    private String bank_name;
    private int year;
    private float amount;
    private int numClients;
    public Set<String> set;

}
