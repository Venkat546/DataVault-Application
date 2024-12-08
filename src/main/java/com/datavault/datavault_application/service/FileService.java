package com.datavault.datavault_application.service;

import com.datavault.datavault_application.exception.FileNotFoundException;
import com.datavault.datavault_application.model.FileEntity;
import com.datavault.datavault_application.repository.FileRepository;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileEntity uploadFile(MultipartFile file, String uploadDir) throws IOException {
        String fileType = file.getContentType();
        if (!isValidFileType(fileType)) {
            throw new RuntimeException("Invalid file type. Only images and PDFs are allowed.");
        }

        String fileName = file.getOriginalFilename();
        File targetFile = new File(uploadDir + "/" + fileName);
        file.transferTo(targetFile);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileName);
        fileEntity.setPath(targetFile.getAbsolutePath());
        fileEntity.setUploadedAt(LocalDateTime.now());

        return fileRepository.save(fileEntity);
    }



    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    public void deleteFile(Long id) {
        FileEntity fileEntity = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with ID: " + id));
        File file = new File(fileEntity.getPath());
        if (file.delete()) {
            fileRepository.deleteById(id);
        } else {
            throw new RuntimeException("Failed to delete file from the server.");
        }
    }

    public FileEntity updateMetadata(Long id, FileEntity updatedMetadata) {
        FileEntity fileEntity = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with ID: " + id));
        fileEntity.setName(updatedMetadata.getName());
        return fileRepository.save(fileEntity);
    }

    private boolean isValidFileType(String fileType) {
        return MediaType.IMAGE_JPEG_VALUE.equals(fileType) ||
                MediaType.IMAGE_PNG_VALUE.equals(fileType) ||
                MediaType.APPLICATION_PDF_VALUE.equals(fileType);
    }
}
