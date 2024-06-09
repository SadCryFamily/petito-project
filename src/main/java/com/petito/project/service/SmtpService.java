package com.petito.project.service;

import com.petito.project.entity.order.Order;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class SmtpService
{
    private static final Logger logger = LogManager.getLogger(SmtpService.class);
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String SENDER_MAIL_ADDRESS;

    public void registrationNotify(String email, String firstname)
    {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            mimeMessageHelper.setFrom(SENDER_MAIL_ADDRESS);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText(generateMessage(firstname));
            mimeMessageHelper.setSubject("Petito Registration");

            mailSender.send(mimeMessage);

        } catch (Exception e)
        {
            logger.error("Exception during SMTP request", e);
        }
    }

    public void orderArrivedNotify(String email, String firstname, Order order)
    {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            mimeMessageHelper.setFrom(SENDER_MAIL_ADDRESS);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText(generateArriveMessage(firstname, order));
            mimeMessageHelper.setSubject("Petito");

            mailSender.send(mimeMessage);

        } catch (Exception e)
        {
            logger.error("Exception during SMTP request", e);
        }
    }

    private String generateMessage(String username)
    {
        StringBuilder messageBuffer = new StringBuilder();
        String greetingMessage = String.format("Hello, %s", username);

        messageBuffer.append(greetingMessage);
        messageBuffer.append(System.lineSeparator());
        messageBuffer.append("We welcome you to the animal lovers community at petito.com!");
        messageBuffer.append("Your account has been successfully created,");
        messageBuffer.append("and now you have become part of our friendly world,");
        messageBuffer.append("where there is love for thousands of people.");

        return messageBuffer.toString();
    }

    private String generateArriveMessage(String username, Order order)
    {
        StringBuilder messageBuffer = new StringBuilder();
        String greetingMessage = String.format("Hello, %s", username);

        String arrivedMessage = String.format("Your order for product [%s] arrived!",
                order.getProducts().get(0).getProductType());

        messageBuffer.append(greetingMessage);
        messageBuffer.append(System.lineSeparator());
        messageBuffer.append(arrivedMessage);

        return messageBuffer.toString();
    }

}
