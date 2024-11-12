package com.trading.trading.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
private JavaMailSender mailSender;
public  void sendVerificationEmail(String email, String otp) throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
    String subject = "Verify OTP";
    String  text = "your verification code is " + otp;

    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(text);
    mimeMessageHelper.setTo(email);

    try {
        mailSender.send(mimeMessage);
    }catch (MailException e){
        throw new MessagingException("Failed to send verification email");
    }
}
}
