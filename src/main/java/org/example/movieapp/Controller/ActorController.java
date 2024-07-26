package org.example.movieapp.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Model.Actor;
import org.example.movieapp.Model.Country;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Model.UserEntity;
import org.example.movieapp.Service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/actor")
public class ActorController {
    private ActorService actorService;
    private UserEntityService userEntityService;
    private ActorImportExportService actorImportExportService;
    private CountryService countryService;
    private MovieService movieService;
    private ImageService imageService;

    public ActorController(ActorService actorService, UserEntityService userEntityService, ActorImportExportService actorImportExportService, CountryService countryService, MovieService movieService, ImageService imageService) {
        this.actorService = actorService;
        this.userEntityService = userEntityService;
        this.actorImportExportService = actorImportExportService;
        this.countryService = countryService;
        this.movieService = movieService;
        this.imageService = imageService;
    }

    @GetMapping
    public String findAllActors(@RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
                                @RequestParam(name = "pageSize", required = false, defaultValue = "25") int pageSize,
                                @RequestParam(name = "name", required = false) String name,
                                @RequestParam(name = "surname", required = false) String surname,
                                @RequestParam(name = "startDate", required = false) LocalDate startDate,
                                @RequestParam(name = "endDate", required = false) LocalDate endDate,
                                @RequestParam(name = "countries", required = false) List<Country> countries,
                                @RequestParam(name = "movies", required = false) List<Movie> movies,
                                Model model) {
        PageDto<Actor> actorPageDto = actorService.findActorsWithFilter(name, surname, startDate, endDate, countries, movies, pageNum, pageSize);
        List<Movie> movieList = movieService.findAll();
        List<Country> countryList = countryService.findAll();
        model.addAttribute("actorsPage", actorPageDto);
        model.addAttribute("movies", movieList);
        model.addAttribute("countries", countryList);
        model.addAttribute("countriesParam", countries);
        model.addAttribute("moviesParam", movies);
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

    @GetMapping("/edit/{id}")
    public String editActorPage(@PathVariable("id") long id, Model model) {
        Actor actor = actorService.findById(id);
        List<Country> countryList = countryService.findAll();
        model.addAttribute("actor", actor);
        model.addAttribute("countryList", countryList);
        return "actor-edit";
    }

    @PostMapping("/edit/{id}")
    public String editActor(@PathVariable("id") long id, @ModelAttribute("actor") Actor actor) {
        actorService.save(actor);
        return String.format("redirect:/actor/%d", id);
    }

    @GetMapping("/export/excel")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public void exportExcel(HttpServletResponse response) {
        actorImportExportService.exportToExcel(response);
    }

    @PostMapping("/import/excel")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public String importExcel(@RequestParam("file") MultipartFile file) {
        actorImportExportService.importFromExcel(file);
        return "redirect:/actor";
    }
}
