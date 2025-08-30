package com.Sagarjobs.Jobs.JobSeeker;

import com.Sagarjobs.Jobs.Application.ApplicationEntity;
import com.Sagarjobs.Jobs.CompanyOtp.Seekerotpservice;
import com.Sagarjobs.Jobs.Experiences.ExperienceEntity;
import com.Sagarjobs.Jobs.Experiences.ExperienceRepository;
import com.Sagarjobs.Jobs.JobSeeker.job_seekerEntity;
import com.Sagarjobs.Jobs.JobSeeker.job_seekerService;
import com.Sagarjobs.Jobs.JobSeeker.jobseekerRepositary;
import com.Sagarjobs.Jobs.Skills.SkillRepository;
import com.Sagarjobs.Jobs.Skills.skills;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

@Controller
public class jobSeekerController {

@Autowired
jobseekerRepositary  job;

@Autowired
SkillRepository skillRepository;

@Autowired
    ExperienceRepository experienceRepository;
@Autowired
Seekerotpservice seekerotpservice;

@Autowired
job_seekerService jobSeekerService;

   @GetMapping("/loginseeker")
    public String form(Model model)
   {
       model.addAttribute("Data",new job_seekerEntity());
       return "JobSeeker/job_seeker";
   }

   @PostMapping("/SeekerOtpverification")
   public String otpVerification(@Valid @ModelAttribute("Data") job_seekerEntity Data, BindingResult result,@RequestParam("img")MultipartFile image,@RequestParam("img2")MultipartFile image2,HttpSession session)
   {
      if(result.hasErrors())
      {
          return "Jobseeker/job_seeker";
      }
      else
      {
          try {
              Data.setProfile_picture(image.getBytes());
              Data.setResume(image2.getBytes());
              Data.setResume(Data.getResume());
              String OTP = seekerotpservice.GenerateOtp(Data.getEmail());
              seekerotpservice.sendOTP(Data.getEmail(), OTP);
              session.setAttribute("Otp",OTP);
              session.setAttribute("data",Data);
               return "JobSeeker/OtpPage";
          }
          catch (Exception e)
          {
               return e.getMessage();
          }
      }
   }
   @GetMapping("/saveData")
   public String SaveData( @ModelAttribute("Data") job_seekerEntity Data, @RequestParam("otp") String userotp,HttpSession session,Model model) throws Exception
   {
        job_seekerEntity allData=(job_seekerEntity) session.getAttribute("data");
        String sessionotp= (String) session.getAttribute("Otp");
        System.out.println(sessionotp);
        if(allData==null || sessionotp==null)
        {
            model.addAttribute("error","Session expired ! please Register Again .");
            return "JobSeeker/OtpPage";
        }
       else
       {
        if(!sessionotp.equals(userotp))
        {
            model.addAttribute("error","Invalid OTP . Enter a Valid OTP");
            return "JobSeeker/OtpPage";
        }
        jobSeekerService.savealldata(allData);
        return "Save";
       }
   }

   //seeker Profile

    @GetMapping("/seekerLogin")
    public String LoginCall()
    {
        return "JobSeeker/seekerLogin";
    }

    @PostMapping("/jobProfile")
    public String Login(@RequestParam("email") String email, @RequestParam("password") String Password,Model model,HttpSession session)
    {
        Optional<job_seekerEntity> data =job.findByEmailAndPassword(email,Password);
        if(!data.isPresent())
        {
            model.addAttribute("error","Username and Password Invalid !");
            return "Jobseeker/seekerLogin";
        }
        List<skills> list=skillRepository.findByJobseekeridAndisenabled(data.get().jobseeker_id);
        List<ExperienceEntity> explist=experienceRepository.findByjobIdAndisenabled(data.get().jobseeker_id);

        session.setAttribute("experiencelist",explist);
        model.addAttribute("experiencelist",explist);
        System.out.println(explist.size());
        session.setAttribute("skillData",list);
        model.addAttribute("skillData",list);
        session.setAttribute("seekerobject",data);
         job_seekerEntity jobdata=data.get();
//       System.out.println(jobdata.getPassword());
        session.setAttribute("seekerData",jobdata);
        session.setAttribute("email",email);
        model.addAttribute("seekerData",jobdata);
        model.addAttribute("email",email);
         return "JobSeeker/jobProfile";
    }

    //Resume
    @GetMapping("/getProfile")
    public ResponseEntity<byte []> Resume(HttpSession session) {
        Optional<job_seekerEntity> jobsdata = (Optional<job_seekerEntity>) session.getAttribute("seekerobject");
        if (jobsdata == null) {
            ResponseEntity.notFound().build();
        }
        int id = jobsdata.get().getJobseeker_id();
        job_seekerEntity jobSeeker = job.findById(id).orElseThrow(null);
        if (jobSeeker == null || jobSeeker.getProfile_picture() == null)
        {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(jobSeeker.getProfile_picture());
    }
//    @GetMapping("/resume/view/{application_id}")
//    public void showmodelImage(@PathVariable int application_id, HttpServletResponse response)throws Exception
//    {
//        ApplicationEntity app = applicationRepository.findById(application_id)
//                .orElseThrow(() -> new RuntimeException("Application not found"));
//
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "inline; filename=resume.pdf");
//        response.getOutputStream().write(app.getResume());
//        response.getOutputStream().flush();
//    }
//
    @GetMapping("/resume/seeker/{jobseeker_id}")
    public void Resume(@PathVariable int jobseeker_id,HttpServletResponse response) throws IOException {
        job_seekerEntity app=job.findById(jobseeker_id)
                .orElseThrow(() -> new RuntimeException("Resume Not Uploaded :"));

        byte [] resumeData=app.getResume();
        if(resumeData==null || resumeData.length==0)
        {
            throw new RuntimeException("Resume Not Uploaded");
        }


        response.setContentType("application/pdf");
       response.setHeader("Content-Disposition","inline; filename=resume.pdf");
       response.setContentLength(resumeData.length);
        response.getOutputStream().write(app.getResume());
        response.getOutputStream().flush();
    }

    @GetMapping("/GetUpdate")
    public String EditForm(Model model ,HttpSession session)
    {
        job_seekerEntity data= (job_seekerEntity) session.getAttribute("seekerData");
        model.addAttribute("seekerData",data);
        return "Jobseeker/EditSeekerProfile";
    }
@PostMapping("/UpdateSeekerProfile")

    public String EditSeeker(@Valid @ModelAttribute("seekerData") job_seekerEntity Data ,BindingResult result, @ModelAttribute("file") MultipartFile file,HttpSession session ,Model model)throws IOException
    {
        if(result.hasErrors())
        {
            return "Jobseeker/EditSeekerProfile";
        }
        job_seekerEntity jobdata= (job_seekerEntity) session.getAttribute("seekerData");
        if(jobdata==null || Data==null)
        {
            model.addAttribute("error","Data is Not Get");
            return "JobSeeker/jobProfile";
        }
        int id= jobdata.getJobseeker_id();
        job_seekerEntity jobdata2=job.findById(id).orElse(null);
        if(jobdata2==null)
        {
            model.addAttribute("error","Data is Not Get");
            return "JobSeeker/jobProfile";
        }
        jobdata2.setName(Data.getName());
        jobdata2.setEmail(Data.getEmail());
        jobdata2.setAddress(Data.getAddress());
        jobdata2.setContact(Data.getContact());
        jobdata2.setResume(file.getBytes());
        job.save(jobdata2);
        session.setAttribute("seekerData",jobdata2);
        model.addAttribute("seekerData",jobdata2);
        model.addAttribute("Updated","UpdatedData");
        return "JobSeeker/jobProfile";
    }

}
