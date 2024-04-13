package ru.hogwarts.school32.Impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school32.exception.FacultyIllegalArgumentException;
import ru.hogwarts.school32.exception.FacultyNotFoundException;
import ru.hogwarts.school32.model.Faculty;
import ru.hogwarts.school32.service.FacultyService;

import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {
    private Map<Long, Faculty> facultyMap = new HashMap<>();
    private Long idCount = 1L;

    @Override
    public Faculty createFaculty(Faculty faculty) {
        Long id = idCount++;
        faculty.setId(id);
        facultyMap.put(id, faculty);
        return faculty;
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        if (facultyMap.containsKey(id)) {
            facultyMap.put(id, faculty);
            return faculty;
        }
        throw new FacultyNotFoundException();
    }

    @Override
    public Faculty deleteFaculty(Long id) {
        if (facultyMap.containsKey(id)) {
            Faculty deleteFaculty = facultyMap.get(id);
            facultyMap.remove(id);
            return deleteFaculty;
        }
        throw new FacultyNotFoundException();
    }

    @Override
    public Faculty getFacultyById(Long id) {
        if (facultyMap.containsKey(id)) {
            Faculty result = facultyMap.get(id);
            return result;
        }
        throw new FacultyNotFoundException();
    }

    @Override
    public Map<Long, Faculty> getAll() {
        return facultyMap;
    }

    @Override
    public Map<Long, Faculty> filterFacultyByColor(String color) {
        Map<Long, Faculty> filterFaculty = new HashMap<>();
        for (Map.Entry<Long, Faculty> entry : facultyMap.entrySet()) {
            Faculty faculty = entry.getValue();
            if (faculty.getColor().equals(color)) {
                filterFaculty.put(entry.getKey(), faculty);
            }
        }
        if (filterFaculty.isEmpty()) {
            throw new FacultyIllegalArgumentException();
        }
        return filterFaculty;
    }
}
