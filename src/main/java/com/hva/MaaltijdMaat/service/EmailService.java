package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Class for all different email templates.
 */
@Component
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    /**
     * Default email template for sending short messages with parameters.
     * @param to String email parameter where email will be send to.
     * @param subject The subject of the email.
     * @param text The contents of the mail in string format.
     */
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@maaltijdmaat.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    /**
     * Reset token email template.
     * @param token The created token for resetting password.
     * @param user The user object where email will be send to.
     */
    public void constructResetTokenEmail(String token, User user) {
        String url = "http://localhost:4200/forgot/" + token;
        String message = "Follow this link to change your password.\n The link is valid for 1 hour.";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Reset Password");
        email.setText(message + " \r\n" + url);
        email.setTo(user.getEmail());
        email.setFrom("noreply@maaltijdmaat.com");
        emailSender.send(email);
    }
}
