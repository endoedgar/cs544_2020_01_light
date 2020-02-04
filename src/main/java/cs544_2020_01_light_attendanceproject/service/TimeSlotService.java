/**
 * 
 */
package cs544_2020_01_light_attendanceproject.service;

import java.util.Optional;

import cs544_2020_01_light_attendanceproject.domain.Timeslot;

/**
 * @author Adeola Adeleke
 * 
 * TimeSlot interface
 *
 */
public interface TimeSlotService {

	public Optional<Timeslot> get(String abbr);
	public Iterable<Timeslot> getAll();
	public Timeslot create(Timeslot tm);
	public Timeslot update(Timeslot tm);
	public void delete(String abbr);
}
