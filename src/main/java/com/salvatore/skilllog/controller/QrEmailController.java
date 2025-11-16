package com.salvatore.skilllog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salvatore.skilllog.service.EmailService;
import com.salvatore.skilllog.utils.QrCodeGenerator;

@RestController
public class QrEmailController {

    private final EmailService emailService;

    public QrEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/sendQr")
    public String sendQr(@RequestParam String email) {
        try {
            String url = "https://github.com/salvatoreadamo2023/skill-log/";
            byte[] qrCode = QrCodeGenerator.generateQrCode(url, 250, 250);

            emailService.sendEmailWithQrCode(
                email,
                "Visiona il mio progetto skill log",
                "Scansiona il QR code allegato per visionare il progetto",
                qrCode
            );

            return "Email inviata con QR code!";
        } catch (Exception e) {
            return "Errore: " + e.getMessage();
        }
    }
}
