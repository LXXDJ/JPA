package com.ohgiraffers.task.employee.controller;

import com.ohgiraffers.task.common.Pagenation;
import com.ohgiraffers.task.common.PagingButton;
import com.ohgiraffers.task.employee.dto.EmployeeDTO;
import com.ohgiraffers.task.employee.dto.JobDTO;
import com.ohgiraffers.task.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/{empId}")
    public String findEmployeeByEmpId(@PathVariable String empId, Model model){
        EmployeeDTO resultEmployee = employeeService.findEmployeeByEmpId(empId);
        model.addAttribute("employee", resultEmployee);
        return "/employee/detail";
    }

    @GetMapping("/list")
    public String findEmployeeList(Model model, @PageableDefault Pageable pageable){
        Page<EmployeeDTO> employeeList = employeeService.findEmployeeList(pageable);
        PagingButton paging = Pagenation.getPagingButtonInfo(employeeList);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("paging", paging);
        return "/employee/list";
    }

    @GetMapping("/querymethod")
    public void querymethod(){}

    @GetMapping("/search")
    public String findBySalary(@RequestParam Integer salary, Model model){
        List<EmployeeDTO> employeeList = employeeService.findBySalary(salary);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("salary", salary);
        return "/employee/searchResult";
    }

    @GetMapping("/regist")
    public void regist(){}

    @GetMapping("/job")
    @ResponseBody
    public List<JobDTO> findJobList(){return employeeService.findAllJob();}

    @PostMapping("/regist")
    public String registEmployee(@ModelAttribute EmployeeDTO employeeDTO){
        int salary = employeeDTO.getSalary();
        if(salary >= 1000000 && salary <= 1999999) employeeDTO.setSalLevel("S6");
        else if(salary >= 2000000 && salary <= 2999999) employeeDTO.setSalLevel("S5");
        else if(salary >= 3000000 && salary <= 3999999) employeeDTO.setSalLevel("S4");
        else if(salary >= 4000000 && salary <= 4999999) employeeDTO.setSalLevel("S3");
        else if(salary >= 5000000 && salary <= 5999999) employeeDTO.setSalLevel("S2");
        else if(salary >= 6000000 && salary <= 10000000) employeeDTO.setSalLevel("S1");

        employeeService.registEmployee(employeeDTO);
        return "redirect:/employee/list";
    }

    @GetMapping("/modify")
    public void modify(){}

    @PostMapping("/modify")
    public String modifyEmployee(@ModelAttribute EmployeeDTO employeeDTO){
        int salary = employeeDTO.getSalary();
        if(salary >= 1000000 && salary <= 1999999) employeeDTO.setSalLevel("S6");
        else if(salary >= 2000000 && salary <= 2999999) employeeDTO.setSalLevel("S5");
        else if(salary >= 3000000 && salary <= 3999999) employeeDTO.setSalLevel("S4");
        else if(salary >= 4000000 && salary <= 4999999) employeeDTO.setSalLevel("S3");
        else if(salary >= 5000000 && salary <= 5999999) employeeDTO.setSalLevel("S2");
        else if(salary >= 6000000 && salary <= 10000000) employeeDTO.setSalLevel("S1");

        employeeService.modifyEmployee(employeeDTO);
        return "redirect:/employee/"+employeeDTO.getEmpId();
    }

    @GetMapping("/delete")
    public void delete(){}

    @PostMapping("/delete")
    public String deleteEmployee(@RequestParam String empId){
        employeeService.deleteEmployee(empId);
        return "redirect:/employee/list";
    }
}
