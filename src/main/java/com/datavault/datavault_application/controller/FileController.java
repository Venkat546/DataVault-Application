package com.datavault.datavault_application.controller;

import com.datavault.datavault_application.model.FileEntity;
import com.datavault.datavault_application.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<FileEntity> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileEntity fileEntity = fileService.uploadFile(file, uploadDir);
            return ResponseEntity.ok(fileEntity);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }



    @GetMapping
    public ResponseEntity<List<FileEntity>> listAllFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.ok("File deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FileEntity> updateFileMetadata(@PathVariable Long id, @RequestBody FileEntity updatedMetadata) {
        try {
            FileEntity updatedFile = fileService.updateMetadata(id, updatedMetadata);
            return ResponseEntity.ok(updatedFile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
