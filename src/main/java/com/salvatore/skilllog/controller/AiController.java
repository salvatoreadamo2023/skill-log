package com.salvatore.skilllog.controller;

import com.salvatore.skilllog.dto.AiRequest;
import com.salvatore.skilllog.service.AiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/message")
    public ResponseEntity<String> getMessage(@RequestBody AiRequest request) throws IOException {
        String message = aiService.generateMessage(request.getPrompt());
        return ResponseEntity.ok(message);
    }
}
