package com.exist.helpdesk.ticket;

import com.exist.helpdesk.employee.Employee;
import com.exist.helpdesk.employee.EmployeeService;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Ticket {

    private enum Severity{
        LOW,NORMAL, MAJOR, CRITICAL;
    }
    private enum Status{
        NEW, ASSIGNED, INPROGRESS, CLOSED;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private Status status;
    //Ticket
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assignee_id", referencedColumnName = "id")
    private Employee assignee;

    @ManyToMany
    @JoinTable(name="watchers",
            joinColumns =  @JoinColumn(name="ticket_id"),
            inverseJoinColumns = @JoinColumn(name="employee_id")
    )
    Set<Employee> watchers = new HashSet<>();

    private String description;
    private Severity severity;

    public Ticket() {
    }
//    Severity: LOW,NORMAL, MAJOR, CRITICAL;
//    Status: NEW, ASSIGNED, INPROGRESS, CLOSED;
    public Ticket(String title,
                  Status status,
                  Employee assignee,
                  Set<Employee> watchers,
                  String description,
                  Severity severity
    ) {
        this.title = title;
        this.status = status;
        this.assignee = assignee;
        this.watchers = watchers;
        this.description = description;
        this.severity = severity;
    }
    public void addWatchers(Employee employee){
        this.watchers.add(employee);
        employee.getTicketsAssigned().add(this);
    }

    public void removeWatcher(Employee employee){
        this.watchers.remove(employee);
    }

    public void removeWatcher(Long employeeId){
        for(Employee employee: this.watchers){
            if(employee.getId() == employeeId){
                this.watchers.remove(employee);
            }
        }
    }

    public void removeAllWatchers(){
        for(Employee employee: watchers){
            employee.removeTicketWatched(this);
        }
    }

    public long getTicketID() {
        return id;
    }

    public void setTicketID(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Employee getAssignee() {
        return assignee;
    }

    public void setAssignee(Employee assignee) {
        this.assignee = assignee;
    }

    public Set<Employee> getWatchers() {
        return watchers;
    }

    public void setWatchers(Set<Employee> watchers) {
        this.watchers = watchers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
}
