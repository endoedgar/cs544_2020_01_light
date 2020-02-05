package cs544_2020_01_light_attendanceproject.controller;

import cs544_2020_01_light_attendanceproject.domain.Session;
import cs544_2020_01_light_attendanceproject.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/session")
public class SessionController {
    @Autowired
    private SessionService sessionService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({ "ROLE_ADMIN" })
    public Session addSession(@RequestBody @Valid Session session) {
        return sessionService.createSession(session);
    }


    @GetMapping("/{id}")
    @Secured({ "ROLE_ADMIN", "ROLE_FACULTY", "ROLE_STUDENT" })
    public Session findSession(@PathVariable Long id) {
        return sessionService.findSessionById(id);
    }

    @DeleteMapping("/delete")
    @Secured({ "ROLE_ADMIN"})
    public void deleteSession(Session session) {
        sessionService.deleteSession(session);
    }

    @PutMapping("/update")
    @Secured({ "ROLE_ADMIN"})
    public Session update(Session session) {
        return sessionService.updateSession(session);
    }
}
