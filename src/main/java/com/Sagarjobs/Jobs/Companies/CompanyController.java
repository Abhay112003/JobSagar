package com.Sagarjobs.Jobs.Companies;

//import com.Sagarjobs.Jobs.CompaniesService.JobService;
import com.Sagarjobs.Jobs.Jobs.JobsRepository;
import com.Sagarjobs.Jobs.Application.ApplicationRepository;
import com.Sagarjobs.Jobs.Application.ApplicationEntity;
//import com.Sagarjobs.Jobs.CompaniesService.JobService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
public class CompanyController {

@Autowired
CompanyRepositary companyRepositary;

@Autowired
EmailService emailservice;

@Autowired
CompanyOtpService otpService;

//@Autowired
//JobService jobservice;

@Autowired
ApplicationRepository applicationRepository;

@Autowired
CompanySaveService companySaveService;

//call compnay Registration page
 @GetMapping("/CompanyRegistration")
 public String CompanyRegistration(Model model)
 {
     model.addAttribute("validate",new CompanyEntity());
     return "Company/CompanyRegistration";
 }

@RequestMapping("/dashboard")
public String dashboard()
{
    return "Company/CompanyDashboard";
}


//otp create and send otp
@PostMapping("/Otpverification")
public String OtpVerify(@Valid @ModelAttribute("validate") CompanyEntity validate,
                        BindingResult result, // immediately after @Valid param
                        @RequestParam("logo") MultipartFile logo,
                        HttpSession session,
                        Model model) throws Exception
{

        if (result.hasErrors()) {
            return "Company/CompanyRegistration";
        }
//        if(jobsrepositary.findByEmail(validate.getEmail()).isPresent())
//        {
//         model.addAttribute("error","Email Already Exists : ");
//         return "Company/CompanyRegistration";
//        }
//            if(!logo.isEmpty())
//            {
//              String filename= UUID.randomUUID()+"_"+logo.getOriginalFilename();
//              Path path= Paths.get("uploads",filename);
//                Files.createDirectories(path.getParent());
//                Files.write(path,logo.getBytes());
//                validate.setLogopath(path.toString());
//             }

               validate.setFile(logo.getBytes());
                String otp = otpService.GenerateOtp(validate.getEmail());
                emailservice.sendmail(validate.getEmail(), otp);
                LocalTime currenttime=LocalTime.now();
                System.out.println(currenttime);
                LocalTime plustime=currenttime.plusMinutes(5);
                System.out.println(plustime);
                session.setAttribute("Data", validate);
                session.setAttribute("otp", otp);
                session.setAttribute("PlusTime",plustime);

               return "Company/CompanyOtpForm";

}

@GetMapping("/ResendOtp")
public String Resend(HttpSession session,Model model) throws Exception
{
    session.removeAttribute("PlusTime");

    LocalTime currtime=LocalTime.now();
    LocalTime plusTen=currtime.plusMinutes(5);
   CompanyEntity validate=(CompanyEntity) session.getAttribute("Data");
    if(session==null || validate==null)
    {
        model.addAttribute("error","Session Expired Please Register again");
        return "Company/CompanyOtpForm";
    }

     String otp=otpService.GenerateOtp(validate.getEmail());
     emailservice.sendmail(validate.getEmail(),otp);
     session.setAttribute("otp", otp);
     session.setAttribute("PlusTime",plusTen);
     System.out.println(currtime);
     System.out.println(otp);
     System.out.println(plusTen);
     model.addAttribute("message","A new OTP has been sent Successfully :");
     return "Company/CompanyOtpForm";
}


//otp confirmation and data save
 @PostMapping("/savedData")
  public String savedata(@ModelAttribute("validate") CompanyEntity validate, @RequestParam("otp")String userotp, Model model, HttpSession session) throws IOException {
     try {

        CompanyEntity job= (CompanyEntity) session.getAttribute("Data");
        String sessionotp= (String) session.getAttribute("otp");
        LocalTime time=(LocalTime) session.getAttribute("PlusTime");
         LocalTime currtime=LocalTime.now();
        if(job==null || sessionotp==null)
        {
            model.addAttribute("error","Session Expired . please Register again");
            model.addAttribute("validate",validate);
            return "Company/CompanyRegistration";
        }
      if(!sessionotp.equals(userotp))
      {
          model.addAttribute("error","Please Enter a Valid Otp");
          model.addAttribute("validate",validate);
          return "Company/CompanyOtpForm";
      }
      if(currtime.isAfter(time))
      {
           model.addAttribute("error","Otp Expired . Please request a new Otp");
          model.addAttribute("validate",validate);
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
    @PostMapping("/logindashboard")
    public String loginpage(@RequestParam("email") String email, @RequestParam("password")String password, Model model, HttpSession session)
    {
        Optional<CompanyEntity> job=companyRepositary.findByEmailAndPassword(email,password);
//        System.out.println("job"+job.isPresent());
        if(job.isPresent()) {
            int loggincompany = job.get().getCompany_id();
//            System.out.println(loggincompany);
            model.addAttribute("comdata",job.get());
            session.setAttribute("comdata",job.get());
            return "company/CompanyDashboard";

            //find application id who can applied in this compnay
//            int company_id = loggincompany.getCompany_id();
//            List<ApplicationEntity> list = applicationRepository.findByCompanyId(company_id);
//
//
//            model.addAttribute("applicationdata", list);
//            return "Company/CompanyApplication";

        }
//        }
        else
        {
            model.addAttribute("error","Invalid Email or Password");
            return "Company/CompanyLoginPage";
        }
    }

    //Getting Logo and insert into dashboard
  @GetMapping("/companylogo")
  public ResponseEntity<byte []> showlogo(HttpSession session)
  {
      CompanyEntity job= (CompanyEntity) session.getAttribute("comdata");
      if(job==null)
      {
          ResponseEntity.notFound().build();
      }
      int id=job.getCompany_id();

       CompanyEntity data=companyRepositary.findById(id).orElseThrow(null);
       if(data == null  || data.getFile()==null)
       {
           ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok()
               .contentType(MediaType.IMAGE_JPEG)
               .body(data.getFile());
  }
  //company aplication image
    @GetMapping("/companydata/image")
    public ResponseEntity<byte []> companyimage(HttpSession session)
    {
        CompanyEntity job= (CompanyEntity) session.getAttribute("companydata");
        if(job==null)
        {
            ResponseEntity.notFound().build();
        }
        int id=job.getCompany_id();
        CompanyEntity data=companyRepositary.findById(id).orElseThrow(null);
        if(data==null || data.getFile()==null)
        {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(data.getFile());
    }


    //jobs Repository
    @Autowired
 JobsRepository jobsRepositary;


  //companyapplication
    @GetMapping("/Companyapplication")
    public String CompanyApplication(HttpSession session,Model model)
    {
         CompanyEntity data= (CompanyEntity) session.getAttribute("comdata");
         int id=data.getCompany_id();

         List<ApplicationEntity> list=applicationRepository.findByCompanyId(id);//list of all application where this id found
         System.out.println(list);

         model.addAttribute("applicationdata",list);
         model.addAttribute("companydata",data);
         session.setAttribute("applicationdata",list);
         session.setAttribute("companydata",data);

          List<String> titledata=jobsRepositary.findDistinctJobTitlesByCompanyId(id);
          model.addAttribute("jobtitle",titledata);
          session.setAttribute("jobtitle",titledata);
          System.out.println(titledata);
         return "Company/CompanyApplication";
    }

    //application using filter
   @GetMapping("/view-applicants")
   public String Title(@RequestParam(name = "JobTitle" ,required = false ) String title,HttpSession session,Model model)
   {
     CompanyEntity data= (CompanyEntity) session.getAttribute("companydata");
     List<String> list= (List<String>) session.getAttribute("applicationdata");
     List<String> jobtitle= (List<String>) session.getAttribute("jobtitle");
     int companyid=data.getCompany_id();

     List<ApplicationEntity> applicationdata;

     if(title!=null && !title.isEmpty())
     {
         System.out.println(companyid);
         applicationdata=applicationRepository.findByCompanyIdAndTitle(companyid,title);
     }
     else
     {
         System.out.println(companyid);
         applicationdata=applicationRepository.findByCompanyId(companyid);
     }
     System.out.println(applicationdata);
     model.addAttribute("companydata",data);
     model.addAttribute("jobtitle",jobtitle);
     model.addAttribute("applicationdata",applicationdata);
       return "Company/CompanyApplication";
   }

//Company html page call to edit
    @RequestMapping("/editprofile")
    public String Edit(Model model,HttpSession session)
    {
        CompanyEntity comdata= (CompanyEntity) session.getAttribute("comdata");
        model.addAttribute("comdata",comdata);
        return "Company/CompanyEdit";
    }

    @PostMapping("/company/getcompanyupdate")
    public String editDone(@Valid @ModelAttribute("comdata") CompanyEntity data,BindingResult result,HttpSession session,Model model)
    {
        if(result.hasErrors())
            return "Company/CompanyEdit";
        else
        {
            CompanyEntity company= (CompanyEntity) session.getAttribute("comdata");
            int id=company.getCompany_id();
              System.out.println(id);
            CompanyEntity company2=companyRepositary.findById(id).orElse(null);

            if(company2==null)
            {
                model.addAttribute("error","ID Not Found");
                return "Company/CompanyDashboard";
            }
            company2.setName(data.getName());
            company2.setEmail(data.getEmail());
            company2.setAddress(data.getAddress());
            company2.setContact(data.getContact());
            company2.setDescription(data.getDescription());
            companyRepositary.save(company2);
            model.addAttribute("Update","Company Profile Update");
            session.setAttribute("comdata",company2);
            model.addAttribute("comdata",company2);
            return "Company/CompanyDashboard";
        }
    }


    //logout
    @GetMapping("/Company/logout")
    public String Logout(HttpSession session)
    {
        session.invalidate();
        return "Company/CompanyLoginPage";
    }


 //  application
    @RequestMapping("/application")
    public String company()
    {
        return "Company/CompanyApplication";
    }


    //forgetPassword
    @RequestMapping("/forgetpassword")
    public String ForgetPassword()
    {
        return "Company/CompanyForgetPassword";
    }
    @RequestMapping("/ForgetOtp")
    public String ForgetOtp(HttpSession session,@RequestParam("email") String email,Model model)throws Exception
    {
        System.out.println(email);
        Optional<CompanyEntity> company=companyRepositary.findByEmail(email);
        System.out.println(company);
        if(!company.isPresent())
        {
            model.addAttribute("Error","Email Not Register!");
        }
        String OTP=otpService.GenerateOtp(email);
        emailservice.sendmail(email,OTP);
        session.setAttribute("comdata",company);
        session.setAttribute("OTP",OTP);
        model.addAttribute("comdata",company);
        model.addAttribute("Send","OTP Send Successfully");
        return "Company/VerifyOtp";
    }
    @GetMapping("/Forgetresendotp")
    public String ResendOtp(HttpSession session,Model model) throws Exception
    {
        Optional<CompanyEntity>company= (Optional<CompanyEntity>) session.getAttribute("comdata");
         if(company==null)
         {
              model.addAttribute("error","Enter again Email");
         }
         session.removeAttribute("OTP");
         CompanyEntity company1=company.get();
        String OTP=otpService.GenerateOtp(company1.getEmail());
        emailservice.sendmail(company1.getEmail(),OTP);
        session.setAttribute("OTP",OTP);
        model.addAttribute("comdata",company);
        model.addAttribute("Resend","Resend Successfully");
        return "Company/VerifyOtp";
    }

    @GetMapping("/VerifyOtp")
    public String VerifyOtp(@RequestParam("otp") String otp,HttpSession session,Model model)
    {
        Optional<CompanyEntity> company =(Optional<CompanyEntity>)session.getAttribute("comdata");
        if(company==null)
        {
            model.addAttribute("error","Session Expired");
        }
        String Otp= (String) session.getAttribute("OTP");
        if(!otp.equals(Otp)) {
            model.addAttribute("error", "OTP Didn't Match");
        }
        CompanyEntity company1=company.get();
        model.addAttribute("comdata",company1);
        session.setAttribute("comdata",company1);
        return "Company/CompanyResetPassword";
    }

     @PostMapping("/UpdatePassword")
      public String UpdatePassword(@RequestParam("password") String password, HttpSession session, Model model)
     {
        CompanyEntity company= (CompanyEntity) session.getAttribute("comdata");
        System.out.println(company);
        if(company==null)
        {
            model.addAttribute("error","Session Expired ! Try Again..");
        }
        company.setPassword(password);
        companyRepositary.save(company);
        model.addAttribute("Update","Password Updated");
       return "Company/CompanyLoginPage";
     }
}

