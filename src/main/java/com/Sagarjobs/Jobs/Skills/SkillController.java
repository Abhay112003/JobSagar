package com.Sagarjobs.Jobs.Skills;

import com.Sagarjobs.Jobs.Experiences.ExperienceEntity;
import com.Sagarjobs.Jobs.Experiences.ExperienceRepository;
import com.Sagarjobs.Jobs.JobSeeker.job_seekerEntity;
import com.Sagarjobs.Jobs.Jobs.Jobs;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class SkillController {

    @Autowired
    ExperienceRepository experienceRepository;
    @Autowired
    SkillRepository skillRepository;

    @GetMapping("/getskill")
    public String GetSkill(Model model,HttpSession session)
    {
        model.addAttribute("Data",new skills());
        job_seekerEntity jobData= (job_seekerEntity) session.getAttribute("seekerData");
        if(jobData == null)
        {
            model.addAttribute("error","Session Expired !");
            return "jobSeeker/jobProfile";
        }
        int id=jobData.getJobseeker_id();
        List<skills> list=skillRepository.findByJobseekeridAndisenabled(id);
        System.out.println(list);
        session.setAttribute("seekerData",jobData);
        session.setAttribute("skillData",list);
        model.addAttribute("skillData",list);
        model.addAttribute("seekerData",jobData);
        return "JobSeeker/Skills";
    }

    @PostMapping("/SkillSave")
    public String Skills(@Valid @ModelAttribute("Data") skills Data, BindingResult result, HttpSession session ,Model model)
    {
        if (result.hasErrors())
        {
            return "JobSeeker/Skills";
        }
        job_seekerEntity jobdata= (job_seekerEntity) session.getAttribute("seekerData");
        if(jobdata==null)
        {
            model.addAttribute("error","Session Invalid Login Again :");
            return "JobSeeker/jobProfile";
        }
        //nowSave in skillTable to database
        System.out.println(jobdata.getJobseeker_id());
        skills Data1=new skills();
        Data1.setSkill(Data.getSkill());
        Data1.setCategory(Data.getCategory());
        Data1.setIsenabled('t');
        Data1.setJobseekerid(jobdata.getJobseeker_id());
        skillRepository.save(Data1);

        List<skills> list=skillRepository.findByJobseekeridAndisenabled(jobdata.getJobseeker_id());
        session.setAttribute("skillData",list);
        model.addAttribute("skillData",list);
        session.setAttribute("seekerData",jobdata);
        model.addAttribute("seekerData",jobdata);
        model.addAttribute("Update","Saved Data");

        return "JobSeeker/Skills";
    }

    @GetMapping("/DeleteSkill")

    public String DeleteSkill(@RequestParam("skills_id") int id,HttpSession session,Model model)
    {
      Optional<skills> data=skillRepository.findById(id);

      job_seekerEntity seeker= (job_seekerEntity) session.getAttribute("seekerData");
      if(!data.isPresent() || seeker==null)
      {
          model.addAttribute("error","Session Expired");
      }
      skills newdata=data.get();
      newdata.setIsenabled('f');
      skillRepository.save(newdata);

      //Refresh Page
      List<skills> list=skillRepository.findByJobseekeridAndisenabled(seeker.getJobseeker_id());
      List<ExperienceEntity> expList=experienceRepository.findByjobIdAndisenabled(seeker.getJobseeker_id());
      System.out.println();
      session.setAttribute("skillData",list);
      session.setAttribute("seekerData",seeker);
      session.setAttribute("experiencelist",expList);

      model.addAttribute("experiencelist",expList);
      model.addAttribute("skillData",list);
      model.addAttribute("seekerData",seeker);
      model.addAttribute("Deleted","Skill Deleted");

      return "JobSeeker/jobProfile";
    }
}
