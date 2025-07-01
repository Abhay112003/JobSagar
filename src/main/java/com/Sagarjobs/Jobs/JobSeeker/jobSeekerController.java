//package com.Sagarjobs.Jobs.JobController;
//
//import com.Sagarjobs.Jobs.CompanyOtp.Seekerotpservice;
//import com.Sagarjobs.Jobs.Entity.JobSeeker.job_seekerEntity;
//import com.Sagarjobs.Jobs.JobServices.job_seekerService;
//import com.Sagarjobs.Jobs.JobSeeker.jobseekerRepositary;
//import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@Controller
//public class jobSeekerController {
//
//@Autowired
//    jobseekerRepositary  job;
//
//@Autowired
//    Seekerotpservice seekerotpservice;
//
//@Autowired
//job_seekerService jobService;
//
//
//   @GetMapping("/seekerform")
//    public String form(Model model)
//   {
//       model.addAttribute("Data",new job_seekerEntity());
//       return "JobSeeker/job_seeker";
//   }
//
//   @PostMapping("/SeekerOtpverification")
//   public String otpVerification(@Valid @ModelAttribute("Data") job_seekerEntity Data, BindingResult result,@RequestParam("img")MultipartFile image,@RequestParam("img2")MultipartFile image2,HttpSession session)
//   {
//      if(result.hasErrors())
//      {
//          return "Jobseeker/job_seeker";
//      }
//      else
//      {
//          try {
//              Data.setProfile_picture(image.getBytes());
//              Data.setResume(image2.getBytes());
//              Data.setResume(Data.getResume());
//              String OTP = seekerotpservice.GenerateOtp(Data.getEmail());
//              seekerotpservice.sendOTP(Data.getEmail(), OTP);
//              session.setAttribute("Otp",OTP);
//              session.setAttribute("data",Data);
//               return "JobSeeker/OtpPage";
//          }
//          catch (Exception e)
//          {
//               return e.getMessage();
//          }
//      }
//   }
//   @GetMapping("/saveData")
//   public String SaveData( @ModelAttribute("Data") job_seekerEntity Data, @RequestParam("otp") String userotp,HttpSession session,Model model) throws Exception
//   {
//        job_seekerEntity allData=(job_seekerEntity) session.getAttribute("data");
//        String sessionotp= (String) session.getAttribute("Otp");
//        System.out.println(sessionotp);
//        if(allData==null || sessionotp==null)
//        {
//            model.addAttribute("error","Session expired ! please Register Again .");
//            return "JobSeeker/OtpPage";
//        }
//       else
//       {
//        if(!sessionotp.equals(userotp))
//        {
//            model.addAttribute("error","Invalid OTP . Enter a Valid OTP");
//            return "JobSeeker/OtpPage";
//        }
//
//        jobService.savealldata(allData);
//        return "Save";
//       }
//   }
//
//}
