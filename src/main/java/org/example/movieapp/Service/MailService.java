package org.example.movieapp.Service;

import org.example.movieapp.Dto.RequestMailDto;

public interface MailService {
    void sendEmail(String subject, String body, String to);
    void sendDefaultEmail(String to);
}
