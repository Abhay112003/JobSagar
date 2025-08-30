package com.Sagarjobs.Jobs.Experiences;

import com.Sagarjobs.Jobs.JobSeeker.job_seekerEntity;
import com.Sagarjobs.Jobs.Jobs.Jobs;
import com.Sagarjobs.Jobs.Skills.SkillRepository;
import com.Sagarjobs.Jobs.Skills.skills;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
public class ExperienceController {

  @Autowired
    SkillRepository skillRepository;

  @Autowired
  ExperienceRepository experienceRepository;

@GetMapping("/experience")
public String Experience(Model model, HttpSession session)
{
     job_seekerEntity jobdata= (job_seekerEntity) session.getAttribute("seekerData");
     if(jobdata==null)
     {
         model.addAttribute("error","Session Expired !");
         return "JobSeeker/jobProfile";
     }
     model.addAttribute("seekerData",jobdata);
     session.setAttribute("seekerData",jobdata);
     model.addAttribute("experience",new ExperienceEntity());
     return "Experience";
}
@PostMapping("/SavedExperience")
 public String Saved(@Valid @ModelAttribute("experience") ExperienceEntity experience , BindingResult result,HttpSession session,Model model)
 {
     job_seekerEntity jobdata= (job_seekerEntity) session.getAttribute("seekerData");
     if(result.hasErrors()) {
         model.addAttribute("seekerData",jobdata);
         model.addAttribute("error","Error in Code");
           return "Experience";
       }
     ExperienceEntity experienceEntity =new ExperienceEntity();
     experienceEntity.setJobtitle(experience.getJobtitle());
     experienceEntity.setJobId(jobdata.getJobseeker_id());
     experienceEntity.setCompanyname(experience.getCompanyname());
     experienceEntity.setStartdate(experience.getStartdate());
     experienceEntity.setEnddate(experience.getEnddate());
     experienceEntity.setDescription(experience.getDescription());
     experienceRepository.save(experienceEntity);

     model.addAttribute("seekerData",jobdata);
     model.addAttribute("Saved","Saved Data");
     return "Experience";

 }

 @GetMapping("/DeleteExperience")
 public String Experience(@RequestParam("exp_id")int id, HttpSession session , Model model)
 {
   Optional<ExperienceEntity>deldata=experienceRepository.findById(id);

   job_seekerEntity jobdata= (job_seekerEntity) session.getAttribute("seekerData");
   List<ExperienceEntity>explist=experienceRepository.findByjobIdAndisenabled(jobdata.getJobseeker_id());

   if(jobdata == null || !deldata.isPresent())
   {
       model.addAttribute("error", "Session Expired. Login Again");
       model.addAttribute("seekerData",jobdata);
       model.addAttribute("experiencelist",explist);
       return "JobSeeker/jobProfile";
   }
   ExperienceEntity experienceEntity=deldata.get();

   experienceEntity.setIsenabled('F');
   experienceRepository.save(experienceEntity);

   model.addAttribute("seekerData",jobdata);
   //Referesh and get new Experience List
   List<ExperienceEntity>exlist=experienceRepository.findByjobIdAndisenabled(jobdata.getJobseeker_id());
   List<skills>skill=skillRepository.findByJobseekeridAndisenabled(jobdata.getJobseeker_id());
   model.addAttribute("skillData",skill);
   model.addAttribute("experiencelist",exlist);
   model.addAttribute("DeletedExperience","Experience Deleted");
   return "JobSeeker/jobProfile";
 }
}


