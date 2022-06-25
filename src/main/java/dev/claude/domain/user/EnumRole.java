package dev.claude.domain.user;

import dev.claude.service.exception.InternalErrorException;

public enum EnumRole {
    ROLE_USER,
    ROLE_STUDENT,
    ROLE_TEACHER,
    ROLE_ADMIN;

    public static EnumRole getRole(String role) {
        if (role.equals(ROLE_ADMIN.name())) {
            return ROLE_ADMIN;
        } else if (role.equals(ROLE_TEACHER.name())) {
            return ROLE_TEACHER;
        } else if (role.equals(ROLE_STUDENT.name())) {
            return ROLE_STUDENT;
        } else if (role.equals(ROLE_USER.name())) {
            return ROLE_USER;
        }
        throw new InternalErrorException("This role does not exist");
    }

}
