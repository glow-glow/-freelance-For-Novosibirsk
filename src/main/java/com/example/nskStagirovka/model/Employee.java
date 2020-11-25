package com.example.nskStagirovka.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@ToString
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic
    @Column(name = "id")
    private UUID id;
    @Basic
    @Column(name = "organization")
    private String organization;
    @Basic
    @Column(name = "fio")
    private String fio;
    @Basic
    @Column(name = "position")
    private String position;
    @Basic
    @Column(name ="employment")
    private String dateEmployment;
    @Basic
    @Column(name = "layoffs")
    private String dateLayoffs;
    @Basic
    @Column(name = "education")
    private String education;
    @Basic
    @Column(name = "education_end")
    private String educationEnd;
    @Column(name = "status")
    private Boolean status;

    public Employee(String organization, String fio, String position, String dateEmployment, String dateLayoffs, String education, String educationEnd, Boolean status) {
        this.organization = organization;
        this.fio = fio;
        this.position = position;
        this.dateEmployment = dateEmployment;
        this.dateLayoffs = dateLayoffs;
        this.education = education;
        this.educationEnd = educationEnd;
        this.status = status;
    }

   //1 работает 0 уволен


   

    public Employee(String organization, String fio, String position, String dateEmployment, String dateLayoffs, String educationEnd, Boolean status) {

    }

    public Employee() {

    }

    public boolean isStatus() {
        return status;
    }



    public void setStatus(boolean isStatus) {
        this.status = isStatus;
    }
}
