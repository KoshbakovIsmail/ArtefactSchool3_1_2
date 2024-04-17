package ru.hogwarts.school32.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school32.model.Faculty;

import java.util.List;

public interface RepositoryFaculty extends JpaRepository<Faculty, Long> {

    List<Faculty> findByColor(String Color);
}
