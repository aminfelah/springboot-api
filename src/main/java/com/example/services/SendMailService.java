package com.example.services;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SendMailService {
	 @Autowired
	    private JavaMailSender mailSender;
	public void sendEmail(String recipientEmail, String link , String action)
            throws MessagingException, UnsupportedEncodingException {
        
		MimeMessage message = mailSender.createMimeMessage();              
        MimeMessageHelper helper = new MimeMessageHelper(message);
         
        helper.setFrom("backend@test.com", "backend Support");
        helper.setTo(recipientEmail);
         
        String subject = "Here's the link to "+action+" your account";
         
        String content = "<p>Hello,</p>"
                + "<p>You have requested to "+action+" your account.</p>"
                + "<p>Click the link below to " +action+" your account:</p>"
                + "<p><a href=\"" + link + "\">"+ action+ " my account</a></p>"
                + "<br>"
                + "<p>Ignore this email if you did not make an account, "
                + "or you have not made the request.</p>";
         
        helper.setSubject(subject);
         
        helper.setText(content, true);
         
        mailSender.send(message);
    }
}
