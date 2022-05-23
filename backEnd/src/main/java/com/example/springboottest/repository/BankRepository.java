package com.example.springboottest.repository;

import com.example.springboottest.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank,String> {
}
