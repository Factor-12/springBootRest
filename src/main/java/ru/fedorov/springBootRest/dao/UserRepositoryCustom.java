package ru.fedorov.springBootRest.dao;

import ru.fedorov.springBootRest.model.User;

public interface UserRepositoryCustom {
    User findOneWithRoles(String email);
}
