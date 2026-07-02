package com.pm.contentloaderadapter.contoller;

import com.pm.contentloaderadapter.domain.entity.UploadJob;
import com.pm.contentloaderadapter.service.FileProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/loader")
@RequiredArgsConstructor
public class UploadController {

    private final FileProcessorService fileProcessorService;

    @PostMapping("/upload")
    public ResponseEntity<UploadJob> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "UNKNOWN") String source) {
        UploadJob job = fileProcessorService.processFile(file, source);
        return ResponseEntity.status(HttpStatus.CREATED).body(job);
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<UploadJob> getJob(@PathVariable UUID id) {
        return ResponseEntity.ok(fileProcessorService.getJob(id));
    }
}