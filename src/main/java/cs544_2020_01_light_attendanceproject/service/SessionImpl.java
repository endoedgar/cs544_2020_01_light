package cs544_2020_01_light_attendanceproject.service;

import cs544_2020_01_light_attendanceproject.dao.SessionRepository;
import cs544_2020_01_light_attendanceproject.domain.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class SessionImpl implements SessionService {
    @Autowired
    SessionRepository sessionRepository;


    @Override
    public void deleteSession(Session session) {
        sessionRepository.delete(session);
    }

    @Override
    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session updateSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session findSessionById(Long id) {
        return sessionRepository.findById(id).get();
    }
}
