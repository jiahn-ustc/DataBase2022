package com.example.springboottest.repository;

import com.example.springboottest.TriggersId.Client_get_loanTriggersId;
import com.example.springboottest.entity.Client_get_loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface Client_get_loanRepository extends JpaRepository<Client_get_loan, Client_get_loanTriggersId>,
                                JpaSpecificationExecutor<Client_get_loan>
{
}
