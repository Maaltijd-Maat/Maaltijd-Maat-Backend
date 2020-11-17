package com.hva.MaaltijdMaat.service;

import com.hva.MaaltijdMaat.model.Invite;
import com.hva.MaaltijdMaat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

/**
 * Class for all different email templates.
 */
@Component
public class EmailService {
    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {this.emailSender = emailSender;}

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

    /**
     * Group invitation email template
     */
    public void constructGroupInvitationEmail(Invite invite) {
        String url = "http://localhost:4200/groups/invites/" + invite.getId();
        String message = String.format("%s invited you to join '%s'. Follow %s to accept the invitation.",
                invite.getInviter().getFirstname(),
                invite.getGroup().getName(),
                url);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Invitation to " + invite.getGroup().getName());
        email.setText(message);
        email.setTo(invite.getInvitee().getEmail());
        email.setFrom("noreply@maaltijdmaat.com");
        emailSender.send(email);
    }
}
