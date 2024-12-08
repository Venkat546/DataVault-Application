package com.datavault.datavault_application.repository;


import com.datavault.datavault_application.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
