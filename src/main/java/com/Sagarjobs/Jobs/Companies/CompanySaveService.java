package com.Sagarjobs.Jobs.Companies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class CompanySaveService {

   @Autowired
   CompanyRepositary companyRepositary;

    //Save company profileData
   public void save(@ModelAttribute("") CompanyEntity job)
   {
       companyRepositary.save(job);
   }

}
