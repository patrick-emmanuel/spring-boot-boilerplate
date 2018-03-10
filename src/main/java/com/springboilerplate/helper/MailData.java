package com.springboilerplate.helper;

import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MailData {
    private static final String from = "noreply@mobstaff.com";
    private String to;
    private String subject;
    private String text;
    private String fileName;
    private InputStreamResource inputStreamResource;
    private JavaMailSender mailSender;

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStreamResource getInputStreamResource() {
        return this.inputStreamResource;
    }

    public void setInputStreamResource(InputStreamResource inputStreamResource) {
        this.inputStreamResource = inputStreamResource;
    }

    public MailData(String to, String subject, String text, JavaMailSender mailSender) {
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.mailSender = mailSender;
    }

    public MailData(String to, String subject, String text, JavaMailSender mailSender, String fileName, InputStreamResource inputStreamResource) {
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.mailSender = mailSender;
        this.fileName = fileName;
        this.inputStreamResource = inputStreamResource;
    }

    public MimeMessage toMimeMessage() throws MessagingException {
        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("noreply@boilerplate.com");
        helper.setTo(this.to);
        helper.setSubject(this.subject);
        helper.setText(this.text, true);
        if (this.inputStreamResource != null) {
            helper.addAttachment(this.fileName, this.inputStreamResource);
        }

        return message;
    }

    public void sendMessage() throws MessagingException {
        this.mailSender.send(this.toMimeMessage());
    }
}


