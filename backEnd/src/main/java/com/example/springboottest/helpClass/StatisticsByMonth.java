package com.example.springboottest.helpClass;

import lombok.Data;

import java.util.Set;

@Data
public class StatisticsByMonth {
    private String bank_name;
    private String  yearWithMonth;
    private float amount;
    private int numClients;
    public Set<String> set;
}
