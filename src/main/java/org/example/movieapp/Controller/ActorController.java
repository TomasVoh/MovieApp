package org.example.movieapp.Controller;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Model.Actor;
import org.example.movieapp.Model.Country;
import org.example.movieapp.Model.UserEntity;
import org.example.movieapp.Service.ActorService;
import org.example.movieapp.Service.CountryService;
import org.example.movieapp.Service.ImageService;
import org.example.movieapp.Service.UserEntityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/actor")
public class ActorController {
    private ActorService actorService;
    private UserEntityService userEntityService;
    private CountryService countryService;
    private ImageService imageService;

    public ActorController(ActorService actorService, UserEntityService userEntityService, CountryService countryService, ImageService imageService) {
        this.actorService = actorService;
        this.userEntityService = userEntityService;
        this.countryService = countryService;
        this.imageService = imageService;
    }

    @GetMapping
    public String findAllActors(@RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
                                @RequestParam(name = "pageSize", required = false, defaultValue = "25") int pageSize,
                                Model model) {
        PageDto<Actor> actorPageDto = actorService.findAllByPage(pageNum, pageSize);
        model.addAttribute("actorsPage", actorPageDto);
        return "actors-list";
    }

    @GetMapping("/{id}")
    public String findActorById(@PathVariable("id") int id, Model model, Principal principal) {
        Actor actor = actorService.findById(id);
        UserEntity user = userEntityService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("actor", actor);
        return "actor-detail";
    }

    @GetMapping("/new")
    public String saveActorPage(Model model) {
        Actor actor = new Actor();
        List<Country> countryList = countryService.findAll();
        model.addAttribute("actor", actor);
        model.addAttribute("countryList", countryList);
        return "actor-new";
    }

    @PostMapping("/new")
    public String saveActor(@ModelAttribute("actor") Actor actor, @RequestParam("image") MultipartFile file) {
        String path = imageService.saveImage(file);
        actor.setImagePath(path);
        actorService.save(actor);
        return "redirect:/actor";
    }
}
