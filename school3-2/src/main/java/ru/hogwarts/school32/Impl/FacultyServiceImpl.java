package ru.hogwarts.school32.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school32.exception.FacultyNotFoundException;
import ru.hogwarts.school32.model.Faculty;
import ru.hogwarts.school32.model.Student;
import ru.hogwarts.school32.repositorys.RepositoryFaculty;
import ru.hogwarts.school32.service.FacultyService;

import java.util.Comparator;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final RepositoryFaculty repositoryFaculty;
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(RepositoryFaculty repositoryFaculty) {
        this.repositoryFaculty = repositoryFaculty;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Вызвано метод createFaculty у класса FacultyServiceImpl");
        return repositoryFaculty.save(faculty);
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        logger.info("Вызвано метод updateFaculty у класса FacultyServiceImpl");
        Faculty updateFaculty = repositoryFaculty.findById(id).orElse(null);
        if (updateFaculty != null) {
            updateFaculty.setId(id);
            updateFaculty.setName(faculty.getName());
            updateFaculty.setColor(faculty.getColor());
            return repositoryFaculty.save(updateFaculty);
        }
        throw new FacultyNotFoundException();
    }

    @Override
    public Faculty deleteFaculty(Long id) {
        logger.info("Вызвано метод daleteFaculty у класса FacultyServiceImpl");
        Faculty deleteFaculty = repositoryFaculty.findById(id).orElseThrow(() -> new FacultyNotFoundException());
        repositoryFaculty.deleteById(id);
        return deleteFaculty;
    }

    @Override
    public Faculty getFacultyById(Long id) {
        logger.info("Вызвано метод getFacultyById у класса FacultyServiceImpl");
        return repositoryFaculty.findById(id).orElseThrow(() -> new FacultyNotFoundException());
    }

    @Override
    public List<Faculty> getAll() {
        logger.info("Вызвано метод getAll у класса FacultyServiceImpl");
        return repositoryFaculty.findAll();
    }

    @Override
    public List<Faculty> filterFacultyByColor(String color) {
        logger.info("Вызвано метод filterFacultyByColor у класса FacultyServiceImpl");
        return repositoryFaculty.findByColor(color);
    }

    @Override
    public List<Faculty> getByColorOrName(String color, String name) {
        logger.info("Вызвано метод getByColorOrName у класса FacultyServiceImpl");
        return repositoryFaculty.findByColorIgnoreCaseAndNameIgnoreCase(color, name);
    }

    @Override
    public List<Student> getStudentsFaculty(Long facultyId) {
        logger.info("Вызвано метод getStudentFaculty у класса FacultyServiceImpl");
        return getFacultyById(facultyId).getStudentList();
    }

    @Override
    public String getLongestFacultyName() {
        return repositoryFaculty.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }
}
