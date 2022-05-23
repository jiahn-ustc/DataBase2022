package com.example.springboottest.repository;

import com.example.springboottest.TriggersId.Loan_payTriggersId;
import com.example.springboottest.entity.Loan_pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface Loan_payRepository extends JpaRepository<Loan_pay, Loan_payTriggersId>,
                            JpaSpecificationExecutor<Loan_pay>
{
}
