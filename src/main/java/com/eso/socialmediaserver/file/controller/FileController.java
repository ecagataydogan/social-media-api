package com.eso.socialmediaserver.file.controller;

import com.eso.socialmediaserver.file.config.CdnConfig;
import com.eso.socialmediaserver.file.dto.response.FileResponse;
import com.eso.socialmediaserver.file.mapper.FileMapper;
import com.eso.socialmediaserver.file.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController {

    private final FileService fileService;
    private final CdnConfig cdnConfig;

    @GetMapping("/{id}")
    public FileResponse getFile(@PathVariable(name = "id") Long id) {
        return fileService.getFile(id);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public FileResponse uploadFile(@RequestPart("file") MultipartFile file) {
        return FileMapper.toResponse(fileService.uploadFile(file), cdnConfig.getUploadPath(), cdnConfig.getHost());
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long id) throws IOException {
        return fileService.downloadFile(id);
    }
}
