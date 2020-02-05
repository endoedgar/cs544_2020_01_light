package cs544_2020_01_light_attendanceproject.controller;

import java.net.URI;

import javax.validation.Valid;

import cs544_2020_01_light_attendanceproject.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cs544_2020_01_light_attendanceproject.domain.User;
import cs544_2020_01_light_attendanceproject.exceptions.AdminsCannotDeleteThemselvesException;
import cs544_2020_01_light_attendanceproject.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("")
    public ResponseEntity<Object> newUser(@RequestBody @Valid User user) {
        userService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getUsername())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("")
    public Iterable<User> all() {
        return userService.listUsers();
    }

    @Secured(value = {"ROLE_ADMIN","ROLE_FACULTY","ROLE_STUDENT"})
    @GetMapping("/{username}")
    public User one(@PathVariable String username) {
        return userService.findUserByUsername(username).orElseThrow(() -> new ItemNotFoundException(username, User.class));
    }

    @Secured(value = {"ROLE_ADMIN"})
    @DeleteMapping("/{username}")
    public User deleteUser(@PathVariable String username) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username.equals(currentUsername))
            throw new AdminsCannotDeleteThemselvesException(username);
        User user = userService.findUserByUsername(username).map( u -> {
            userService.deleteUser(u);
            return u;
        }).orElse(null);

        return user;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PutMapping("/{username}")
    public User replaceUser(@RequestBody @Valid User newUser, @PathVariable String username) {
        User oldUser = userService.findUserByUsername(username).orElse(newUser);
        oldUser.setAttendances(newUser.getAttendances());
        oldUser.setRoles(newUser.getRoles());
        oldUser.setBarCodeId(newUser.getBarCodeId());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setEnabled(newUser.isEnabled());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setFirstName(newUser.getFirstName());
        oldUser.setLastName(newUser.getLastName());
        return userService.replaceUser(oldUser);
    }
    
    
    
}
