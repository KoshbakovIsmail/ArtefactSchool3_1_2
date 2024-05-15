package ru.hogwarts.school32.Impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school32.exception.FacultyNotFoundException;
import ru.hogwarts.school32.model.Faculty;
import ru.hogwarts.school32.model.Student;
import ru.hogwarts.school32.repositorys.RepositoryFaculty;
import ru.hogwarts.school32.service.FacultyService;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final RepositoryFaculty repositoryFaculty;

    public FacultyServiceImpl(RepositoryFaculty repositoryFaculty) {
        this.repositoryFaculty = repositoryFaculty;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return repositoryFaculty.save(faculty);
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
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
        Faculty deleteFaculty = repositoryFaculty.findById(id).orElseThrow(() -> new FacultyNotFoundException());
        repositoryFaculty.deleteById(id);
        return deleteFaculty;
    }

    @Override
    public Faculty getFacultyById(Long id) {
        return repositoryFaculty.findById(id).orElseThrow(() -> new FacultyNotFoundException());
    }

    @Override
    public List<Faculty> getAll() {
        return repositoryFaculty.findAll();
    }

    @Override
    public List<Faculty> filterFacultyByColor(String color) {
        return repositoryFaculty.findByColor(color);
    }

    @Override
    public List<Faculty> getByColorOrName(String color, String name) {
        return repositoryFaculty.findByColorIgnoreCaseAndNameIgnoreCase(color, name);
    }

    @Override
    public List<Student> getStudentsFaculty(Long facultyId) {
        return getFacultyById(facultyId).getStudentList();
    }
}
