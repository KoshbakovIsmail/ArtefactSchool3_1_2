package ru.hogwarts.school32.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school32.model.Avatar;

import java.io.IOException;

public interface AvatarService {
    void uploadAvatarOriginalImage(Long studentId, MultipartFile avatar) throws IOException;

    void uploadAvatarOriginalAndMiniImage(Long studentId, MultipartFile avatarFile) throws IOException;

    ResponseEntity<byte[]> downloadAvatar(Long id);

    Avatar findAvatar(Long id);

    void deleteAvatar(Long studentId);

    Page<Avatar> findAll(int page,int size);
}
