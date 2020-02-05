/**
 * 
 */
package cs544_2020_01_light_attendanceproject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cs544_2020_01_light_attendanceproject.dao.TimeslotRepository;
import cs544_2020_01_light_attendanceproject.domain.Timeslot;

/**
 * @author Adeola Adeleke
 * 
 * TimeSlot service implementation
 *
 */

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

	private TimeslotRepository tsRepository;
	
	/**
	 * @param tsRepository
	 */
	@Autowired
	public TimeSlotServiceImpl(TimeslotRepository tsRepository) {
		super();
		this.tsRepository = tsRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Timeslot> get(String abbr) {
		// TODO Auto-generated method stub
		return tsRepository.findById(abbr);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Timeslot> getAll() {
		// TODO Auto-generated method stub
		return tsRepository.findAll();
	}

	@Override
	@Transactional
	public Timeslot create(Timeslot tm) {
		// TODO Auto-generated method stub
		return tsRepository.save(tm);
	}

	@Override
	@Transactional
	public Timeslot update(Timeslot tm) {
		// TODO Auto-generated method stub
		return tsRepository.save(tm);
	}

	@Override
	@Transactional
	public void delete(Timeslot timeslot) {
		// TODO Auto-generated method stub
		tsRepository.delete(timeslot);
	}

}
