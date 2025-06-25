package com.Sagarjobs.Jobs.CompaniesService;

import com.Sagarjobs.Jobs.Entity.CompanyEntities.JobEntity;
import com.Sagarjobs.Jobs.Repository.JobsRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanySaveService {

   @Autowired
    JobsRepositary jobsRepositary;

    //Save company profileData
   public void save(JobEntity job)
   {
       jobsRepositary.save(job);
   }

}
