package ru.fedorov.springBootRest.service;

import java.util.List;
import ru.fedorov.springBootRest.model.Role;
import ru.fedorov.springBootRest.model.User;

public interface UserService {

    List<User> getAllUsers();

    void save(User user);

    void removeUser(long id);

    User getById(long id);

    User getByUsername(String username);

    User getByEmail(String username);

    Role getRole(String role);

    List<Role> getAllRoles();
}
