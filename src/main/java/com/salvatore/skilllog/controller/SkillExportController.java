package com.salvatore.skilllog.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salvatore.skilllog.service.SkillExportService;

@RestController
@RequestMapping("/api/skills")
public class SkillExportController {

    @Autowired
    private SkillExportService exportService;

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportSkills() throws IOException {
        ByteArrayInputStream in = exportService.exportSkillsToExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=skill_log.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }
}
