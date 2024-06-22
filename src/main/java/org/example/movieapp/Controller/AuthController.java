package org.example.movieapp.Controller;

import org.example.movieapp.Dto.RegisterDto;
import org.example.movieapp.Model.Genre;
import org.example.movieapp.Model.UserEntity;
import org.example.movieapp.Service.UserEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/auth/{id}")
    public String userDetailPage(Model model, @PathVariable("id") Long id) {
        UserEntity userEntity = userEntityService.findById(id);
        List<Map.Entry<Genre, Double>> genrePriority = userEntityService.findUsersGenreRatioList(userEntity.getEmail());
        model.addAttribute("genrePriority", genrePriority);
        model.addAttribute("user", userEntity);
        return "user-detail";
    }

    @PostMapping("/favourite/movie/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String addToFavouriteMovie(@PathVariable("id") long id, Principal principal) {
        userEntityService.addToFavouriteMovie(id, principal.getName());
        return String.format("redirect:/movie/%d", id);
    }

    @PostMapping("/rmFavourite/movie/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String removeFromFavouriteMovie(@PathVariable("id") long id, Principal principal) {
        userEntityService.removeFromFavouriteMovie(id, principal.getName());
        return String.format("redirect:/movie/%d", id);
    }

    @PostMapping("/favourite/actor/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String addToFavouriteActor(@PathVariable("id") long id, Principal principal) {
        userEntityService.addToFavouriteActor(id, principal.getName());
        return String.format("redirect:/actor/%d", id);
    }

    @PostMapping("/rmFavourite/actor/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String removeFromFavouriteActor(@PathVariable("id") long id, Principal principal) {
        userEntityService.removeFromFavouriteActor(id, principal.getName());
        return String.format("redirect:/actor/%d", id);
    }

    @PostMapping("/favourite/director/{id}")
    public String addToFavouriteDirector(@PathVariable("id") long id, Principal principal) {
        userEntityService.addToFavouriteDirector(id, principal.getName());
        return String.format("redirect:/director/%d", id);
    }

    @PostMapping("/rmFavourite/director/{id}")
    public String removeFromFavouriteDirector(@PathVariable("id") long id, Principal principal) {
        userEntityService.removeFromFavouriteDirector(id, principal.getName());
        return String.format("redirect:/director/%d", id);
    }
}
