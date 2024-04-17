package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
    private final RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> listRoles() {
        return roleDAO.listRoles();
    }

    @Override
    public Role findRoleByName(String roleName) throws RoleNotFoundException {
        return roleDAO.findRoleByName(roleName);
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        roleDAO.addRole(role);
    }

    @Override
    public Set<Role> setRolesId(Set<Role> userRoleSet) {
        if (!userRoleSet.isEmpty()) {
            userRoleSet.forEach(role -> {
                try {
                    role.setId(findRoleByName(role.getRoleName()).getId());
                } catch (RoleNotFoundException e) {
                }
            });
        }
        return userRoleSet;
    }
}
