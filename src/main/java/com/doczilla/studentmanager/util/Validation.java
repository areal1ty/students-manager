package com.doczilla.studentmanager.util;

import com.doczilla.studentmanager.model.Student;
import com.doczilla.studentmanager.util.exception.NotFoundException;

public class Validation {

    private Validation() {
    }

    public static <T> T checkNotFoundWithId(T object, Long id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, Long id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(Student s) {
        if (!s.isNew()) {
            throw new IllegalArgumentException(s + " must be new (id=null)");
        }
    }
}
