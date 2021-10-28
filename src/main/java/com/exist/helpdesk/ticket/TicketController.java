package com.exist.helpdesk.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/tickets")
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

    //CREATE TICKET
    @PostMapping
    Ticket createTicket(@RequestBody Ticket ticket){
        return ticketService.createTicket(ticket);
    }

    //ADD ASSIGNEE
    @PutMapping("/{ticketId}/assignee/{employeeId}")
    Ticket addAssigneeToTicket(@PathVariable long ticketId,
                               @PathVariable long employeeId){
       return ticketService.addAsignee(ticketId, employeeId);
    }

    //ADD WATCHERS TO TICKET
    @PutMapping("/{ticketId}/watchers/{employeeId}")
    Ticket addWatchersToTicket(@PathVariable long ticketId,
                               @PathVariable long employeeId){
        return ticketService.addWatcher(ticketId, employeeId);
    }

    //DELETE TICKET
    @DeleteMapping(path = "{ticketId}")
    void deleteTicket(@PathVariable("ticketId") long ticketID){
        ticketService.deleteTicket(ticketID);
    }

    //DELETE WATCHER FROM TICKET BY ID
    @DeleteMapping(path = "/{ticketId}/watchers/{employeeId}")
    void removeWatcherFromTicket(@PathVariable long ticketId,
                                 @PathVariable long employeeId){
        ticketService.removeWatcherFromTicket(ticketId, employeeId);
    }


    //UPDATE TICKET
    @PutMapping(path = "{ticketId}")
    void updateTicket(@PathVariable("ticketId") long ticketID,
                      @RequestBody Ticket requestBodyTicket){
        ticketService.updateTicket(ticketID,requestBodyTicket);
    }
}
