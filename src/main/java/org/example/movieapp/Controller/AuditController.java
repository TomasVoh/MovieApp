package org.example.movieapp.Controller;

import org.example.movieapp.Dto.RevisionDto;
import org.example.movieapp.Model.CustomRevisionEntity;
import org.example.movieapp.Service.AuditService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
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

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String findAudits(Model model) {
        List<RevisionDto> revisionEntityList = auditService.findAll();
        model.addAttribute("revisionEntityList", revisionEntityList);
        return "audit";
    }
}
