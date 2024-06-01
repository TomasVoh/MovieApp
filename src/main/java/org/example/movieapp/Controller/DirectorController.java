package org.example.movieapp.Controller;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Model.Director;
import org.example.movieapp.Model.Country;
import org.example.movieapp.Model.Director;
import org.example.movieapp.Model.UserEntity;
import org.example.movieapp.Service.CountryService;
import org.example.movieapp.Service.DirectorService;
import org.example.movieapp.Service.ImageService;
import org.example.movieapp.Service.UserEntityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/director")
public class DirectorController {
    private DirectorService directorService;
    private UserEntityService userEntityService;
    private ImageService imageService;
    private CountryService countryService;

    public DirectorController(DirectorService directorService, UserEntityService userEntityService, ImageService imageService, CountryService countryService) {
        this.directorService = directorService;
        this.userEntityService = userEntityService;
        this.imageService = imageService;
        this.countryService = countryService;
    }

    @GetMapping
    public String findDirectorPage(@RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
                                   @RequestParam(name = "pageSize", required = false, defaultValue = "25") int pageSize,
                                   Model model) {
        PageDto<Director> directorList = directorService.findAllByPage(pageNum, pageSize);
        model.addAttribute("directorsPage", directorList);
        return "director-list";
    }

    @GetMapping("/{id}")
    public String findDirectorById(@PathVariable int id, Model model, Principal principal) {
        Director director = directorService.findById(id);
        if(principal != null) {
            UserEntity userEntity = userEntityService.findUserByEmail(principal.getName());
            model.addAttribute("user", userEntity);
        }
        model.addAttribute("director", director);
        return "director-detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String saveDirectorPage(Model model) {
        Director director = new Director();
        List<Country> countries = countryService.findAll();
        model.addAttribute("director", director);
        model.addAttribute("countryList", countries);
        return "director-new";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String saveDirector(@ModelAttribute("actor") Director director, @RequestParam("image") MultipartFile file) {
        String path = imageService.saveImage(file);
        director.setImagePath(path);
        directorService.save(director);
        return "redirect:/director";
    }
}
