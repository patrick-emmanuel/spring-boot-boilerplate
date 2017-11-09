package com.springboilerplate.springboilerplate.mocks;

import com.springboilerplate.springboilerplate.helper.MailService;
import org.mockito.Mockito;

import javax.mail.MessagingException;

import static org.mockito.Matchers.anyString;

public class MailServiceMocks {
    public void initMocks(MailService mailService) throws MessagingException {
        Mockito.doNothing().when(mailService).sendMail(anyString(), anyString(), anyString());
    }
}
