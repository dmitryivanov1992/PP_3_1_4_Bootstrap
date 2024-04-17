package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.management.relation.RoleNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Component
public class RunAfterStartup {
    private final UserService userService;
    private final RoleService roleService;

    public RunAfterStartup(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    //добавление в базу первого пользователя и ролей
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStart() {
        Set<Role> rolesList = new HashSet<>();
        String username = "dmitry";
        rolesList.add(new Role("ROLE_ADMIN"));
        rolesList.add(new Role("ROLE_USER"));
        rolesList.forEach(role -> {
            try {
                roleService.findRoleByName(role.getRoleName());
            } catch (RoleNotFoundException e) {
                roleService.addRole(role);
            }
        });
        try {
            userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            Set<Role> defaultUserRoles = new HashSet<>();
            try {
                defaultUserRoles.add(roleService.findRoleByName("ROLE_ADMIN"));
                defaultUserRoles.add(roleService.findRoleByName("ROLE_USER"));
            } catch (RoleNotFoundException exception) {
            }

            User defaultUser =
                    new User("Dmitry", "Ivanov", 31,
                            "dmitryivanov92@gmail.com", defaultUserRoles);
            defaultUser.setUsername(username);
            defaultUser.setPassword("123");
            userService.addUser(defaultUser);
        }
    }


}

