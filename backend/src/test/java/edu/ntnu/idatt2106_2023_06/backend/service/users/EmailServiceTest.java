package edu.ntnu.idatt2106_2023_06.backend.service.users;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;


@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private TokenService tokenService;
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private Logger logger;

    @Test
    public void createLinkButton_success() {
        // Arrange
        EmailService emailService = new EmailService(tokenService, mailSender);
        String buttonText = "Test Button Text";
        String link = "http://example.com";

        // Act
        String buttonHtml = emailService.createLinkButton(buttonText, link);

        // Assert
        assertNotNull(buttonHtml);
        assertTrue(buttonHtml.contains("<a href=\"" + link + "\""));
        assertTrue(buttonHtml.contains(">" + buttonText + "</a>"));
    }

    @Test
    public void createEmailBody_success() {
        // Arrange
        EmailService emailService = new EmailService(tokenService, mailSender);
        String name = "Test User";
        String message = "Test message.";
        String buttonHtml = emailService.createLinkButton("Test Button Text", "http://example.com");

        // Act
        String emailBodyHtml = emailService.createEmailBody(name, message, buttonHtml);

        // Assert
        assertNotNull(emailBodyHtml);
        assertTrue(emailBodyHtml.contains("<h1>Activate Your SmartMat Account</h1>"));
        assertTrue(emailBodyHtml.contains("<p>Dear " + name + ",</p>"));
        assertTrue(emailBodyHtml.contains("<p>" + message + "</p>"));
        assertTrue(emailBodyHtml.contains(buttonHtml));
    }
}
