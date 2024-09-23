package com.eso.socialmediaserver.file.service;

import com.eso.socialmediaserver.file.config.CdnConfig;
import com.eso.socialmediaserver.file.dto.response.FileResponse;
import com.eso.socialmediaserver.file.dto.response.UploadResponse;
import com.eso.socialmediaserver.file.entity.File;
import com.eso.socialmediaserver.file.mapper.FileMapper;
import com.eso.socialmediaserver.file.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@Service
@Transactional
@AllArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final UploadService uploadService;
    private final CdnConfig cdnConfig;

    public FileResponse getFile(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found")); // todo resource missing
        return FileMapper.toResponse(file, cdnConfig.getUploadPath(), cdnConfig.getHost());
    }

    public File uploadFile(MultipartFile multipartFile) {
        UploadResponse uploadResponse = uploadService.upload(multipartFile);
        File file = new File();
        file.setName(multipartFile.getOriginalFilename());
        file.setPath(uploadResponse.getPath());
        file.setContentType(multipartFile.getContentType());
        return fileRepository.save(file);
    }

    public ResponseEntity<ByteArrayResource> downloadFile(Long id) throws IOException {
        File fileEntity = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found.")); // todo resource missing

        java.io.File file = new java.io.File(fileEntity.getPath());
        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] arr = new byte[(int) file.length()];
        fileInputStream.read(arr);
        fileInputStream.close();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileEntity.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileEntity.getName())
                .body(new ByteArrayResource(arr));
    }
}
