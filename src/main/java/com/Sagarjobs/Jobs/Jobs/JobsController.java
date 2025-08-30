package com.Sagarjobs.Jobs.Jobs;

import com.Sagarjobs.Jobs.Companies.CompanyEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class JobsController {

 @Autowired
 JobsRepository jobsRepository;


    @GetMapping("/jobpost")
     public String job(Model model,HttpSession session)
    {
        model.addAttribute("jobs",new Jobs());
        return "Jobs/jobs";
    }

    @PostMapping("/company/savejob")
    @ResponseBody
    public String SaveJob(@ModelAttribute("jobs") Jobs jobs, HttpSession session)
    {
        CompanyEntity data= (CompanyEntity) session.getAttribute("comdata");
        int companyid=data.getCompany_id();
        System.out.println(companyid);
        Jobs jobs1=new Jobs();
        jobs1.setCompanyId(companyid);
         jobs1.setJobtitle(jobs.getJobtitle());
        jobs1.setDescription(jobs.getDescription());
        jobs1.setLocation(jobs.getLocation());
        jobs1.setMin_salary(jobs.getMin_salary());
        jobs1.setMax_salary(jobs.getMax_salary());
        jobs1.setExperience(jobs.getExperience());
        jobs1.setJob_type(jobs.getJob_type());
        jobs1.setSkills(jobs.getSkills());
        LocalDate date=LocalDate.now();
        jobs1.setDateposted(date);
        jobs1.setDeadline(jobs.getDeadline());
        jobs1.setIsenabled(true);
        jobsRepository.save(jobs1);
        return "save";
    }

}
