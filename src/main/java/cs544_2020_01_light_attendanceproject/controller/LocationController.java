package cs544_2020_01_light_attendanceproject.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonView;
import cs544_2020_01_light_attendanceproject.domain.CourseOffering;
import org.hibernate.ObjectNotFoundException;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cs544_2020_01_light_attendanceproject.domain.Location;
import cs544_2020_01_light_attendanceproject.service.LocationService;

@RestController
@RequestMapping("/locations")
@Secured({ "ROLE_ADMIN" })
public class LocationController {

	@Autowired
	private LocationService locationService;

	@GetMapping
	@JsonView(Location.SummaryView.class)
	@ResponseStatus(HttpStatus.OK)
	public List<Location> retrieveAllLocations() {
		return (List<Location>) locationService.findAllLocations();
	}

	@GetMapping("/id/{id}")
	@JsonView(Location.DetailView.class)
	public Location retrieveLocationById(@PathVariable @Min(1) long id) {
		return locationService.findLocationById(id).orElseThrow(() -> new ObjectNotFoundException(Location.class, "Location not found by description."));
	}

	@GetMapping("/description/{description}")
	@JsonView(Location.DetailView.class)
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

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Location deleteLocation(@PathVariable long id) {
		return locationService.findLocationById(id).
				map(l -> {
					locationService.deleteLocation(l);
					return l;
				}).orElse(null);
	}

}
