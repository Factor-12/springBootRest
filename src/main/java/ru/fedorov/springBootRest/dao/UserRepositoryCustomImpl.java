package ru.fedorov.springBootRest.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ru.fedorov.springBootRest.model.User;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findOneWithRoles(String email) {
        return entityManager.createQuery(
                "select u from User u left join fetch u.roles where u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
