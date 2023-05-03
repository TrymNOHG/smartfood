package edu.ntnu.idatt2106_2023_06.backend.service.users;

import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import jakarta.mail.MessagingException;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

public interface IEmailService {


    @Async
    void sendActivationEmail(User user) throws MessagingException;

    @Async
    void sendResetPassword(User user) throws MessagingException;
}
