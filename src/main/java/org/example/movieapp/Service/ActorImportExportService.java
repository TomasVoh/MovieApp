package org.example.movieapp.Service;

import jakarta.servlet.http.HttpServletResponse;

public interface ActorImportExportService {
    void exportToExcel(HttpServletResponse response);
}
