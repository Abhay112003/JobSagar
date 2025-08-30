//package com.Sagarjobs.Jobs.CompaniesService;
//
//import com.Sagarjobs.Jobs.CompaniesController.JobEntity;
//import com.Sagarjobs.Jobs.Repository.JobsRepositary;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.IOException;
//
//@Service
//public class JobService {
//@Autowired
//JobsRepositary jobsrepositary;
//
//public JobEntity Upload(MultipartFile file, String name, String email, String address, String company_type, String contact, String description, String password) throws IOException {
//  JobEntity job=new JobEntity();
//  job.setName(name);
//  job.setEmail(email);
//  job.setAddress(address);
//  job.setCompany_type(company_type);
//  job.setContact(contact);
//  job.setDescription(description);
//  job.setPassword(password);
//  job.setFile(file.getBytes());
//  return jobsrepositary.save(job);
//}

//}
