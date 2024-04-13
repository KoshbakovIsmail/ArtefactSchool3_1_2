package ru.hogwarts.school32.service;

import ru.hogwarts.school32.model.Faculty;
import java.util.Map;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty updateFaculty(Long id, Faculty faculty);

    Faculty deleteFaculty(Long id);

    Faculty getFacultyById(Long id);

    Map<Long, Faculty> getAll();

    Map<Long, Faculty> filterFacultyByColor(String color);

}
