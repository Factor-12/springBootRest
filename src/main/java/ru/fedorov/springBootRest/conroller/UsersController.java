package ru.fedorov.springBootRest.conroller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.fedorov.springBootRest.model.User;
import ru.fedorov.springBootRest.service.UserService;

@Controller
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap model, Principal principal) {
        model.addAttribute("user", userService.getByUsername(principal.getName()));
        return "admin";
    }

    @GetMapping("/user")
    public String forUser(ModelMap modelMap, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        modelMap.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUserC(Principal principal) {
        User user = userService.getByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
