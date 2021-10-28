package com.exist.helpdesk.employee;

import com.exist.helpdesk.utility.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UtilityService utilityService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           UtilityService utilityService,
                           EntityManager entityManager) {
        this.employeeRepository = employeeRepository;
        this.utilityService = utilityService;
    }

    //GET ALL EMPLOYEE SERVICE
    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    //GET SINGLE EMPLOYEE BY ID SERVICE
    public Optional<Employee> getEmployeeById(Long employeeId) {
        utilityService.employeeExist(employeeId);
        return employeeRepository.findById(employeeId);
    }

    //CREATE EMPLOYEE SERVICE
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    //DELETE EMPLOYEE SERVICE
    public void deleteEmployee(Long employeeId) {
        //UTILITY SERVICE
        //checks if the current employee exist in the database
        utilityService.employeeExist(employeeId);
        Employee employee = employeeRepository.findById(employeeId).get();
        if(employee.getTicketsAssigned().isEmpty()){
            employee.removeAllTicketsWatched();
            employeeRepository.deleteById(employeeId);
        } else {
            throw new IllegalStateException("Fail to delete Employee, currently assigned to a Ticket.");
        }

    }

    public Employee updateUser(Long employeeId, Employee requestBodyEmployee) {
        utilityService.employeeExist(employeeId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new IllegalStateException("Employee with id "
                        + employeeId + " does not exist."));
        employee.setEmployeeNumber(requestBodyEmployee.getEmployeeNumber());
        employee.setFirstName(requestBodyEmployee.getFirstName());
        employee.setMiddleName(requestBodyEmployee.getMiddleName());
        employee.setLastName(requestBodyEmployee.getLastName());
        employee.setDepartment(requestBodyEmployee.getDepartment());
        return employeeRepository.save(employee);
    }
}
