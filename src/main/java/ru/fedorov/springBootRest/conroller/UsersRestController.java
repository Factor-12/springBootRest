package ru.fedorov.springBootRest.conroller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fedorov.springBootRest.model.Role;
import ru.fedorov.springBootRest.model.User;
import ru.fedorov.springBootRest.service.UserService;
import ru.fedorov.springBootRest.dto.UserDto;

@RestController
@RequestMapping("/api")
public class UsersRestController {

    private final UserService userService;

    @Autowired
    public UsersRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") long id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/admin")
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) {
        User user = new User(userDto);
        Set<Role> roles = new HashSet<>();
        for (String roleName : userDto.getRoles()) {
            String fullRole = "ROLE_" + roleName;
            roles.add(userService.getRole(fullRole));
        }
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("admin")
    public ResponseEntity<User> editUser(@RequestBody UserDto userDto) {
        User user = new User(userDto);
        Set<Role> roles = new HashSet<>();
        for (String roleName : userDto.getRoles()) {
            String fullRole = "ROLE_" + roleName;
            roles.add(userService.getRole(fullRole));
        }
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("admin/roles")
    public List<Role> getAllRoles() {
        return userService.getAllRoles();
    }
}
