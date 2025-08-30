package com.Sagarjobs.Jobs.CompanyOtp;


import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Seekerotpservice {

    @Autowired
    private  JavaMailSender javaMailSender;

    Map<String,String> map=new HashMap<>();
    public String GenerateOtp(String email)
    {
        int Otp=(int)(Math.random()*100000)+100000;
        String OTP=String.valueOf(Otp);
        map.put(email,OTP);
        return OTP;
    }

    public boolean checkOTP(String email,String OTP)
    {
        return map.containsKey(email) && map.get(email).equals(OTP);
    }
    public void sendOTP(String email,String body)throws Exception
    {
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);
        helper.setTo(email);
        helper.setFrom("abhayahir2003@gmail.com");
        helper.setSubject("OTP (One Time Password)");
        helper.setText("Your Otp is "+body+"(ONE TIME PASSWORD)");
        javaMailSender.send(message);
    }
}
