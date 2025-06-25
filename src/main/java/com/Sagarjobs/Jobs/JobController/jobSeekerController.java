package com.Sagarjobs.Jobs.JobController;

import com.Sagarjobs.Jobs.Entity.JobSeeker.job_seekerEntity;
import com.Sagarjobs.Jobs.JobServices.job_seekerService;
import com.Sagarjobs.Jobs.Repository.jobseekerRepositary;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class jobSeekerController {

@Autowired
    jobseekerRepositary  job;


@Autowired
job_seekerService jobService;


   @GetMapping("/seekerform")
    public String form(Model model)
   {
       model.addAttribute("Data",new job_seekerEntity());
       return "JobSeeker/job_seeker";
   }

   @PostMapping("/saveData")
   public String SaveData(@Valid @ModelAttribute("Data") job_seekerEntity Data, BindingResult result, @RequestParam("img") MultipartFile file, @RequestParam("img2") MultipartFile file2, String name, String email, String contact , String address, String skill, String password) throws Exception
   {
       try{
        if(result.hasErrors())
            return "JobSeeker/job_seeker";
        else
        {
            Data.setName(name);
            Data.setResume(file2.getBytes());
            Data.setProfile_picture(file.getBytes());
            Data.setEmail(email);
            Data.setContact(contact);
            Data.setSkill(skill);
            Data.setPassword(password);
            Data.setAddress(address);
            job.save(Data);
            return "LoginPage";
        }
       }
       catch(Exception e)
       {
            return "ERROR"+e;
       }
   }

}
