package com.exist.helpdesk.controller;

import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.service.EmployeeService;
import com.exist.helpdesk.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //GET ALL EMPLOYEES
    @GetMapping
    List<Employee> getEmployees(){
        return employeeService.getEmployees();
    }

    //GET SINGLE EMPLOYEE BY ID
    @GetMapping("/{employeeId}")
    Optional<Employee> getEmployeeById(@PathVariable("employeeId") Long employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    //GET EMPLOYEE ASSIGNED TICKETS
    @GetMapping("/assignee/{employeeId}")
    List<Ticket> getTicketsAssigned(@PathVariable("employeeId") Long employeeId){
        return employeeService.getTicketsAssigned(employeeId);
    }

    //GET EMPLOYEE WATCHED TICKETS
    @GetMapping("/watcher/{employeeId}")
    List<Ticket> getTicketsWatched(@PathVariable("employeeId") Long employeeId){
        return employeeService.getTicketsWatched(employeeId);
    }






}
