package com.exist.helpdesk.employee;

import com.exist.helpdesk.ticket.Ticket;
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
    @GetMapping("/{employeeId}/assignee")
    List<Ticket> getTicketsAssigned(@PathVariable("employeeId") Long employeeId){
        return employeeService.getTicketsAssigned(employeeId);
    }

    //GET EMPLOYEE WATCHED TICKETS
    @GetMapping("/{employeeId}/watcher")
    List<Ticket> getTicketsWatched(@PathVariable("employeeId") Long employeeId){
        return employeeService.getTicketsWatched(employeeId);
    }

    //CREATE EMPLOYEE
    @PostMapping
    Employee createEmployee(@RequestBody Employee employee){
        return employeeService.createEmployee(employee);
    }

    //DELETE EMPLOYEE
    @DeleteMapping(path = "{employeeId}")
    void deleteUser(@PathVariable("employeeId") Long employeeId){
        employeeService.deleteEmployee(employeeId);
    }

    //UPDATE EMPLOYEE
    @PutMapping(path = "{employeeId}")
    void updateUser(@PathVariable("employeeId") Long employeeId,
                    @RequestBody Employee requestBodyEmployee){
        employeeService.updateUser(employeeId, requestBodyEmployee);
    }
}
