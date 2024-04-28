package org.example.movieapp.Controller;

import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Model.Genre;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Service.GenreService;
import org.example.movieapp.Service.ImageService;
import org.example.movieapp.Service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {
    private MovieService movieService;
    private GenreService genreService;
    private ImageService imageService;
    private Logger logger = LoggerFactory.getLogger(MovieController.class);

    public MovieController(MovieService movieService, GenreService genreService, ImageService imageService) {
        this.movieService = movieService;
        this.genreService = genreService;
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

    @GetMapping("/{id}")
    public String findMovieById(@PathVariable int id, Model model) {
        Movie movie = movieService.findById(id);
        model.addAttribute("movie", movie);
        return "movie-detail";
    }

    @GetMapping("/new")
    public String saveNewMoviePage(Model model) {
        Movie movie = new Movie();
        List<Genre> genreList = genreService.findAll();
        model.addAttribute("movie", movie);
        model.addAttribute("genreList", genreList);
        return "movie-new";
    }

    @PostMapping("/new")
    public String saveMovie(@ModelAttribute("movie") Movie movie, @RequestParam("image") MultipartFile image) {
        String filePath = imageService.saveImage(image);
        movie.setImagePath(filePath);
        movieService.save(movie);
        return "redirect:/movie";
     }

    @GetMapping("/genre/{id}")
    public String findMoviesByGenre(@RequestParam(required = false, defaultValue = "0", name = "pageNum") int pageNum,
                                    @RequestParam(required = false, defaultValue = "25", name = "pageSize") int pageSize,
                                    @PathVariable int id, Model model) {
        PageDto<Movie> movies = movieService.findMoviesByGenre(id, pageNum, pageSize);
        Genre genre = genreService.findById(id);
        model.addAttribute("genre", genre);
        model.addAttribute("movies", movies);
        return "movie-genre";
    }
}
