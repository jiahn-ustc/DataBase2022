package com.example.springboottest.controller;

import com.example.springboottest.entity.Bank;
import com.example.springboottest.entity.Check_account;
import com.example.springboottest.entity.Saving_account;
import com.example.springboottest.helpClass.*;
import com.example.springboottest.repository.BankRepository;
import com.example.springboottest.repository.Check_accountRepository;
import com.example.springboottest.repository.Saving_accountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/bank")
public class BankHandler {
    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private Saving_accountRepository saving_accountRepository;

    @Autowired
    private Check_accountRepository check_accountRepository;

    public int getYear(String input)
    {
        return Integer.valueOf(input.substring(0,4));
    }
    public int getMonth(String input)
    {
        return Integer.valueOf(input.substring(5,7));
    }
    public int getSeason(String input)
    {
        int month = Integer.valueOf(input.substring(5,7));
        if(month>=3 && month<=5)
            return 0;
        if(month>=6 && month<=8)
            return 1;
        if(month>=9 && month<=11)
            return 2;
        if(month ==12 || (month>=1 && month<=2))
            return 3;
        return -1;
    }


    @GetMapping("/findAll/{page}/{size}")
    public Page<Bank> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return bankRepository.findAll(pageable);
    }

    @GetMapping("/findSavingYear")
    public List<StatisticsByYear>  findSavingYear(){
        List<StatisticsByYear> statisticsByYears = new ArrayList<>();
        List<Saving_account> saving_accounts = saving_accountRepository.findAll();
        List<Bank> banks = bankRepository.findAll();
        int min_year,max_year;
        Saving_account saving_account_initialize = saving_accounts.get(0);
        min_year = getYear(saving_account_initialize.getStart_time());
        max_year = min_year;

        for(Saving_account saving_account:saving_accounts)
        {
            int year = getYear(saving_account.getStart_time());
            if(year<min_year)
                min_year = year;
            if(year>max_year)
                max_year = year;
        }
        for (Bank bank : banks) {
            for (int i = min_year; i <= max_year; i++) {
                StatisticsByYear statisticsByYear = new StatisticsByYear();
                statisticsByYear.setYear(i);
                statisticsByYear.setAmount(0);
                statisticsByYear.setBank_name(bank.getBank_name());
                statisticsByYear.setNumClients(0);
                statisticsByYear.set = new HashSet<>();
                statisticsByYears.add(statisticsByYear);
            }
        }
        for(Saving_account saving_account:saving_accounts)
        {
            String bank_name = saving_account.getBank_name();
            int year = getYear(saving_account.getStart_time());
            int i;
            for(i=0;i< banks.size();i++)
            {
                if(banks.get(i).getBank_name().equals(bank_name))
                    break;
            }
            int index = year-min_year +i*(max_year-min_year+1);
            float amount = Float.parseFloat(saving_account.getBalance());
            float temp_account =statisticsByYears.get(index).getAmount();
            /*int temp_clients = statisticsByYears.get(index).getNumClients();
            statisticsByYears.get(index).setNumClients(temp_clients+1);*/
            statisticsByYears.get(index).set.add(saving_account.getClient_id());
            int temp_clients =  statisticsByYears.get(index).set.size();
            statisticsByYears.get(index).setNumClients(temp_clients+1);
            statisticsByYears.get(index).setAmount(temp_account+amount);
        }
        return statisticsByYears;
    }

    @GetMapping("/findSavingYear/{page}/{size}")
    public StaticsByYearWithTotalElements findSavingYearPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        List<StatisticsByYear> statisticsByYears = new ArrayList<>();
        List<Saving_account> saving_accounts = saving_accountRepository.findAll();
        List<Bank> banks = bankRepository.findAll();
        int min_year,max_year;
        Saving_account saving_account_initialize = saving_accounts.get(0);
        min_year = getYear(saving_account_initialize.getStart_time());
        max_year = min_year;

        for(Saving_account saving_account:saving_accounts)
        {
            int year = getYear(saving_account.getStart_time());
            if(year<min_year)
                min_year = year;
            if(year>max_year)
                max_year = year;
        }
        for (Bank bank : banks) {
            for (int i = min_year; i <= max_year; i++) {
                StatisticsByYear statisticsByYear = new StatisticsByYear();
                statisticsByYear.setYear(i);
                statisticsByYear.setAmount(0);
                statisticsByYear.setBank_name(bank.getBank_name());
                statisticsByYear.setNumClients(0);
                statisticsByYear.set = new HashSet<>();
                statisticsByYears.add(statisticsByYear);
            }
        }
        for(Saving_account saving_account:saving_accounts)
        {
            String bank_name = saving_account.getBank_name();
            int year = getYear(saving_account.getStart_time());
            int i;
            for(i=0;i< banks.size();i++)
            {
                if(banks.get(i).getBank_name().equals(bank_name))
                    break;
            }
            int index = year-min_year +i*(max_year-min_year+1);
            float amount = Float.parseFloat(saving_account.getBalance());
            float temp_account =statisticsByYears.get(index).getAmount();
            statisticsByYears.get(index).set.add(saving_account.getClient_id());
            int temp_clients = statisticsByYears.get(index).set.size();
            statisticsByYears.get(index).setNumClients(temp_clients);
            statisticsByYears.get(index).setAmount(temp_account+amount);
        }
        StaticsByYearWithTotalElements staticsByYearWithTotalElements = new StaticsByYearWithTotalElements();
        staticsByYearWithTotalElements.totalElements = statisticsByYears.size();
        staticsByYearWithTotalElements.statisticsByYears = new ArrayList<>();
        for(int j=size*(page-1);j< statisticsByYears.size() && j<size*page;j++)
        {
            staticsByYearWithTotalElements.statisticsByYears.add(statisticsByYears.get(j));
        }
        return staticsByYearWithTotalElements;
    }

    @GetMapping("/findSavingSeason/{page}/{size}")
    public StaticsBySeasonWithTotalElements findSavingSeasonPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        List<StatisticsBySeason> statisticsBySeasons = new ArrayList<>();
        List<Saving_account> saving_accounts = saving_accountRepository.findAll();
        List<Bank> banks = bankRepository.findAll();
        int min_year,max_year;
        Saving_account saving_account_initialize = saving_accounts.get(0);
        min_year = getYear(saving_account_initialize.getStart_time());
        max_year = min_year;
        String[] season={"春","夏","秋","冬"};

        for(Saving_account saving_account:saving_accounts)
        {
            int year = getYear(saving_account.getStart_time());
            if(year<min_year)
                min_year = year;
            if(year>max_year)
                max_year = year;
        }
        for (Bank bank : banks) {
            for (int i = min_year; i <= max_year; i++) {
                for(int j=0;j<=3;j++)
                {
                    StatisticsBySeason statisticsBySeason = new StatisticsBySeason();
                    statisticsBySeason.setAmount(0);
                    statisticsBySeason.setBank_name(bank.getBank_name());
                    statisticsBySeason.setNumClients(0);
                    statisticsBySeason.setYearWithSeason(String.valueOf(i)+"年"+season[j]);
                    statisticsBySeason.set=new HashSet<>();
                    statisticsBySeasons.add(statisticsBySeason);
                }
            }
        }
        for(Saving_account saving_account:saving_accounts)
        {
            String bank_name = saving_account.getBank_name();
            int year = getYear(saving_account.getStart_time());
            int temp_season = getSeason(saving_account.getStart_time());
            int i;
            for(i=0;i< banks.size();i++)
            {
                if(banks.get(i).getBank_name().equals(bank_name))
                    break;
            }

            int index = temp_season+(year-min_year)*4 +i*(max_year-min_year+1)*4;
            float amount = Float.parseFloat(saving_account.getBalance());
            float temp_account =statisticsBySeasons.get(index).getAmount();
            statisticsBySeasons.get(index).set.add(saving_account.getClient_id());
            int temp_clients = statisticsBySeasons.get(index).set.size();
            statisticsBySeasons.get(index).setNumClients(temp_clients);
            statisticsBySeasons.get(index).setAmount(temp_account+amount);
        }
        StaticsBySeasonWithTotalElements staticsBySeasonWithTotalElements = new StaticsBySeasonWithTotalElements();
        staticsBySeasonWithTotalElements.totalElements = statisticsBySeasons.size();
        staticsBySeasonWithTotalElements.statisticsBySeasons = new ArrayList<>();
        for(int j=size*(page-1);j< statisticsBySeasons.size() && j<size*page;j++)
        {
            staticsBySeasonWithTotalElements.statisticsBySeasons.add(statisticsBySeasons.get(j));
        }
        return staticsBySeasonWithTotalElements;
    }

    @GetMapping("/findSavingMonth/{page}/{size}")
    public StaticsByMonthWithTotalElements findSavingMonthPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        List<StatisticsByMonth> statisticsByMonths = new ArrayList<>();
        List<Saving_account> saving_accounts = saving_accountRepository.findAll();
        List<Bank> banks = bankRepository.findAll();
        int min_year,max_year;
        Saving_account saving_account_initialize = saving_accounts.get(0);
        min_year = getYear(saving_account_initialize.getStart_time());
        max_year = min_year;


        for(Saving_account saving_account:saving_accounts)
        {
            int year = getYear(saving_account.getStart_time());
            if(year<min_year)
                min_year = year;
            if(year>max_year)
                max_year = year;
        }
        for (Bank bank : banks) {
            for (int i = min_year; i <= max_year; i++) {
                for(int j=1;j<=12;j++)
                {
                    StatisticsByMonth statisticsByMonth = new StatisticsByMonth();
                    statisticsByMonth.setAmount(0);
                    statisticsByMonth.setBank_name(bank.getBank_name());
                    statisticsByMonth.setNumClients(0);
                    statisticsByMonth.set = new HashSet<>();
                    statisticsByMonth.setYearWithMonth(String.valueOf(i)+"年"+String.valueOf(j)+"月");
                    statisticsByMonths.add(statisticsByMonth);
                }
            }
        }
        for(Saving_account saving_account:saving_accounts)
        {
            String bank_name = saving_account.getBank_name();
            int year = getYear(saving_account.getStart_time());
            int month = getMonth(saving_account.getStart_time());
            int i;
            for(i=0;i< banks.size();i++)
            {
                if(banks.get(i).getBank_name().equals(bank_name))
                    break;
            }

            int index = month-1+(year-min_year)*12 +i*(max_year-min_year+1)*12;
            float amount = Float.parseFloat(saving_account.getBalance());
            float temp_account =statisticsByMonths.get(index).getAmount();
            statisticsByMonths.get(index).set.add(saving_account.getClient_id());
            int temp_clients = statisticsByMonths.get(index).set.size();
            statisticsByMonths.get(index).setNumClients(temp_clients);
            statisticsByMonths.get(index).setAmount(temp_account+amount);
        }
        StaticsByMonthWithTotalElements staticsByMonthWithTotalElements = new StaticsByMonthWithTotalElements();
        staticsByMonthWithTotalElements.totalElements = statisticsByMonths.size();
        staticsByMonthWithTotalElements.statisticsByMonths = new ArrayList<>();
        for(int j=size*(page-1);j< statisticsByMonths.size() && j<size*page;j++)
        {
            staticsByMonthWithTotalElements.statisticsByMonths.add(statisticsByMonths.get(j));
        }
        return staticsByMonthWithTotalElements;
    }


    @GetMapping("/findCheckYear/{page}/{size}")
    public StaticsByYearWithTotalElements findCheckYearPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        List<StatisticsByYear> statisticsByYears = new ArrayList<>();
        List<Check_account> check_accounts = check_accountRepository.findAll();
        List<Bank> banks = bankRepository.findAll();
        int min_year,max_year;
        Check_account check_account_initialize = check_accounts.get(0);
        min_year = getYear(check_account_initialize.getStart_time());
        max_year = min_year;

        for(Check_account check_account:check_accounts)
        {
            int year = getYear(check_account.getStart_time());
            if(year<min_year)
                min_year = year;
            if(year>max_year)
                max_year = year;
        }
        for (Bank bank : banks) {
            for (int i = min_year; i <= max_year; i++) {
                StatisticsByYear statisticsByYear = new StatisticsByYear();
                statisticsByYear.setYear(i);
                statisticsByYear.setAmount(0);
                statisticsByYear.setBank_name(bank.getBank_name());
                statisticsByYear.setNumClients(0);
                statisticsByYear.set = new HashSet<>();
                statisticsByYears.add(statisticsByYear);
            }
        }
        for(Check_account check_account:check_accounts)
        {
            String bank_name = check_account.getBank_name();
            int year = getYear(check_account.getStart_time());
            int i;
            for(i=0;i< banks.size();i++)
            {
                if(banks.get(i).getBank_name().equals(bank_name))
                    break;
            }
            int index = year-min_year +i*(max_year-min_year+1);
            float amount = Float.parseFloat(check_account.getBalance());
            float temp_account =statisticsByYears.get(index).getAmount();
            statisticsByYears.get(index).set.add(check_account.getClient_id());
            int temp_clients = statisticsByYears.get(index).set.size();
            statisticsByYears.get(index).setNumClients(temp_clients);
            statisticsByYears.get(index).setAmount(temp_account+amount);
        }
        StaticsByYearWithTotalElements staticsByYearWithTotalElements = new StaticsByYearWithTotalElements();
        staticsByYearWithTotalElements.totalElements = statisticsByYears.size();
        staticsByYearWithTotalElements.statisticsByYears = new ArrayList<>();
        for(int j=size*(page-1);j< statisticsByYears.size() && j<size*page;j++)
        {
            staticsByYearWithTotalElements.statisticsByYears.add(statisticsByYears.get(j));
        }
        return staticsByYearWithTotalElements;
    }

    @GetMapping("/findCheckSeason/{page}/{size}")
    public StaticsBySeasonWithTotalElements findCheckSeasonPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        List<StatisticsBySeason> statisticsBySeasons = new ArrayList<>();
        List<Check_account> check_accounts = check_accountRepository.findAll();
        List<Bank> banks = bankRepository.findAll();
        int min_year,max_year;
        Check_account check_account_initialize = check_accounts.get(0);
        min_year = getYear(check_account_initialize.getStart_time());
        max_year = min_year;
        String[] season={"春","夏","秋","冬"};

        for(Check_account check_account:check_accounts)
        {
            int year = getYear(check_account.getStart_time());
            if(year<min_year)
                min_year = year;
            if(year>max_year)
                max_year = year;
        }
        for (Bank bank : banks) {
            for (int i = min_year; i <= max_year; i++) {
                for(int j=0;j<=3;j++)
                {
                    StatisticsBySeason statisticsBySeason = new StatisticsBySeason();
                    statisticsBySeason.setAmount(0);
                    statisticsBySeason.setBank_name(bank.getBank_name());
                    statisticsBySeason.setNumClients(0);
                    statisticsBySeason.set = new HashSet<>();
                    statisticsBySeason.setYearWithSeason(String.valueOf(i)+"年"+season[j]);
                    statisticsBySeasons.add(statisticsBySeason);
                }
            }
        }
        for(Check_account check_account:check_accounts)
        {
            String bank_name = check_account.getBank_name();
            int year = getYear(check_account.getStart_time());
            int temp_season = getSeason(check_account.getStart_time());
            int i;
            for(i=0;i< banks.size();i++)
            {
                if(banks.get(i).getBank_name().equals(bank_name))
                    break;
            }

            int index = temp_season+(year-min_year)*4 +i*(max_year-min_year+1)*4;
            float amount = Float.parseFloat(check_account.getBalance());
            float temp_account =statisticsBySeasons.get(index).getAmount();
            statisticsBySeasons.get(index).set.add(check_account.getClient_id());
            int temp_clients = statisticsBySeasons.get(index).set.size();
            statisticsBySeasons.get(index).setNumClients(temp_clients);
            statisticsBySeasons.get(index).setAmount(temp_account+amount);
        }
        StaticsBySeasonWithTotalElements staticsBySeasonWithTotalElements = new StaticsBySeasonWithTotalElements();
        staticsBySeasonWithTotalElements.totalElements = statisticsBySeasons.size();
        staticsBySeasonWithTotalElements.statisticsBySeasons = new ArrayList<>();
        for(int j=size*(page-1);j< statisticsBySeasons.size() && j<size*page;j++)
        {
            staticsBySeasonWithTotalElements.statisticsBySeasons.add(statisticsBySeasons.get(j));
        }
        return staticsBySeasonWithTotalElements;
    }

    @GetMapping("/findCheckMonth/{page}/{size}")
    public StaticsByMonthWithTotalElements findCheckMonthPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        List<StatisticsByMonth> statisticsByMonths = new ArrayList<>();
        List<Check_account> check_accounts = check_accountRepository.findAll();
        List<Bank> banks = bankRepository.findAll();
        int min_year,max_year;
        Check_account check_account_initialize = check_accounts.get(0);
        min_year = getYear(check_account_initialize.getStart_time());
        max_year = min_year;


        for(Check_account check_account:check_accounts)
        {
            int year = getYear(check_account.getStart_time());
            if(year<min_year)
                min_year = year;
            if(year>max_year)
                max_year = year;
        }
        for (Bank bank : banks) {
            for (int i = min_year; i <= max_year; i++) {
                for(int j=1;j<=12;j++)
                {
                    StatisticsByMonth statisticsByMonth = new StatisticsByMonth();
                    statisticsByMonth.setAmount(0);
                    statisticsByMonth.setBank_name(bank.getBank_name());
                    statisticsByMonth.setNumClients(0);
                    statisticsByMonth.set = new HashSet<>();
                    statisticsByMonth.setYearWithMonth(String.valueOf(i)+"年"+String.valueOf(j)+"月");
                    statisticsByMonths.add(statisticsByMonth);
                }
            }
        }
        for(Check_account check_account:check_accounts)
        {
            String bank_name = check_account.getBank_name();
            int year = getYear(check_account.getStart_time());
            int month = getMonth(check_account.getStart_time());
            int i;
            for(i=0;i< banks.size();i++)
            {
                if(banks.get(i).getBank_name().equals(bank_name))
                    break;
            }

            int index = month-1+(year-min_year)*12 +i*(max_year-min_year+1)*12;
            float amount = Float.parseFloat(check_account.getBalance());
            float temp_account =statisticsByMonths.get(index).getAmount();
            statisticsByMonths.get(index).set.add(check_account.getClient_id());
            int temp_clients = statisticsByMonths.get(index).set.size();
            statisticsByMonths.get(index).setNumClients(temp_clients);
            statisticsByMonths.get(index).setAmount(temp_account+amount);
        }
        StaticsByMonthWithTotalElements staticsByMonthWithTotalElements = new StaticsByMonthWithTotalElements();
        staticsByMonthWithTotalElements.totalElements = statisticsByMonths.size();
        staticsByMonthWithTotalElements.statisticsByMonths = new ArrayList<>();
        for(int j=size*(page-1);j< statisticsByMonths.size() && j<size*page;j++)
        {
            staticsByMonthWithTotalElements.statisticsByMonths.add(statisticsByMonths.get(j));
        }
        return staticsByMonthWithTotalElements;
    }

}
