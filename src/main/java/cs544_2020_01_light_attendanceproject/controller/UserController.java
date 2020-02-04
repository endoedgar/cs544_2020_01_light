package cs544_2020_01_light_attendanceproject.controller;

import cs544_2020_01_light_attendanceproject.domain.User;
import cs544_2020_01_light_attendanceproject.exceptions.UserNotFoundException;
import cs544_2020_01_light_attendanceproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public User newUser(@RequestBody @Valid User user) {
        return userService.registerNewUserAccount(user);
    }

    @GetMapping("/")
    public Iterable<User> all() {
        return userService.listUsers();
    }

    @GetMapping("/{username}")
    public User one(@PathVariable String username) {
        return userService.findOneUser(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
    }

    @PutMapping("/{username}")
    public User replaceUser(@RequestBody @Valid User newUser, @PathVariable String username) {
        return userService.replaceUser(newUser, username);
    }
}
