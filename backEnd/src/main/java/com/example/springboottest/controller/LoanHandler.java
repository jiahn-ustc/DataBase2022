package com.example.springboottest.controller;

import com.example.springboottest.TriggersId.Client_get_loanTriggersId;
import com.example.springboottest.TriggersId.Loan_payTriggersId;
import com.example.springboottest.entity.Client_get_loan;
import com.example.springboottest.entity.Loan;
import com.example.springboottest.entity.Loan_pay;
import com.example.springboottest.repository.Client_get_loanRepository;
import com.example.springboottest.repository.LoanRepository;
import com.example.springboottest.repository.Loan_payRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanHandler {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private Client_get_loanRepository client_get_loanRepository;

    @Autowired
    private Loan_payRepository loan_payRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Loan> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return loanRepository.findAll(pageable);
    }

    @DeleteMapping("/deleteById/{loan_id}")
    public String deleteById(@PathVariable("loan_id") String loan_id)
    {
        float amount = Float.parseFloat(loanRepository.findById(loan_id).get().getAmount());
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
        if(amounts_provided.size()>0)
        {
            float amountSum=0;
            for(Float amount_provided:amounts_provided)
            {
                amountSum += amount_provided;
            }
            if(amountSum<amount)
            {
                return "贷款正在发放中，不能删除";
            }
        }
        //删除贷款发放信息
        List<Loan_pay> loanPays = loan_payRepository.findAll();
        for(Loan_pay loanPay:loanPays)
        {
            if(loanPay.getLoan_id().equals(loan_id))
            {
                Loan_payTriggersId loan_payTriggersId = new Loan_payTriggersId();
                loan_payTriggersId.setLoan_id(loan_id);
                loan_payTriggersId.setPay_code(loanPay.getPay_code());
                loan_payRepository.deleteById(loan_payTriggersId);
            }
        }

        //删除客户办理贷款信息
        List<Client_get_loan> client_get_loans = client_get_loanRepository.findAll();
        for(Client_get_loan client_get_loan:client_get_loans)
        {
            if(client_get_loan.getLoan_id().equals(loan_id))
            {
                Client_get_loanTriggersId client_get_loanTriggersId = new Client_get_loanTriggersId();
                client_get_loanTriggersId.setLoan_id(loan_id);
                client_get_loanTriggersId.setClient_id(client_get_loan.getClient_id());
                client_get_loanRepository.deleteById(client_get_loanTriggersId);
            }
        }

        //删除贷款信息
        loanRepository.deleteById(loan_id);

        return "success";
    }
}
