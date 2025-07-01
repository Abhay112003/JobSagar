package com.Sagarjobs.Jobs.Companies;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CompanyOtpService {

private final Map<String ,String> otpmap=new HashMap<>();


public String GenerateOtp(String email)
{
    int otpin = (int)(Math.random() * 900000) + 100000;
    String otp = String.valueOf(otpin);
       otpmap.put(email,otp);
       return otp;
}

public boolean VerifyOtp(String email,String otp)
{
     return otpmap.containsKey(email) && otpmap.get(email).equals(otp);
}

}
