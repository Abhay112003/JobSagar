package com.Sagarjobs.Jobs.CompaniesController;

import com.Sagarjobs.Jobs.Repository.ApplicationRepository;
import com.Sagarjobs.Jobs.CompanyOtp.EmailService;
import com.Sagarjobs.Jobs.CompanyOtp.OtpService;
import com.Sagarjobs.Jobs.Entity.ApplicationEntity;
import com.Sagarjobs.Jobs.Entity.CompanyEntities.JobEntity;
import com.Sagarjobs.Jobs.CompaniesService.CompanySaveService;
import com.Sagarjobs.Jobs.CompaniesService.JobService;
import com.Sagarjobs.Jobs.Repository.JobsRepositary;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class JobController {

@Autowired
JobsRepositary jobsrepositary;

@Autowired
EmailService emailservice;

@Autowired
OtpService otpService;

@Autowired
JobService jobservice;

@Autowired
ApplicationRepository applicationRepository;

@Autowired
CompanySaveService companySaveService;

//call compnay Registration page
 @GetMapping("/CompanyRegistration")
 public String CompanyRegistration(Model model)
 {
     model.addAttribute("validate",new JobEntity());
     return "Company/CompanyRegistration";
 }

//otp create and send otp
 @PostMapping("/Otpverification")
public String OtpVerify(@Valid @ModelAttribute("validate") JobEntity validate, BindingResult result, HttpSession session,@RequestParam("logo")MultipartFile logo, Model model) throws Exception
{

        if (result.hasErrors()) {
            return "Company/CompanyRegistration";
        }
//            if(!logo.isEmpty())
//            {
//              String filename= UUID.randomUUID()+"_"+logo.getOriginalFilename();
//              Path path= Paths.get("uploads",filename);
//                Files.createDirectories(path.getParent());
//                Files.write(path,logo.getBytes());
//                validate.setLogopath(path.toString());
//             }
         else{
                validate.setFile(logo.getBytes());
                String otp = otpService.GenerateOtp(validate.getEmail());
                emailservice.sendmail(validate.getEmail(), otp);
                session.setAttribute("Data", validate);
                session.setAttribute("otp", otp);
               return "Company/CompanyOtpForm";
        }
}

@GetMapping("/ResendOtp")
public String Resend(HttpSession session,Model model) throws Exception
{

   JobEntity validate=(JobEntity) session.getAttribute("Data");
    if(session==null)
    {
        model.addAttribute("error","Session Expired Please Register again");
        return "Company/CompanyOtpForm";
    }

     String otp=otpService.GenerateOtp(validate.getEmail());
     emailservice.sendmail(validate.getEmail(),otp);
     session.setAttribute("otp", otp);
     model.addAttribute("message","A new OTP has been sent Successfully :");
     return "Company/CompanyOtpForm";
}


//otp confirmation and data save
 @PostMapping("/savedData")
  public String savedata(@ModelAttribute("validate") JobEntity validate,@RequestParam("otp")String userotp,Model model,HttpSession session) throws IOException {
     try {

        JobEntity job= (JobEntity) session.getAttribute("Data");
        String sessionotp= (String) session.getAttribute("otp");

        if(job==null || sessionotp==null)
        {
            model.addAttribute("error","Session Expired . please Register again");
            return "Company/CompanyRegistration";
        }

      if(!sessionotp.equals(userotp))
      {
          model.addAttribute("error","Please Enter a Valid Otp");
          return "Company/CompanyOtpForm";
      }


        companySaveService.save(job);
          session.removeAttribute("Data");
          session.removeAttribute("otp");

         return "success";

     }
      catch (Exception e) {
         System.out.println(e);
         return "ERROR";
     }
 }
    @GetMapping("/success")
    public String showSuccessPage() {
        return "Success"; // maps to success.html
    }

    //register and redirect loginpage
    @GetMapping("/login")
    public String login()
    {
       return "Company/CompanyLoginPage";
    }

    @RequestMapping("/LoginPage")
    public String loginn()
    {
        return "Company/CompanyLoginPage";
    }


    //check login user and return company dashboard
    @PostMapping("/loginpage")
    public String loginpage(@RequestParam("email") String email, @RequestParam("password")String password, Model model, HttpSession session)
    {
        Optional<JobEntity> job=jobsrepositary.findByEmailAndPassword(email,password);
        System.out.println("job"+job.isPresent());
        if(job.isPresent())
        {
            JobEntity loggincompany=job.get();
            session.setAttribute("loginInUser",loggincompany);

            //find application id who can applied in this compnay
            int company_id=loggincompany.getCompany_id();
            List <ApplicationEntity> list = applicationRepository.findByCompanyId(company_id);


            model.addAttribute("applicationdata", list);
            return "Company/CompanyApplication";

        }
        else
        {
            model.addAttribute("error","Invalid Email or Password");
            return "Company/CompanyLoginPage";
        }
    }

 //application
    @RequestMapping("/application")
    public String company()
    {
        return "Company/CompanyApplication";
    }
}

