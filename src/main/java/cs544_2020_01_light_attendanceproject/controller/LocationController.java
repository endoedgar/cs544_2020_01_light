package cs544_2020_01_light_attendanceproject.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cs544_2020_01_light_attendanceproject.domain.Location;
import cs544_2020_01_light_attendanceproject.service.LocationService;

@RestController
@RequestMapping("/locations")
@Secured({ "ROLE_ADMIN" })
public class LocationController {

	@Autowired
	private LocationService locationService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Location> retrieveAllLocations() {
		return (List<Location>) locationService.findAllLocations();
	}

	@GetMapping("/id/{id}")
	public Location retrieveLocationById(@PathVariable @Min(1) long id) {
		return locationService.findLocationById(id).get();
	}

	@GetMapping("/description/{description}")
	public Location findByDescription(@PathVariable String description) {
		return locationService.findLocationByDescription(description);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Location createLocation(@Valid @RequestBody Location location) {
		return locationService.createLocation(location);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Location updateLocation(@Valid @RequestBody Location location, @PathVariable @Min(1) long id) {
		Location oldLocation = locationService.findLocationById(id).get();
		oldLocation.setDescription(location.getDescription());
		return locationService.updateLocation(oldLocation);
	}

	@DeleteMapping("/locations/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Location deleteLocation(@PathVariable long id) {
		Location oldLocation = locationService.findLocationById(id).get();
		locationService.deleteLocation(oldLocation);
		return oldLocation;
	}

}
