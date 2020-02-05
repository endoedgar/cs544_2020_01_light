package cs544_2020_01_light_attendanceproject.controller;

import cs544_2020_01_light_attendanceproject.domain.Session;
import cs544_2020_01_light_attendanceproject.domain.Timeslot;
import cs544_2020_01_light_attendanceproject.exceptions.ItemNotFoundException;
import cs544_2020_01_light_attendanceproject.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/session")
public class SessionController {
    @Autowired
    private SessionService sessionService;

    @PostMapping
    @Secured({ "ROLE_ADMIN" })
    public ResponseEntity<Object> addSession(@RequestBody @Valid Session session) {
        sessionService.createSession(session);

        URI locationUrl = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(session.getId())
                .toUri();

        return ResponseEntity.created(locationUrl).build();
    }


    @GetMapping("/{id}")
    @Secured({ "ROLE_ADMIN", "ROLE_FACULTY", "ROLE_STUDENT" })
    public Session findSession(@PathVariable Long id) {
        return sessionService.findSessionById(id).orElseThrow(() -> new ItemNotFoundException(id.toString(), Session.class));
    }

    @DeleteMapping("/{id}")
    @Secured({ "ROLE_ADMIN"})
    public Session deleteSession(@PathVariable Long id) {
        return sessionService.findSessionById(id).map(s -> {
            sessionService.deleteSession(s);
            return s;
        }).orElse(null);
    }

    @PutMapping("/{id}")
    @Secured({ "ROLE_ADMIN"})
    public Session update(@PathVariable Long id, @RequestBody @Valid Session newSession) {
        Session oldSession = sessionService.findSessionById(id).orElse(newSession);
        oldSession.setCourseOffering(newSession.getCourseOffering());
        oldSession.setDate(newSession.getDate());
        oldSession.setTimeslot(newSession.getTimeslot());
        return sessionService.updateSession(oldSession);
    }
}
