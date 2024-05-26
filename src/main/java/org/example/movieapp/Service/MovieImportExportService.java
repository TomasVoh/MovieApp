package org.example.movieapp.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MovieImportExportService {
    void exportToExcel(HttpServletResponse response);
    void importFromExcel(MultipartFile file);
}
