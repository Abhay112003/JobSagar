package com.Sagarjobs.Jobs.Application;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ApplicationController {

 @Autowired
   private ApplicationRepository applicationRepository;


@GetMapping("/application")
    public String application(Model model)
   {
       List<ApplicationEntity> data = applicationRepository.findAll();
       model.addAttribute("ApplicationData",data);
       return "CompanyApplication";
   }

//   @RequestMapping("/applicationform")
//   public String application()
//   {
//       return "JobSeeker/Application";
//   }

 //Resume Display on Applied Show by id
   @GetMapping("/resume/view/{application_id}")
    public void showmodelImage(@PathVariable int application_id, HttpServletResponse response)throws Exception
   {
       ApplicationEntity app = applicationRepository.findById(application_id)
               .orElseThrow(() -> new RuntimeException("Application not found"));

       response.setContentType("application/pdf");
       response.setHeader("Content-Disposition", "inline; filename=resume.pdf");
       response.getOutputStream().write(app.getResume());
       response.getOutputStream().flush();
   }

//   @PostMapping("/SaveApplication")
//   @ResponseBody
//    public String Save(@RequestParam("file")MultipartFile file, @RequestParam("company") int company, @RequestParam("seeker") int seeker, @RequestParam("status")String status , @RequestParam("applied") LocalDate applied, @RequestParam("title")String jobtitle)throws Exception
//   {
//       ApplicationEntity application=new ApplicationEntity();
//       application.setCompanyId(company);
//       application.setSeeker_id(seeker);
//       application.setResume(file.getBytes());
//       application.setStatus(status);
//       application.setApplied_date(applied);
//      application.setTitle(jobtitle);
//      System.out.println(jobtitle);
//       applicationRepository.save(application);
//       System.out.println(application.getTitle());
//       return "save";
//   }
}
