package com.springboilerplate.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class MailServiceImpl implements MailService{

    private JavaMailSender mailSender;
    private AsyncMailer asyncMailer;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender, AsyncMailer asyncMailer) {
        this.mailSender = mailSender;
        this.asyncMailer = asyncMailer;
    }

    @Override
    public void sendMail(String email, String message, String subject) throws MessagingException {
        MailData mailData = new MailData(email, subject, message, mailSender);
        asyncMailer.sendMail(mailData);
    }
}
