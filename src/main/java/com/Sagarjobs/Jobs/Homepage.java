package com.Sagarjobs.Jobs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Homepage {

@GetMapping("/home")
    public String homepage()
{
     return "Homepage/Homecenter";
}

@GetMapping("/homepage")
    public String HomePage()
{
    return "Homepage/Homecenter";
}

@RequestMapping("/contactus")
    public String Contact()
{
     return "Homepage/contactus";
}

@RequestMapping("/termandcondition")
  public String termAndCondition()
  {
      return "Homepage/termsandconditions";
  }

}
