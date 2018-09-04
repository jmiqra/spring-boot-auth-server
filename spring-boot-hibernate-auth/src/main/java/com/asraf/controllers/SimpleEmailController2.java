package com.asraf.controllers;

import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleEmailController2 {
    
    @Autowired
    private JavaMailSender sender;

    @RequestMapping("/simpleemail2")
    @ResponseBody
    String home() {
        try {
            sendEmail();
            return "Email Sent!";
        }catch(Exception ex) {
            return "Error in sending email: "+ex;
        }
    }

    private void sendEmail() throws Exception{
        MimeMessage message = sender.createMimeMessage();
        
        // Enable the multipart flag!
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        
        helper.setTo("set-your-recipient-email-here@gmail.com");
        helper.setText("How are you?");
        helper.setSubject("Hi");
        
        ClassPathResource file = new ClassPathResource("cat.jpg");
        helper.addAttachment("cat.jpg", file);
        
        sender.send(message);
    }
}