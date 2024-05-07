package ru.hogwarts.school32.controller;

import ru.hogwarts.school32.model.Faculty;
import ru.hogwarts.school32.model.Student;

import java.util.Collections;
import java.util.List;

public class ModelConstantsTest {
    public static final Long STUDENT_ID_1 = 20L;
    public static final Long STUDENT_ID_2 = 18L;
    public static final String STUDENT_NAME_1 = "name1";
    public static final String STUDENT_NAME_2 = "name2";
    public static final Integer STUDENT_AGE_1 = 25;
    public static final Integer STUDENT_AGE_2 = 26;

    public static final Long FACULTY_ID_1 = 6L;
    public static final Long FACULTY_ID_2 = 7L;
    public static final String FACULTY_NAME_1 = "Faculty1";
    public static final String FACULTY_NAME_2 = "Faculty2";
    public static final String FACULTY_COLOR_1 = "Color1";
    public static final String FACULTY_COLOR_2 = "Color2";

    public static final Student STUDENT1 = new Student(STUDENT_ID_1,STUDENT_NAME_1, STUDENT_AGE_1);
    public static final Student STUDENT2 = new Student(STUDENT_ID_2,STUDENT_NAME_2, STUDENT_AGE_2);

    public static final Faculty FACULTY1 = new Faculty(FACULTY_ID_1, FACULTY_NAME_1, FACULTY_COLOR_1);
    public static final Faculty FACULTY2 = new Faculty(FACULTY_ID_2, FACULTY_NAME_2, FACULTY_COLOR_2);

    public static final List<Student> STUDENT_LIST = Collections.singletonList(STUDENT1);
    public static final List<Faculty> FACULTY_LIST = Collections.singletonList(FACULTY1);

}
