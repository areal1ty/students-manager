DROP TABLE IF EXISTS students;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 1;

CREATE TABLE students
(
    id INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    patronymic VARCHAR(30) NOT NULL,
    date_of_birth DATE NOT NULL,
    student_group VARCHAR(10) NOT NULL
);
