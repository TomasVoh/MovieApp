package org.example.movieapp.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.movieapp.Dto.PageDto;
import org.example.movieapp.Model.*;
import org.example.movieapp.Repository.MovieRepository;
import org.example.movieapp.Service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {
    private final MovieRepository movieRepository;
    private MovieService movieService;
    private GenreService genreService;
    private ReviewService reviewService;
    private UserEntityService userEntityService;
    private DirectorService directorService;
    private ActorService actorService;
    private MovieImportExportService movieImportExportService;
    private CountryService countryService;
    private ImageService imageService;
    private Logger logger = LoggerFactory.getLogger(MovieController.class);

    public MovieController(MovieService movieService, GenreService genreService, ReviewService reviewService, UserEntityService userEntityService, DirectorService directorService, ActorService actorService, MovieImportExportService movieImportExportService, CountryService countryService, ImageService imageService, MovieRepository movieRepository) {
        this.movieService = movieService;
        this.genreService = genreService;
        this.reviewService = reviewService;
        this.userEntityService = userEntityService;
        this.directorService = directorService;
        this.actorService = actorService;
        this.movieImportExportService = movieImportExportService;
        this.countryService = countryService;
        this.imageService = imageService;
        this.movieRepository = movieRepository;
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
    public String findMovieById(@PathVariable int id, Model model, Principal principal,
                                @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum,
                                @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        Movie movie = movieService.findById(id);
        PageDto<Review> reviews = reviewService.findByMovie(pageNum, pageSize, movie);
        BigDecimal averageRating = reviewService.findAverageRatingByMovie(id);
        if (principal != null) {
            UserEntity userEntity = userEntityService.findUserByEmail(principal.getName());
            model.addAttribute("user", userEntity);
        }
        model.addAttribute("movie", movie);
        model.addAttribute("rating", averageRating);
        model.addAttribute("reviews", reviews);
        return "movie-detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public String saveNewMoviePage(Model model) {
        Movie movie = new Movie();
        List<Actor> actors = actorService.findAll();
        List<Genre> genreList = genreService.findAll();
        List<Country> countries = countryService.findAll();
        List<Director> directors = directorService.findAll();
        model.addAttribute("directors", directors);
        model.addAttribute("countries", countries);
        model.addAttribute("movie", movie);
        model.addAttribute("actors", actors);
        model.addAttribute("genreList", genreList);
        return "movie-new";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public String saveMovie(@ModelAttribute("movie") Movie movie, @RequestParam("image") MultipartFile image) {
        String filePath = imageService.saveImage(image);
        movie.setImagePath(filePath);
        movieService.save(movie);
        return "redirect:/movie";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public String updateMoviePage(Model model, @PathVariable("id") long id) {
        Movie movie = movieService.findById(id);
        List<Actor> actors = actorService.findAll();
        List<Genre> genreList = genreService.findAll();
        List<Country> countries = countryService.findAll();
        List<Director> directors = directorService.findAll();
        model.addAttribute("movie", movie);
        model.addAttribute("directors", directors);
        model.addAttribute("countries", countries);
        model.addAttribute("actors", actors);
        model.addAttribute("genreList", genreList);
        return "movie-edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public String updateMovie(@PathVariable("id") long id, @ModelAttribute("movie") Movie movie) {
        movieRepository.save(movie);
        return String.format("redirect:/movie/%d", id);
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

    @GetMapping("/country/{id}")
    public String findMovieByCountry(@RequestParam(required = false, defaultValue = "0", name = "pageNum") int pageNum,
                                     @RequestParam(required = false, defaultValue = "25", name = "pageSize") int pageSize,
                                     @PathVariable int id, Model model) {
        Country country = countryService.findById(id);
        PageDto<Movie> movies = movieService.findMovieByCountry(id, pageNum, pageSize);
        model.addAttribute("country", country);
        model.addAttribute("movies", movies);
        return "movie-country";
    }

    @GetMapping("/{movieId}/review")
    @PreAuthorize("isAuthenticated()")
    public String saveReviewPage(@PathVariable("movieId") long id, Model model) {
        Review review = new Review();
        Movie movie = movieService.findById(id);
        model.addAttribute("review", review);
        model.addAttribute("movie", movie);
        return "review-new";
    }

    @PostMapping("/{movieId}/review")
    @PreAuthorize("isAuthenticated()")
    public String saveReview(@PathVariable("movieId") long id, @ModelAttribute("review") Review review, Principal principal) {
        UserEntity userEntity = userEntityService.findUserByEmail(principal.getName());
        Movie movie = movieService.findById(id);
        review.setMovie(movie);
        review.setUser(userEntity);
        reviewService.save(review);
        return String.format("redirect:/movie/%d", id);
    }

    @GetMapping("/export/excel")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EDITOR')")
    public void exportMoviesToExcel(HttpServletResponse res) {
        movieImportExportService.exportToExcel(res);
    }

    @PostMapping("/import/excel")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String importFromExcel(@RequestParam("file") MultipartFile file) {
        movieImportExportService.importFromExcel(file);
        return "redirect:/movie";
    }
}
