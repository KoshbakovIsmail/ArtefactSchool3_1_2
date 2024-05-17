package ru.hogwarts.school32.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school32.model.Student;

import java.util.List;

public interface RepositoryStudent extends JpaRepository<Student, Long> {
    List<Student> findByAge(Integer age);

    List<Student> findByAgeBetween(Integer min, Integer max);

    @Query("SElECT COUNT(s) FROM Student s")
    int getTotalStudentsCount();

    @Query("SELECT AVG(s.age) FROM Student s ")
    int getAverageStudentAge();

    @Query("SELECT s FROM Student s ORDER BY s.id DESC")
    List<Student> findTop5Students();
}
