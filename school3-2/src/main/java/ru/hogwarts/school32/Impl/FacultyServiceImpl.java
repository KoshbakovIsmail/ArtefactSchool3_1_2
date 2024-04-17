package ru.hogwarts.school32.Impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school32.exception.FacultyIllegalArgumentException;
import ru.hogwarts.school32.exception.FacultyNotFoundException;
import ru.hogwarts.school32.model.Faculty;
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
        if (faculty == null) {
            throw new FacultyIllegalArgumentException();
        }
        if (repositoryFaculty.existsById(id)) {
            faculty.setId(id);
            return repositoryFaculty.save(faculty);
        }
        throw new FacultyNotFoundException();
    }

    @Override
    public Faculty deleteFaculty(Long id) {
        if (repositoryFaculty.existsById(id)) {
            Faculty deleteFacult = repositoryFaculty.findById(id).orElse(null);
            repositoryFaculty.deleteById(id);
            return deleteFacult;
        }
        throw new FacultyNotFoundException();
    }

    @Override
    public Faculty getFacultyById(Long id) {
        return repositoryFaculty.findById(id).orElseThrow(FacultyNotFoundException::new);
    }

    @Override
    public List<Faculty> getAll() {
        return repositoryFaculty.findAll();
    }

    @Override
    public List<Faculty> filterFacultyByColor(String color) {
        return repositoryFaculty.findByColor(color);
    }
}
