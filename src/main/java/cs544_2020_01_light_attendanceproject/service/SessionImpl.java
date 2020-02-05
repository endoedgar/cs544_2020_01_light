package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.dao.SessionRepository;
import cs544_2020_01_light_attendanceproject.domain.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SessionImpl implements SessionService {
    @Autowired
    SessionRepository sessionRepository;

    @Override
    @Transactional
    public void deleteSession(Session session) {
        sessionRepository.delete(session);
    }

    @Override
    @Transactional
    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    @Transactional
    public Session updateSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Session> findSessionById(Long id) {
        return sessionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Session> findAllSessions() {
        return sessionRepository.findAll();
    }
}
