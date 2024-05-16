package com.ohgiraffers.task.employee.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "employee")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Employee {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String empId;
    private String empName;
    private String empNo;
    private String email;
    private String phone;
    private String deptCode;
    private String jobCode;
    private String salLevel;
    private Integer salary;
    private Integer bonus;
    private String managerId;
    private Date hireDate;
    private Date entDate;
    private String entYn;

    public void modifySalary(Integer salary) {
        this.salary = salary;
    }
}
