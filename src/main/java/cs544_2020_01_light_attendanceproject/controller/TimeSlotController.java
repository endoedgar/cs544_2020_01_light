/**
 * 
 */
package cs544_2020_01_light_attendanceproject.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
    public ResponseEntity<Object> newTimeSlot(@RequestBody @Valid Timeslot timeSlot) {
	    timeSlotService.create(timeSlot);

        URI locationUrl = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(timeSlot.getAbbreviation())
                .toUri();

        return ResponseEntity.created(locationUrl).build();
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
    public Timeslot deleteTs(@PathVariable String abbr) {
	    return timeSlotService.get(abbr).map(ts -> {
            timeSlotService.delete(ts);
            return ts;
        }).orElse(null);
    }

	@Secured(value = { "ROLE_ADMIN" })
    @PutMapping("/{abbr}")
    public Timeslot updateTs(@RequestBody @Valid Timeslot newTs, @PathVariable String abbr) {
	    Timeslot oldTimeslot = timeSlotService.get(abbr).orElse(newTs);
	    oldTimeslot.setBeginTime(newTs.getBeginTime());
	    oldTimeslot.setDescription(newTs.getDescription());
	    oldTimeslot.setEndTime(newTs.getEndTime());
        return timeSlotService.update(oldTimeslot);
    }
	
	

}
