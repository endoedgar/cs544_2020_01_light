package cs544_2020_01_light_attendanceproject.controller;

import cs544_2020_01_light_attendanceproject.domain.Timeslot;
import cs544_2020_01_light_attendanceproject.domain.User;
import cs544_2020_01_light_attendanceproject.exceptions.UserNotFoundException;
import cs544_2020_01_light_attendanceproject.service.TimeSlotService;
import cs544_2020_01_light_attendanceproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private TimeSlotService timeSlotService; 
    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User newUser(@RequestBody @Valid User user) {
        return userService.registerNewUserAccount(user);
    }

    @GetMapping("/user")
    public Iterable<User> all() {
        return userService.listUsers();
    }

    @GetMapping("/user/{username}")
    public User one(@PathVariable String username) {
        return userService.findOneUser(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @DeleteMapping("/user/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
    }

    @PutMapping("/user/{username}")
    public User replaceUser(@RequestBody @Valid User newUser, @PathVariable String username) {
        return userService.replaceUser(newUser, username);
    }
    
    //TimeSlot
    @PostMapping("/timeslots")
    @ResponseStatus(HttpStatus.CREATED)
    public Timeslot newTimeSlot(@RequestBody @Valid Timeslot timeSlot) {
        return timeSlotService.create(timeSlot);
    }

    @GetMapping("/timeslots")
    public Iterable<Timeslot> fetchAllTs() {
        return timeSlotService.getAll();
    }

    @GetMapping("/timeslots/{abbr}")
    public Timeslot fetchTs(@PathVariable String abbr) {
        return timeSlotService.get(abbr).orElse(null);
    }

    @DeleteMapping("/timeslots/{abbr}")
    public void deleteTs(@PathVariable String abbr) {
        timeSlotService.delete(abbr);
    }

    @PutMapping("/timeslots/{abbr}")
    public Timeslot updateTs(@RequestBody @Valid Timeslot newTs, @PathVariable String abbr) {
        return timeSlotService.update(newTs);
    }
    
}
