package com.joe.project.repository;

import com.joe.project.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public List<Employee> findAllByOrderByFirstNameAsc();

    public List<Employee> findByFirstNameContainsOrSurNameContainsAllIgnoreCase(String firstName, String surName);


    Optional<Employee> findByEmail(String email);
}
