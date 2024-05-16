package com.ohgiraffers.task.employee.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.Date;

@Getter
@Setter
@ToString
public class EmployeeDTO {
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
}
