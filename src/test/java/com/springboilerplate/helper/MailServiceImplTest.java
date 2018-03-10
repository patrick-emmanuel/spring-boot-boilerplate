package com.springboilerplate.helper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class MailServiceImplTest {

    @MockBean
    private AsyncMailer asyncMailer;
    @MockBean
    private JavaMailSender mailSender;

    private MailService mailService;

    @Before
    public void setUp() throws Exception {
        mailService = new MailServiceImpl(mailSender, asyncMailer);
        doNothing().when(asyncMailer).sendMail(any(MailData.class));
    }

    @Test
    public void sendMail() throws Exception {
        mailService.sendMail("mail", "message", "subject");

        verify(asyncMailer).sendMail(any(MailData.class));
    }

}