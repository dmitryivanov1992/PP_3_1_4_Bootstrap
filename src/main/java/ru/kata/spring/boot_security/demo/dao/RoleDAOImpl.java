package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import javax.management.relation.RoleNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> listRoles() {
        TypedQuery<Role> query = entityManager.createQuery("from Role", Role.class);
        return query.getResultList();
    }

    @Override
    public Role findRoleByName(String roleName) throws RoleNotFoundException {
        TypedQuery<Role> query = entityManager.createQuery("from Role where roleName=:rolename", Role.class);
        query.setParameter("rolename", roleName);
        try {
            return query.getSingleResult();
        } catch (NoResultException exception) {
            throw new RoleNotFoundException("Role not found");
        }
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }
}
