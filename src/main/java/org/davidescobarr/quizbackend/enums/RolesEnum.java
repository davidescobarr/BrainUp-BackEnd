package org.davidescobarr.quizbackend.enums;

public enum RolesEnum {
    USER,
    TEACHER,
    ADMIN;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
