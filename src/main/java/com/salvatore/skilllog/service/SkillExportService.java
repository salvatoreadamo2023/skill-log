package com.salvatore.skilllog.service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salvatore.skilllog.model.Skill;
import com.salvatore.skilllog.repository.SkillRepository;

@Service
public class SkillExportService {

    @Autowired
    private SkillRepository skillRepository;

    public ByteArrayInputStream exportSkillsToExcel() throws IOException {
        String[] columns = {"Nome Skill", "Livello", "Ore di Studio"};
        List<Skill> skills = skillRepository.findAll();

        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            XSSFSheet sheet = workbook.createSheet("SkillLog");

            // Header
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Data rows
            int rowIdx = 1;
            for (Skill skill : skills) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(skill.getNome());
                row.createCell(1).setCellValue(skill.getLivello());
                row.createCell(2).setCellValue(skill.getOreStudio());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
