package com.salvatore.skilllog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;



@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailWithQrCode(String to, String subject, String text, byte[] qrCodeImage) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        helper.addAttachment("qrcode.png", new org.springframework.core.io.ByteArrayResource(qrCodeImage));

        mailSender.send(message);
    }
}
