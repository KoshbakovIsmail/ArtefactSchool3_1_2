ALTER TABLE Student ADD CONSTRAINT age_above_16 CHECK (age >= 16);

ALTER TABLE Student ADD CONSTRAINT name_unique UNIQUE (name);

ALTER TABLE Student ALTER COLUMN name SEET NOT NULL;

ALTER TABLE Student ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE Faculty
ADD CONSTRAINT UNQ_FacultyColorName UNIQUE (name, color)

CREATE TABLE Person (
    PersonID INT PRIMARY KEY,
    NamePerson VARCHAR(100),
    Age INT,
    HasDrivingLicense BIT
);


CREATE TABLE Car (
    CarID INT PRIMARY KEY,
    Brand VARCHAR(50),
    Model VARCHAR(50),
    Cost DECIMAL(10, 2)
);


CREATE TABLE PersonCar (
    PersonID INT,
    CarID INT,
    FOREIGN KEY (PersonID) REFERENCES Person(PersonID),
    FOREIGN KEY (CarID) REFERENCES Car(CarID),
    PRIMARY KEY (PersonID, CarID)
);

SELECT s.name AS name, s.age AS age, f.name AS faculty_name
FROM Student s
LEFT JOIN Faculty f ON s.faculty_id = f.id;


SELECT s."name" AS StudentName, s."age" AS StudentAge
FROM Student s
INNER JOIN Avatar a ON s."id" = a.student_id;