package com.springboilerplate.springboilerplate.helper;

import com.springboilerplate.springboilerplate.app.user.MailData;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class AsyncMailer {
    @Async
    public void sendMail(MailData mailData) throws MessagingException {
        mailData.sendMessage();
    }
}
