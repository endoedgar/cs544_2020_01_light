package cs544_2020_01_light_attendanceproject.controller;

import cs544_2020_01_light_attendanceproject.domain.User;
import cs544_2020_01_light_attendanceproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/myRoles")
    public Collection<SimpleGrantedAuthority> getMyRoles() {
        return(Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    @PostMapping("/user")
    public String registerNewUserAccount(@Valid User user) {
        userService.registerNewUserAccount(user);
        return "redirect:/";
    }

    @GetMapping("/user")
    public Iterable<User> getUsers() {
        return userService.listUsers();
    }
}
