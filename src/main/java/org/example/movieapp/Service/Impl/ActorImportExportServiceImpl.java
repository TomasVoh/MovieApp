package org.example.movieapp.Service.Impl;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.example.movieapp.Model.Actor;
import org.example.movieapp.Repository.ActorRepository;
import org.example.movieapp.Service.ActorImportExportService;
import org.example.movieapp.Service.ExcelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorImportExportServiceImpl implements ActorImportExportService {
    private ActorRepository actorRepository;
    private ExcelService excelService;

    public ActorImportExportServiceImpl(ActorRepository actorRepository, ExcelService excelService) {
        this.actorRepository = actorRepository;
        this.excelService = excelService;
    }

    @Override
    public void exportToExcel(HttpServletResponse response) {
        List<Actor> actorList = actorRepository.findAll();
        Sheet sheet = excelService.createWorkbook("herci");
        excelService.writeHeaders(new String[]{"ID", "Jméno", "Přijmení", "Datum narození"});
        int rowCount = 1;
        for (Actor actor : actorList) {
            int columnCount = 0;
            Row row = sheet.createRow(rowCount);
            excelService.createCell(row, columnCount++, actor.getId(), null);
            excelService.createCell(row, columnCount++, actor.getName(), null);
            excelService.createCell(row, columnCount++, actor.getSurname(), null);
            excelService.createCell(row, columnCount++, actor.getBirthday(), null);
            rowCount++;
        }
        excelService.initResponse(response, "actors");
    }
}
