package com.exist.helpdesk.service;

import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.model.Ticket;
import com.exist.helpdesk.repository.EmployeeRepository;
import com.exist.helpdesk.repository.TicketRepository;
import com.exist.helpdesk.utility.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EmployeeRepository employeeRepository;
    private final UtilityService utilityService;

    @Autowired
    public TicketService(TicketRepository ticketRepository,
                         EmployeeRepository employeeRepository,
                         UtilityService utilityService) {
        this.ticketRepository = ticketRepository;
        this.employeeRepository = employeeRepository;
        this.utilityService = utilityService;
    }

    public List<Ticket> getTickets(){
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(Long ticketId) {
        utilityService.ticketExist(ticketId);
        return ticketRepository.findById(ticketId);
    }

    public Ticket createTicket(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    public Ticket addAsignee(long ticketId, long employeeId) {
        utilityService.ticketExist(ticketId);
        Ticket ticket = ticketRepository.findById(ticketId).get();
        Employee employee = employeeRepository.findById(employeeId).get();
        ticket.setAssignee(employee);
        return ticketRepository.save(ticket);
    }

    public Ticket addWatcher(long ticketId, long employeeId) {
        utilityService.ticketExist(ticketId);
        Ticket ticket = ticketRepository.findById(ticketId).get();
        Employee employee = employeeRepository.findById(employeeId).get();
        ticket.addWatchers(employee);
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(long ticketID) {
        utilityService.ticketExist(ticketID);
        Ticket ticket = ticketRepository.findById(ticketID).get();
        if(ticket.getAssignee() != null){
            ticket.getAssignee().removeAssignedTickets(ticket);
        }
        if(!ticket.getWatchers().isEmpty()){
            ticket.removeAllWatchers();
        }
        ticketRepository.deleteById(ticketID);
    }

    public void updateTicket(long ticketID, Ticket requestBodyTicket) {
        utilityService.ticketExist(ticketID);
        Ticket ticket = ticketRepository.findById(ticketID)
                .orElseThrow(()-> new IllegalStateException("Ticket with id "
                        + ticketID + " does not exist"));
        ticket.setStatus(requestBodyTicket.getStatus());
        ticket.setDescription(requestBodyTicket.getDescription());
        ticket.setSeverity(requestBodyTicket.getSeverity());
        ticketRepository.save(ticket);
    }

    public void removeWatcherFromTicket(long ticketId, long employeeId) {
        Ticket ticket = ticketRepository.findById(ticketId).get();
        boolean exist = utilityService.watcherExist(ticketId,employeeId);
        if(exist){
            ticket.removeWatcher(employeeId);
        }
        ticketRepository.save(ticket);
    }

    public List<Employee> getWatchers(Long ticketId) {
        utilityService.ticketExist(ticketId);
        Ticket ticket = ticketRepository.findById(ticketId).get();
        return new ArrayList<>(ticket.getWatchers());
    }

//    public Optional<Ticket> getTicketByAssignee(Long ticketId, Long employeeId) {
//        utilityService.ticketExist(ticketId);
//        utilityService.employeeExist(employeeId);
//        Ticket ticket = ticketRepository.findById(ticketId)
//    }
}
