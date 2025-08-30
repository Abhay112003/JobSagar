package com.Sagarjobs.Jobs.Jobs;

import com.Sagarjobs.Jobs.Companies.CompanyEntity;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class JobsController {

    @Autowired
    JobsRepository jobsRepository;


    @GetMapping("/jobpost")
    public String job(Model model, HttpSession session) {
        model.addAttribute("jobs", new Jobs());
        return "Jobs/jobs";
    }

    @PostMapping("/company/savejob")
    @ResponseBody
    public String SaveJob(@ModelAttribute("jobs") Jobs jobs, HttpSession session) {
        CompanyEntity data = (CompanyEntity) session.getAttribute("comdata");
        int companyid = data.getCompany_id();
        System.out.println(companyid);
        Jobs jobs1 = new Jobs();
        jobs1.setCompanyId(companyid);
        jobs1.setJobtitle(jobs.getJobtitle());
        jobs1.setDescription(jobs.getDescription());
        jobs1.setLocation(jobs.getLocation());
        jobs1.setMin_salary(jobs.getMin_salary());
        jobs1.setMax_salary(jobs.getMax_salary());
        jobs1.setExperience(jobs.getExperience());
        jobs1.setJob_type(jobs.getJob_type());
        jobs1.setSkills(jobs.getSkills());
        LocalDate date = LocalDate.now();
        jobs1.setDateposted(date);
        jobs1.setDeadline(jobs.getDeadline());
        jobs1.setIsenabled(true);
        jobsRepository.save(jobs1);
        return "save";
    }

    @GetMapping("/getshowalljobs")
    public String Alljobs(Model model, HttpSession session) {
        CompanyEntity company = (CompanyEntity) session.getAttribute("comdata");
        if (company == null) {
            model.addAttribute("error", "Id Not Found");
            return "Company/CompanyDashboard";
        }
        int cmpid = company.getCompany_id();
        List<Jobs> jobdata = jobsRepository.findByCompanyIdAndisenabled(cmpid);
        if (jobdata == null) {
            model.addAttribute("joberror", "JobData Not Found");
            return "Company/CompanyDashboard";
        }
        session.setAttribute("jobdata", jobdata);
        session.setAttribute("comdata", company);
        model.addAttribute("jobdata", jobdata);
        model.addAttribute("comdata", company);
        return "Jobs/AllJobs";
    }

    @GetMapping("/jobdelete")
    public String jobdelete(@RequestParam("jobid") int job_id, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        CompanyEntity company = (CompanyEntity) session.getAttribute("comdata");
        if (job_id == ' ' && company == null) {
            model.addAttribute("error", "Session Expired !! ");
            return "Jobs/Alljobs";
        }
        System.out.println(job_id);
        jobsRepository.disabledJob(job_id);
        List<Jobs> job = jobsRepository.findByCompanyIdAndisenabled(company.getCompany_id());
        model.addAttribute("comdata", company);
        model.addAttribute("jobdata", job);
        model.addAttribute("Deleted ", "Deleted Succefully");
        model.addAttribute("success", "Job Deleted Successfully");
        return "Jobs/AllJobs";
    }

    @GetMapping("/UpdateJobs")
    public String Update(@RequestParam("jobid") int jobid, Model model, HttpSession session) {
        CompanyEntity company = (CompanyEntity) session.getAttribute("comdata");
        Optional<Jobs> optional = jobsRepository.findById(jobid);
        if (!optional.isPresent()) {
            List<Jobs> Jobdata = (List<Jobs>) jobsRepository.findByCompanyIdAndisenabled(company.getCompany_id());
            session.setAttribute("jobdata", Jobdata);
            model.addAttribute("comdata", company);
            model.addAttribute("jobdata", Jobdata);
            return "Jobs/Alljobs";
        }
        model.addAttribute("data", optional.get());
        model.addAttribute("comdata", company);
        return "Jobs/UpdateJobs";
    }
    @PostMapping("/UpdatedData")
    public String UpdateJobs(@Valid @ModelAttribute("data") Jobs job , BindingResult result, @RequestParam("job_id") int job_id, HttpSession session, Model model) {
        if(result.hasErrors())
        {
            return "Jobs/UpdateJobs";
        }
        Optional<Jobs> jobid=jobsRepository.findById(job_id);
        Jobs jobs1=jobid.get();
        System.out.println(job.getJob_id());
        jobs1.setJobtitle(job.getJobtitle());
        jobs1.setDescription(job.getDescription());
        jobs1.setLocation(job.getLocation());
        jobs1.setMin_salary(job.getMin_salary());
        jobs1.setMax_salary(job.getMax_salary());
        jobs1.setExperience(job.getExperience());
        jobs1.setJob_type(job.getJob_type());
        jobs1.setDeadline(job.getDeadline());
        jobs1.setSkills(job.getSkills());
        jobs1.setIsenabled(true);
        jobsRepository.save(jobs1);
        CompanyEntity company1 = (CompanyEntity) session.getAttribute("comdata");
        List<Jobs> jobsList=jobsRepository.findByCompanyIdAndisenabled(company1.getCompany_id());
        session.setAttribute("comdata",company1);
        model.addAttribute("jobdata",jobsList);
        model.addAttribute("comdata",company1);
        model.addAttribute("UpdateJobs","updated Details");
        return "Jobs/Alljobs";

    }

}
