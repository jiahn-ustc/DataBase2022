package com.example.springboottest.repository;

import com.example.springboottest.TriggersId.Manage_departmentTriggersId;

import com.example.springboottest.entity.Manage_department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface Manage_departmentRepository
            extends JpaRepository<Manage_department, Manage_departmentTriggersId>,
                    JpaSpecificationExecutor<Manage_department>
{
}
