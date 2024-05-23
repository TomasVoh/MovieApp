package org.example.movieapp.Controller;

import org.example.movieapp.Model.Movie;
import org.example.movieapp.Service.RecommendService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/recommend")
public class RecommendController {
    private RecommendService recommendService;

    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @GetMapping("")
    public String recommendPage(Model model, Principal principal) {
        List<Movie> movies = recommendService.findRecommendedMovies(principal.getName());
        model.addAttribute("movies", movies);
        return "recommend";
    }

}
