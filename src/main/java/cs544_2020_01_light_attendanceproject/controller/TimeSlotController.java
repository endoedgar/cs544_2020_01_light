/**
 * 
 */
package cs544_2020_01_light_attendanceproject.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cs544_2020_01_light_attendanceproject.domain.Timeslot;
import cs544_2020_01_light_attendanceproject.service.TimeSlotService;

/**
 * @author Adeola Adeleke
 *
 */

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {
	
	private TimeSlotService timeSlotService;

	/**
	 * @param timeSlotService
	 */
	@Autowired
	public TimeSlotController(TimeSlotService timeSlotService) {
		super();
		this.timeSlotService = timeSlotService;
	} 
	
	@Secured(value = { "ROLE_ADMIN" })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Timeslot newTimeSlot(@RequestBody @Valid Timeslot timeSlot) {
        return timeSlotService.create(timeSlot);
    }

	@Secured(value = { "ROLE_ADMIN", "ROLE_FACULTY" })
    @GetMapping
    public Iterable<Timeslot> fetchAllTs() {
        return timeSlotService.getAll();
    }

	@Secured(value = { "ROLE_ADMIN" })
    @GetMapping("/{abbr}")
    public Timeslot fetchTs(@PathVariable String abbr) {
        return timeSlotService.get(abbr).orElse(null);
    }

	@Secured(value = { "ROLE_ADMIN" })
    @DeleteMapping("/{abbr}")
    public void deleteTs(@PathVariable String abbr) {
        timeSlotService.delete(abbr);
    }

	@Secured(value = { "ROLE_ADMIN" })
    @PutMapping("/{abbr}")
    public Timeslot updateTs(@RequestBody @Valid Timeslot newTs, @PathVariable String abbr) {
        return timeSlotService.update(newTs);
    }
	
	

}
