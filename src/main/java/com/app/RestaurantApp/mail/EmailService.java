package com.app.RestaurantApp.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final int NUM_OF_QUICK_SERVICE_THREADS = 10;

    private final ScheduledExecutorService quickService = Executors.newScheduledThreadPool(NUM_OF_QUICK_SERVICE_THREADS);

    public void sendEmail(EmailContent content) {
        String[] emailIds = new String[content.getRecipients().size()];
        for (int i = 0; i < content.getRecipients().size(); i++) {
            emailIds[i] = content.getRecipients().get(i);
        }

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emailIds);
        email.setSubject(content.getSubject());
        email.setText(content.getBody());
        quickService.submit(() -> javaMailSender.send(email));
    }

}
