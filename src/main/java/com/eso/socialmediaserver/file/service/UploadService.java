package com.eso.socialmediaserver.file.service;

import com.eso.socialmediaserver.file.config.CdnConfig;
import com.eso.socialmediaserver.file.dto.response.UploadResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UploadService {

    private final CdnConfig cdnConfig;

    @SneakyThrows
    public UploadResponse upload(MultipartFile multipartFile) {
        ZonedDateTime now =  ZonedDateTime.now();

        String datePath = now.getYear() + "/" + now.getMonthValue() + "/" + now.getDayOfMonth();
        File folder = new File(cdnConfig.getUploadPath() + "/" + datePath);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }

        String oldName = multipartFile.getOriginalFilename();
        String newName;

        try {
            newName = UUID.randomUUID() + oldName.substring(oldName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException exception) {
            throw new RuntimeException("File extension is missing."); // todo validation exception
        }

        multipartFile.transferTo(new File(folder, newName));

        // Return the access path of the uploaded file
        UploadResponse uploadResponseDTO = new UploadResponse();
        uploadResponseDTO.setName(newName);
        uploadResponseDTO.setPath(datePath + "/" + newName);
        return uploadResponseDTO;
    }
}
