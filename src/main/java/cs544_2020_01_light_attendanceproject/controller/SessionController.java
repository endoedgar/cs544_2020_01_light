package cs544_2020_01_light_attendanceproject.controller;

import cs544_2020_01_light_attendanceproject.dao.SessionRepository;
import cs544_2020_01_light_attendanceproject.domain.Session;
import cs544_2020_01_light_attendanceproject.domain.User;
import cs544_2020_01_light_attendanceproject.exceptions.AdminsCannotDeleteThemselvesException;
import cs544_2020_01_light_attendanceproject.exceptions.UserNotFoundException;
import cs544_2020_01_light_attendanceproject.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/session")
public class SessionController {
    @Autowired
    private SessionService sessionService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Session addSession(@RequestBody @Valid Session session) {
        return sessionService.createSession(session);
    }


    @GetMapping("/{id}")
    public Session findSession(@PathVariable Long id) {
        return sessionService.findSessionById(id);
    }

    @DeleteMapping("/delete")
    public void deleteSession(Session session) {
        sessionService.deleteSession(session);
    }

    @PutMapping("/update")
    public Session update(Session session) {
        return sessionService.updateSession(session);
    }
}
