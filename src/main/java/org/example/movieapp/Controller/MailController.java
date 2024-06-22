package org.example.movieapp.Controller;

import org.example.movieapp.Dto.RequestMailDto;
import org.example.movieapp.Service.MailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mail")
public class MailController {
    private MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public String sendMail(@ModelAttribute("mail") RequestMailDto requestMailDto) {
        mailService.sendEmail(requestMailDto.getSubject(), requestMailDto.getBody(), "vohradniktom@gmail.com");
        mailService.sendDefaultEmail(requestMailDto.getTo());
        return "redirect:/movie";
    }
}
