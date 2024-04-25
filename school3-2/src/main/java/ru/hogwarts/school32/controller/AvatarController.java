package ru.hogwarts.school32.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school32.model.Avatar;
import ru.hogwarts.school32.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@Tag(name = "Работа Аватаркой студентов")
public class AvatarController {
    private AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/avatars", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Добовление аватара студента по Id студента")
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar)
            throws IOException {
        avatarService.uploadAvatarOriginalAndMiniImage(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar-from-db")
    @Operation(summary = "Получение аватара по Id Студента с Базы данных")
    public ResponseEntity<byte[]> downloadAvatarOriginalImageDb(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "/{id}/avatar-from-db-option 2")
    @Operation(summary = "Получение аватара по Id Студента с Базы данных")
    public ResponseEntity<byte[]> downloadAOriginalImageFromDb(@PathVariable Long id) throws IOException {
        return avatarService.downloadAvatar(id);
    }

    @GetMapping(value = "/{id}/avatar-from-file")
    @Operation(summary = "Получение аватара по Id Студента с Файла")
    public void downloadAvatarOriginalImageFile(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @DeleteMapping(value = "/{id}/delete-Avatar")
    @Operation(summary = "Удаление аватара Студента по id")
    public void deleteAvatar(@PathVariable Long id) {
        avatarService.deleteAvatar(id);
    }


}
