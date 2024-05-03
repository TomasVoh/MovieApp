package org.example.movieapp.Controller;

import lombok.extern.java.Log;
import org.example.movieapp.Dto.RegisterDto;
import org.example.movieapp.Service.UserEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserEntityService userEntityService;
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        RegisterDto user = new RegisterDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegisterDto user) {
        logger.info("info registrace");
        userEntityService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
