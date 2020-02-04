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
	public Optional<Timeslot> get(String abbr) {
		// TODO Auto-generated method stub
		return tsRepository.findById(abbr);
	}

	@Override
	public Iterable<Timeslot> getAll() {
		// TODO Auto-generated method stub
		return tsRepository.findAll();
	}

	@Override
	public Timeslot create(Timeslot tm) {
		// TODO Auto-generated method stub
		return tsRepository.save(tm);
	}

	@Override
	public Timeslot update(Timeslot tm) {
		// TODO Auto-generated method stub
		return tsRepository.save(tm);
	}

	@Override
	public void delete(String abbr) {
		// TODO Auto-generated method stub
		tsRepository.deleteById(abbr);
		
	}

}
