package com.example.springboottest.controller;

import com.example.springboottest.entity.Check_account;
import com.example.springboottest.entity.Client;
import com.example.springboottest.entity.Client_get_loan;
import com.example.springboottest.entity.Saving_account;

import com.example.springboottest.repository.Check_accountRepository;
import com.example.springboottest.repository.ClientRepository;
import com.example.springboottest.repository.Client_get_loanRepository;
import com.example.springboottest.repository.Saving_accountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/client")
public class ClientHandler {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private Saving_accountRepository saving_accountRepository;

    @Autowired
    private Check_accountRepository check_accountRepository;

    @Autowired
    private Client_get_loanRepository client_get_loanRepository;

    public static boolean isInteger(String input){
        Matcher mer = Pattern.compile("^[0-9]+$").matcher(input);
        return mer.find();
    }
    public static boolean isAllChinese(String str) {
        if (str == null) { return false; }
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<Client> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size)
    {
        Pageable pageable = PageRequest.of(page-1,size);
        return clientRepository.findAll(pageable);
    }
    @PostMapping("/save")
    public String save(@RequestBody Client client)
    {
        String client_id = client.getClient_id();
        String tel = client.getTel();
        String address = client.getAddress();
        String name = client.getName();
        if(!isInteger(client_id))
        {
            return "??????????????????????????????";
        }
        else if(client_id.length()>18)
        {
            return "????????????????????????18???";
        }
        else if(!isInteger(tel))
        {
            return "???????????????????????????";
        }
        else if(tel.length()>11)
        {
            return "?????????????????????11???";
        }
        else if(address.length()>32)
        {
            return "??????????????????32?????????";
        }
        else if(!isAllChinese(name))
        {
            return "??????????????????????????????";
        }
        else {
            Optional<Client> O = clientRepository.findById(client.getClient_id());
            if(O.isPresent())
            {
                return "?????????????????????????????????";
            }
            Client result = clientRepository.save(client);
            if(result != null) {
                return "success";
            }
            else{
                return "error";
            }
        }
    }
    @PostMapping("/update")
    public String update(@RequestBody Client client){
        String client_id = client.getClient_id();
        String tel = client.getTel();
        String address = client.getAddress();
        String name = client.getName();
        if(!isInteger(client_id))
        {
            return "??????????????????????????????";
        }
        else if(!isInteger(tel))
        {
            return "???????????????????????????";
        }
        else if(client_id.length()>18)
        {
            return "????????????????????????18???";
        }
        else if(!isInteger(tel))
        {
            return "???????????????????????????";
        }
        else if(tel.length()>11)
        {
            return "?????????????????????11???";
        }
        else if(address.length()>32)
        {
            return "??????????????????32?????????";
        }
        else if(!isAllChinese(name))
        {
            return "??????????????????????????????";
        }
        else {
            Client result = clientRepository.save(client);
            if(result != null) {
                return "success";
            }
            else{
                return "error";
            }
        }
    }

    @GetMapping("/findById/{client_id}")
    public Client findById(@PathVariable("client_id") String client_id){
        if(clientRepository.findById(client_id).isPresent())
            return clientRepository.findById(client_id).get();
        else {
            Client client = new Client();
            client.setClient_id(client_id);
            return client;
        }
    }
    @DeleteMapping("/deleteById/{client_id}")
    public String deleteById(@PathVariable("client_id") String client_id)
    {
        List<Saving_account> saving_accounts = saving_accountRepository.findAll();
        for(Saving_account saving_account:saving_accounts)
        {
            if(saving_account.getClient_id().equals(client_id))
                return "??????????????????????????????????????????????????????";
        }
        List<Check_account> check_accounts = check_accountRepository.findAll();
        for(Check_account check_account:check_accounts)
        {
            if(check_account.getClient_id().equals(client_id))
                return "??????????????????????????????????????????????????????";
        }
        List<Client_get_loan> client_get_loans = client_get_loanRepository.findAll();
        for(Client_get_loan client_get_loan:client_get_loans)
        {
            if(client_get_loan.getClient_id().equals(client_id))
                return "?????????????????????????????????????????????";
        }
        clientRepository.deleteById(client_id);
        return "success";
    }
}
