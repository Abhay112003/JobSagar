package com.Sagarjobs.Jobs.Companies;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

        @Autowired
        private JavaMailSender mailSender;

        public void sendmail(String toemail,String otp) throws Exception
        {
            MimeMessage message=mailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);

            helper.setFrom("JOBSAGAR123@gmail.com");
            helper.setTo(toemail);
            helper.setText("Your Otp is ",otp + " One Time  Password ");
            helper.setSubject("YOUR OTP CODE");
            mailSender.send(message);
        }
    }

