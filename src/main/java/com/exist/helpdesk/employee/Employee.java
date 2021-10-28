package com.exist.helpdesk.employee;

import com.exist.helpdesk.ticket.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Employee {
    private enum Department{
        IT, ADMIN, HR, SALES ;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private long employeeNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private Department department;
    //Employee
    @JsonIgnore
    @OneToMany(mappedBy = "assignee",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    Set<Ticket> ticketsAssigned = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "watchers")
    Set<Ticket> ticketsWatched = new HashSet<>();


    public Employee() {
    }

    public Employee(long employeeNumber,
                    String firstName,
                    String middleName,
                    String lastName,
                    Department department) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.department = department;
    }

    public void removeAllTicketsWatched(){
        for(Ticket tickets: ticketsWatched){
            tickets.removeWatcher(this);
        }
    }

    public void removeAssignedTickets(Ticket ticket){
        this.ticketsAssigned.remove(ticket);
    }

    public void removeTicketWatched(Ticket ticket){
        this.ticketsWatched.remove(ticket);
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(long employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Ticket> getTicketsAssigned() {
        return ticketsAssigned;
    }

    public void setTicketsAssigned(Set<Ticket> ticketsAssigned) {
        this.ticketsAssigned = ticketsAssigned;
    }

    public Set<Ticket> getTicketsWatched() {
        return ticketsWatched;
    }

    public void setTicketsWatched(Set<Ticket> ticketsWatched) {
        this.ticketsWatched = ticketsWatched;
    }


}
