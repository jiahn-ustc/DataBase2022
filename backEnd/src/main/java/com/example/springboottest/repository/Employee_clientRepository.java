package com.example.springboottest.repository;

import com.example.springboottest.TriggersId.Employee_clientTriggersId;
import com.example.springboottest.entity.Employee_client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface Employee_clientRepository extends JpaRepository<Employee_client, Employee_clientTriggersId>,
                JpaSpecificationExecutor<Employee_client> {
}
