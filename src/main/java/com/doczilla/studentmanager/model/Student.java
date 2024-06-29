package com.doczilla.studentmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.util.Assert;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    private Integer id;
    @Column("first_name")
    @NonNull
    private String firstName;
    @Column("last_name")
    @NonNull
    private String lastName;
    @Column("patronymic")
    @NonNull
    private String patronymic;
    @Column("date_of_birth")
    @NonNull
    private Date dateOfBirth;
    @Column("student_group")
    @NonNull
    private String studentGroup;

    public int getId() {
        Assert.notNull(id, "Student must have an id");
        return id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student that = (Student) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}