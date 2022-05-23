package com.example.springboottest.controller;

import com.example.springboottest.TriggersId.Client_get_loanTriggersId;
import com.example.springboottest.entity.Client_get_loan;
import com.example.springboottest.entity.Loan;
import com.example.springboottest.entity.Loan_pay;
import com.example.springboottest.helpClass.Client_get_loanWithAmount;
import com.example.springboottest.helpClass.LoanSearch;
import com.example.springboottest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clientGetLoan")
public class Client_get_loanHandler {
    @Autowired
    private Client_get_loanRepository client_get_loanRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private Loan_payRepository loan_payRepository;

    public static boolean isFloatNumber(String str){
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<Client_get_loan> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return client_get_loanRepository.findAll(pageable);
    }
    @PostMapping("/save")
    public String save(@RequestBody Client_get_loanWithAmount client_get_loanWithAmount){
        String client_id = client_get_loanWithAmount.getClient_id();
        String bank_name = client_get_loanWithAmount.getBank_name();
        String loan_id = client_get_loanWithAmount.getLoan_id();
        String amount = client_get_loanWithAmount.getAmount();
        if(!isFloatNumber(amount))
            return "贷款金额必须为浮点型数据";
        if(!bankRepository.findById(bank_name).isPresent())
        {
            return "贷款银行不存在";
        }
        if(!clientRepository.findById(client_id).isPresent())
        {
            return "该客户不存在";
        }
        if(loanRepository.findById(loan_id).isPresent())
        {
            //return "该贷款号已存在";
            Client_get_loanTriggersId client_get_loanTriggersId = new Client_get_loanTriggersId();
            client_get_loanTriggersId.setClient_id(client_id);
            client_get_loanTriggersId.setLoan_id(loan_id);
            if(client_get_loanRepository.findById(client_get_loanTriggersId).isPresent())
                return "该客户已办理该贷款，请勿重复办理";
            else
            {
                Client_get_loan client_get_loan = new Client_get_loan();
                client_get_loan.setClient_id(client_id);
                client_get_loan.setBank_name(bank_name);
                client_get_loan.setLoan_id(loan_id);
                client_get_loanRepository.save(client_get_loan);
            }
        }
        else{
            Loan loan = new Loan();
            loan.setLoan_id(loan_id);
            loan.setBank_name(bank_name);
            loan.setAmount(amount);
            loanRepository.save(loan);

            Client_get_loan client_get_loan = new Client_get_loan();
            client_get_loan.setClient_id(client_id);
            client_get_loan.setBank_name(bank_name);
            client_get_loan.setLoan_id(loan_id);
            client_get_loanRepository.save(client_get_loan);
        }





        return "success";
    }

    @GetMapping("/findById/{loan_id}")
    public LoanSearch findById(@PathVariable("loan_id") String loan_id){
        LoanSearch loanSearch = new LoanSearch();
        loanSearch.setLoan_id(loan_id);
        if(!loanRepository.findById(loan_id).isPresent())
        {
            loanSearch.setReturn_data("数据库中没有此贷款信息");
            return loanSearch;
        }
        else {
            Loan loan = loanRepository.findById(loan_id).get();
            loanSearch.setAmount(loan.getAmount());
            loanSearch.setBank_name(loan.getBank_name());
        }
        float amount = Float.parseFloat(loanSearch.getAmount());
        List<Float> amounts_provided = new ArrayList<>();
        List<Loan_pay> loan_pays = loan_payRepository.findAll();
        for(Loan_pay loan_pay:loan_pays)
        {
            if(loan_pay.getLoan_id().equals(loan_id))
            {
                Float temp = Float.parseFloat(loan_pay.getPay_amount());
                amounts_provided.add(temp);
            }
        }
        if(amounts_provided.size()==0)
        {
            loanSearch.setLoan_status("未发放");
        }
        else {
            float amountSum=0;
            for(Float amount_provided:amounts_provided)
            {
                amountSum += amount_provided;
            }
            if(amountSum<amount)
            {
                loanSearch.setLoan_status("发放中");
            }
            else if(Math.abs(amountSum-amount)<1e-3)
            {
                loanSearch.setLoan_status("已全部发放");
            }
            else if(amountSum>amount)
            {
                loanSearch.setLoan_status("发生了某种错误，发多了");
            }
            loanSearch.setAmount_provided(String.valueOf(amountSum) );
        }


        return loanSearch;
    }
}
