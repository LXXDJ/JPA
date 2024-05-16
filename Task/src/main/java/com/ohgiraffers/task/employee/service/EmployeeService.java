package com.ohgiraffers.task.employee.service;

import com.ohgiraffers.task.employee.dto.EmployeeDTO;
import com.ohgiraffers.task.employee.dto.JobDTO;
import com.ohgiraffers.task.employee.entity.Employee;
import com.ohgiraffers.task.employee.entity.Job;
import com.ohgiraffers.task.employee.repository.EmployeeRepository;
import com.ohgiraffers.task.employee.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final JobRepository jobRepository;

    public EmployeeDTO findEmployeeByEmpId(String empId){
        Employee foundEmployee = employeeRepository.findById(empId).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(foundEmployee, EmployeeDTO.class);
    }

    public List<EmployeeDTO> findEmployeeList(){
        List<Employee> employeeList = employeeRepository.findAll(Sort.by("empId").descending());
        return employeeList.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).toList();
    }

    public Page<EmployeeDTO> findEmployeeList(Pageable pageable){
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("empId").descending()
        );
        Page<Employee> employeeList = employeeRepository.findAll(pageable);
        return employeeList.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    public List<EmployeeDTO> findBySalary(Integer salary){
        List<Employee> employeeList = employeeRepository.findBySalaryGreaterThan(salary, Sort.by("salary").descending());
        return employeeList.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).toList();
    }

    public List<JobDTO> findAllJob() {
        List<Job> jobList = jobRepository.findAllJob();
        return jobList.stream().map(job -> modelMapper.map(job, JobDTO.class)).toList();
    }

    @Transactional
    public void registEmployee(EmployeeDTO employeeDTO){
        employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));
    }

    @Transactional
    public void modifyEmployee(EmployeeDTO employeeDTO){
        Employee foundEmployee = employeeRepository.findById(employeeDTO.getEmpId()).orElseThrow(IllegalArgumentException::new);
        foundEmployee.modifySalary(employeeDTO.getSalary());
    }

    @Transactional
    public void deleteEmployee(String empId){employeeRepository.deleteById(empId);}
}
