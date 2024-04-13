package ru.hogwarts.school32.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FacultyIllegalArgumentException extends RuntimeException {
}
