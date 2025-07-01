package com.Sagarjobs.Jobs.Companies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanySaveService {

   @Autowired
   JobRepositary jobsRepositary;

    //Save company profileData
   public void save(CompanyEntity job)
   {
       jobsRepositary.save(job);
   }

}
