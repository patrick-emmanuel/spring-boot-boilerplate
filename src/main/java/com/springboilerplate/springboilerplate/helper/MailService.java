package com.springboilerplate.springboilerplate.helper;

import org.springframework.stereotype.Component;

import javax.mail.MessagingException;


public interface MailService {

    void sendMail(String email, String message, String subject) throws MessagingException;
}
