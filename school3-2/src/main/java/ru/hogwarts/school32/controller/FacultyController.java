package ru.hogwarts.school32.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school32.model.Faculty;
import ru.hogwarts.school32.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@Tag(name = "API для работы с Факультетами")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    @Operation(summary = "Получение всех значение")
    public List<Faculty> getAll() {
        return facultyService.getAll();
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Получение факультета по ID")
    public Faculty getFacultyById(@PathVariable Long id) {
        return facultyService.getFacultyById(id);
    }

    @GetMapping("/color/{color}")
    @Operation(summary = "Фильтрация по цвету факультета")
    public List<Faculty> filterFacultyByColor(@PathVariable String color) {
        return facultyService.filterFacultyByColor(color);
    }

    @PostMapping()
    @Operation(summary = "Добавить факультет")
    public Faculty createFacult(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение факультета по ID")
    public Faculty updateFaculty(@PathVariable Long id, @RequestBody Faculty faculty) {
        return facultyService.updateFaculty(id, faculty);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление факультета по ID")
    public Faculty deleteFaculty(@PathVariable Long id) {
        return facultyService.deleteFaculty(id);
    }
}
