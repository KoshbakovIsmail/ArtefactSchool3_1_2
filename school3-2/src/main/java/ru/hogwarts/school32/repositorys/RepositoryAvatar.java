package ru.hogwarts.school32.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school32.model.Avatar;

import java.util.Optional;


public interface RepositoryAvatar extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findAvatarByStudentId(Long studentId);


}
