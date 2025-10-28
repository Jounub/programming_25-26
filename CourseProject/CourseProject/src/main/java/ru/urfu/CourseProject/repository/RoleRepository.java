package ru.urfu.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.CourseProject.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
