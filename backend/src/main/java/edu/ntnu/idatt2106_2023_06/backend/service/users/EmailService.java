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
            sendEmail("Activate your SmartMat account", user.getEmail(), createEmailBody(user.getUsername(), """
                To activate your account, click on the button below. The link will expire in 24 hours and an additional link will then need to be sent.
                """, createLinkButton("Activate Account", "http://localhost:5173/user/activate?token=" + token)));
        } catch (MessagingException e) {
            logger.error("Send of email was unsuccessful", e);
            throw new MessagingException("Activation Email was unsuccessful");
        }
    }

    @Async
    @Override
    public void sendResetPassword(User user) throws MessagingException {
        String token = UUID.randomUUID().toString();
        tokenService.saveToken(token, user);
        try {
            sendEmail("Reset Password", user.getEmail(), createEmailBody(user.getUsername(),
                    "Reset password message...", createLinkButton("Reset Password", "http://localhost:5173/user/reset-password?=%s" + token)));
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
    private String createLinkButton(String buttonText, String link) {
        return "<a href=\"" + link + "\" style=\"background-color: #008CBA; color: white; padding: 14px 25px; text-align: center; text-decoration: none; display: inline-block; border-radius: 5px; font-size: 16px; font-weight: bold;\">" + buttonText + "</a>";
    }

    /**
     * This method includes a generic email body with an embedded link.
     * @param name    The username of the person, represented as a String.
     * @param message The message to be sent, represented as a String.
     * @param button  The button link to the page, represented as a String.
     * @return        The actual HTML, represented as a String.
     */
    private String createEmailBody(String name, String message, String button) {
        StringBuilder body = new StringBuilder();
        body.append("<html><body>");
        body.append("<h1>Activate Your SmartMat Account</h1>");
        body.append("<hr>");
        body.append("<br>");
        body.append("<div style=\"font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #333;\">");
        body.append("<p>Dear " + name + ",</p>");
        body.append("<br>");
        body.append("<p>" + message + "</p>");
        body.append("<br><br>");
        body.append("<p style=\"text-align:center\">" + button + "</p>");
        body.append("</div>");
        body.append("</body></html>");
        return body.toString();
    }

}