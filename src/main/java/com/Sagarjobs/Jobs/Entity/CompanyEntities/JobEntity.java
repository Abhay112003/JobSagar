package com.Sagarjobs.Jobs.Entity.CompanyEntities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "companies")
public class JobEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
int company_id;
@Size(min = 2,message = "Please enter a valid name")
String name;
@Pattern(regexp = "^\\d{10}$",message = "Enter a valid contact")
String contact;
@Email(message = "Enter valid Email")
String email;
@Size(min=5,message="Enter a valid Address")
String address;
String company_type;
@Size(min=10,message = "Minimum 10 Words")
String description;
@Size(min =8 ,message = "Minimun 8 Character")
String password;
@Column(name = "company_logo")
byte [] file;


    public JobEntity(int company_id, String name, String contact, String email, String address, String company_type, String description, String password, byte[] file) {
        this.company_id = company_id;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.company_type = company_type;
        this.description = description;
        this.password = password;
        this.file = file;
    }

    public JobEntity() {
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany_type() {
        return company_type;
    }

    public void setCompany_type(String company_type) {
        this.company_type = company_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

}
