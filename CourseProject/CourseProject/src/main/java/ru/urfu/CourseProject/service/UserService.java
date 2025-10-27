package ru.urfu.CourseProject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.urfu.CourseProject.entity.Role;
import ru.urfu.CourseProject.entity.User;
import ru.urfu.CourseProject.repository.RoleRepository;
import ru.urfu.CourseProject.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public void updateUserRoles(String username, List<Integer> roleIds) {
        User user = userRepository.findByName(username);
        if(user == null){
            throw new RuntimeException("Пользователь не найден");
        }


//        Set<Role> roles = new HashSet<>();
//        for (Integer roleId : roleIds) {
//            Role role = roleRepository.findByName(roleId)
//                    .orElseThrow(() -> new RuntimeException("Роль не найдена"));
//            roles.add(role);
//        }
//
//        user.setRoles(roles);
//        userRepository.save(user);
    }

    @Transactional
    public void createUser(User user, String password) {
        if (userRepository.findByName(user.getName()) != null) {
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }

        user.setPassword(passwordEncoder.encode(password));
        //user.setEnabled(true);

        // По умолчанию назначаем роль READ_ONLY
//        Role readOnlyRole = roleRepository.findByName("ROLE_READ_ONLY")
//                .orElseThrow(() -> new RuntimeException("Роль READ_ONLY не найдена"));

        //user.setRoles(Set.of(readOnlyRole));
        userRepository.save(user);
    }
}
