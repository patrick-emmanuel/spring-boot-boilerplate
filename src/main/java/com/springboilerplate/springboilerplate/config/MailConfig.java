package com.springboilerplate.springboilerplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender mailSender(Environment env) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("MOBSTAFF_MAIL_HOST"));
        mailSender.setPort(465);
        mailSender.setUsername("mobstaffwg@gmail.com");
        mailSender.setPassword("kingsley2000");
        mailSender.setProtocol("smtp");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        mailSender.setJavaMailProperties(props);
        return mailSender;
    }
}
