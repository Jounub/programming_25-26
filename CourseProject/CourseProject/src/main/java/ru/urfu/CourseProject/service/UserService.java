package ru.urfu.CourseProject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.urfu.CourseProject.entity.Role;
import ru.urfu.CourseProject.entity.User;
import ru.urfu.CourseProject.repository.RoleRepository;
import ru.urfu.CourseProject.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findByName(String username) {
        return userRepository.findByName(username);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public void updateUserRoles(String username, List<Role> roles) {
        User user = userRepository.findByName(username);
        user.setRoles(roles);

        if(user == null){
            throw new RuntimeException("Пользователь не найден");
        }

        userRepository.save(user);
    }

    @Transactional
    public void createUser(User user, String password) {
        if (userRepository.findByName(user.getName()) != null) {
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }

        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        if (user.isPresent()) {
            User foundUser = user.get();
            String name = foundUser.getEmail();
            if (name.equals(currentUsername)) {
                throw new RuntimeException("Нельзя удалить собственный аккаунт");
            }
        }

        userRepository.deleteById(id);
    }
}
