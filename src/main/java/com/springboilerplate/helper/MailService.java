package com.springboilerplate.helper;

import javax.mail.MessagingException;


public interface MailService {

    void sendMail(String email, String message, String subject) throws MessagingException;
}
