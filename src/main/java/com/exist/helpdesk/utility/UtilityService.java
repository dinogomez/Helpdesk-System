package com.exist.helpdesk.utility;

import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.repository.EmployeeRepository;
import com.exist.helpdesk.model.Ticket;
import com.exist.helpdesk.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilityService {
    private final EmployeeRepository employeeRepository;
    private final TicketRepository ticketRepository;


    @Autowired
    public UtilityService(EmployeeRepository employeeRepository, TicketRepository ticketRepository) {
        this.employeeRepository = employeeRepository;
        this.ticketRepository = ticketRepository;
    }

    public void employeeExist(Long employeeId){
        boolean exist = employeeRepository.existsById(employeeId);
        if(!exist){
            throw new IllegalStateException(
                    "Employee with id "+ employeeId + " does not exist.");

        }
    }

    public void ticketExist(Long ticketId){
        boolean exist = ticketRepository.existsById(ticketId);
        if(!exist){
            throw new IllegalStateException(
                    "Ticket with id "+ ticketId + " does not exist.");
        }
    }

    public boolean watcherExist(Long ticketId, Long employeeId){
        Ticket ticket = ticketRepository.findById(ticketId).get();
        for(Employee employee : ticket.getWatchers()){
            if(employee.getId() == employeeId){
                return true;
            }
        }
        return false;
    }


}
