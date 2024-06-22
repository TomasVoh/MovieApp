package org.example.movieapp.Service.Impl;

import org.example.movieapp.Dto.RequestMailDto;
import org.example.movieapp.Service.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    private JavaMailSender mailSender;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String subject, String body, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vohradniktom@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @Override
    public void sendDefaultEmail(String to) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("vohradniktom@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("Dotaz");
        simpleMailMessage.setText("Dobrý den\nváš dotaz byl poslán na zpracování");
        mailSender.send(simpleMailMessage);
    }

}
