package cs544_2020_01_light_attendanceproject.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import cs544_2020_01_light_attendanceproject.domain.User;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import cs544_2020_01_light_attendanceproject.domain.Location;
import cs544_2020_01_light_attendanceproject.service.LocationService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		return locationService.findLocationByDescription(description).orElseThrow(() -> new ObjectNotFoundException(Location.class, "Location not found by description."));
	}

	@PostMapping
	public ResponseEntity<Object> createLocation(@Valid @RequestBody Location location) {
		locationService.createLocation(location);

		URI locationUrl = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/id/{id}")
				.buildAndExpand(location.getId())
				.toUri();

		return ResponseEntity.created(locationUrl).build();
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Location updateLocation(@Valid @RequestBody Location location, @PathVariable @Min(1) long id) {
		Location oldLocation = locationService.findLocationById(id).orElse(location);
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
