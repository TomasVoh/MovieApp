package org.example.movieapp.Controller;

import lombok.extern.java.Log;
import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Service.ImageService;
import org.example.movieapp.Service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/movie")
public class MovieController {
    private MovieService movieService;
    private ImageService imageService;
    private Logger logger = LoggerFactory.getLogger(MovieController.class);

    public MovieController(MovieService movieService, ImageService imageService) {
        this.movieService = movieService;
        this.imageService = imageService;
    }

    @GetMapping
    public String findAllMovies(@RequestParam(required = false, defaultValue = "0", name = "pageNum") int pageNum,
                                @RequestParam(required = false, defaultValue = "25", name = "pageSize") int pageSize,
                                Model model) {
        PageDto<Movie> movies = movieService.findAllByPage(pageNum, pageSize);
        logger.trace("paged movies: {}", movies);
        model.addAttribute("movies", movies);
        return "movie-list";
    }

    @GetMapping("/new")
    public String saveNewMoviePage(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
        return "movie-new";
    }

    @PostMapping("/new")
    public String saveMovie(@ModelAttribute("movie") Movie movie, @RequestParam("image") MultipartFile image) {
        String filePath = imageService.saveImage(image);
        movie.setImagePath(filePath);
        movieService.save(movie);
        return "redirect:/movie";
     }
}
