package org.example.movieapp.Service;

import jakarta.servlet.http.HttpServletResponse;

public interface MovieImportExportService {
    void exportToExcel(HttpServletResponse response);
}
