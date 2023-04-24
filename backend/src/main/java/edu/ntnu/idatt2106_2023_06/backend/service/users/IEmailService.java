package edu.ntnu.idatt2106_2023_06.backend.service.users;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface IEmailService {


    @Async
    void sendActivationEmail(String receiver, String token) throws MessagingException;

    @Async
    void sendResetPassword(String receiver, String token) throws MessagingException;
}
