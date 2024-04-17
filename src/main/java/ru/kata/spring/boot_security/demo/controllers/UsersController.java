package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("")
public class UsersController {
    private final UserService userService;
    private final RoleService roleService;

    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String indexUsersPage(@ModelAttribute("user") User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("currentUser", auth.getPrincipal());
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("rolesList", roleService.listRoles());
        return "users/index";
    }

    @GetMapping("/remove")
    public String removeUser(@RequestParam int id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        user.setRoles(roleService.setRolesId(user.getRoles()));
        user.setUsername(user.getEmail());
        userService.addUser(user);
        return "redirect:/";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user) {
        user.setRoles(roleService.setRolesId(user.getRoles()));
        userService.editUser(user);
        return "redirect:/";
    }
}
