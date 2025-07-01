package com.Sagarjobs.Jobs.Jobs;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Jobs {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
int job_id;
@Column(name = "company_id")
int companyId;
String jobtitle,description,location,min_salary,max_salary,experience,job_type,skills;
LocalDate dateposted;
boolean isenabled;
LocalDate deadline;

    public Jobs(int job_id, int companyId, String jobtitle, String description, String location, String min_salary, String max_salary, String experience, String job_type, String skills, LocalDate dateposted, boolean isenabled, LocalDate deadline) {
        this.job_id = job_id;
        this.companyId = companyId;
        this.jobtitle = jobtitle;
        this.description = description;
        this.location = location;
        this.min_salary = min_salary;
        this.max_salary = max_salary;
        this.experience = experience;
        this.job_type = job_type;
        this.skills = skills;
        this.dateposted = dateposted;
        this.isenabled = isenabled;
        this.deadline = deadline;
    }

    public Jobs() {
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMin_salary() {
        return min_salary;
    }

    public void setMin_salary(String min_salary) {
        this.min_salary = min_salary;
    }

    public String getMax_salary() {
        return max_salary;
    }

    public void setMax_salary(String max_salary) {
        this.max_salary = max_salary;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public LocalDate getDateposted() {
        return dateposted;
    }

    public void setDateposted(LocalDate dateposted) {
        this.dateposted = dateposted;
    }

    public boolean isIsenabled() {
        return isenabled;
    }

    public void setIsenabled(boolean isenabled) {
        this.isenabled = isenabled;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
