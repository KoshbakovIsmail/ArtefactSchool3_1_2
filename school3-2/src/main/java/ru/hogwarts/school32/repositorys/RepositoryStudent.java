package ru.hogwarts.school32.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school32.model.Student;

import java.util.List;

public interface RepositoryStudent extends JpaRepository<Student, Long> {
    List<Student> findByAge(Integer age);
}
