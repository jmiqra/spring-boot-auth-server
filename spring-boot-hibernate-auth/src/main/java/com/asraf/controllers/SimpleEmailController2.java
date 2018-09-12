package com.asraf.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
public class SimpleEmailController2 {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration freemarkerConfig;
    
    @Autowired
    private TemplateEngine templateEngine;

    @RequestMapping("/simpleemail2")
    @ResponseBody
    String home() {
        try {
            sendEmail();
            return "Email Sent!";
        } catch (Exception ex) {
            return "Error in sending email: " + ex;
        }
    }

    private void sendEmail() throws Exception {
        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

//        Map<String, Object> model = new HashMap();
//        model.put("user", "iqrah");
        
        // set loading location to src/main/resources
        // You may want to use a subfolder such as /templates here
        //freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
       
        //Template t = freemarkerConfig.getTemplate("welcome.htm");
        //String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        
        Context context = new Context();
        context.setVariable("message", "Hello World!");
        context.setVariable("name", "Iqrah");
        
        String text = templateEngine.process("welcome.html", context);
        
        helper.setTo("jurdana.masuma@gmail.com");
        helper.setText(text, true);
        helper.setSubject("Hi");
    
        ClassPathResource file = new ClassPathResource("image.jpg");
        helper.addInline("id101", file);

        
        sender.send(message);
    }
}