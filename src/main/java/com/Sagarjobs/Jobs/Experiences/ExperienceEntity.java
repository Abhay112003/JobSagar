package com.Sagarjobs.Jobs.Experiences;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "experience")
public class ExperienceEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
int exid;
@Column(name = "job_id")
int jobId;
@Size(min = 5 , message = "Enter a Valid Title")
String jobtitle;
@NotBlank(message = "Company Name is Required")
@Size(min=5 ,message = "Enter a Valid Company name")
String companyname;
@DateTimeFormat(pattern = "yyyy-MM-dd")
Date startdate,enddate;
@Size(min = 20, message = "Enter minimum 20 Word Description")
@Size(max = 100,message = "Enter maximum 100 Word Desciption")
String description;
char isenabled;

    public ExperienceEntity(int exid, int jobId, String jobtitle, String companyname, Date startdate, Date enddate, String description, char isenabled) {
        this.exid = exid;
        this.jobId = jobId;
        this.jobtitle = jobtitle;
        this.companyname = companyname;
        this.startdate = startdate;
        this.enddate = enddate;
        this.description = description;
        this.isenabled = isenabled;
    }

    public ExperienceEntity() {
    }

    public int getExid() {
        return exid;
    }

    public void setExid(int exid) {
        this.exid = exid;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public char getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(char isenabled) {
        this.isenabled = isenabled;
    }
}
