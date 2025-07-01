package com.Sagarjobs.Jobs.Application;

import com.Sagarjobs.Jobs.JobSeeker.job_seekerEntity;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

@Entity
@Table(name = "application")
public class ApplicationEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
int application_id;
@Column(name = "company_id")
int companyId;
@Column(insertable = false, updatable = false)
int seeker_id;
String status;
LocalDate applied_date;
byte [] resume;
String title;

    public ApplicationEntity(int application_id, int companyId, int seeker_id, String status, LocalDate applied_date, byte[] resume, String title) {
        this.application_id = application_id;
        this.companyId = companyId;
        this.seeker_id = seeker_id;
        this.status = status;
        this.applied_date = applied_date;
        this.resume = resume;
        this.title = title;
    }

    public ApplicationEntity() {
    }

    @ManyToOne
    @JoinColumn(name = "seeker_id", referencedColumnName = "jobseeker_id")
    private job_seekerEntity jobseeker;

    public int getApplication_id() {
        return application_id;
    }
    public job_seekerEntity getJobseeker() {
        return jobseeker;
    }

    public void setJobseeker(job_seekerEntity jobseeker) {
        this.jobseeker = jobseeker;
    }
    public void setApplication_id(int application_id) {
        this.application_id = application_id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getSeeker_id() {
        return seeker_id;
    }

    public void setSeeker_id(int seeker_id) {
        this.seeker_id = seeker_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getApplied_date() {
        return applied_date;
    }

    public void setApplied_date(LocalDate applied_date) {
        this.applied_date = applied_date;
    }

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
