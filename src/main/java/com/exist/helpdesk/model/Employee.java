package com.exist.helpdesk.model;

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

    @Column(unique = true)
    private String username;
    private String password;
    private String roles;

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
                    String username,
                    String password,
                    String roles,
                    Department department,
                    Set<Ticket> ticketsAssigned,
                    Set<Ticket> ticketsWatched) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.department = department;
        this.ticketsAssigned = ticketsAssigned;
        this.ticketsWatched = ticketsWatched;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
