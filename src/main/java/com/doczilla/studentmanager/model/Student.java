package com.doczilla.studentmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "students")
public class Student {
    @Id
    @JsonProperty("id")
    private Long id;
    @Column("first_name")
    @JsonProperty("firstName")
    private String firstName;
    @Column("last_name")
    @JsonProperty("lastName")
    private String lastName;
    @Column("patronymic")
    @JsonProperty("patronymic")
    private String patronymic;
    @Column("date_of_birth")
    @JsonProperty("dateOfBirth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @Column("student_group")
    @JsonProperty("studentGroup")
    private String studentGroup;

    public boolean isNew() {
        return this.id == null;
    }
}