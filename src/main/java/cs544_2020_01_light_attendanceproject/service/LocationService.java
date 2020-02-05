package cs544_2020_01_light_attendanceproject.service;

import java.util.Optional;

import cs544_2020_01_light_attendanceproject.domain.Location;

public interface LocationService {

	public Optional<Location> findLocationById(long id);

	public Location findLocationByDescription(String description);

	public Iterable<Location> findAllLocations();

	public Location createLocation(Location location);

	public Location updateLocation(Location location);

	public void deleteLocation(Location location);

}
