package cs544_2020_01_light_attendanceproject.controller;

import cs544_2020_01_light_attendanceproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/account")
@Secured({"ROLE_STUDENT", "ROLE_FACULTY", "ROLE_ADMIN"})
public class AccountController {
    private UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody Map<String, String> payload) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        this.userService.setUserPassword(currentUsername, payload.get("password"));
        return payload.get("password");
    }
}
