package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.domain.Session;

public interface SessionService {
    public void deleteSession(Session session);
    public Session createSession(Session session);
    public Session updateSession(Session session);
    public Session findSessionById(Long id);
}
