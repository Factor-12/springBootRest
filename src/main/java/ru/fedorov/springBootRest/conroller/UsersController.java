package ru.fedorov.springBootRest.conroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.fedorov.springBootRest.model.User;

@Controller
public class UsersController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "admin";
    }

    @GetMapping("/user")
    public String forUser(ModelMap modelMap, @AuthenticationPrincipal User user) {
        modelMap.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUserC(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
