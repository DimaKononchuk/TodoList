package com.example.todolist.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;


@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    public void sendSimpleMessage(String email,String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dimakononchuk12@gmail.com");
        message.setTo(email);
        message.setSubject("Verification code");
        message.setText(code);
        mailSender.send(message);

    }

    public void sendHTMLSimpleMessage(String email, Context context) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("dimakononchuk12@gmail.com");
//        message.setTo(email);
//        message.setSubject("Verification code");
//        message.setText(code);
//        mailSender.send(message);
        try {
            helper.setTo(email);
            helper.setSubject("Verification code");
            String htmlContent = templateEngine.process("email-content", context);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
