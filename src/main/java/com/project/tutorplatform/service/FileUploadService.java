package com.project.tutorplatform.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.tutorplatform.entity.FileMetadata;
import com.project.tutorplatform.entity.Tutor;
import com.project.tutorplatform.exception.ResourceNotFoundException;
import com.project.tutorplatform.repository.FileMetadataRepository;
import com.project.tutorplatform.repository.TutorRepository;

@Service
public class FileUploadService {

    private final StorageService storageService;
    private final FileMetadataRepository fileRepository;
    private final TutorRepository tutorRepository;

    public FileUploadService(StorageService storageService,
                             FileMetadataRepository fileRepository,
                             TutorRepository tutorRepository) {
        this.storageService = storageService;
        this.fileRepository = fileRepository;
        this.tutorRepository = tutorRepository;
    }

    public FileMetadata uploadTutorDocument(Long tutorId, MultipartFile file) {

        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found"));

        String storedName = storageService.storeFile(file);

        FileMetadata meta = new FileMetadata();
        meta.setFileName(file.getOriginalFilename());
        meta.setFileUrl(storedName);
        meta.setFileType(file.getContentType());
        meta.setTutor(tutor);

        return fileRepository.save(meta);
    }

    public void deleteFile(Long fileId) {

        FileMetadata meta = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        storageService.deleteFile(meta.getFileUrl());
        fileRepository.delete(meta);
    }
}