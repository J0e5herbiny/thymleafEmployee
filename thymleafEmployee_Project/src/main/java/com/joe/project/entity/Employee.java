package com.joe.project.entity;

import com.joe.project.dto.EmployeeDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Employee {

    @Id
    @SequenceGenerator(
//            name = "employee_id",
            name = "id",
            sequenceName = "employee_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "employee_id"
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "sur_name")
    private String surName;

    @Column(name = "email")
    private String email;

    public Employee() {
    }

    public Employee(String firstName,
                    String surName,
                    String email) {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
    }

    public Employee(Long id,
                    String firstName,
                    String surName,
                    String email) {
        this.id = id;
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeDto employeeDto(String fName, String sName, String eMail){
        this.firstName = fName;
        this.surName = sName;
        this.email = eMail;
        return new EmployeeDto(fName, sName, eMail);
    }
    public EmployeeDto employeeDto(){
        return new EmployeeDto(id, firstName, surName, email);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
