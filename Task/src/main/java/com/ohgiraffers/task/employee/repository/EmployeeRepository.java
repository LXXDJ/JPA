package com.ohgiraffers.task.employee.repository;

import com.ohgiraffers.task.employee.entity.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findBySalaryGreaterThan(Integer salary, Sort sort);
}
