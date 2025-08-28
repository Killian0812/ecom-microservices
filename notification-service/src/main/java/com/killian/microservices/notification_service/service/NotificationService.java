package com.killian.microservices.notification_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.killian.microservices.notification_service.event.OrderPlacedEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent event) {
        log.info("Received OrderPlacedEvent for Order ID: {}", event);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("ecomshop@email.com");
            messageHelper.setTo(event.getEmail());
            messageHelper.setSubject(String.format("Your Order with OrderNumber %s is placed successfully",
                    event.getOrderNumber()));
            messageHelper.setText(String.format("""
                    Your order with order number %s is now placed successfully.

                    Best Regards
                    Ecom Shop
                    """,
                    event.getOrderNumber()));
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Order Notifcation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new RuntimeException("Exception occurred when sending mail to springshop@email.com", e);
        }
    }
}
