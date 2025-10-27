package ru.urfu.CourseProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.urfu.CourseProject.entity.Role;
import ru.urfu.CourseProject.entity.User;
import ru.urfu.CourseProject.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

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
                                  @RequestParam List<Integer> roleIds,
                                  RedirectAttributes redirectAttributes) {
        try {
            userService.updateUserRoles(username, roleIds);
            redirectAttributes.addFlashAttribute("success", "Роли пользователя обновлены");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении ролей: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    // Форма создания нового пользователя
    @GetMapping("/users/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/new-user";
    }

    // Создание нового пользователя
    @PostMapping("/users/create")
    public String createUser(@ModelAttribute User user,
                             @RequestParam String password,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(user, password);
            redirectAttributes.addFlashAttribute("success", "Пользователь создан успешно");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании пользователя: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
}
