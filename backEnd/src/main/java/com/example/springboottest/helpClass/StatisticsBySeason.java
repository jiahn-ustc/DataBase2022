package com.example.springboottest.helpClass;

import lombok.Data;

import java.util.Set;

@Data
public class StatisticsBySeason {
    private String bank_name;
    private String  yearWithSeason;
    private float amount;
    private int numClients;
    public Set<String> set;
}
