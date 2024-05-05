package ru.hogwarts.school32.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school32.model.Avatar;
import ru.hogwarts.school32.model.Student;
import ru.hogwarts.school32.repositorys.RepositoryAvatar;
import ru.hogwarts.school32.repositorys.RepositoryStudent;
import ru.hogwarts.school32.service.AvatarService;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarDir;

    private final RepositoryAvatar repositoryAvatar;
    private final RepositoryStudent repositoryStudent;

    public AvatarServiceImpl(@Value("${path.to.avatars.folder}") String avatarDir,
                             RepositoryAvatar repositoryAvatar,
                             RepositoryStudent repositoryStudent) {
        this.avatarDir = avatarDir;
        this.repositoryAvatar = repositoryAvatar;
        this.repositoryStudent = repositoryStudent;
    }

    @Override
    public void uploadAvatarOriginalImage(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = repositoryStudent.getById(studentId);
        Optional<Avatar> avatar1 = repositoryAvatar.findAvatarByStudentId(studentId);
        Avatar avatar = avatar1.orElse(null);
        if (avatar == null) {
            avatar = new Avatar();
            avatar.setStudent(student);
        }
        Path filePath = Path.of(avatarDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 2024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 2024);
        ) {
            bis.transferTo(bos);
        }
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        repositoryAvatar.save(avatar);
    }

    @Override
    public void uploadAvatarOriginalAndMiniImage(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = repositoryStudent.getById(studentId);
        Optional<Avatar> avatar1 = repositoryAvatar.findAvatarByStudentId(studentId);
        Avatar avatar = avatar1.orElse(null);
        if (avatar == null) {
            avatar = new Avatar();
            avatar.setStudent(student);
        }
        Path filePath = Path.of(avatarDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 2024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 2024);
        ) {
            bis.transferTo(bos);
        }
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generateImagePreview(filePath));
        repositoryAvatar.save(avatar);
    }

    @Override
    public ResponseEntity<byte[]> downloadAvatar(Long id) {
        Optional<Avatar> avatar1 = repositoryAvatar.findAvatarByStudentId(id);
        Avatar avatar = avatar1.orElse(null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @Override
    public Avatar findAvatar(Long id) {
        return repositoryAvatar.findAvatarByStudentId(id).orElse(new Avatar());
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 2024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }

    }

    @Override
    public void deleteAvatar(Long studentId) {
        // Находим аватар в базе данных по ID студента
        Optional<Avatar> avatar1 = repositoryAvatar.findAvatarByStudentId(studentId);
        Avatar avatar = avatar1.orElse(null);
        if (avatar != null) {

            String filePath = avatar.getFilePath();

            filePath = filePath.replaceAll("[<\">]", "");

            try {
                Path fileToDelete = Path.of(filePath);
                Files.deleteIfExists(fileToDelete);
            } catch (IOException e) {
                e.printStackTrace();
            }

            repositoryAvatar.delete(avatar);
        } else {

            System.out.println("Avatar not found for student with ID: " + studentId);
        }
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


}
