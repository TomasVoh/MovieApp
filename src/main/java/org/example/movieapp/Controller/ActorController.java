package org.example.movieapp.Controller;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Model.Actor;
import org.example.movieapp.Service.ActorService;
import org.example.movieapp.Service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/actor")
public class ActorController {
    private ActorService actorService;
    private ImageService imageService;

    public ActorController(ActorService actorService, ImageService imageService) {
        this.actorService = actorService;
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

    @GetMapping("/new")
    public String saveActorPage(Model model) {
        Actor actor = new Actor();
        model.addAttribute("actor", actor);
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
