package com.Sagarjobs.Jobs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Homepage {

@RequestMapping("/home")
    public String homepage()
{
     return "Homepage/ProjectHome_Page";
}

@RequestMapping("/homepage")
    public String HomePage()
{
    return "Homepage/ProjectHome_Page";
}

@RequestMapping("/contactus")
    public String Contact()
{
     return "Homepage/contactus";
}

}
