package edu.ntnu.idatt2106_2023_06.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


/**
 *  This class provides a configuration for the JavaMailSender, which is used to send email.
 *  It sets up the host, port, username, and password for the email server to be used.
 *  Additionally, it configures the properties for the email sender, such as whether to use
 *  starttls and the host and port to use for the SMTP server.
 *  The configuration is retrieved from the application.properties file using Spring's
 *  Value annotation.
 *
 * @author Trym Hamer Gudvangen
 */
@Configuration
//@Profile("!test")
public class MailConfig {

    /**
     *  The host of the email server.
     */
    @Value("${spring.mail.host}")
    private String host;

    /**
     *  The port of the email server.
     */
    @Value("${spring.mail.port}")
    private int port;

    /**
     *  The username used to authenticate with the email server.
     */
    @Value("${spring.mail.username}")
    private String username;

    /**
     *  The password used to authenticate with the email server.
     */
    @Value("${spring.mail.password}")
    private String password;

    /**
     *  Whether to enable starttls for the SMTP server.
     */
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttlsEnabled;

    /**
     * This method creates a new JavaMailSender bean with the configured properties.
     *
     * @return The newly created JavaMailSender
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", starttlsEnabled);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        return mailSender;
    }
}