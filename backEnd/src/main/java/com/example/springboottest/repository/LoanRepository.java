package com.example.springboottest.repository;

import com.example.springboottest.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, String>
{
}
