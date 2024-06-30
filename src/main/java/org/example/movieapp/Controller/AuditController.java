package org.example.movieapp.Controller;

import org.example.movieapp.Dto.PageDto;
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
import org.springframework.web.bind.annotation.RequestParam;

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
    public String findMovieAudits(Model model,
                                  @RequestParam(name = "pageNum", defaultValue = "0", required = false) int page,
                                  @RequestParam(name = "pageSize", defaultValue = "25", required = false) int size) {
        PageDto<RevisionDto<Movie>> revisionEntityList = auditService.findMoviesAudit(page, size);
        model.addAttribute("revisionEntityList", revisionEntityList);
        return "audit-movie";
    }

    @GetMapping("/actor")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String findActorAudits(Model model,
                                  @RequestParam(name = "pageNum", defaultValue = "0", required = false) int page,
                                  @RequestParam(name = "pageSize", defaultValue = "25", required = false) int size) {
        PageDto<RevisionDto<Actor>> revisionEntityList = auditService.findActorsAudit(page, size);
        model.addAttribute("revisionEntityList", revisionEntityList);
        return "audit-actor";
    }


    @GetMapping("/director")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String findDirectorAudits(Model model,
                                     @RequestParam(name = "pageNum", defaultValue = "0", required = false) int page,
                                     @RequestParam(name = "pageSize", defaultValue = "25", required = false) int size) {
        PageDto<RevisionDto<Director>> revisionEntityList = auditService.findDirectorsAudit(page, size);
        model.addAttribute("revisionEntityList", revisionEntityList);
        return "audit-director";
    }
}
