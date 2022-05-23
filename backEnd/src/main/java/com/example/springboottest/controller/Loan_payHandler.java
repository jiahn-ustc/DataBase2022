package com.example.springboottest.controller;

import com.example.springboottest.TriggersId.Loan_payTriggersId;
import com.example.springboottest.entity.Loan_pay;
import com.example.springboottest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/loanPay")
public class Loan_payHandler {
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
    public Page<Loan_pay> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return loan_payRepository.findAll(pageable);
    }
    @PostMapping("/save")
    public String save(@RequestBody Loan_pay loan_pay){
        String bank_name = loan_pay.getBank_name();
        String loan_id = loan_pay.getLoan_id();
        String pay_code = loan_pay.getPay_code();
        String pay_amount = loan_pay.getPay_amount();
        if(!bankRepository.findById(bank_name).isPresent())
            return "发放贷款银行不存在";
        if(!loanRepository.findById(loan_id).isPresent())
            return "贷款号不存在";
        else if(!loanRepository.findById(loan_id).get().getBank_name().equals(bank_name))
        {
            return "发放贷款银行错误，该贷款不属于此银行";
        }
        if(!isFloatNumber(pay_amount))
            return "发放的贷款金额必须是浮点型数据";
        Loan_payTriggersId loan_payTriggersId = new Loan_payTriggersId();
        loan_payTriggersId.setLoan_id(loan_id);
        loan_payTriggersId.setPay_code(pay_code);
        if(loan_payRepository.findById(loan_payTriggersId).isPresent())
            return "此付款码已被使用，请更换付款码";
        float amount = Float.parseFloat(loanRepository.findById(loan_id).get().getAmount());
        float will_pay_amount = Float.parseFloat(pay_amount);

        List<Loan_pay> loan_pays = loan_payRepository.findAll();
        float amount_provided=0;
        for(Loan_pay temp_loan_pay:loan_pays)
        {
            if(temp_loan_pay.getLoan_id().equals(loan_id))
            {
                Float temp = Float.parseFloat(temp_loan_pay.getPay_amount());
                amount_provided += temp;
            }
        }
        if(amount_provided+will_pay_amount>amount)
            return "已发放金额将要多于贷款总金额，禁止发放";
        loan_payRepository.save(loan_pay);


        return "success";
    }
}
