package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.domain.Session;

import java.util.Optional;

public interface SessionService {
    public void deleteSession(Session session);
    public Session createSession(Session session);
    public Session updateSession(Session session);
    public Optional<Session> findSessionById(Long id);
    public Iterable<Session> findAllSessions();
}
