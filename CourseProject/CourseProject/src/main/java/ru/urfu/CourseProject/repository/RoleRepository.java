package ru.urfu.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.CourseProject.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
