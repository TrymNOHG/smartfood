package edu.ntnu.idatt2106_2023_06.backend.service.users;

import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

import java.util.UUID;

/**
 * This class allows for emails to be sent to users regarding issues such as registration and password resetting.
 *
 * @author Trym Hamer Gudvangen
 */
@Service
@AllArgsConstructor
public class EmailService implements IEmailService {

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final static String GROUP_EMAIL = "IDATT2106_2023_06@stud.ntnu.no";
    private final TokenService tokenService;

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendActivationEmail(User user) throws MessagingException {
        String token = UUID.randomUUID().toString();
        tokenService.saveToken(token, user);
        try {
            sendEmail("Activate your SmartMat account", user.getEmail(), createEmailBodyWithLink(String.format("""
                   In order to activate you account, click the button below. The link will expire
                   in 24 hours and an additional link will then need to be sent.
                   """), String.format("http://localhost:5173/user/activate?token=%s", token)));
        } catch (MessagingException e) {
            logger.error("Send of email was unsuccessful", e);
            throw new MessagingException("Activation Email was unsuccessful");
        }
    }

    @Async
    @Override
    public void sendResetPassword(String receiver, String message) throws MessagingException {
        try {
            sendEmail("Reset Password", receiver, createEmailBodyWithLink(message, "http://localhost:5173/user/reset-password"));
        } catch (MessagingException e) {
            logger.error("Send of email was unsuccessful", e);
            throw new MessagingException("Reset Password Email was unsuccessful");
        }
    }

    private void sendEmail(String subject, String receiver, String emailBody) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(emailBody, true);
        helper.setTo(receiver);
        helper.setSubject(subject);
        helper.setFrom(GROUP_EMAIL);
        mailSender.send(mimeMessage);
    }

    /**
     * This method creates an HTML button that links to the provided URL.
     * @param link  The URL that the button should link to.
     * @return      The HTML button, represented as a String.
     */
    private String createLinkButton(String link) {
        StringBuilder button = new StringBuilder();
        button.append("<a href=\"").append(link).append("\" style=\"display: inline-block; padding: 10px; background-color: #007bff; color: #fff; text-decoration: none;\">")
                .append("Click here to proceed")
                .append("</a>");
        return button.toString();
    }

    /**
     * This method includes a generic email body with an embedded link.
     * @param message The message to be sent, represented as a String.
     * @param link    The link to the page, represented as a String.
     * @return        The actual HTML, represented as a String.
     */
    private String createEmailBodyWithLink(String message, String link) {
        StringBuilder body = new StringBuilder();
        body.append("<html><body>");
        body.append("<h2>Thank you for using SmartFood!</h2>");
        body.append("<p>").append(message).append("</p>");
        if (link != null && !link.isEmpty()) {
            String linkButton = createLinkButton(link);
            body.append("<p>").append(linkButton).append("</p>");
        }
        body.append("</body></html>");
        return body.toString();
    }

}