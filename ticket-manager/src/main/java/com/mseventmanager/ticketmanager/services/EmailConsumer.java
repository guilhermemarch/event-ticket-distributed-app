package com.mseventmanager.ticketmanager.services;

import com.mseventmanager.ticketmanager.dto.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "emailQueue", messageConverter = "jackson2JsonMessageConverter")
    public void processEmail(EmailMessage emailMessage) {
        try {
            emailService.sendEmail(emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getBody());
        } catch (Exception e) {
            logger.error("Erro ao enviar e-mail: {}", e.getMessage());
        }
    }
}
