package com.Sagarjobs.Jobs.Entity.JobSeeker;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="job_seeker")
public class job_seekerEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
int jobseeker_id;
@Size(min = 2, max = 30, message = "Please enter a valid Name")
String name;
@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$" ,message = "Enter a valid email")
String email;
@Pattern(regexp = "^\\d{10}$",message = "Enter a valid contact")
String contact;
@Size(min = 10,max = 50 ,message = "Enter a Valid Address")
String address;
@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{8,}$",message = "Enter a Strong Password ")
String password;
String skill;
byte [] resume;
byte [] profile_picture;

    public job_seekerEntity(int jobseeker_id, String name, String email, String contact, String address, String password, String skill, byte[] resume, byte[] profile_picture) {
        this.jobseeker_id = jobseeker_id;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.password = password;
        this.skill = skill;
        this.resume = resume;
        this.profile_picture = profile_picture;
    }

    public job_seekerEntity() {
    }

    public int getJobseeker_id() {
        return jobseeker_id;
    }

    public void setJobseeker_id(int jobseeker_id) {
        this.jobseeker_id = jobseeker_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public byte[] getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(byte[] profile_picture) {
        this.profile_picture = profile_picture;
    }
}
