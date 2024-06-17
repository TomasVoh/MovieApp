package org.example.movieapp.Controller;

import org.example.movieapp.Dto.RevisionDto;
import org.example.movieapp.Model.Actor;
import org.example.movieapp.Model.Director;
import org.example.movieapp.Model.Movie;
import org.example.movieapp.Service.AuditService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/audit")
@Controller
public class AuditController {

    private AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/movie")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String findMovieAudits(Model model) {
        List<RevisionDto<Movie>> revisionEntityList = auditService.findMoviesAudit();
        model.addAttribute("revisionEntityList", revisionEntityList);
        return "audit-movie";
    }

    @GetMapping("/actor")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String findActorAudits(Model model) {
        List<RevisionDto<Actor>> revisionEntityList = auditService.findActorsAudit();
        model.addAttribute("revisionEntityList", revisionEntityList);
        return "audit-actor";
    }


    @GetMapping("/director")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String findDirectorAudits(Model model) {
        List<RevisionDto<Director>> revisionEntityList = auditService.findDirectorsAudit();
        model.addAttribute("revisionEntityList", revisionEntityList);
        return "audit-director";
    }
}
