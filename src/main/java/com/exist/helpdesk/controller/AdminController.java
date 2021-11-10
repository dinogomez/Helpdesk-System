package com.exist.helpdesk.controller;

import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final EmployeeService employeeService;

    @Autowired
    public AdminController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    //Employee Admin Endpoints
    @PostMapping("/employee/add")
    Employee createEmployee(@RequestBody Employee employee){
        return employeeService.createEmployee(employee);
    }

    @DeleteMapping(path = "/employee/delete/{employeeId}")
    void deleteUser(@PathVariable("employeeId") Long employeeId){
        employeeService.deleteEmployee(employeeId);
    }

    @PutMapping(path = "/employee/update/{employeeId}")
    void updateUser(@PathVariable("employeeId") Long employeeId,
                    @RequestBody Employee requestBodyEmployee){
        employeeService.updateUser(employeeId, requestBodyEmployee);
    }



}
