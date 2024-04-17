package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> listRoles();
    Role findRoleByName(String roleName) throws RoleNotFoundException;
    void addRole(Role role);
    Set<Role> setRolesId (Set<Role> userRoleSet);
}
