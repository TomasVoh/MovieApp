package org.example.movieapp.Controller;

import org.example.movieapp.Model.Movie;
import org.example.movieapp.Service.RecommendService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("isAuthenticated()")
    public String recommendPage(Model model, Principal principal) {
        return "recommend";
    }

    @GetMapping("/new")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Movie>> recommend(Principal principal) {
        List<Movie> movies = recommendService.findRecommendedMovies(principal.getName());
        return ResponseEntity.ok(movies);
    }

}
