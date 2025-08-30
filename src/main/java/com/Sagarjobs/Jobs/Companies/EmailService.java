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

        public void sendmail(String toemail,String otp,String companyName) throws Exception
        {
            MimeMessage message=mailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);

            helper.setFrom("JOBSAGAR123@gmail.com");
            helper.setTo(toemail);
            String htmlBody ="HI <span>User</span>"+"<p>Thankyou for registering with <span> "+companyName+"</span>  </p>"
                             +"<p>To continue your registration ,please use the following otp</p>"
                     +"<h2 style='color: #4CAF50; text-align: center;'><span>"+otp+"</span> </h2>"
                    +"This OTP is valid for the next <strong>10 minutes</strong>. If you did not request this, please ignore this email or contact our support team."+
                    "Need help? Reach out to us at <span>contactjobsagar@gmail.com</span>"+
                    "<h1>Welcome aboard</h1>"+
                    "<h1><span>"+companyName+"<span><h1>";
            helper.setText(htmlBody,true);
            helper.setSubject("OTP (One Time Password)");
            mailSender.send(message);
        }
    public void RegistrationSuccess(String toemail,String companyName) throws Exception
    {
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);

        helper.setFrom("JOBSAGAR123@gmail.com");
        helper.setTo(toemail);
        String htmlBody="Hi <span>User</span><br>"+
                "<p>Thank you for registering with <span>"+companyName +"</span> We're excited to have you on board. Your account is ready, and we look forward to helping you make the most of our services.</p>"+
                "<p>If you have any questions, feel free to reach out to us at <span>contactjobsagar@gmail.com</span></p>"+
                "<h3>Welcome aboard!</h3><br>" +
                "<h3>"+companyName+"</h3>"+
                "<h4>"+toemail+"</h4>";
        helper.setText(htmlBody,true);
        helper.setSubject("Registration Successful");
        mailSender.send(message);
    }
    }

