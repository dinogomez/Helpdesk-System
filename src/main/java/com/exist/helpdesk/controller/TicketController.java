package com.exist.helpdesk.controller;

import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.model.Ticket;
import com.exist.helpdesk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    //GET ALL TICKETS
    @GetMapping
    List<Ticket> getTickets(){
        return ticketService.getTickets();
    }

    //GET SINGLE TICKET BY ID
    @GetMapping("/{ticketId}")
    Optional<Ticket> getTicketById(@PathVariable("ticketId") Long ticketId){
        return ticketService.getTicketById(ticketId);
    }

    //GET ALL TICKET WATCHERS
    @GetMapping("/{ticketId}/watchers")
    List<Employee> getWatchers(@PathVariable("ticketId") Long ticketId){
        return ticketService.getWatchers(ticketId);
    }

    //GET TICKET BY ASSIGNEE ID
//    @GetMapping("/{ticketId}/assignee/{employeeId}")
//    Optional<Ticket> getTicketByAssignee(@PathVariable("ticketId") Long ticketId,
//                                           @PathVariable("employeeId") Long employeeId){
//        return ticketService.getTicketByAssignee(ticketId, employeeId);
//    }

    //CREATE TICKET
    @PostMapping
    Ticket createTicket(@RequestBody Ticket ticket){
        return ticketService.createTicket(ticket);
    }

    //ADD ASSIGNEE
    @PutMapping("/{ticketId}/add/assignee/{employeeId}")
    Ticket addAssigneeToTicket(@PathVariable long ticketId,
                               @PathVariable long employeeId){
       return ticketService.addAsignee(ticketId, employeeId);
    }

    //ADD WATCHERS TO TICKET
    @PutMapping("/{ticketId}/add/watchers/{employeeId}")
    Ticket addWatchersToTicket(@PathVariable long ticketId,
                               @PathVariable long employeeId){
        return ticketService.addWatcher(ticketId, employeeId);
    }

    //DELETE TICKET
    @DeleteMapping(path = "/delete/{ticketId}")
    void deleteTicket(@PathVariable("ticketId") long ticketID){
        ticketService.deleteTicket(ticketID);
    }

    //DELETE WATCHER FROM TICKET BY ID
    @DeleteMapping(path = "/admin/delete/{ticketId}/watchers/{employeeId}")
    void removeWatcherFromTicket(@PathVariable long ticketId,
                                 @PathVariable long employeeId){
        ticketService.removeWatcherFromTicket(ticketId, employeeId);
    }

    //UPDATE TICKET
    @PutMapping(path = "/admin/update/{ticketId}")
    void updateTicket(@PathVariable("ticketId") long ticketID,
                      @RequestBody Ticket requestBodyTicket){
        ticketService.updateTicket(ticketID,requestBodyTicket);
    }
}
