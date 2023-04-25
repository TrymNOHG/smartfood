package edu.ntnu.idatt2106_2023_06.backend.service.users;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface IEmailService {

    @Async
    void sendEmail(String subject, String receiver, String emailBody) throws MessagingException;

    @Async
    void sendActivationEmail(String receiver, String email) throws MessagingException;

    @Async
    void sendResetPassword(String receiver, String email) throws MessagingException;
}
