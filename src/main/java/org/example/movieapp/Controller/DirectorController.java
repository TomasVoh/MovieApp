package org.example.movieapp.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Model.*;
import org.example.movieapp.Model.Director;
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
@RequestMapping("/director")
public class DirectorController {
    private DirectorService directorService;
    private UserEntityService userEntityService;
    private ImageService imageService;
    private CountryService countryService;
    private MovieService movieService;
    private DirectorImportExportService directorImportExportService;

    public DirectorController(DirectorService directorService, UserEntityService userEntityService, ImageService imageService, CountryService countryService, MovieService movieService, DirectorImportExportService directorImportExportService) {
        this.directorService = directorService;
        this.userEntityService = userEntityService;
        this.imageService = imageService;
        this.countryService = countryService;
        this.movieService = movieService;
        this.directorImportExportService = directorImportExportService;
    }

    @GetMapping
    public String findDirectorPage(@RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
                                   @RequestParam(name = "pageSize", required = false, defaultValue = "25") int pageSize,
                                   @RequestParam(name = "name", required = false) String name,
                                   @RequestParam(name = "surname", required = false) String surname,
                                   @RequestParam(name = "startDate",  required = false) LocalDate startDate,
                                   @RequestParam(name = "endDate", required = false) LocalDate endDate,
                                   @RequestParam(name = "movies", required = false) List<Movie> movies,
                                   @RequestParam(name = "countries", required = false) List<Country> countries,
                                   Model model) {
        PageDto<Director> directorList = directorService.findDirectorsWithFilter(name, surname, startDate, endDate, movies, countries,pageNum, pageSize);
        List<Country> countryList = countryService.findAll();
        List<Movie> movieList = movieService.findAll();
        model.addAttribute("directorsPage", directorList);
        model.addAttribute("movies", movieList);
        model.addAttribute("moviesParam", movies);
        model.addAttribute("countries", countryList);
        model.addAttribute("countriesParam", countries);
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public String saveDirectorPage(Model model) {
        Director director = new Director();
        List<Country> countries = countryService.findAll();
        model.addAttribute("director", director);
        model.addAttribute("countryList", countries);
        return "director-new";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public String saveDirector(@ModelAttribute("actor") Director director, @RequestParam("image") MultipartFile file) {
        String path = imageService.saveImage(file);
        director.setImagePath(path);
        directorService.save(director);
        return "redirect:/director";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('EDITOR', 'ADMIN')")
    public String editDirectorPage(Model model, @PathVariable("id") long id) {
        Director director = directorService.findById(id);
        List<Country> countryList = countryService.findAll();
        model.addAttribute("countries", countryList);
        model.addAttribute("director", director);
        return "director-edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('EDITOR', 'ADMIN')")
    public String editDirector(@ModelAttribute("actor") Director director, @PathVariable("id") long id) {
        directorService.save(director);
        return String.format("redirect:/director/%d", id);
    }

    @GetMapping("/export/excel")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public void exportToExcel(HttpServletResponse response) {
        directorImportExportService.exportToExcel(response);
    }

    @PostMapping("import/excel")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public String importFromExcel(@RequestParam("file") MultipartFile file) {
        directorImportExportService.importFromExcel(file);
        return "redirect:/director";
    }
}
