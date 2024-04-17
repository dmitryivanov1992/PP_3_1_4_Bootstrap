package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import javax.management.relation.RoleNotFoundException;
import java.util.List;


public interface RoleDAO {
    List<Role> listRoles();
    Role findRoleByName(String roleName) throws RoleNotFoundException;
    void addRole(Role role);
}
