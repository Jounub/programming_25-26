package ru.urfu.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.CourseProject.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByName(String name);
}
