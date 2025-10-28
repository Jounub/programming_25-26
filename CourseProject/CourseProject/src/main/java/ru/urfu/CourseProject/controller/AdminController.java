package ru.urfu.CourseProject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.urfu.CourseProject.entity.Role;
import ru.urfu.CourseProject.entity.User;
import ru.urfu.CourseProject.repository.RoleRepository;
import ru.urfu.CourseProject.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    // Список всех пользователей
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    // Форма редактирования ролей пользователя
    @GetMapping("/users/edit/{username}")
    public String editUserForm(@PathVariable String username, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByName(username);
            if(user == null){
                throw new RuntimeException("Пользователь не найден");
            }
            List<Role> allRoles = userService.findAllRoles();

            model.addAttribute("user", user);
            model.addAttribute("allRoles", allRoles);
            return "admin/user-form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка: " + e.getMessage());
            return "redirect:/admin/users";
        }
    }

    // Обновление ролей пользователя
    @PostMapping("/users/update/{username}")
    public String updateUserRoles(@PathVariable String username,
                                  @RequestParam(required = false) List<Role> roles,
                                  RedirectAttributes redirectAttributes) {

        try {
            if(roles == null || roles.isEmpty()){
                Role readOnlyRole = roleRepository.findByName("ROLE_READ_ONLY");
                roles = new ArrayList<>();
                roles.add(readOnlyRole);
            }
            userService.updateUserRoles(username, roles);
            redirectAttributes.addFlashAttribute("success", "Роли пользователя обновлены");
        } catch (Exception e) {
            log.error("Error " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении ролей: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    // Форма создания нового пользователя
    @GetMapping("/users/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        List<Role> allRoles = userService.findAllRoles();
        model.addAttribute("allRoles", allRoles);
        return "admin/new-user";
    }

    // Создание нового пользователя
    @PostMapping("/users/create")
    public String createUser(@ModelAttribute User user,
                             @RequestParam String password,
                             @RequestParam(required = false) List<Role> roleIds,
                             RedirectAttributes redirectAttributes) {
        try {
            // Автоматическое формирование email на основе имени
            if (user.getName() != null && !user.getName().trim().isEmpty()) {
                String email = convertNameToEmail(user.getName().trim());
                user.setEmail(email);
            }

            // Если роли не выбраны, назначаем роль READ_ONLY
            if (roleIds == null || roleIds.isEmpty()) {
                Role readOnlyRole = roleRepository.findByName("ROLE_READ_ONLY");
                if (readOnlyRole != null) {
                    user.setRoles(List.of(readOnlyRole));
                } else {
                    throw new RuntimeException("Роль READ_ONLY не найдена в системе");
                }
            } else {
                user.setRoles(roleIds);
            }

            userService.createUser(user, password);
            redirectAttributes.addFlashAttribute("success", "Пользователь создан успешно");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании пользователя: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    // Удаление пользователя
    @PostMapping("/users/delete/{userId}")
    public String deleteUser(@PathVariable Long userId,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(userId);
            redirectAttributes.addFlashAttribute("success", "Пользователь " + userId + " удален успешно");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении пользователя: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    // Метод для преобразования имени в email
    private String convertNameToEmail(String name) {
        // Удаляем лишние пробелы и приводим к нижнему регистру
        String cleanedName = name.trim().toLowerCase();

        // Заменяем пробелы и другие разделители на точки
        String emailLocalPart = cleanedName
                .replaceAll("\\s+", ".")  // пробелы на точки
                .replaceAll("[^a-zа-яё0-9.]", "") // удаляем все кроме букв, цифр и точек
                .replaceAll("\\.+", ".")  // убираем множественные точки
                .replaceAll("^\\.|\\.$", ""); // убираем точки в начале и конце

        return emailLocalPart + "@sbproject.ru";
    }
}
